package com.wells.madhu.batchmodpocwork.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trades")
@Getter
@Setter
public class TradesDocument {
    @Id
    private int tradeId;
    private String tradeName;
    private String tradeDate;

    // Getters and setters
}
