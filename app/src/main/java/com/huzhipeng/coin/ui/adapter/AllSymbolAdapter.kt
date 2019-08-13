package com.huzhipeng.coin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huzhipeng.coin.R
import com.huzhipeng.coin.entity.Symbol
import java.math.BigDecimal
import java.math.RoundingMode

class AllSymbolAdapter(arrayList: MutableList<SymbolAdapterEntity>) : BaseQuickAdapter<SymbolAdapterEntity, BaseViewHolder>(R.layout.item_allsymbol, arrayList) {
    override fun convert(helper: BaseViewHolder, item: SymbolAdapterEntity?) {
        helper.setText(R.id.symbol, item?.symbol?.s)
        helper.addOnClickListener(R.id.symbol)
        if(item?.symbol?.getc() == null) {
            helper.setText(R.id.price, "--")
        } else {
            helper.setText(R.id.price, item.symbol?.getc()!!.toBigDecimal().setScale(item.coinEntity.decimal, BigDecimal.ROUND_HALF_UP).toPlainString())
        }
        helper.setText(R.id.tradeAveCount, "" + item?.fiveSecondsAverageTradingVolume)
        helper.setText(R.id.muniteAradeAveCount, "" + item?.lastTradingVolume)
        if (item?.gain5m == null) {
            helper.setText(R.id.gain5m, "-- %")
        } else {
            helper.setText(R.id.gain5m, item.gain5m?.setScale(2, BigDecimal.ROUND_HALF_UP)?.toPlainString() + " %")
        }
        if (item?.gain24 == null) {
            helper.setText(R.id.gain24, "-- %")
        } else {
            helper.setText(R.id.gain24, item.gain24?.setScale(2, BigDecimal.ROUND_HALF_UP)?.toPlainString() + " %")
        }
    }
}