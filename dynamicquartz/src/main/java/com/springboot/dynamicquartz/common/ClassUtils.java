package com.springboot.dynamicquartz.common;

import org.quartz.Job;

/**
 * @Author: niuhaijun
 * @Date: 2019-03-05 11:03
 * @Version 1.0
 */
public class ClassUtils {

  public static Class<? extends Job> getJobClass(String classname) throws Exception {

    return (Class<? extends Job>) Class.forName(classname);
  }

}
