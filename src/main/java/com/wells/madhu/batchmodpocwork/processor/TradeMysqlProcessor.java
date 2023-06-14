package com.wells.madhu.batchmodpocwork.processor;

import com.wells.madhu.batchmodpocwork.entity.TradeHistory;
import com.wells.madhu.batchmodpocwork.entity.TradeHistoryDocument;
import com.wells.madhu.batchmodpocwork.entity.Trades;
import com.wells.madhu.batchmodpocwork.entity.TradesDocument;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TradeMysqlProcessor implements ItemProcessor<Trades, TradesDocument> {

    @Override
    public TradesDocument process(Trades trade) throws Exception {
        // Transform Trades to TradesDocument
        TradesDocument tradesDocument = new TradesDocument();
        tradesDocument.setTradeId(trade.getTradeId());
        tradesDocument.setTradeName(trade.getTradeName() + new Random().nextInt());
        tradesDocument.setTradeDate(trade.getTradeDate());
        return tradesDocument;
    }

    public TradeHistoryDocument processTradeHistory(TradeHistory tradeHistory) throws Exception {
        // Transform TradeHistory to TradeHistoryDocument
        TradeHistoryDocument tradeHistoryDocument = new TradeHistoryDocument();
        tradeHistoryDocument.setTktNum(tradeHistory.getTktNum());
        tradeHistoryDocument.setHist_txt(tradeHistory.getHist_txt());
        tradeHistoryDocument.setHistTmstp(tradeHistory.getHistTmstp());
        tradeHistoryDocument.setLanId(tradeHistory.getLanId());
        tradeHistoryDocument.setSysNm(tradeHistory.getSysNm());
        return tradeHistoryDocument;
    }
}