package com.example.demo.config;

import com.example.demo.spring.AutowiringSpringBeanJobFactory;
import java.io.IOException;
import java.util.Properties;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerConfig {

  private Logger _LOGGER = LoggerFactory.getLogger(getClass());

  @Bean
  public JobFactory jobFactory(ApplicationContext applicationContext) {

    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }

  @Bean(name = "SchedulerFactory")
  public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws Exception {

    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    // use specify jobFactory to create jobDetail
    factory.setJobFactory(jobFactory);
    factory.setQuartzProperties(quartzProperties());
    factory.afterPropertiesSet();
    return factory;
  }

  /**
   * 通过SchedulerFactoryBean获取Scheduler的实例
   */
  @Bean(name = "Scheduler")
  public Scheduler scheduler(JobFactory jobFactory) throws Exception {

    Scheduler scheduler = schedulerFactoryBean(jobFactory).getScheduler();
    scheduler.setJobFactory(jobFactory);
    return scheduler;
  }

  @Bean
  public Properties quartzProperties() throws IOException {

    PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
    //在quartz.properties中的属性被读取并注入后再初始化对象
    propertiesFactoryBean.afterPropertiesSet();
    return propertiesFactoryBean.getObject();
  }

  /**
   * quartz初始化监听器
   */
  @Bean
  public QuartzInitializerListener executorListener() {

    return new QuartzInitializerListener();
  }
}
