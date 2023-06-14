package com.wells.madhu.batchmodpocwork.processor;

import com.wells.madhu.batchmodpocwork.entity.TradeHistory;
import com.wells.madhu.batchmodpocwork.entity.TradeHistoryDocument;
import com.wells.madhu.batchmodpocwork.entity.Trades;
import com.wells.madhu.batchmodpocwork.entity.TradesDocument;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TradeMongoProcessor implements ItemProcessor<TradesDocument, Trades> {

    @Override
    public Trades process(TradesDocument tradesDocument) {
        // Transform Trade to TradesDocument
        Trades trade = new Trades();
        trade.setTradeId(tradesDocument.getTradeId());
        trade.setTradeName(tradesDocument.getTradeName());
        trade.setTradeDate(tradesDocument.getTradeDate());
        return trade;
    }


    public TradeHistory processTradeDocumentHistory(TradeHistoryDocument tradeHistoryDocument) {
        // Transform TradeHistory to TradesDocumentHistory
        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.setTktNum(tradeHistoryDocument.getTktNum());
        tradeHistory.setHist_txt(tradeHistoryDocument.getHist_txt());
        tradeHistory.setHistTmstp(tradeHistoryDocument.getHistTmstp());
        tradeHistory.setSysNm(tradeHistoryDocument.getSysNm());
        tradeHistory.setLanId(tradeHistoryDocument.getLanId());
        return tradeHistory;
    }
}