package com.wells.madhu.batchmodpocwork.writer;

import com.wells.madhu.batchmodpocwork.entity.TradeHistory;
import com.wells.madhu.batchmodpocwork.entity.Trades;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TradesMysqlWriter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<Trades> tradesMysqlItemWriter() {
        JdbcBatchItemWriter<Trades> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
       // writer.setSql("INSERT INTO trades (trade_id, trade_name, trade_date) VALUES (:tradeId, :tradeName, :tradeDate)");
        writer.setSql("INSERT INTO trades (trade_id, trade_name, trade_date) " +
                "VALUES (:tradeId, :tradeName, :tradeDate) " +
                "ON DUPLICATE KEY UPDATE trade_name = VALUES(trade_name), trade_date = VALUES(trade_date)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public JdbcBatchItemWriter<TradeHistory> tradeHistoryMysqlItemWriter() {
        JdbcBatchItemWriter<TradeHistory> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        // writer.setSql("INSERT INTO trades (trade_id, trade_name, trade_date) VALUES (:tradeId, :tradeName, :tradeDate)");
        writer.setSql("INSERT INTO trade_history (tkt_num, sys_nm, hist_tmstp, lan_id, hist_txt) " +
                "VALUES (:tktNum, :sysNm, :histTmstp, :lanId, :histTxt) " +
                "ON DUPLICATE KEY UPDATE sys_nm = VALUES(sys_nm), hist_txt = VALUES(hist_txt)");
        writer.setDataSource(dataSource);
        return writer;
    }
}