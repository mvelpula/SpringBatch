package com.wells.madhu.batchmodpocwork.reader;

import com.wells.madhu.batchmodpocwork.entity.Trades;
import com.wells.madhu.batchmodpocwork.repository.TradeRepo;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class ItemReaderUtil implements ItemReader<Trades> {
    private FlatFileItemReader<Trades> reader;

    @Autowired
    TradeRepo tradeRepo;

    @Autowired
    DataSource dataSource;

    @Value("${data.source}")
    private String dataSourceType;


    public ItemReaderUtil() {
//        reader = new FlatFileItemReader<>();
//        reader.setResource(new ClassPathResource("data/trades.csv"));
//        reader.setLinesToSkip(1); // Skip header line
//        reader.setLineMapper(new DefaultLineMapper<Trades>() {{
//            setLineTokenizer(new DelimitedLineTokenizer() {{
//                setNames("tradeId","tradeName", "tradeDate");
//            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<Trades>() {{
//                setTargetType(Trades.class);
//            }});
//        }});
    }

    public ItemReader<Trades> reader() {

        JdbcCursorItemReader<Trades> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT trade_id, trade_name, trade_date FROM trades");
        reader.setRowMapper((resultSet, rowNum) -> {
            Trades trade = new Trades();
            trade.setTradeId(resultSet.getInt("trade_id"));
            trade.setTradeName(resultSet.getString("trade_name"));
            trade.setTradeDate(resultSet.getString("trade_date"));
            return trade;
        });
        return reader;
    }

    @BeforeStep
    public void open() {
        reader.open(new ExecutionContext());
    }


    @Override
    public Trades read() throws Exception {
        return reader.read();
    }
}

