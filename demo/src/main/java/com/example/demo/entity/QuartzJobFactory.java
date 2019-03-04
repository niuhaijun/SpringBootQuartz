package com.example.demo.entity;

import com.example.demo.service.SampleService;
import com.google.common.collect.Lists;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class QuartzJobFactory implements Job {

  public static List<ScheduleJob> jobList = Lists.newArrayList();

  static {
    for (int i = 0; i < 5; i++) {
      ScheduleJob job = new ScheduleJob();
      job.setJobId(String.valueOf(i));
      job.setJobName("job_name_" + i);
      if (i % 2 == 0) {
        job.setJobGroup("job_group_even");
      } else {
        job.setJobGroup("job_group_odd");
      }
      job.setJobStatus("1");
      job.setCronExpression(String.format("0/%s * * * * ?", (i + 1) * 5));
      job.setDesc("i am job number " + i);
      job.setInterfaceName("interface" + i);
      jobList.add(job);
    }
  }

  @Autowired
  private SampleService sampleService;

  // simulate data from db
  public static List<ScheduleJob> getInitAllJobs() {

    return jobList;
  }


  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap()
        .get("scheduleJob");
    String jobName = scheduleJob.getJobName();

    // execute task inner quartz system
    // spring bean can be @Autowired
    sampleService.hello(jobName);

    // use rabbit MQ to asynchronously notify the task execution in business system
    // sender.send(scheduleJob.getInterfaceName());

    // simulate time-consuming task
    if (jobName.equals("job_name_4") || jobName.equals("addjob")) {
      try {
        Thread.sleep(1000 * 60);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

}
