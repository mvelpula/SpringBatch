package com.wells.madhu.batchmodpocwork.configuration;

import com.wells.madhu.batchmodpocwork.entity.TradeHistory;
import com.wells.madhu.batchmodpocwork.entity.TradeHistoryDocument;
import com.wells.madhu.batchmodpocwork.entity.Trades;
import com.wells.madhu.batchmodpocwork.entity.TradesDocument;
import com.wells.madhu.batchmodpocwork.listener.JobCompletionNotificationListener;
import com.wells.madhu.batchmodpocwork.processor.TradeMongoProcessor;
import com.wells.madhu.batchmodpocwork.processor.TradeMysqlProcessor;
import com.wells.madhu.batchmodpocwork.reader.TradeMongoReader;
import com.wells.madhu.batchmodpocwork.reader.TradesMysqlReader;
import com.wells.madhu.batchmodpocwork.writer.TradesMongoWriter;
import com.wells.madhu.batchmodpocwork.writer.TradesMysqlWriter;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoConfig MongoConfig;

    @Autowired
    private TradesMysqlReader tradesMysqlReader;

    @Autowired
    private TradeMongoReader tradeMongoReader;

    @Autowired
    private TradesMysqlWriter tradesMysqlWriter;

    @Autowired
    private TradesMongoWriter tradesMongoWriter;

    @Autowired
    private TradeMysqlProcessor tradeMysqlProcessor;

    @Autowired
    private TradeMongoProcessor tradeMongoProcessor;

    @Autowired
    private JobCompletionNotificationListener jobCompletionNotificationListener;

    @Bean
    public Step migrateMysqlToMongoStep() {
        return stepBuilderFactory.get("migrateMysqlToMongoStep")
                .<Trades, TradesDocument>chunk(100)
                .reader(tradesMysqlReader.tradeMysqlItemReader())
                .processor(tradeMysqlProcessor)
                .writer(tradesMongoWriter.tradesMongoItemWriter())
                .build();
    }

    @Bean
    public Step migrateMongoToMysqlStep() {
        return stepBuilderFactory.get("migrateMongoToMysqlStep")
                .<TradesDocument, Trades>chunk(100)
                .reader(tradeMongoReader.TradeMongoItemReader())
                .processor(tradeMongoProcessor)
                .writer(tradesMysqlWriter.tradesMysqlItemWriter())
                .build();
    }

//    @Bean
//    public Job tradesMigrationJob(Step migrateMysqlToMongoStep) {
//        return jobBuilderFactory.get("tradesMigrationJob" + new Random().nextInt())
//                .listener(jobCompletionNotificationListener)
//                .start(migrateMysqlToMongoStep)
//                .build();
//    }

    @Bean
    public Job tradesMigrationJob(Step migrateMysqlToMongoStep, Step migrateMongoToMysqlStep) {
        return jobBuilderFactory.get("tradesMigrationJob" + new Random().nextInt())
                .listener(jobCompletionNotificationListener)
                .start(migrateMysqlToMongoStep)
                .next(migrateMongoToMysqlStep)
                .build();
    }
}
