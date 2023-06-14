package com.wells.madhu.batchmodpocwork.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Date;

@Document(collection = "trade_history")
@Getter
@Setter
public class TradeHistoryDocument {

    @Id
    private int tktNum;
    private String sysNm;
    private Date histTmstp;
    private String lanId;
    private String hist_txt;

    // Getters and setters
}
