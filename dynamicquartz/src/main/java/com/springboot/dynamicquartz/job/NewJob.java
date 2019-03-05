package com.springboot.dynamicquartz.job;

import com.springboot.dynamicquartz.service.SampleService;
import java.time.LocalDateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
public class NewJob extends QuartzJobBean {

  @Autowired
  private SampleService sampleService;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

    sampleService.hello("New Job开始时间: " + LocalDateTime.now());
    try {
      Thread.sleep(11000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    sampleService.hello("New Job结束时间: " + LocalDateTime.now());
  }
}  