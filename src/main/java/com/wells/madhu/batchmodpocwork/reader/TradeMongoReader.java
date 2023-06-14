package com.wells.madhu.batchmodpocwork.reader;

import com.wells.madhu.batchmodpocwork.entity.TradeHistoryDocument;
import com.wells.madhu.batchmodpocwork.entity.TradesDocument;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

@Configuration
public class TradeMongoReader {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public MongoItemReader<TradesDocument> TradeMongoItemReader() {
        MongoItemReader<TradesDocument> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setTargetType(TradesDocument.class);
        reader.setCollection("trades");
        reader.setQuery(new Query().with(Sort.by(Sort.Direction.ASC, "trade_id"))); // Set the sort criteria in the Query object
        return reader;
    }

    @Bean
    public MongoItemReader<TradeHistoryDocument> TradeHistoryMongoItemReader() {
        MongoItemReader<TradeHistoryDocument> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setTargetType(TradeHistoryDocument.class);
        reader.setCollection("trades_history");
        reader.setQuery(new Query().with(Sort.by(Sort.Direction.ASC, "id"))); // Set the sort criteria in the Query object
        return reader;
    }
}