package com.springboot.dynamicquartz.service;

import com.springboot.dynamicquartz.entity.JobInfoVO;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;

/**
 * @Author: niuhaijun
 * @Date: 2019-03-05 13:59
 * @Version 1.0
 */
public interface ScheduleJobService {

  List<JobInfoVO> getAllJobList() throws SchedulerException;

  List<JobInfoVO> getRunningJobList() throws SchedulerException;

  void addJob(JobInfoVO scheduleJob) throws Exception;

  void updateJobCronExpression(JobInfoVO scheduleJob) throws SchedulerException;

  void pauseJob(JobInfoVO scheduleJob) throws SchedulerException;

  void resumeJob(JobInfoVO scheduleJob) throws SchedulerException;

  void deleteJob(JobInfoVO scheduleJob) throws SchedulerException;

  void runJobOnce(JobInfoVO scheduleJob) throws SchedulerException;

  SchedulerMetaData getMetaData() throws SchedulerException;

  PageInfo<JobInfoVO> getJobAndTriggerDetails(int pageNum, int pageSize);
}
