package com.wells.madhu.batchmodpocwork.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "trade_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TradeHistory {

    @Id
    @Column(name = "tkt_num")
    private int tktNum;

    @Column(name = "sys_nm")
    private String sysNm;

    @Column(name = "hist_tmstp")
    private Date histTmstp;

    @Column(name = "lan_id")
    private String lanId;

    @Column(name = "hist_txt")
    private String hist_txt;

}
