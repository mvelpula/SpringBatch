package com.wells.madhu.batchmodpocwork.writer;

import com.wells.madhu.batchmodpocwork.entity.Trades;
import com.wells.madhu.batchmodpocwork.repository.TradeRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemWriterUtil implements ItemWriter<Trades> {
    @Autowired
    private TradeRepo tradeRepo;

    @Autowired
    private MongoOperations mongoOperations;

//    @Override
//    public void write(List<? extends Trades> trades) throws Exception {
//        tradeRepo.saveAll(trades);
//    }

    @Override
    public void write(List<? extends Trades> trades) throws Exception {
        List<Trades> tradesToSaveOrUpdate = new ArrayList<>();

        for (Trades trade : trades) {
            Trades existingTrade = tradeRepo.findById(trade.getTradeId()).orElse(null);
            if (existingTrade == null) {
                // No existing trade with the same identifier, save as a new entry
                tradesToSaveOrUpdate.add(trade);
              //  tradeRepo.save(trade);
            } else {
                // An existing trade with the same identifier is found, update the entry
                existingTrade.setTradeName(trade.getTradeName());
                existingTrade.setTradeDate(trade.getTradeDate());
                tradesToSaveOrUpdate.add(existingTrade);
               // tradeRepo.save(existingTrade);
            }
        }

        // Check if the "trades" collection exists, create it if not
        if (!mongoOperations.collectionExists("trades")) {
            mongoOperations.createCollection("trades");
        }
        mongoOperations.insert(tradesToSaveOrUpdate,"trades");
    }


}