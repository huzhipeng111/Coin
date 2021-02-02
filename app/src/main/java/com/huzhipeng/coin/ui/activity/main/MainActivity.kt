package com.huzhipeng.coin.ui.activity.main

import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.media.Ringtone
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.provider.Settings
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.LayoutInflaterFactory
import com.binance.client.coin.SubscriptionClient
import com.binance.client.model.event.SymbolTickerEvent
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.huzhipeng.coin.CoinList
import com.huzhipeng.coin.CoinType
import com.huzhipeng.coin.DingTalkUtil
import com.huzhipeng.coin.R
import com.huzhipeng.coin.application.AppConfig
import com.huzhipeng.coin.base.BaseActivity
import com.huzhipeng.coin.constant.ConstantValue
import com.huzhipeng.coin.db.AlarmRecord
import com.huzhipeng.coin.db.CoinEntity
import com.huzhipeng.coin.db.CoinEntityDao
import com.huzhipeng.coin.entity.Symbol
import com.huzhipeng.coin.service.BackGroundService
import com.huzhipeng.coin.ui.activity.main.component.DaggerMainComponent
import com.huzhipeng.coin.ui.activity.main.contract.MainContract
import com.huzhipeng.coin.ui.activity.main.module.MainModule
import com.huzhipeng.coin.ui.activity.main.presenter.MainPresenter
import com.huzhipeng.coin.ui.adapter.AllSymbolAdapter
import com.huzhipeng.coin.ui.adapter.SymbolAdapterEntity
import com.huzhipeng.coin.utils.SpUtil
import com.huzhipeng.coin.view.BottomSheet
import com.huzhipeng.coin.view.TitleTextWindow
import com.pawegio.kandroid.toast
import com.socks.library.KLog
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_allsymbol.*
import kotlinx.android.synthetic.main.item_allsymbol.symbol
import kotlinx.android.synthetic.main.item_coin_set.*
import okhttp3.*
import okio.ByteString
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.concurrent.thread


/**
 * https://blog.csdn.net/Jeff_YaoJie/article/details/79164507
 */
class MainActivity : BaseActivity(), MainContract.View {
    lateinit var adapterList: MutableList<SymbolAdapterEntity>
    var sortOrder = SortOrder.zhangfu5s
    val timeJianju = 30
    lateinit var ringtone: Ringtone

    private lateinit var eventList : ConcurrentLinkedQueue<MutableList<SymbolTickerEvent>>

    override fun showToast() {

    }

    enum class SortOrder { jiaoyidui, jiage, zhangfu5s, zhangfu24 }

    //全市场所有交易对的完整Ticker
    val allTicker = "wss://fstream.binance.com/ws/!ticker@arr"

    @Inject
    internal lateinit var mPresenter: MainPresenter

    var allSymbolAdapter: AllSymbolAdapter? = null

    override fun setPresenter(presenter: MainContract.MainContractPresenter) {
        mPresenter = presenter as MainPresenter
    }

    override fun showProgressDialog() {
    }

    override fun closeProgressDialog() {
    }

