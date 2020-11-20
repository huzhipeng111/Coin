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
        helper.setTextColor(R.id.heighPrice, mContext.resources.getColor(R.color.color_333))
        helper.setTextColor(R.id.lowPrice, mContext.resources.getColor(R.color.color_333))
        //设置交易对名字
        if(item?.symbol?.lastPrice == null) {
            helper.setText(R.id.price, "--")
        } else {
            helper.setText(R.id.price, item.symbol.lastPrice.setScale(item.coinEntity.decimal, BigDecimal.ROUND_HALF_UP).toPlainString())
        }
        //设置5分钟的涨跌幅
        if (item?.gain5m == null) {
            helper.setText(R.id.gain5m, "-- %")
        } else {
            helper.setText(R.id.gain5m, item.gain5m?.setScale(2, BigDecimal.ROUND_HALF_UP)?.toPlainString() + " %")
        }
        //设置两小时的涨跌幅
        if (item?.gain2h == null) {
            helper.setText(R.id.gain2, "-- %")
        } else {
            helper.setText(R.id.gain2, item.gain2h?.setScale(2, BigDecimal.ROUND_HALF_UP)?.toPlainString() + " %")
        }
        //设置6小时的涨跌幅
        if (item?.gain6h == null) {
            helper.setText(R.id.gain6, "-- %")
        } else {
            helper.setText(R.id.gain6, item.gain6h?.setScale(2, BigDecimal.ROUND_HALF_UP)?.toPlainString() + " %")
        }
        //设置24小时的涨跌幅
        if (item?.gain24 == null) {
            helper.setText(R.id.gain24, "-- %")
        } else {
            helper.setText(R.id.gain24, item.gain24?.setScale(2, BigDecimal.ROUND_HALF_UP)?.toPlainString() + " %")
        }

        helper.setText(R.id.gain24HoursHigh, "" + item?.highPriceList!!.size + "")
        helper.setText(R.id.gain24HoursLow, "" + item?.lowPriceList!!.size + "")

        helper.setText(R.id.gain2HoursHigh, "" + item?.twoHoursHighsPriceList!!.size + "")
        helper.setText(R.id.gain2HoursLow, "" + item?.twoHoursLowPriceList!!.size + "")

        helper.setText(R.id.gain6HoursHigh, "" + item?.sixHoursHighPriceList!!.size + "")
        helper.setText(R.id.gain6HoursLow, "" + item?.sixHoursLowPriceList!!.size + "")

        helper.setText(R.id.gain5mHigh, "" + item?.fiveMinuteHighTimes + "")
        helper.setText(R.id.gain5mLow, "" + item?.fiveMinuteLowTimes + "")

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

        //设置自定义报警的低价
        if (item.coinEntity.lowPrice == 0.toFloat()) {
            helper.setText(R.id.setLow, "-/-")
        } else {
            helper.setText(R.id.setLow, item.coinEntity.lowPrice.toString())
        }
        //设置自定义报警的高价
        if (item.coinEntity.highPrice == 0.toFloat()) {
            helper.setText(R.id.setHigh, "-/-")
        } else {
            helper.setText(R.id.setHigh, item.coinEntity.highPrice.toString())
        }
    }
}