package com.novartis.ecrs.batchJob;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Date;

public class ScheduleBatchJob implements Job{
    private static int count;
    public ScheduleBatchJob() {
        super();
    }

    public void execute(JobExecutionContext jobContext) throws JobExecutionException {
        System.out.println("--------------------------------------------------------------------");
               System.out.println("MyJob start: " + jobContext.getFireTime());
               JobDetail jobDetail = jobContext.getJobDetail();
               
               System.out.println("-----------Batch Job is called at----------"+new Date());
//               System.out.println("Example name is: " + jobDetail.getJobDataMap().getString("example"));       
//               System.out.println("MyJob end: " + jobContext.getJobRunTime() + ", key: " + jobDetail.getKey());
               System.out.println("MyJob next scheduled time: " + jobContext.getNextFireTime());
               System.out.println("--------------------------------------------------------------------");
                      
    }
}
