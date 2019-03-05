package com.example.demo.service.impl;

import com.example.demo.entity.JobInfoVO;
import com.example.demo.entity.ScheduleJob;
import com.example.demo.job.QuartzJobFactory;
import com.example.demo.mapper.JobMapper;
import com.example.demo.service.ScheduleJobService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * job service
 * @author Jverson
 * @date Dec 9, 2017 1:10:47 PM
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

  @Autowired
  @Qualifier("scheduler")
  private Scheduler scheduler;

  @Autowired
  private JobMapper jobMapper;

  @Override
  public List<ScheduleJob> getAllJobList() throws SchedulerException {

    List<ScheduleJob> jobList = new ArrayList<>();
    GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
    Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
    for (JobKey jobKey : jobKeySet) {
      List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
      for (Trigger trigger : triggers) {
        ScheduleJob scheduleJob = new ScheduleJob();
        this.wrapScheduleJob(scheduleJob, scheduler, jobKey, trigger);
        jobList.add(scheduleJob);
      }
    }

    return jobList;
  }

  @Override
  public List<ScheduleJob> getRunningJobList() throws SchedulerException {

    List<JobExecutionContext> executingJobList = scheduler.getCurrentlyExecutingJobs();
    List<ScheduleJob> jobList = new ArrayList<>(executingJobList.size());
    for (JobExecutionContext executingJob : executingJobList) {
      ScheduleJob scheduleJob = new ScheduleJob();
      JobDetail jobDetail = executingJob.getJobDetail();
      JobKey jobKey = jobDetail.getKey();
      Trigger trigger = executingJob.getTrigger();
      this.wrapScheduleJob(scheduleJob, scheduler, jobKey, trigger);
      jobList.add(scheduleJob);
    }
    return jobList;
  }

//  public void saveOrUpdate(ScheduleJob scheduleJob) throws Exception {
//
//    Preconditions.checkNotNull(scheduleJob, "job is null");
//    if (StringUtils.isEmpty(scheduleJob.getJobId())) {
//      addJob(scheduleJob);
//    } else {
//      updateJobCronExpression(scheduleJob);
//    }
//  }

  @Override
  public void addJob(ScheduleJob scheduleJob) throws Exception {

    checkNotNull(scheduleJob);
    Preconditions.checkNotNull(StringUtils.isEmpty(scheduleJob.getCronExpression()),
        "CronExpression is null");

    TriggerKey triggerKey = TriggerKey
        .triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
    if (trigger != null) {
      throw new Exception("job already exists!");
    }

    // simulate job info db persist operation
    scheduleJob.setJobId(String.valueOf(QuartzJobFactory.jobList.size() + 1));
    QuartzJobFactory.jobList.add(scheduleJob);

    JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
        .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
    jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);

    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
        .cronSchedule(scheduleJob.getCronExpression());
    trigger = TriggerBuilder.newTrigger()
        .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
        .withSchedule(cronScheduleBuilder).build();

    scheduler.scheduleJob(jobDetail, trigger);
  }

  @Override
  public void updateJobCronExpression(ScheduleJob scheduleJob) throws SchedulerException {

    checkNotNull(scheduleJob);
    Preconditions.checkNotNull(StringUtils.isEmpty(scheduleJob.getCronExpression()),
        "CronExpression is null");

    TriggerKey triggerKey = TriggerKey
        .triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
        .cronSchedule(scheduleJob.getCronExpression());
    cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey)
        .withSchedule(cronScheduleBuilder).build();
    scheduler.rescheduleJob(triggerKey, cronTrigger);
  }

  @Override
  public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {

    checkNotNull(scheduleJob);
    JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    scheduler.pauseJob(jobKey);
  }

  @Override
  public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {

    checkNotNull(scheduleJob);
    JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    scheduler.resumeJob(jobKey);
  }

  @Override
  public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {

    checkNotNull(scheduleJob);
    JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    scheduler
        .pauseTrigger(TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup()));
    scheduler
        .unscheduleJob(TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup()));
    scheduler.deleteJob(jobKey);
  }

  @Override
  public void runJobOnce(ScheduleJob scheduleJob) throws SchedulerException {

    checkNotNull(scheduleJob);
    JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    scheduler.triggerJob(jobKey);
  }

  private void wrapScheduleJob(ScheduleJob scheduleJob, Scheduler scheduler, JobKey jobKey,
      Trigger trigger) throws SchedulerException {

    scheduleJob.setJobName(jobKey.getName());
    scheduleJob.setJobGroup(jobKey.getGroup());

    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
    ScheduleJob job = (ScheduleJob) jobDetail.getJobDataMap().get("scheduleJob");
    if (job != null) {
      scheduleJob.setDesc(job.getDesc());
      scheduleJob.setJobId(job.getJobId());
    }
    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
    scheduleJob.setJobStatus(triggerState.name());
    if (trigger instanceof CronTrigger) {
      CronTrigger cronTrigger = (CronTrigger) trigger;
      String cronExpression = cronTrigger.getCronExpression();
      scheduleJob.setCronExpression(cronExpression);
    }
  }

  private void checkNotNull(ScheduleJob scheduleJob) {

    Preconditions.checkNotNull(scheduleJob, "job is null");
    Preconditions.checkNotNull(StringUtils.isEmpty(scheduleJob.getJobName()), "jobName is null");
    Preconditions.checkNotNull(StringUtils.isEmpty(scheduleJob.getJobGroup()), "jobGroup is null");
  }

  @Override
  public SchedulerMetaData getMetaData() throws SchedulerException {

    return scheduler.getMetaData();
  }

  @Override
  public PageInfo<JobInfoVO> getJobAndTriggerDetails(int pageNum, int pageSize) {

    PageHelper.startPage(pageNum, pageSize);
    List<JobInfoVO> list = jobMapper.getJobAndTriggerDetails();
    PageInfo<JobInfoVO> page = new PageInfo<>(list);
    return page;
  }
}
