package com.example.demo.mapper;

import com.example.demo.entity.JobInfoVO;
import java.util.List;

public interface JobAndTriggerMapper {

  List<JobInfoVO> getJobAndTriggerDetails();
}
