package com.example.demo.entity;

import java.io.Serializable;

/**
 * @Author: niuhaijun
 * @Date: 2019-03-04 17:41
 * @Version 1.0
 */
public class JobInfoVO implements Serializable {

  private String jobClassName;
  private String jobGroupName;
  private String cronExpression;

  private Integer pageNum;
  private Integer pageSize;

  public String getJobClassName() {

    return jobClassName;
  }

  public void setJobClassName(String jobClassName) {

    this.jobClassName = jobClassName;
  }

  public String getJobGroupName() {

    return jobGroupName;
  }

  public void setJobGroupName(String jobGroupName) {

    this.jobGroupName = jobGroupName;
  }

  public String getCronExpression() {

    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {

    this.cronExpression = cronExpression;
  }

  public Integer getPageNum() {

    return pageNum;
  }

  public void setPageNum(Integer pageNum) {

    this.pageNum = pageNum;
  }

  public Integer getPageSize() {

    return pageSize;
  }

  public void setPageSize(Integer pageSize) {

    this.pageSize = pageSize;
  }

  @Override
  public String toString() {

    return "JobInfoVO{" +
        "jobClassName='" + jobClassName + '\'' +
        ", jobGroupName='" + jobGroupName + '\'' +
        ", cronExpression='" + cronExpression + '\'' +
        '}';
  }
}
