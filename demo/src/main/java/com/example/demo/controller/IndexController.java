package com.example.demo.controller;

import com.example.demo.entity.ScheduleJob;
import com.example.demo.service.ScheduleJobService;
import java.util.List;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

  @Autowired
  private ScheduleJobService scheduleJobService;

  @RequestMapping("/index")
  public String index(Model model) throws SchedulerException {

    List<ScheduleJob> jobList = scheduleJobService.getAllJobList();
    model.addAttribute("jobs", jobList);
    return "index";
  }
}
