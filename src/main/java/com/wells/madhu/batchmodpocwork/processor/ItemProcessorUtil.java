package com.wells.madhu.batchmodpocwork.processor;

import com.wells.madhu.batchmodpocwork.entity.Trades;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessorUtil implements ItemProcessor<Trades, Trades> {

    @Override
    public Trades process(Trades trades) throws Exception {
        // Perform any data transformations or business logic here
        trades.setTradeName(trades.getTradeName() + "-"+trades.getTradeDate());
        return trades;
    }
}
