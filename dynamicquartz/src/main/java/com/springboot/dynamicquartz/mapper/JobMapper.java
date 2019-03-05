package com.springboot.dynamicquartz.mapper;

import com.springboot.dynamicquartz.entity.JobInfoVO;
import java.util.List;

public interface JobMapper {

  List<JobInfoVO> getJobAndTriggerDetails();
}
