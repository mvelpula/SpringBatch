package com.wells.madhu.batchmodpocwork.listener;


import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Clean up MongoDB collections
        // Code for cleaning up MongoDB collections
    }
}