    override fun initData() {
        eventList = ConcurrentLinkedQueue()
        EventBus.getDefault().register(this)
        setMonitorCoin(CoinEntity())
        setListener()
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                var intent1 = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent1, 1);
            } else {
                //TODO do something you need
            }
        }
        tvTime1.setOnClickListener {
            tvTime1.text.toString().split(" ").forEach {
                if (it.contains("BTC")) {
                    AppConfig.instance.showSymbol = it
                }
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun setMonitorCoin(coinEntity: CoinEntity) {
        adapterList = mutableListOf()
        CoinList.allCoinList.forEach {
        var coin = AppConfig.instance.daoSsesion.coinEntityDao.queryBuilder().where(CoinEntityDao.Properties.Symbol.eq(it.toString())).build().unique()
            if (coin == null) {
                var coinEntity = CoinEntity()
                coinEntity.symbol = it.toString()
                AppConfig.instance.daoSsesion.coinEntityDao.insert(coinEntity)
                adapterList.add(SymbolAdapterEntity(coinEntity, SymbolTickerEvent(it.toString())))
            } else {
                adapterList.add(SymbolAdapterEntity(coin, SymbolTickerEvent(it.toString())))
            }
        }
        allSymbolAdapter = AllSymbolAdapter(arrayListOf())
        recyclerview.adapter = allSymbolAdapter

        allSymbolAdapter?.setNewData(adapterList)
        allSymbolAdapter!!.setOnItemClickListener { adapter, view, position ->
            showSetCoinSheet(allSymbolAdapter!!.data[position])
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.config_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.confing -> {
                startActivity(Intent(this, ConfigActivity::class.java))
            }
            R.id.coinset -> {
                startActivity(Intent(this, CoinSetActivity::class.java))
            }
            R.id.history -> {
                startActivity(Intent(this, AlarmRecordActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initView() {
        setContentView(R.layout.activity_main)
        title.text = getString(R.string.app_name)
        sleepModel.visibility = View.VISIBLE
        sleepModel.isChecked = AppConfig.instance.sleepModel
        sleepModel.setOnCheckedChangeListener { compoundButton, b ->
            KLog.i("切换")
            AppConfig.instance.sleepModel = b
            SpUtil.putBoolean(this, ConstantValue.sleepModel, b)
        }
        title.setOnClickListener {
            AppConfig.instance.showSymbol = ""
            startActivity(Intent(this, CourseActivity::class.java))
        }
        if (AppConfig.instance.sortByCoin) {
            symbol.setTextColor(resources.getColor(R.color.color_f51818))
        } else {
            symbol.setTextColor(resources.getColor(R.color.color_333))
        }
        if (AppConfig.instance.sortByGain24) {
            gain24.setTextColor(resources.getColor(R.color.color_f51818))
        } else {
            gain24.setTextColor(resources.getColor(R.color.color_333))
        }
        title.setTextColor(resources.getColor(R.color.color_f51818))
        symbol.setOnClickListener {
            AppConfig.instance.sortByCoin = !AppConfig.instance.sortByCoin
            SpUtil.putBoolean(this, ConstantValue.sortByCoin, AppConfig.instance.sortByCoin)

            AppConfig.instance.sortByGain24 = false
            SpUtil.putBoolean(this, ConstantValue.sortByGain24, false)

            AppConfig.instance.sortByGain5m = false
            SpUtil.putBoolean(this, ConstantValue.sortByGain5m, false)

            if (AppConfig.instance.sortByCoin) {
                symbol.setTextColor(resources.getColor(R.color.color_f51818))
            } else {
                symbol.setTextColor(resources.getColor(R.color.color_333))
            }
            gain24.setTextColor(resources.getColor(R.color.color_333))
        }
        gain24.setOnClickListener {
            AppConfig.instance.sortByGain24 = !AppConfig.instance.sortByGain24
            SpUtil.putBoolean(this, ConstantValue.sortByGain24, AppConfig.instance.sortByGain24)

            AppConfig.instance.sortByCoin = false
            SpUtil.putBoolean(this, ConstantValue.sortByCoin, false)

            AppConfig.instance.sortByGain5m = false
            SpUtil.putBoolean(this, ConstantValue.sortByGain5m, false)

            if (AppConfig.instance.sortByGain24) {
                gain24.setTextColor(resources.getColor(R.color.color_f51818))
            } else {
                gain24.setTextColor(resources.getColor(R.color.color_333))
            }
            symbol.setTextColor(resources.getColor(R.color.color_333))
        }
        tvTime.setOnClickListener {
            startActivity(Intent(this, LogActivity::class.java).putExtra("type", "vum"))
        }
    }

    override fun setupActivityComponent() {
        DaggerMainComponent
                .builder()
                .appComponent((application as AppConfig).applicationComponent)
                .mainModule(MainModule(this))
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), LayoutInflaterFactory { parent, name, context, attrs ->
            if (name.equals("com.android.internal.view.menu.IconMenuItemView", ignoreCase = true) || name.equals("com.android.internal.view.menu.ActionMenuItemView", ignoreCase = true) || name.equals("android.support.v7.view.menu.ActionMenuItemView", ignoreCase = true)) {
                try {
                    val f = layoutInflater
                    val view = f.createView(name, null, attrs)
                    if (view is TextView) {
                        view.setTextColor(resources.getColor(R.color.mainColor))
                        view.setAllCaps(false)
                    }
                    return@LayoutInflaterFactory view
                } catch (e: InflateException) {
                    e.printStackTrace()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }

            }
            null
        })
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setListener() {
        val client = SubscriptionClient.create("", "")

        client.subscribeAllTickerEvent({ event: MutableList<SymbolTickerEvent> ->
//            KLog.i(event)
            eventList.offer(event)
//            client.unsubscribeAll()
        }, null)
        filterData()
    }

    fun filterData() {
        try{
            thread {
                val disposable: Disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .onErrorReturn { 0.toLong() }
                        .subscribe(object : Consumer<Long?> {
                            override fun accept(aLong: Long?) {
                                var list = eventList.poll()
                                if (list != null) {
                                    handlerData(list)
                                }
                            }
                        })
            }
        } catch (e :Exception) {
            e.printStackTrace()
        }
    }

    fun sendDingMessage(message : String, coinType: CoinType) {
        thread {
            DingTalkUtil.sendMessage(message, coinType)
        }
    }

    val SET_TIME_INTERVAL = 5*60*1000
    val NEW_PRICE_INTERVAL = 1*60*1000

    /**
     * 处理数据
     */
    fun handlerData(mutableList: MutableList<SymbolTickerEvent>) {
        adapterList.forEach { symbolAadapterEntity ->
            mutableList.forEach {
                if (it.symbol.equals(symbolAadapterEntity.coinEntity!!.symbol)) {
                    symbolAadapterEntity.symbol = it
                    symbolAadapterEntity.update()
                    //破新高报警
//                    if (symbolAadapterEntity.symbol.totalTradedQuoteAssetVolume >= 15000000.toBigDecimal() && symbolAadapterEntity.fiveMinuteHighTimes >= 4 && symbolAadapterEntity.highPriceList!!.size >= 8 && (symbolAadapterEntity.highPriceList!!.last().lastPrice > symbolAadapterEntity.highPriceList!![symbolAadapterEntity!!.highPriceList!!.size - 2].lastPrice) && symbolAadapterEntity.symbol.lastPrice >= symbolAadapterEntity.symbol.high) {
//                        if (symbolAadapterEntity.coinEntity.newHighPriceTime == 0.toLong() || System.currentTimeMillis() - symbolAadapterEntity.coinEntity.newHighPriceTime > NEW_PRICE_INTERVAL) {
//                            symbolAadapterEntity.coinEntity.newHighPriceTime = System.currentTimeMillis()
//                            AppConfig.instance.daoSsesion.coinEntityDao.update(symbolAadapterEntity.coinEntity)
//                            runOnUiThread {
//                                if (AppConfig.instance.sleepModel) {
//                                    return@runOnUiThread
//                                }
//                                addAlarm(symbolAadapterEntity, CoinType.NewHighCoin)
//                            }
//                        }
//                    }
                    //破新低报警
                    if (symbolAadapterEntity.symbol.totalTradedQuoteAssetVolume >= 10000000.toBigDecimal() && symbolAadapterEntity.fiveMinuteLowTimes >= 5 && (symbolAadapterEntity.lowPriceList!!.last().lastPrice < symbolAadapterEntity.lowPriceList!![symbolAadapterEntity!!.lowPriceList!!.size - 2].lastPrice) && symbolAadapterEntity.symbol.lastPrice <= symbolAadapterEntity.symbol.low) {
                        if (symbolAadapterEntity.coinEntity.newLowPriceTime == 0.toLong() || System.currentTimeMillis() - symbolAadapterEntity.coinEntity.newLowPriceTime > NEW_PRICE_INTERVAL) {
                            symbolAadapterEntity.coinEntity.newLowPriceTime = System.currentTimeMillis()
                            AppConfig.instance.daoSsesion.coinEntityDao.update(symbolAadapterEntity.coinEntity)
                            runOnUiThread {
                                if (AppConfig.instance.sleepModel) {
                                    return@runOnUiThread
                                }
                                addAlarm(symbolAadapterEntity, CoinType.NewLowCoin)
                            }
                        }
                    }
                    //达到设定的低价报警
                    if (symbolAadapterEntity.coinEntity.lowPrice != 0.toFloat() && symbolAadapterEntity.symbol.lastPrice <= symbolAadapterEntity.coinEntity.lowPrice.toBigDecimal()) {
                        if (symbolAadapterEntity.coinEntity.setLowPriceTime == 0.toLong() || System.currentTimeMillis() - symbolAadapterEntity.coinEntity.setLowPriceTime > SET_TIME_INTERVAL) {
                            symbolAadapterEntity.coinEntity.setLowPriceTime = System.currentTimeMillis()
                            AppConfig.instance.daoSsesion.coinEntityDao.update(symbolAadapterEntity.coinEntity)
                            runOnUiThread {
                                addAlarm(symbolAadapterEntity, CoinType.SetLowCoin)
                            }
                        }
                    }
                    //达到设定的高价报警
                    if (symbolAadapterEntity.coinEntity.highPrice != 0.toFloat() && symbolAadapterEntity.symbol.lastPrice >= symbolAadapterEntity.coinEntity.highPrice.toBigDecimal()) {
                        if (symbolAadapterEntity.coinEntity.setHighPriceTime == 0.toLong() || System.currentTimeMillis() - symbolAadapterEntity.coinEntity.setHighPriceTime > SET_TIME_INTERVAL) {
                            symbolAadapterEntity.coinEntity.setHighPriceTime = System.currentTimeMillis()
                            AppConfig.instance.daoSsesion.coinEntityDao.update(symbolAadapterEntity.coinEntity)
                            runOnUiThread {
                                addAlarm(symbolAadapterEntity, CoinType.SetHighCoin)
                            }
                        }
                    }
                }
            }
        }
        val c1: Comparator<SymbolAdapterEntity> = Comparator { o1, o2 ->
            if (o1.gain24 != null && o2.gain24 != null) {
                o2.gain24!!.compareTo(o1.gain24)
            } else {
                0
            }
        }
        try {
            if (AppConfig.instance.sortByCoin) {
                adapterList.sortBy { it.symbol!!.symbol }
            } else if (AppConfig.instance.sortByGain24) {
                adapterList.sortByDescending { it.gain24 }
            } else {
                adapterList.sortWith(c1)
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
        adapterList.forEachIndexed { index1, it ->
            if (it.symbol != null && index1 == 0) {
                if (it.symbol!!.eventTime == null) {
                    return@forEachIndexed
                }
                var xiangchaShiJian = (System.currentTimeMillis() - it.symbol!!.eventTime) / 1000
                if (xiangchaShiJian > 20) {
                    KLog.i("超时重连了。。。")
                }
            }
        }
        runOnUiThread {
            allSymbolAdapter?.notifyDataSetChanged()
        }
    }


    /**
     * 添加报警的symbol
     */
    private fun addAlarm(symbolAadapterEntity: SymbolAdapterEntity, type: CoinType) {
        KLog.i("触发报警了: " + symbolAadapterEntity.symbol.symbol)
        var alarmRecord = AlarmRecord()
        alarmRecord.symbol = symbolAadapterEntity.symbol.symbol
        alarmRecord.alarmTime = System.currentTimeMillis()
        alarmRecord.alarmPrice = symbolAadapterEntity.symbol.lastPrice.toPlainString()
        alarmRecord.alarmType = type.ordinal
        alarmRecord.gainTimeJianju = AppConfig.instance.PriceGainOfSencond
        alarmRecord.gain = symbolAadapterEntity.gain5m!!.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
        alarmRecord.gain24 = symbolAadapterEntity.gain24!!.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
        AppConfig.instance.daoSsesion.alarmRecordDao.insert(alarmRecord)
        showBaojingDialog(symbolAadapterEntity, type, alarmRecord.symbol)
    }

    fun showSetCoinSheet(symbolAadapterEntity: SymbolAdapterEntity) {
        val maskView = LayoutInflater.from(this).inflate(R.layout.set_coin_pop_layout, null)
        val builder = BottomSheet.Builder(this, true)

        val etHighPrice = maskView.findViewById<EditText>(R.id.etHighPrice)
        val etLowPrice = maskView.findViewById<EditText>(R.id.etLowPrice)
        val tvConfirmHigh = maskView.findViewById<TextView>(R.id.tvConfirmHigh)
        val tvConfirmLow = maskView.findViewById<TextView>(R.id.tvConfirmLow)
        tvConfirmHigh.setOnClickListener {
            if ("".equals(etHighPrice.text.toString().trim())) {
                symbolAadapterEntity.coinEntity.highPrice = 0.toFloat()
            } else {
                symbolAadapterEntity.coinEntity.highPrice = etHighPrice.text.toString().toFloat()
            }
            AppConfig.instance.daoSsesion.coinEntityDao.update(symbolAadapterEntity.coinEntity)
            toast("更新成功")
        }

        tvConfirmLow.setOnClickListener {
            if ("".equals(etLowPrice.text.toString().trim())) {
                symbolAadapterEntity.coinEntity.lowPrice = 0.toFloat()
            } else {
                symbolAadapterEntity.coinEntity.lowPrice = etLowPrice.text.toString().toFloat()
            }
            AppConfig.instance.daoSsesion.coinEntityDao.update(symbolAadapterEntity.coinEntity)
            toast("更新成功")
        }

        etHighPrice.setText(symbolAadapterEntity.coinEntity.highPrice.toString())
        etLowPrice.setText(symbolAadapterEntity.coinEntity.lowPrice.toString())

        etHighPrice.setSelection(etHighPrice.text.toString().length)
        etLowPrice.setSelection(etLowPrice.text.toString().length)

        builder.setApplyTopPadding(false)
        val tvPrice = maskView.findViewById<TextView>(R.id.tvPrice)
        val tvSymobl = maskView.findViewById<TextView>(R.id.tvSymbol)
        tvPrice.text = symbolAadapterEntity.symbol.lastPrice.toPlainString()
        tvSymobl.text = symbolAadapterEntity.symbol.symbol
        builder.setCustomView(maskView)
        builder.create().setOnDismissListener {

        }
        builder.create().show()
    }

    /**
     * 显示报警
     */
    private fun showBaojingDialog(symbolAadapterEntity: SymbolAdapterEntity, type: CoinType, symbol: String) {
        if (System.currentTimeMillis() < symbolAadapterEntity.coinEntity.unIngnoreTime) {
            return
        }
        if (AppConfig.instance.alarmVibrate) {
            showAlarmVibrate()
        }
        if (AppConfig.instance.alarmVoice) {
//            showAlarmVoice(type)
        }

//        var textWindow = TitleTextWindow(this)
//        textWindow.coinEntity = symbolAadapterEntity.coinEntity
        var content = symbolAadapterEntity.symbol.symbol + "\n报警时价格为：" + symbolAadapterEntity.symbol.lastPrice.toPlainString() + "\n" + "5分钟涨幅为：" + symbolAadapterEntity.gain5m + "%\n24小时涨幅为：" + symbolAadapterEntity.gain24 + "%\n报警类型为：" + getType(type, symbolAadapterEntity)
//        textWindow.show(content)
        sendDingMessage(content, type)
    }

    private fun getType(alarmType: CoinType, symbolAadapterEntity: SymbolAdapterEntity): String {
        if (alarmType == CoinType.NewHighCoin) {
            return "破新高"
        } else if (alarmType == CoinType.NewLowCoin) {
            return "破新低"
        } else if (alarmType == CoinType.SetLowCoin) {
            return "达到设定低价"
        } else if (alarmType == CoinType.SetHighCoin) {
            return "达到设定高价"
        }
        return ""
    }


    /**
     * 震动
     */
    fun showAlarmVibrate() {
    }

    /**
     * 播放报警声音
     */
    fun showAlarmVoice(type: Int) {
    }



    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    inner class EchoWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            //            String openid = "1";
            //连接成功后，发送登录信息
            //            String message = sendData();
            //            mSocket.send(message);
            output("连接成功！")
            title.setTextColor(resources.getColor(R.color.color_333))


        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
            super.onMessage(webSocket, bytes)
            output("receive bytes:" + bytes!!.hex())
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            super.onMessage(webSocket, text)
//            output("服务器端发送来的信息：" + text!!)
            var symbols = Gson().fromJson<MutableList<Symbol>>(text!!)
            var mutableList = mutableListOf<SymbolTickerEvent>()
            /**
             * {
            "e": "24hrTicker",  // 事件类型
            "E": 123456789,     // 事件时间
            "s": "BNBBTC",      // 交易对
            "p": "0.0015",      // 24小时价格变化
            "P": "250.00",      // 24小时价格变化(百分比)
            "w": "0.0018",      // 平均价格
            "c": "0.0025",      // 最新成交价格
            "Q": "10",          // 最新成交价格上的成交量
            "o": "0.0010",      // 24小时内第一比成交的价格
            "h": "0.0025",      // 24小时内最高成交价
            "l": "0.0010",      // 24小时内最低成交加
            "v": "10000",       // 24小时内成交量
            "q": "18",          // 24小时内成交额
            "O": 0,             // 统计开始时间
            "C": 86400000,      // 统计关闭时间
            "F": 0,             // 24小时内第一笔成交交易ID
            "L": 18150,         // 24小时内最后一笔成交交易ID
            "n": 18151          // 24小时内成交数
            }
             */
            symbols.forEach { jsonWrapper ->
                val result = SymbolTickerEvent()
                result.eventType = jsonWrapper.e
                result.eventTime = jsonWrapper.E
                result.symbol = jsonWrapper.s
                result.priceChange = jsonWrapper.p.toBigDecimal()
                result.priceChangePercent = jsonWrapper.P.toBigDecimal()
                result.weightedAvgPrice = jsonWrapper.w.toBigDecimal()
                result.lastPrice = jsonWrapper.c.toBigDecimal()
                result.lastQty = jsonWrapper.Q.toBigDecimal()
                result.open = jsonWrapper.o.toBigDecimal()
                result.high = jsonWrapper.h.toBigDecimal()
                result.low = jsonWrapper.l.toBigDecimal()
                result.totalTradedBaseAssetVolume = jsonWrapper.v.toBigDecimal()
                result.totalTradedQuoteAssetVolume = jsonWrapper.q.toBigDecimal()
                result.openTime = jsonWrapper.O.toLong()
                result.closeTime = jsonWrapper.C.toLong()
                result.firstId = jsonWrapper.F.toLong()
                result.lastId = jsonWrapper.L.toLong()
                result.count = jsonWrapper.n.toLong()
                mutableList.add(result)
            }
            try {
                handlerData(mutableList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
//            }
        }

        override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
            super.onClosed(webSocket, code, reason)
            output("closed:" + reason!!)

        }

        override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
            super.onClosing(webSocket, code, reason)
            output("closing:" + reason!!)
            if (!isCloseedApp) {
                setListener()
                runOnUiThread {
                    title.setTextColor(resources.getColor(R.color.color_f51818))
                }
            }
            title.setTextColor(resources.getColor(R.color.color_f51818))
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            if (!isCloseedApp) {
                output("failure:" + t.message)
                setListener()
                runOnUiThread {
                    title.setTextColor(resources.getColor(R.color.color_f51818))
                }
            }
        }
    }

    var isCloseedApp = false
    override fun onDestroy() {
        stopService(Intent(this, BackGroundService::class.java))
        isCloseedApp = true
        EventBus.getDefault().unregister(this)
        super.onDestroy()
        System.exit(0)
    }


    private fun output(text: String) {
        runOnUiThread {
            KLog.e("text: $text")
        }
    }
}
