package com.novartis.ecrs.ui.bean;



import com.novartis.ecrs.batchJob.ScheduleBatchJob;

import javax.faces.event.ActionEvent;

import java.util.concurrent.CountDownLatch;
import com.novartis.ecrs.batchJob.ILatch;

import oracle.adf.view.rich.component.rich.RichPopup;
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

import java.util.Map;

import oracle.adf.share.ADFContext;

import org.quartz.CronScheduleBuilder;

public class ScheduleBatchJobBean{
    private RichInputDate jobSubmitDateTime;
    private Boolean isScheduleRunning;
    private RichPopup stopScheduleProcessPopup;

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
                    }
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            Scheduler scheduler = schedFact.getScheduler();
            if(!scheduler.isStarted())
            scheduler.start();
            JobDetail jobDetail = JobBuilder.newJob(ScheduleBatchJob.class).withIdentity("ADFJob", "ADFGroupOne").build();

            String cronexp = cronString.toString();  
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("ADFTrigger", "ADFTriggerGroupOne").withSchedule(CronScheduleBuilder.cronSchedule(cronexp)).build();
            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(jobDetail, trigger);

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

    public void submitJobEveryMin(ActionEvent actionEvent) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        Object scheduledValue = pageFlowScope.get("scheduledValue");
        
        String start = "0 0/";
        String last = " * 1/1 * ? *";
        String cronString = start.concat(scheduledValue.toString()).concat(last);
        
        try{
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = schedFact.getScheduler();
        if(!scheduler.isStarted())
        scheduler.start();
        JobDetail jobDetail = JobBuilder.newJob(ScheduleBatchJob.class).withIdentity("ADFJob", "ADFGroupOne").build();
        String cronexp = cronString.toString();  
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("ADFTrigger", "ADFTriggerGroupOne").withSchedule(CronScheduleBuilder.cronSchedule(cronexp)).build();
        scheduler.scheduleJob(jobDetail, trigger);
        String processMsg = "Scheduled batch job to run for every ".concat(scheduledValue.toString()).concat(" minutes. Use 'Stop Scheduler' button to reschedule job.");
        Map applicationScope = adfCtx.getApplicationScope();
        applicationScope.put("processRunningText",processMsg);
        } catch (SchedulerException se) {
            se.printStackTrace();
        } 
    }

    public void submitJobEveryDay(ActionEvent actionEvent) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        Object scheduledValue = pageFlowScope.get("scheduledValue");
        Object scheduledHours = pageFlowScope.get("scheduledHours");
        Object scheduledMinitues = pageFlowScope.get("scheduledMinitues");
        String start = "0 ";
        String middle = "1/";
        String last = " * ? *";
        
        //String cronString = "0 14 12 1/8 * ? *";
        String cronString = start.concat(scheduledMinitues.toString())
                                 .concat(" ")
                                 .concat(scheduledHours.toString())
                                 .concat(" ")
                                 .concat(middle)
                                 .concat(scheduledValue.toString())
                                 .concat(last);

        try{
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = schedFact.getScheduler();
        if(!scheduler.isStarted())
        scheduler.start();

        JobDetail jobDetail = JobBuilder.newJob(ScheduleBatchJob.class).withIdentity("ADFJob", "ADFGroupOne").build();
        String cronexp = cronString.toString();  
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("ADFTrigger", "ADFTriggerGroupOne").withSchedule(CronScheduleBuilder.cronSchedule(cronexp)).build();
        scheduler.scheduleJob(jobDetail, trigger);
        String processMsg = "Scheduled batch job to run every day at ".concat(scheduledHours.toString()).concat(":").concat(scheduledMinitues.toString()).concat(" . Use 'Stop Scheduler' button to reschedule job.");
        Map applicationScope = adfCtx.getApplicationScope();
        applicationScope.put("processRunningText",processMsg);
            } catch (SchedulerException se) {
                se.printStackTrace();
            } 
    }

    public void setIsScheduleRunning(Boolean isScheduleRunning) {
        this.isScheduleRunning = isScheduleRunning;
    }

    public Boolean getIsScheduleRunning() {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler;
        Boolean value = false;
        try {
            scheduler = schedFact.getScheduler();
            value = scheduler.isStarted();
        } catch (SchedulerException e) {
        }
 
        return value;
    }

    public void stopScheduleProcess(ActionEvent actionEvent) {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler;
        try {
            scheduler = schedFact.getScheduler();
            if(scheduler.isStarted())
            scheduler.shutdown();
            this.getStopScheduleProcessPopup().hide();
        } catch (SchedulerException e) {
        }

    }

    public void setStopScheduleProcessPopup(RichPopup stopScheduleProcessPopup) {
        this.stopScheduleProcessPopup = stopScheduleProcessPopup;
    }

    public RichPopup getStopScheduleProcessPopup() {
        return stopScheduleProcessPopup;
    }

    public void closePopup(ActionEvent actionEvent) {
        this.getStopScheduleProcessPopup().hide();
    }
}
