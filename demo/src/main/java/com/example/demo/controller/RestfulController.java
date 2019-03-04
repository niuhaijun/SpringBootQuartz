package com.example.demo.controller;

import com.example.demo.common.Message;
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
  public Message metaData() {

    Message message = Message.failure();
    try {
      SchedulerMetaData metaData = scheduleJobService.getMetaData();
      message = Message.success();
      message.setData(metaData);
    } catch (Exception e) {
      message.setMsg(e.getMessage());
      logger.error("metaData ex:", e);
    }
    return message;
  }

  @RequestMapping("/getAllJobs")
  public Message getAllJobs() {

    Message message = Message.failure();
    try {
      List<ScheduleJob> jobList = scheduleJobService.getAllJobList();
      message = Message.success();
      message.setData(jobList);
    } catch (Exception e) {
      message.setMsg(e.getMessage());
      logger.error("getAllJobs ex:", e);
    }
    return message;
  }

  @RequestMapping("/getRunningJobs")
  public Message getRunningJobs()  {

    Message message = Message.failure();
    try {
      List<ScheduleJob> jobList = scheduleJobService.getRunningJobList();
      message = Message.success();
      message.setData(jobList);
    } catch (Exception e) {
      message.setMsg(e.getMessage());
      logger.error("getRunningJobs ex:", e);
    }
    return message;
  }

  @RequestMapping(value = "/pauseJob")
  public Object pauseJob(ScheduleJob job) {

    logger.info("params, job = {}", job);
    Message message = Message.failure();
    try {
      scheduleJobService.pauseJob(job);
      message = Message.success();
    } catch (Exception e) {
      message.setMsg(e.getMessage());
      logger.error("pauseJob ex:", e);
    }
    return message;
  }

  @RequestMapping(value = "/resumeJob")
  public Object resumeJob(ScheduleJob job) {

    logger.info("params, job = {}", job);
    Message message = Message.failure();
    try {
      scheduleJobService.resumeJob(job);
      message = Message.success();
    } catch (Exception e) {
      message.setMsg(e.getMessage());
      logger.error("resumeJob ex:", e);
    }
    return message;
  }

  @RequestMapping(value = "/deleteJob")
  public Object deleteJob(ScheduleJob job) {

    logger.info("params, job = {}", job);
    Message message = Message.failure();
    try {
      scheduleJobService.deleteJob(job);
      message = Message.success();
    } catch (Exception e) {
      message.setMsg(e.getMessage());
      logger.error("deleteJob ex:", e);
    }
    return message;
  }

  @RequestMapping(value = "/runJob")
  public Object runJob(ScheduleJob job) {

    logger.info("params, job = {}", job);
    Message message = Message.failure();
    try {
      scheduleJobService.runJobOnce(job);
      message = Message.success();
    } catch (Exception e) {
      message.setMsg(e.getMessage());
      logger.error("runJob ex:", e);
    }
    return message;
  }

  @RequestMapping(value = "/saveOrUpdate")
  public Object saveOrUpdate(ScheduleJob job) {

    logger.info("params, job = {}", job);
    Message message = Message.failure();
    try {
      scheduleJobService.saveOrUpdate(job);
      message = Message.success();
    } catch (Exception e) {
      message.setMsg(e.getMessage());
      logger.error("updateCron ex:", e);
    }
    return message;
  }
}
