package com.novartis.ecrs.ui.bean;



import com.novartis.ecrs.batchJob.ScheduleBatchJob;

import javax.faces.event.ActionEvent;

import java.util.concurrent.CountDownLatch;
import com.novartis.ecrs.batchJob.ILatch;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import java.util.Date;
import java.util.Calendar;

import org.quartz.CronScheduleBuilder;

public class ScheduleBatchJobBean{
    private RichInputDate jobSubmitDateTime;

    public ScheduleBatchJobBean() {
        super();
    }

    public void submitJob(ActionEvent actionEvent) {
        try {
            Date inputDate = null;
            StringBuilder cronString = new StringBuilder("");
            Object dateValue = this.getJobSubmitDateTime().getValue();
            if(dateValue != null){
                inputDate = (Date) dateValue;
                Calendar cal = Calendar. getInstance();
                cal. setTime(inputDate);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH); // Note: zero based!
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                int second = cal.get(Calendar.SECOND);
                cronString.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append(day).append(" ").append(month+1).append(" ").append("?").append(" ").append(year);
                System.out.println(" Date "+day+" Month "+month+" Year "+year+" hour "+hour+" minute "+minute+" second "+second);
            }
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            Scheduler scheduler = schedFact.getScheduler();
            if(!scheduler.isStarted())
            scheduler.start();
            System.out.println("Scheduler started? "+ scheduler.isStarted());
            // define the job and tie it to our HelloJob class
            System.out.println("Scheduler name: "+ scheduler.getSchedulerName());
            System.out.println("Scheduler instanceId: "+ scheduler.getSchedulerInstanceId());
            JobDetail jobDetail = JobBuilder.newJob(ScheduleBatchJob.class).withIdentity("ADFJob", "ADFGroupOne").build();
//            JobBuilder jobBuilder = JobBuilder.newJob(ScheduleBatchJob.class);
//            JobDetail jobDetail = jobBuilder.usingJobData("example", "com.javacodegeeks.quartz.QuartzSchedulerExample")
//                                            .usingJobData(data)
//                                            .withIdentity("myJob", "group1")
//                                            .build();


            // Trigger the job to run now, and then every 40 seconds
//            Trigger trigger = TriggerBuilder.newTrigger()
//                                            .withIdentity("myTrigger", "group1")
//                                            .startNow()
//                                            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                                                                               .withRepeatCount(repeatCount)
//                                                                               .withIntervalInSeconds(2))
//                                            .build();
            //String cronexp = " 0 35 3 3 6 ? 2019";  
            String cronexp = cronString.toString();  
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("ADFTrigger", "ADFTriggerGroupOne").withSchedule(CronScheduleBuilder.cronSchedule(cronexp)).build();
            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(jobDetail, trigger);
//            System.out.println("All triggers executed. Shutdown scheduler");
//          //  TriggerState triggerState = scheduler.getTriggerState("ADFTrigger");
//            boolean waitForJobsToComplete = true;
//            scheduler.shutdown(waitForJobsToComplete);
//            System.out.println("Scheduler shutdown? "+ scheduler.isShutdown());
        } catch (SchedulerException se) {
            // TODO: Add catch code
            se.printStackTrace();
        } 
    }

    public void setJobSubmitDateTime(RichInputDate jobSubmitDateTime) {
        this.jobSubmitDateTime = jobSubmitDateTime;
    }

    public RichInputDate getJobSubmitDateTime() {
        return jobSubmitDateTime;
    }
}
