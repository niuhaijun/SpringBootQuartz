package com.example.demo.service;

import com.example.demo.entity.JobInfoVO;
import com.example.demo.entity.ScheduleJob;
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

  List<ScheduleJob> getAllJobList() throws SchedulerException;

  List<ScheduleJob> getRunningJobList() throws SchedulerException;

//  void saveOrUpdate(ScheduleJob scheduleJob) throws Exception;

  void addJob(ScheduleJob scheduleJob) throws Exception;

  void updateJobCronExpression(ScheduleJob scheduleJob) throws SchedulerException;


  void pauseJob(ScheduleJob scheduleJob) throws SchedulerException;

  void resumeJob(ScheduleJob scheduleJob) throws SchedulerException;

  void deleteJob(ScheduleJob scheduleJob) throws SchedulerException;

  void runJobOnce(ScheduleJob scheduleJob) throws SchedulerException;

  SchedulerMetaData getMetaData() throws SchedulerException;

  PageInfo<JobInfoVO> getJobAndTriggerDetails(int pageNum, int pageSize);
}
