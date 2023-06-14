package com.wells.madhu.batchmodpocwork.writer;

import com.wells.madhu.batchmodpocwork.entity.TradeHistoryDocument;
import com.wells.madhu.batchmodpocwork.entity.TradesDocument;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class TradesMongoWriter {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public MongoItemWriter<TradesDocument> tradesMongoItemWriter() {
        MongoItemWriter<TradesDocument> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("trades");
        return writer;
    }

    @Bean
    public MongoItemWriter<TradeHistoryDocument> tradeHistoryMongoItemWriter() {
        MongoItemWriter<TradeHistoryDocument> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("trade_history");
        return writer;
    }
}