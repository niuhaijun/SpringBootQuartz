package com.example.demo.service.impl;

import com.example.demo.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by david on 2015-01-20.
 */
@Service
public class SampleServiceImpl implements SampleService {

  private static final Logger LOG = LoggerFactory.getLogger(SampleServiceImpl.class);

  @Override
  public void hello(String content) {

    LOG.info(content);
  }
}
