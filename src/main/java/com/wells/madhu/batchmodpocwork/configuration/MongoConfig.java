package com.wells.madhu.batchmodpocwork.configuration;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import org.springframework.core.convert.converter.Converter;

import java.util.Collections;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        Converter<ObjectId, Integer> objectIdToIntegerConverter = new Converter<ObjectId, Integer>() {
            @Override
            public Integer convert(ObjectId objectId) {
                // Convert ObjectId to int as per your logic
                return objectId.hashCode();
            }
        };

        return new MongoCustomConversions(Collections.singletonList(objectIdToIntegerConverter));
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory, MappingMongoConverter mappingMongoConverter) {
        mappingMongoConverter.setCustomConversions(mongoCustomConversions());
        return new MongoTemplate(mongoDbFactory, mappingMongoConverter);
    }
}
