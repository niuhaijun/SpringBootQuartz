package com.springboot.dynamicquartz.job;

import com.springboot.dynamicquartz.service.SampleService;
import java.time.LocalDateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
public class HelloJob extends QuartzJobBean {

  @Autowired
  private SampleService sampleService;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

    String UUID = java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

    sampleService.hello("Hello Job开始时间: " + LocalDateTime.now() + "-> " + UUID);
    try {
      Thread.sleep(5 * 1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    sampleService.hello("Hello Job结束时间: " + LocalDateTime.now() + "-> " + UUID);
  }
}  
