package com.huzhipeng.coin.ui.adapter

import com.binance.client.model.event.SymbolTickerEvent
import com.huzhipeng.coin.db.CoinEntity
import com.huzhipeng.coin.entity.PriceEntity
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class SymbolAdapterEntity {
    constructor() {}
    constructor(coinEntity: CoinEntity, symbol: SymbolTickerEvent) {
        this.coinEntity = coinEntity
        this.symbol = symbol
        priceList = arrayListOf()

        highPriceList = arrayListOf()
        lowPriceList = arrayListOf()

        sixHoursHighPriceList = arrayListOf()
        sixHoursLowPriceList = arrayListOf()

        twoHoursHighsPriceList = arrayListOf()
        twoHoursLowPriceList = arrayListOf()

        twoHoursPriceList = arrayListOf()
        sixHoursPriceList = arrayListOf()
    }

    fun update() {
        try {
            gain24 = symbol.priceChangePercent
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val newPriceEntity = PriceEntity()
        newPriceEntity.symbol = symbol.symbol
        newPriceEntity.timeStamp = symbol.eventTime
        newPriceEntity.lastPrice = symbol.lastPrice
        newPriceEntity.priceType = PriceEntity.PriceType.Normal
        if (newPriceEntity.lastPrice == symbol.high) {
            newPriceEntity.priceType = PriceEntity.PriceType.Hiht
            highPriceList!!.add(newPriceEntity)
        } else if (newPriceEntity.lastPrice == symbol.low) {
            newPriceEntity.priceType = PriceEntity.PriceType.Low
            lowPriceList!!.add(newPriceEntity)
        }

        if (priceList!!.size > timeDay) {
            priceList!!.removeAt(0)
        }

        if (highPriceList!!.size > 0 && (symbol.eventTime - (highPriceList!![0].timeStamp + timeDay) >= 0)) {
            highPriceList!!.removeAt(0)
        }

        if (lowPriceList!!.size > 0 && (symbol.eventTime - (lowPriceList!![0].timeStamp + timeDay) >= 0)) {
            lowPriceList!!.removeAt(0)
        }

        priceList!!.add(newPriceEntity)
        fiveMinuteHighTimes = highPriceList!!.filter { it.timeStamp + timeFiveMinute  >= newPriceEntity.timeStamp}.size
        fiveMinuteLowTimes = lowPriceList!!.filter { it.timeStamp + timeFiveMinute  >= newPriceEntity.timeStamp}.size

        var fiveMinutesList = priceList!!.filter { it.timeStamp + timeFiveMinute  >= newPriceEntity.timeStamp }
        if (fiveMinutesList.isNotEmpty()) {
            gain5m = (newPriceEntity.lastPrice - fiveMinutesList[0].lastPrice) / newPriceEntity.lastPrice * 100.toBigDecimal()
        }

        //初始化记录集合
        if (twoHoursHighsPriceList!!.size == 0) {
            twoHoursHighsPriceList!!.add(newPriceEntity)
            twoHoursLowPriceList!!.add(newPriceEntity)
            sixHoursHighPriceList!!.add(newPriceEntity)
            sixHoursLowPriceList!!.add(newPriceEntity)
        }
        //添加两个小时内的高价记录
        if (newPriceEntity.lastPrice > twoHoursHighsPriceList!!.last().lastPrice) {
            twoHoursHighsPriceList!!.add(newPriceEntity)
        }
        //添加两个小时内的低价记录
        if (newPriceEntity.lastPrice < twoHoursLowPriceList!!.last().lastPrice) {
            twoHoursLowPriceList!!.add(newPriceEntity)
        }

        //添加六个小时内的高价记录
        if (newPriceEntity.lastPrice > sixHoursHighPriceList!!.last().lastPrice) {
            sixHoursHighPriceList!!.add(newPriceEntity)
        }
        //添加六个小时内的低价记录
        if (newPriceEntity.lastPrice < sixHoursLowPriceList!!.last().lastPrice) {
            sixHoursLowPriceList!!.add(newPriceEntity)
        }

        twoHoursPriceList!!.add(newPriceEntity)
        sixHoursPriceList!!.add(newPriceEntity)

        if (twoHoursPriceList!!.size > twoHoursTims / 1000) {
            twoHoursPriceList!!.removeAt(0)
        }
        if (sixHoursPriceList!!.size > sixHoursTims / 1000) {
            sixHoursPriceList!!.removeAt(0)
        }

        //清除超过两个小时的高价记录
        if (twoHoursHighsPriceList!![0].timeStamp > newPriceEntity.timeStamp + twoHoursTims) {
            twoHoursHighsPriceList!!.removeAt(0)
            if (twoHoursHighsPriceList!!.size == 0) {
                twoHoursHighsPriceList!!.add(newPriceEntity)
            }
        }
        //清除超过两个小时的低价记录
        if (twoHoursLowPriceList!![0].timeStamp > newPriceEntity.timeStamp + twoHoursTims) {
            twoHoursLowPriceList!!.removeAt(0)
            if (twoHoursLowPriceList!!.size == 0) {
                twoHoursLowPriceList!!.add(newPriceEntity)
            }
        }

        //清除超过六个小时的高价记录
        if (sixHoursHighPriceList!![0].timeStamp > newPriceEntity.timeStamp + sixHoursTims) {
            sixHoursHighPriceList!!.removeAt(0)
            if (sixHoursHighPriceList!!.size == 0) {
                sixHoursHighPriceList!!.add(newPriceEntity)
            }
        }
        //清除超过六个小时的低价记录
        if (sixHoursLowPriceList!![0].timeStamp > newPriceEntity.timeStamp + sixHoursTims) {
            sixHoursLowPriceList!!.removeAt(0)
            if (sixHoursLowPriceList!!.size == 0) {
                sixHoursLowPriceList!!.add(newPriceEntity)
            }
        }
        fiveMinuteHighTimes = highPriceList!!.filter { it.timeStamp + timeFiveMinute > newPriceEntity.timeStamp }.size
        fiveMinuteLowTimes = lowPriceList!!.filter { it.timeStamp + timeFiveMinute > newPriceEntity.timeStamp }.size

        //计算2小时的涨跌幅
        gain2h = (newPriceEntity.lastPrice - twoHoursPriceList!!.first().lastPrice) / newPriceEntity.lastPrice * 100.toBigDecimal()
        gain6h = (newPriceEntity.lastPrice - sixHoursPriceList!!.first().lastPrice) / newPriceEntity.lastPrice * 100.toBigDecimal()
    }

    constructor(symbol: SymbolTickerEvent) {
        this.symbol = symbol
    }

    lateinit var coinEntity: CoinEntity
    lateinit var symbol: SymbolTickerEvent
    val timeDay = 1000*60*60*24
    val twoHoursTims = 1000*60*60*2
    val sixHoursTims = 1000*60*60*6
    //5分钟的时间间隔
    val timeFiveMinute = 1000*60*5
    //5分钟新高的次数
    var fiveMinuteHighTimes = 0
    //5分钟新低的次数
    var fiveMinuteLowTimes = 0


    var highPriceList: ArrayList<PriceEntity>? = null
    var lowPriceList: ArrayList<PriceEntity>? = null
    var priceList: ArrayList<PriceEntity>? = null
    var sixHoursHighPriceList: ArrayList<PriceEntity>? = null
    var sixHoursLowPriceList: ArrayList<PriceEntity>? = null
    var twoHoursHighsPriceList: ArrayList<PriceEntity>? = null
    var twoHoursLowPriceList: ArrayList<PriceEntity>? = null

    var twoHoursPriceList: ArrayList<PriceEntity>? = null
    var sixHoursPriceList: ArrayList<PriceEntity>? = null

    //5分钟内的涨幅
    var gain5m: BigDecimal? = null

    var gain2h: BigDecimal? = null
    var gain6h: BigDecimal? = null

    //24小时的涨幅
    var gain24: BigDecimal? = null
}