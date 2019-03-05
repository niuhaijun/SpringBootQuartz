package com.example.demo.controller;

import com.example.demo.common.CommonResponse;
import com.example.demo.entity.ScheduleJob;
import com.example.demo.service.ScheduleJobService;
import java.util.List;
import org.quartz.SchedulerMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestfulController {

  private static Logger logger = LoggerFactory.getLogger(RestfulController.class);

  @Autowired
  private ScheduleJobService scheduleJobService;

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
      List<ScheduleJob> jobList = scheduleJobService.getAllJobList();
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
      List<ScheduleJob> jobList = scheduleJobService.getRunningJobList();
      commonResponse = CommonResponse.success();
      commonResponse.setData(jobList);
    } catch (Exception e) {
      commonResponse.setMsg(e.getMessage());
      logger.error("getRunningJobs ex:", e);
    }
    return commonResponse;
  }

  @RequestMapping(value = "/pauseJob")
  public CommonResponse pauseJob(ScheduleJob job) {

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
  public CommonResponse resumeJob(ScheduleJob job) {

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
  public CommonResponse deleteJob(ScheduleJob job) {

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
  public CommonResponse runJob(ScheduleJob job) {

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

  @RequestMapping(value = "/saveOrUpdate")
  public CommonResponse saveOrUpdate(ScheduleJob job) {

    logger.info("params, job = {}", job);
    CommonResponse commonResponse = CommonResponse.failure();
    try {
      scheduleJobService.saveOrUpdate(job);
      commonResponse = CommonResponse.success();
    } catch (Exception e) {
      commonResponse.setMsg(e.getMessage());
      logger.error("updateCron ex:", e);
    }
    return commonResponse;
  }
}
