SELECT qrtz_job_details.JOB_NAME,
       qrtz_job_details.JOB_GROUP,
       qrtz_job_details.JOB_CLASS_NAME,
       qrtz_triggers.TRIGGER_NAME,
       qrtz_triggers.TRIGGER_GROUP,
       qrtz_cron_triggers.CRON_EXPRESSION,
       qrtz_cron_triggers.TIME_ZONE_ID
FROM QRTZ_JOB_DETAILS qrtz_job_details
       JOIN QRTZ_TRIGGERS qrtz_triggers
       JOIN QRTZ_CRON_TRIGGERS qrtz_cron_triggers
            ON qrtz_job_details.JOB_NAME = qrtz_triggers.JOB_NAME
              AND qrtz_triggers.TRIGGER_NAME = qrtz_cron_triggers.TRIGGER_NAME
              AND qrtz_triggers.TRIGGER_GROUP = qrtz_cron_triggers.TRIGGER_GROUP;


SELECT QRTZ_JOB_DETAILS.JOB_NAME          AS jobName,
       QRTZ_JOB_DETAILS.JOB_GROUP         AS jobGroup,
       QRTZ_JOB_DETAILS.JOB_CLASS_NAME    AS jobClass,
       QRTZ_TRIGGERS.DESCRIPTION          AS jobDescription,
       QRTZ_TRIGGERS.TRIGGER_NAME         AS triggerName,
       QRTZ_TRIGGERS.TRIGGER_GROUP        AS triggerGroup,
       QRTZ_TRIGGERS.START_TIME           AS startTime,
       QRTZ_TRIGGERS.END_TIME             AS endTime,
       QRTZ_TRIGGERS.TRIGGER_STATE        AS triggerState,
       QRTZ_CRON_TRIGGERS.CRON_EXPRESSION AS cronExpression,
       QRTZ_CRON_TRIGGERS.TIME_ZONE_ID    AS timeZoneId
FROM QRTZ_JOB_DETAILS
       JOIN QRTZ_TRIGGERS
       JOIN QRTZ_CRON_TRIGGERS ON QRTZ_JOB_DETAILS.JOB_NAME = QRTZ_TRIGGERS.JOB_NAME
  AND QRTZ_TRIGGERS.TRIGGER_NAME = QRTZ_CRON_TRIGGERS.TRIGGER_NAME
  AND QRTZ_TRIGGERS.TRIGGER_GROUP = QRTZ_CRON_TRIGGERS.TRIGGER_GROUP;