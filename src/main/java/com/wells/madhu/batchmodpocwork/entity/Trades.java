package com.wells.madhu.batchmodpocwork.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trades {

    @Id
    @Column(name = "trade_id")
    private int tradeId;

    @Column(name = "trade_name")
    private String tradeName;

    @Column(name = "trade_date")
    private String tradeDate;

}
