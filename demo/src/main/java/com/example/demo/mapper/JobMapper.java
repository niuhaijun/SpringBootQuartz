package com.example.demo.mapper;

import com.example.demo.entity.JobInfoVO;
import java.util.List;

public interface JobMapper {

  List<JobInfoVO> getJobAndTriggerDetails();
}
