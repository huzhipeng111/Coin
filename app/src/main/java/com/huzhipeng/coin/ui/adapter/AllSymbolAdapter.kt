package com.huzhipeng.coin.ui.adapter

import android.icu.text.NumberFormat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huzhipeng.coin.R
import java.math.BigDecimal
import java.util.*

class AllSymbolAdapter(arrayList: MutableList<SymbolAdapterEntity>) : BaseQuickAdapter<SymbolAdapterEntity, BaseViewHolder>(R.layout.item_trade_pair, arrayList) {
    override fun convert(helper: BaseViewHolder, item: SymbolAdapterEntity?) {
        helper.setText(R.id.symbol, item?.symbol?.symbol)
        val formatter = NumberFormat.getInstance(Locale("en_US"))
        if (item?.symbol?.totalTradedQuoteAssetVolume != null) {
            helper.setText(R.id.volum, "量 " + formatter.format(item?.symbol?.totalTradedQuoteAssetVolume))
        }
        helper.setTextColor(R.id.heighPrice, mContext.resources.getColor(R.color.color_333))
        helper.setTextColor(R.id.lowPrice, mContext.resources.getColor(R.color.color_333))
        if(item?.symbol?.lastPrice == null) {
            helper.setText(R.id.price, "--")
        } else {
            helper.setText(R.id.price, item.symbol.lastPrice.setScale(item.coinEntity.decimal, BigDecimal.ROUND_HALF_UP).toPlainString())
        }
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
        if (item?.symbol?.low == null) {
            helper.setText(R.id.lowPrice, "--")
        } else {
            helper.setText(R.id.lowPrice, item.symbol!!.low.setScale(item.coinEntity.decimal, BigDecimal.ROUND_HALF_UP)?.toPlainString())
            if ((item.symbol.lastPrice - item.symbol.low).divide(item.symbol.lastPrice, item.coinEntity.decimal, BigDecimal.ROUND_HALF_UP) <= 0.01.toBigDecimal()) {
                helper.setTextColor(R.id.lowPrice, mContext.resources.getColor(R.color.color_down))
            }
        }
        if (item?.symbol?.high == null) {
            helper.setText(R.id.heighPrice, "--")
        } else {
            helper.setText(R.id.heighPrice, item.symbol!!.high.setScale(item.coinEntity.decimal, BigDecimal.ROUND_HALF_UP)?.toPlainString())
            if ((item.symbol.high - item.symbol.lastPrice).divide(item.symbol.lastPrice, item.coinEntity.decimal, BigDecimal.ROUND_HALF_UP) <= 0.01.toBigDecimal()) {
                helper.setTextColor(R.id.heighPrice, mContext.resources.getColor(R.color.color_up))
            }
        }
    }
}