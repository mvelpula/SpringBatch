package com.wells.madhu.batchmodpocwork.reader;

import com.wells.madhu.batchmodpocwork.entity.TradeHistory;
import com.wells.madhu.batchmodpocwork.entity.Trades;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class TradesMysqlReader {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Trades> tradeMysqlItemReader() {
        JdbcCursorItemReader<Trades> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT trade_id, trade_name, trade_date FROM trades");
        reader.setRowMapper(new BeanPropertyRowMapper<>(Trades.class));
        return reader;
    }

    @Bean
    public JdbcCursorItemReader<TradeHistory> tradeHistoryMysqlItemReader() {
        JdbcCursorItemReader<TradeHistory> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT * FROM trade_history");
        reader.setRowMapper(new BeanPropertyRowMapper<>(TradeHistory.class));
        return reader;
    }
}