package com.wells.madhu.batchmodpocwork.configuration;

import com.wells.madhu.batchmodpocwork.entity.Trades;
import com.wells.madhu.batchmodpocwork.listener.MyStepListener;
import com.wells.madhu.batchmodpocwork.processor.ItemProcessorUtil;
import com.wells.madhu.batchmodpocwork.reader.ItemReaderUtil;
import com.wells.madhu.batchmodpocwork.repository.TradeRepo;
import com.wells.madhu.batchmodpocwork.writer.ItemWriterUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

//@Configuration
//@EnableBatchProcessing
public class BatchConfig2 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private TradeRepo tradeRepo;

    @Autowired
    private ItemReaderUtil itemReaderUtil;

    @Autowired
    private ItemProcessorUtil itemProcessorUtil;

    @Autowired
    private ItemWriterUtil itemWriterUtil;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private JobLocator jobLocator;

    @Autowired
    private JobOperator jobOperator;


    @Bean
    public Job importJob(JobBuilderFactory jobBuilderFactory, Step importStep) {
        return jobBuilderFactory.get("importJob-" + new Random().nextInt())
                .incrementer(new RunIdIncrementer())
                .start(importStep)
                .build();
    }

    @Bean
    public Step importStep(StepBuilderFactory stepBuilderFactory, ItemReader<Trades> reader,
                           ItemProcessor<Trades, Trades> processor, ItemWriter<Trades> writer) {
        return stepBuilderFactory.get("importStep")
                .<Trades, Trades>chunk(10)
                .reader(itemReaderUtil.reader())
                .processor(itemProcessorUtil)
                .writer(itemWriterUtil)
                .faultTolerant()
                .skipLimit(10)
                .skip(Exception.class)
                .listener(new MyStepListener()) // Custom listener to handle restart logic
                .build();
    }
//
//    public void runJob() {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("timestamp", System.currentTimeMillis())
//                .toJobParameters();
//
//        try {
//            JobExecution jobExecution = jobLauncher.run(importJob, jobParameters);
//
//            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
//                // Job completed successfully
//            } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
//                // Job failed
//            } else if (jobExecution.getStatus() == BatchStatus.STOPPED) {
//                // Job stopped
//            }
//        } catch (JobExecutionException e) {
//            // Exception occurred during job execution
//        }
//
//    }

//    public JobExecution restartJob(long executionId) throws JobExecutionException, NoSuchJobException {
//        JobInstance jobInstance = jobExplorer.getJobExecution(executionId).getJobInstance();
//        String jobName = jobInstance.getJobName();
//        JobParameters jobParameters = jobInstance.ge();
//        return jobOperator.restart(executionId);
//    }

}
