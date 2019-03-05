package com.example.demo.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * @Author: niuhaijun
 * @Date: 2019-03-05 12:59
 * @Version 1.0
 */
@Data
public class JobInfoVO implements Serializable {

  private static final Long serialVersionUID = -12654128415L;

  /** 任务ID */
  private String jobId;

  /** 任务名称 */
  private String jobName;
  /** 任务分组 */
  private String jobGroup;
  /** 任务执行方法 */
  private String jobClass;
  /** 任务描述 */
  private String jobDescription;
  /** 触发器名称 */
  private String triggerName;
  /** 触发器分组 */
  private String triggerGroup;
  /** 开始时间 */
  private Long startTime;
  /** 结束时间 */
  private Long endTime;
  /** 触发器状态 */
  private String triggerState;
  /** cron 表达式 */
  private String cronExpression;
  /** 时区 */
  private String timeZoneId;
  /** 分页号 */
  private Integer pageNum;
  /** 页面数据量 */
  private Integer pageSize;
}
