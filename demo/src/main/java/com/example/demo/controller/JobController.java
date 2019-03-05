package com.example.demo.controller;

import com.example.demo.common.ClassUtils;
import com.example.demo.entity.JobAndTrigger;
import com.example.demo.entity.JobInfoVO;
import com.example.demo.job.BaseJob;
import com.example.demo.service.IJobAndTriggerService;
import com.github.pagehelper.PageInfo;
import java.util.HashMap;
import java.util.Map;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: niuhaijun
 * @Date: 2019-03-04 17:37
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/job")
public class JobController {

  private static final Logger LOGGGER = LoggerFactory.getLogger(JobController.class);

  @Autowired
  private IJobAndTriggerService iJobAndTriggerService;

  @Autowired
  @Qualifier("scheduler")
  private Scheduler scheduler;

//  public BaseJob getClass(String classname) throws Exception {
//
//    Class<?> class1 = Class.forName(classname);
//    return (BaseJob) class1.newInstance();
//  }

  @PostMapping(value = "/addjob")
  public void addJob(JobInfoVO jobInfoVO) throws Exception {

    String jobGroupName = jobInfoVO.getJobGroupName();
    String jobClassName = jobInfoVO.getJobClassName();
    String cronExpression = jobInfoVO.getCronExpression();

    // 启动调度器
    scheduler.start();

    //构建job信息
    JobDetail jobDetail = JobBuilder.newJob(ClassUtils.getJobClass(jobClassName))
        .withIdentity(jobClassName, jobGroupName).build();

    //表达式调度构建器(即任务执行的时间)
    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

    //按新的cronExpression表达式构建一个新的trigger
    CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
        .withSchedule(scheduleBuilder).build();

    try {
      scheduler.scheduleJob(jobDetail, trigger);

    } catch (SchedulerException e) {
      System.out.println("创建定时任务失败" + e);
      throw new Exception("创建定时任务失败");
    }
  }

  @PostMapping(value = "/pausejob")
  public void pauseJob(JobInfoVO jobInfoVO) throws Exception {

    String jobGroupName = jobInfoVO.getJobGroupName();
    String jobClassName = jobInfoVO.getJobClassName();

    scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
  }

  @PostMapping(value = "/resumejob")
  public void resumeJob(JobInfoVO jobInfoVO) throws Exception {

    String jobGroupName = jobInfoVO.getJobGroupName();
    String jobClassName = jobInfoVO.getJobClassName();

    scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
  }

  @PostMapping(value = "/reschedulejob")
  public void rescheduleJob(JobInfoVO jobInfoVO) throws Exception {

    String jobGroupName = jobInfoVO.getJobGroupName();
    String jobClassName = jobInfoVO.getJobClassName();
    String cronExpression = jobInfoVO.getCronExpression();

    try {
      TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
      // 表达式调度构建器
      CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

      CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

      // 按新的cronExpression表达式重新构建trigger
      trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder)
          .build();

      // 按新的trigger重新设置job执行
      scheduler.rescheduleJob(triggerKey, trigger);
    } catch (SchedulerException e) {
      System.out.println("更新定时任务失败" + e);
      throw new Exception("更新定时任务失败");
    }
  }

  @PostMapping(value = "/deletejob")
  public void deleteJob(JobInfoVO jobInfoVO) throws Exception {

    String jobGroupName = jobInfoVO.getJobGroupName();
    String jobClassName = jobInfoVO.getJobClassName();

    scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
    scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
    scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
  }

  @GetMapping(value = "/queryjob")
  public Map<String, Object> queryJob(JobInfoVO jobInfoVO) {

    Integer pageNum = jobInfoVO.getPageNum();
    Integer pageSize = jobInfoVO.getPageSize();

    PageInfo<JobAndTrigger> jobAndTrigger = iJobAndTriggerService
        .getJobAndTriggerDetails(pageNum, pageSize);
    Map<String, Object> map = new HashMap<>();
    map.put("JobAndTrigger", jobAndTrigger);
    map.put("number", jobAndTrigger.getTotal());
    return map;
  }
}
