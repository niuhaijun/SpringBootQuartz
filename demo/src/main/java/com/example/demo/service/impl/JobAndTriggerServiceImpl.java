package com.example.demo.service.impl;

import com.example.demo.entity.JobAndTrigger;
import com.example.demo.mapper.JobAndTriggerMapper;
import com.example.demo.service.IJobAndTriggerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JobAndTriggerServiceImpl implements IJobAndTriggerService {

  @Autowired
  private JobAndTriggerMapper jobAndTriggerMapper;

  public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {

    PageHelper.startPage(pageNum, pageSize);
    List<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
    PageInfo<JobAndTrigger> page = new PageInfo<JobAndTrigger>(list);
    return page;
  }

}