package com.example.demo.job;

import java.time.LocalDateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewJob implements BaseJob {

  private static Logger _log = LoggerFactory.getLogger(NewJob.class);

  @Override
  public void execute(JobExecutionContext context)
      throws JobExecutionException {

    _log.info("New Job执行时间: " + LocalDateTime.now());
  }
}  