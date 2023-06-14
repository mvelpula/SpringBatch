package com.wells.madhu.batchmodpocwork.configuration;

import com.wells.madhu.batchmodpocwork.entity.Trades;
import com.wells.madhu.batchmodpocwork.repository.TradeRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

//@Configuration
//@EnableBatchProcessing
public class BatchConfig1 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private TradeRepo tradeRepo;

    @Bean
    public ItemReader<Trades> reader() {
        FlatFileItemReader<Trades> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("data/trades.csv"));
        reader.setLineMapper(new DefaultLineMapper<Trades>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("tradeName", "tradeDate");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Trades>() {{
                setTargetType(Trades.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<Trades, Trades> processor() {
        return trade -> {
            // Perform some processing on the trade object
            trade.setTradeName(trade.getTradeName() + "->" + trade.getTradeDate());
            return trade;
        };
    }

    @Bean
    public ItemWriter<Trades> writer() {
        return trades -> {
            // Write the items to a database
            for (Trades trade : trades) {
                // Perform database write operation
                tradeRepo.save(trade);
                System.out.println("Writing item: " + trade.getTradeName());
            }
        };
    }

    @Bean
    public Step step(ItemReader<Trades> reader, ItemProcessor<Trades, Trades> processor,
                     ItemWriter<Trades> writer) {
        return stepBuilderFactory.get("step")
                .<Trades, Trades>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(Step step) {
        return jobBuilderFactory.get("job")
                .flow(step)
                .end()
                .build();
    }

    public void restartJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(job(null), jobParameters);
    }
}
