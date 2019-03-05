package com.example.demo.controller;

import com.example.demo.common.CommonResponse;
import com.example.demo.entity.JobInfoVO;
import com.example.demo.entity.ScheduleJob;
import com.example.demo.service.ScheduleJobService;
import com.github.pagehelper.PageInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.quartz.SchedulerMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: niuhaijun
 * @Date: 2019-03-04 17:37
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/job")
public class ScheduleJobController {

  private static final Logger logger = LoggerFactory.getLogger(ScheduleJobController.class);

  /**  汇总的方法          */
  @Autowired
  private ScheduleJobService scheduleJobService;

  @GetMapping(value = "/queryJob")
  public Map<String, Object> queryJob(JobInfoVO jobInfoVO) {

    Integer pageNum = jobInfoVO.getPageNum();
    Integer pageSize = jobInfoVO.getPageSize();

    PageInfo<JobInfoVO> pagedJobInfo = scheduleJobService
        .getJobAndTriggerDetails(pageNum, pageSize);
    Map<String, Object> map = new HashMap<>();
    map.put("pagedJobInfo", pagedJobInfo);
    map.put("number", pagedJobInfo.getTotal());
    return map;
  }

  @RequestMapping("/metaData")
  public CommonResponse metaData() {

    CommonResponse commonResponse = CommonResponse.failure();
    try {
      SchedulerMetaData metaData = scheduleJobService.getMetaData();
      commonResponse = CommonResponse.success();
      commonResponse.setData(metaData);
    } catch (Exception e) {
      commonResponse.setMsg(e.getMessage());
      logger.error("metaData ex:", e);
    }
    return commonResponse;
  }

  @RequestMapping("/getAllJobs")
  public CommonResponse getAllJobs() {

    CommonResponse commonResponse = CommonResponse.failure();
    try {
      List<JobInfoVO> jobList = scheduleJobService.getAllJobList();
      commonResponse = CommonResponse.success();
      commonResponse.setData(jobList);
    } catch (Exception e) {
      commonResponse.setMsg(e.getMessage());
      logger.error("getAllJobs ex:", e);
    }
    return commonResponse;
  }

  @RequestMapping("/getRunningJobs")
  public CommonResponse getRunningJobs() {

    CommonResponse commonResponse = CommonResponse.failure();
    try {
      List<JobInfoVO> jobList = scheduleJobService.getRunningJobList();
      commonResponse = CommonResponse.success();
      commonResponse.setData(jobList);
    } catch (Exception e) {
      commonResponse.setMsg(e.getMessage());
      logger.error("getRunningJobs ex:", e);
    }
    return commonResponse;
  }

  @RequestMapping(value = "/pauseJob")
  public CommonResponse pauseJob(JobInfoVO job) {

    logger.info("params, job = {}", job);
    CommonResponse commonResponse = CommonResponse.failure();
    try {
      scheduleJobService.pauseJob(job);
      commonResponse = CommonResponse.success();
    } catch (Exception e) {
      commonResponse.setMsg(e.getMessage());
      logger.error("pauseJob ex:", e);
    }
    return commonResponse;
  }

  @RequestMapping(value = "/resumeJob")
  public CommonResponse resumeJob(JobInfoVO job) {

    logger.info("params, job = {}", job);
    CommonResponse commonResponse = CommonResponse.failure();
    try {
      scheduleJobService.resumeJob(job);
      commonResponse = CommonResponse.success();
    } catch (Exception e) {
      commonResponse.setMsg(e.getMessage());
      logger.error("resumeJob ex:", e);
    }
    return commonResponse;
  }

  @RequestMapping(value = "/deleteJob")
  public CommonResponse deleteJob(JobInfoVO job) {

    logger.info("params, job = {}", job);
    CommonResponse commonResponse = CommonResponse.failure();
    try {
      scheduleJobService.deleteJob(job);
      commonResponse = CommonResponse.success();
    } catch (Exception e) {
      commonResponse.setMsg(e.getMessage());
      logger.error("deleteJob ex:", e);
    }
    return commonResponse;
  }

  @RequestMapping(value = "/runJob")
  public CommonResponse runJob(JobInfoVO job) {

    logger.info("params, job = {}", job);
    CommonResponse commonResponse = CommonResponse.failure();
    try {
      scheduleJobService.runJobOnce(job);
      commonResponse = CommonResponse.success();
    } catch (Exception e) {
      commonResponse.setMsg(e.getMessage());
      logger.error("runJob ex:", e);
    }
    return commonResponse;
  }

  @RequestMapping(value = "/addJob")
  public CommonResponse addJob(JobInfoVO job) {

    logger.info("params, job = {}", job);
    CommonResponse commonResponse = CommonResponse.failure();
    try {
      scheduleJobService.addJob(job);
      commonResponse = CommonResponse.success();
    } catch (Exception e) {
      commonResponse.setMsg(e.getMessage());
      logger.error("updateCron ex:", e);
    }
    return commonResponse;
  }

  @RequestMapping(value = "/updateJobCronExpression")
  public CommonResponse updateJobCronExpression(JobInfoVO job) {

    logger.info("params, job = {}", job);
    CommonResponse commonResponse = CommonResponse.failure();
    try {
      scheduleJobService.updateJobCronExpression(job);
      commonResponse = CommonResponse.success();
    } catch (Exception e) {
      commonResponse.setMsg(e.getMessage());
      logger.error("updateCron ex:", e);
    }
    return commonResponse;
  }
}
