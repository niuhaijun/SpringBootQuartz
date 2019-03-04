package com.example.demo.job;

import java.time.LocalDateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements BaseJob {

  private static Logger _log = LoggerFactory.getLogger(HelloJob.class);

  @Override
  public void execute(JobExecutionContext context)
      throws JobExecutionException {

    _log.info("Hello Job执行时间: " + LocalDateTime.now());
  }
}  
