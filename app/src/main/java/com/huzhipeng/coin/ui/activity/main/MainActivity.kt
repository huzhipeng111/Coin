package com.huzhipeng.coin.ui.activity.main

import android.content.Intent
import android.media.MediaPlayer
import android.media.Ringtone
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.provider.Settings
import android.support.v4.view.LayoutInflaterCompat
import android.support.v4.view.LayoutInflaterFactory
import android.view.InflateException
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.JSONPObject
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.huzhipeng.coin.R
import com.huzhipeng.coin.application.AppConfig
import com.huzhipeng.coin.base.BaseActivity
import com.huzhipeng.coin.constant.ConstantValue
import com.huzhipeng.coin.db.AlarmRecord
import com.huzhipeng.coin.db.CoinEntity
import com.huzhipeng.coin.entity.Symbol
import com.huzhipeng.coin.entity.SymbolTickerEvent
import com.huzhipeng.coin.service.BackGroundService
import com.huzhipeng.coin.ui.activity.main.component.DaggerMainComponent
import com.huzhipeng.coin.ui.activity.main.contract.MainContract
import com.huzhipeng.coin.ui.activity.main.module.MainModule
import com.huzhipeng.coin.ui.activity.main.presenter.MainPresenter
import com.huzhipeng.coin.ui.adapter.AllSymbolAdapter
import com.huzhipeng.coin.ui.adapter.AllSymbolNameAdapter
import com.huzhipeng.coin.ui.adapter.SymbolAdapterEntity
import com.huzhipeng.coin.utils.MathUtils
import com.huzhipeng.coin.utils.SpUtil
import com.huzhipeng.coin.utils.TimeUtil
import com.huzhipeng.coin.view.TitleTextWindow
import com.pawegio.kandroid.toast
import com.socks.library.KLog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_allsymbol.*
import okhttp3.*
import okio.ByteString
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * https://blog.csdn.net/Jeff_YaoJie/article/details/79164507
 */
class MainActivity : BaseActivity(), MainContract.View {
    lateinit var allSymbol10AllMuMap: HashMap<String, MutableList<SymbolTickerEvent>>
    lateinit var coinList: MutableList<CoinEntity>
    lateinit var mOkHttpClient: OkHttpClient
    lateinit var adapterList: MutableList<SymbolAdapterEntity>
    lateinit var alarmAllMap: HashMap<String, MutableList<AlarmRecord>>
    var mVibrator: Vibrator? = null
    var sortOrder = SortOrder.zhangfu5s
    val timeJianju = 30
    var index = 0
    lateinit var ringtone: Ringtone
    lateinit var mediaPlayer: MediaPlayer
    override fun showToast() {
        toast("点击啦。。。。哈哈哈")
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
//        val backGroundService = Intent(this, BackGroundService::class.java)
//        this.startService(backGroundService)

        EventBus.getDefault().register(this)
        mVibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        setMonitorCoin(CoinEntity())
        allSymbol10AllMuMap = hashMapOf()
        alarmAllMap = hashMapOf()
        mediaPlayer = MediaPlayer.create(this, R.raw.voice)
        mediaPlayer.setVolume(1f, 1f)
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
        mustSymbol.setOnClickListener {
            if ("".equals(AppConfig.instance.showSymbol)) {
                mustSymbol.text.toString().split(" ").forEach {
                    if (it.contains("BTC")) {
                        AppConfig.instance.showSymbol = it
                    }
                }
            } else {
                AppConfig.instance.showSymbol = ""
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
//        gain.text = "涨幅" + AppConfig.instance.PriceGainOfSencond + "s"
        adapterList = mutableListOf()
        coinList = AppConfig.instance.daoSsesion.coinEntityDao.loadAll()
        coinList.forEach {
            adapterList.add(SymbolAdapterEntity(it, SymbolTickerEvent(it.symbol)))
        }
        allSymbolAdapter = AllSymbolAdapter(arrayListOf())
        recyclerview.adapter = allSymbolAdapter

        allSymbolAdapter?.setNewData(adapterList)
        allSymbolAdapter!!.setOnItemClickListener { adapter, view, position ->
            startActivity(Intent(this, CoinDetailActivity::class.java).putExtra("coin", allSymbolAdapter!!.data[position].symbol.symbol))
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
        muniteAradeAveCount.setOnClickListener {
            AppConfig.instance.sortByGain5m = !AppConfig.instance.sortByGain5m
            SpUtil.putBoolean(this, ConstantValue.sortByGain5m, AppConfig.instance.sortByGain5m)

            AppConfig.instance.sortByCoin = false
            SpUtil.putBoolean(this, ConstantValue.sortByCoin, false)

            AppConfig.instance.sortByGain24 = false
            SpUtil.putBoolean(this, ConstantValue.sortByGain24, false)

            if (AppConfig.instance.sortByGain5m) {
                muniteAradeAveCount.setTextColor(resources.getColor(R.color.color_f51818))
            } else {
                muniteAradeAveCount.setTextColor(resources.getColor(R.color.color_333))
            }
            symbol.setTextColor(resources.getColor(R.color.color_333))
            gain24.setTextColor(resources.getColor(R.color.color_333))
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
        AppConfig.instance!!.applicationComponent!!.httpApiWrapper
    }

    private var mSocket: WebSocket? = null
    var socketListener = EchoWebSocketListener()
    private fun setListener() {
        mOkHttpClient = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build()
        val request = Request.Builder().url(allTicker).build()
        mOkHttpClient.newWebSocket(request, socketListener)
        mOkHttpClient.dispatcher().executorService().shutdown()
    }


    /**
     * 处理数据
     */
    fun handlerData(mutableList: MutableList<SymbolTickerEvent>, index: Int) {
        mutableList.forEach {
            if (allSymbol10AllMuMap.get(it.symbol) == null) {
                allSymbol10AllMuMap.put(it.symbol, mutableListOf(it))
            } else {
                allSymbol10AllMuMap.get(it.symbol)?.add(it)
                if (allSymbol10AllMuMap.get(it.symbol)!!.size > 3600) {
                    allSymbol10AllMuMap.get(it.symbol)!!.removeAt(0)
                }
            }
        }

        adapterList.forEach { symbolAadapterEntity ->
            allSymbol10AllMuMap.forEach {
                if (it.key.equals(symbolAadapterEntity.coinEntity.symbol)) {
                    symbolAadapterEntity.symbol = it.value.last()
                    if (symbolAadapterEntity.mustHeighPrice == BigDecimal.ZERO) {
                        symbolAadapterEntity.mustHeighPrice = symbolAadapterEntity.symbol.high
                    }
                    getFiveSecondsAverageTradingVolume(symbolAadapterEntity, it.value, index)
                }
            }
        }
        val c1: Comparator<SymbolAdapterEntity> = Comparator { o1, o2 ->
            if (o2.lastTradingVolume != o1.lastTradingVolume) {
                o2.lastTradingVolume.compareTo(o1.lastTradingVolume)
            } else {
                if (o1.gain24 != null && o2.gain24 != null) {
                    o2.gain24.compareTo(o1.gain24)
                } else {
                    0
                }
            }
        }

        if (AppConfig.instance.sortByCoin) {
            adapterList.sortBy { it.symbol.symbol }
        } else if (AppConfig.instance.sortByGain24) {
            adapterList.sortByDescending { it.gain24 }
        } else if (AppConfig.instance.sortByGain5m) {
            adapterList.sortByDescending { it.lastTradingVolume }
        } else {
            adapterList.sortWith(c1)
        }
        var time = ""
        var jiangeTime = ""
        var showTitle = getString(R.string.app_name)
        adapterList.forEachIndexed { index1, it ->
            if (it.symbol != null && index1 == 0) {
                time = TimeUtil.getTimeYear(it.symbol.eventTime)
                var xiangchaShiJian = (System.currentTimeMillis() - it.symbol.eventTime) / 1000
                jiangeTime = ("相差的时间为：" + xiangchaShiJian)
                if (xiangchaShiJian > 20) {
                    mSocket?.close(1000, "timeout")
                    KLog.i("超时重连了。。。")
                }
            }
            if (AppConfig.instance.showSymbol.equals("")) {

            } else {
                if (it.symbol.symbol.equals(AppConfig.instance.showSymbol)) {
                    showTitle = it.symbol.symbol + " -> " + it.coinEntity.decimal + "  " + it.symbol.lastPrice.setScale(it.coinEntity.decimal, BigDecimal.ROUND_HALF_UP).toPlainString() + "  " + it.symbol.priceChange + "%  (" + it.twoMinuteTradeCount + ")"
                    AppConfig.instance.allSymbolTradMuniteVulm.forEach { s, mutableList1 ->
                        if (s.equals(AppConfig.instance.showSymbol)) {
                            if (mutableList1.size > 29) {
                                runOnUiThread {
                                    mustSymbolTrade.text = mutableList1.asReversed().subList(0, 29).joinToString {
                                        "$it "
                                    }
                                }
                            } else {
                                runOnUiThread {
                                    mustSymbolTrade.text = mutableList1.asReversed().joinToString {
                                        "$it "
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //报警条件 1
            if (it.lastTradingVolume > 2) {
                if (it.lastTradingVolume < 90) {
                } else {
                    KLog.i("报警index= ${index}")
                    KLog.i(it.symbol.toString())
                    runOnUiThread { addAlarm(it, 3, index) }
                }
            }


        }
        runOnUiThread {
            var maxTradeSymbolAdapterEntity = adapterList.maxBy { it.twoMinuteTradeCount }
            if (maxTradeSymbolAdapterEntity != null) {
                if (showTitle.equals(getString(R.string.app_name)) && maxTradeSymbolAdapterEntity.symbol.lastPrice != null) {
                    showTitle = maxTradeSymbolAdapterEntity.symbol.symbol + " -> " + maxTradeSymbolAdapterEntity.symbol.lastPrice.toPlainString() + "  " + maxTradeSymbolAdapterEntity.symbol.priceChange + "%  (" + maxTradeSymbolAdapterEntity.twoMinuteTradeCount + ")"
                    AppConfig.instance.allSymbolTradMuniteVulm.forEach { s, mutableList ->
                        if (AppConfig.instance.showSymbol.equals("")) {
                            mustSymbol.setTextColor(resources.getColor(R.color.color_333))
                            if (s.equals(maxTradeSymbolAdapterEntity.coinEntity.symbol)) {
                                if (mutableList.size > 29) {
                                    mustSymbolTrade.text = mutableList.asReversed().subList(0, 29).joinToString {
                                        "$it "
                                    }
                                } else {
                                    mustSymbolTrade.text = mutableList.asReversed().joinToString {
                                        "$it "
                                    }
                                }
                            }
                        } else {
                            mustSymbol.setTextColor(resources.getColor(R.color.color_down))
                        }
                    }
                }
                tvTime.text = jiangeTime
                tvTime1.text = time + " " + "( " + maxTradeSymbolAdapterEntity.symbol.symbol + " " + maxTradeSymbolAdapterEntity.twoMinuteTradeCount + " )"
//                EventBus.getDefault().post(com.huzhipeng.coin.entity.Notification(jiangeTime + "  " + time + " " + "(" + maxTradeSymbolAdapterEntity.symbol.symbol + " " + maxTradeSymbolAdapterEntity.twoMinuteTradeCount + ")", showTitle))
                allSymbolAdapter?.notifyDataSetChanged()
            } else {
                tvTime.text = jiangeTime
                tvTime1.text = time
//                EventBus.getDefault().post(com.huzhipeng.coin.entity.Notification(jiangeTime + "  " + time, showTitle, ""))
                allSymbolAdapter?.notifyDataSetChanged()
            }
            mustSymbol.text = showTitle
        }
    }

    /**
     * 添加报警的symbol
     */
    private fun addAlarm(symbolAadapterEntity: SymbolAdapterEntity, type: Int, index: Int) {
//        if (symbolAadapterEntity.symbol.symbol.equals("BTCUSDT") && !symbolAadapterEntity.coinEntity.inIgnore) {
//            KLog.i("过滤掉比特币")
//            return
//        }
        KLog.i("触发报警了: " + symbolAadapterEntity.symbol.symbol)
        var alarmRecord = AlarmRecord()
        alarmRecord.symbol = symbolAadapterEntity.symbol.symbol
        alarmRecord.alarmTime = System.currentTimeMillis()
        alarmRecord.alarmPrice = symbolAadapterEntity.symbol.lastPrice.toPlainString()
        alarmRecord.fiveSecondStandardDeviation = symbolAadapterEntity.fiveSecondStandardDeviation.toInt()
        alarmRecord.fiveSecondsAverageTradingVolume = symbolAadapterEntity.fiveSecondsAverageTradingVolume.toInt()
        alarmRecord.alarmType = type
        alarmRecord.index = index
        alarmRecord.yimiaojiaoyiliang = symbolAadapterEntity.lastTradingVolume
        alarmRecord.gainTimeJianju = AppConfig.instance.PriceGainOfSencond
        alarmRecord.gain = symbolAadapterEntity.gain5m.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
        alarmRecord.gain24 = symbolAadapterEntity.gain24.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
        AppConfig.instance.daoSsesion.alarmRecordDao.insert(alarmRecord)
//        if (AppConfig.instance.daoSsesion.alarmRecordDao.loadAll().size > 100) {
//            AppConfig.instance.daoSsesion.alarmRecordDao.deleteAll()
//        }
        if (alarmAllMap.get(alarmRecord.symbol) == null) {
            alarmAllMap.put(alarmRecord.symbol, mutableListOf(alarmRecord))
        } else {
            alarmAllMap.get(alarmRecord.symbol)!!.add(alarmRecord)
        }
        showBaojingDialog(symbolAadapterEntity, type, alarmRecord.symbol)
    }

    /**
     * 显示报警
     */
    private fun showBaojingDialog(symbolAadapterEntity: SymbolAdapterEntity, type: Int, symbol: String) {
        var alarmList = alarmAllMap.get(symbol)

        if (AppConfig.instance.alarmVibrate) {
            showAlarmVibrate()
        }
        if (AppConfig.instance.alarmVoice) {
            showAlarmVoice()
        }
        if (alarmList!!.size > 50) {
            alarmList.removeAt(0)
        }
        if (System.currentTimeMillis() > symbolAadapterEntity.coinEntity.unIngnoreTime) {
            var textWindow = TitleTextWindow(this)
            textWindow.coinEntity = symbolAadapterEntity.coinEntity
            textWindow.show("报警的交易对为：" + symbolAadapterEntity.symbol.symbol + "-> " + symbolAadapterEntity.coinEntity.decimal + "\n 报警时价格为：" + symbolAadapterEntity.symbol.lastPrice.setScale(symbolAadapterEntity.coinEntity.decimal, BigDecimal.ROUND_HALF_UP).toPlainString() + "\n " + "5分钟涨幅为：" + symbolAadapterEntity.gain5m + "%\n 24小时涨幅为：" + symbolAadapterEntity.gain24 + "%\n 报警类型为：" + getType(type, symbolAadapterEntity))
        }
    }

    private fun getType(alarmType: Int, symbolAadapterEntity: SymbolAdapterEntity): String {
        if (alarmType == 0) {
            return "平均交易次数 : " + symbolAadapterEntity.fiveSecondsAverageTradingVolume
        } else if (alarmType == 1) {
            return "超过20秒"
        } else if (alarmType == 2) {
            return "5分钟涨幅"
        } else if (alarmType == 3) {
            return "最新一秒交易次数超过: " + symbolAadapterEntity.lastTradingVolume
        }

        return ""
    }


    /**
     * 震动
     */
    fun showAlarmVibrate() {
        mVibrator!!.vibrate(2000)
    }

    /**
     * 播放报警声音
     */
    fun showAlarmVoice() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    //分析某一个币的具体数据。
    fun getFiveSecondsAverageTradingVolume(symbolAadapterEntity: SymbolAdapterEntity, symbolList: MutableList<SymbolTickerEvent>, index: Int) {
        if (index != symbolAadapterEntity.symbol.index) {
            return
        }
        try {
            var list = arrayListOf<Long>()
            symbolAadapterEntity.gain24 = symbolAadapterEntity.symbol.priceChangePercent
//            if (symbolList.size >= 10) {
//                symbolList.forEachIndexed { index, symbol ->
//                    if (index >= symbolList.size - 9) {
//                        list.add(symbol.lastId - symbolList[index - 1].lastId)
//                    }
//                }
//                symbolAadapterEntity.fiveSecondsAverageTradingVolume = MathUtils.average(list)
//                symbolAadapterEntity.fiveSecondStandardDeviation = MathUtils.standardDeviation(list)
//                symbolAadapterEntity.isReachLow = MathUtils.reachLow(list)
//            } else {
//                if (symbolList.size > 1) {
//                    symbolList.forEachIndexed { index, symbol ->
//                        if (index >= 1) {
//                            list.add(symbol.lastId - symbolList[index - 1].lastId)
//                        }
//                    }
//                    symbolAadapterEntity.fiveSecondsAverageTradingVolume = MathUtils.average(list)
//                    symbolAadapterEntity.fiveSecondStandardDeviation = 0.toDouble()
//                } else {
//                    symbolAadapterEntity.fiveSecondsAverageTradingVolume = 0.toDouble()
//                    symbolAadapterEntity.fiveSecondStandardDeviation = 0.toDouble()
//                }
//                symbolAadapterEntity.isReachLow = false
//            }
            if (symbolList.size >= 2) {
                if (index == symbolList.last().index) {
                    symbolAadapterEntity.lastTradingVolume = symbolList[symbolList.size - 1].lastId - symbolList[symbolList.size - 2].lastId
                } else {
                    symbolAadapterEntity.lastTradingVolume = 0
                }
//                if ("KNCUSDT".equals(symbolAadapterEntity.symbol.symbol)) {
//                    KLog.i("KNCUSDT的交易量为：" + symbolAadapterEntity.lastTradingVolume)
//                    KLog.i("KNCUSDT的最后id为：" + symbolList[symbolList.size - 1].lastId)
//                }
                if (AppConfig.instance.allSymbolTradMuniteVulm.get(symbolAadapterEntity.coinEntity.symbol) == null) {
                    AppConfig.instance.allSymbolTradMuniteVulm.put(symbolAadapterEntity.coinEntity.symbol, mutableListOf(symbolAadapterEntity.lastTradingVolume))
                } else {
                    AppConfig.instance.allSymbolTradMuniteVulm.get(symbolAadapterEntity.coinEntity.symbol)?.add(symbolAadapterEntity.lastTradingVolume)
                    //记录一个小时的交易次数。
                    if (AppConfig.instance.allSymbolTradMuniteVulm.get(symbolAadapterEntity.coinEntity.symbol)!!.size > 3600) {
                        AppConfig.instance.allSymbolTradMuniteVulm.get(symbolAadapterEntity.coinEntity.symbol)!!.removeAt(0)
                    }
                }
            }
            if (symbolList.size >= AppConfig.instance.PriceGainOfSencond) {
                var gain = ((symbolList[symbolList.size - 1].lastPrice) - symbolList[symbolList.size - AppConfig.instance.PriceGainOfSencond].lastPrice).divide(symbolList[symbolList.size - AppConfig.instance.PriceGainOfSencond].lastPrice, 4, BigDecimal.ROUND_HALF_UP)
                symbolAadapterEntity.tenMinuteGain = gain.multiply(BigDecimal.valueOf(100))
            } else {
                if (symbolList.size >= 2) {
                    var gain = (symbolList[symbolList.size - 1].lastPrice - symbolList[0].lastPrice).divide(symbolList[0].lastPrice, 4, BigDecimal.ROUND_HALF_UP)
                    symbolAadapterEntity.tenMinuteGain = gain.multiply(BigDecimal.valueOf(100))
                } else {
                    symbolAadapterEntity.tenMinuteGain = BigDecimal.ZERO
                }
            }
            if (symbolList.size >= 30) {
                symbolAadapterEntity.twoMinuteTradeCount = symbolList.last().lastId - symbolList[symbolList.size - 30].lastId
            } else {
                symbolAadapterEntity.twoMinuteTradeCount = symbolList.last().lastId - symbolList.first().lastId
            }
            //存储5分钟之前的价格
            if (symbolList.size >= 300) {
                if (symbolList[0].eventTime / 1000 - symbolAadapterEntity.fiveMinuteTime <= 300) {

                } else {
                    symbolAadapterEntity.fiveMinuteTime = symbolList[symbolList.size - 301].eventTime / 1000
                    symbolAadapterEntity.fiveMinutePrice = symbolList[symbolList.size - 301].lastPrice
                }
            } else if (symbolList.size != 1) {
                if (symbolAadapterEntity.fiveMinuteTime != symbolList[0].eventTime / 1000 && symbolAadapterEntity.fiveMinuteTime != 0.toLong()) {

                } else {
                    symbolAadapterEntity.fiveMinuteTime = symbolList[0].eventTime / 1000
                    symbolAadapterEntity.fiveMinutePrice = symbolList[0].lastPrice
                }
            } else {
                symbolAadapterEntity.fiveMinuteTime = symbolList[0].eventTime / 1000
                symbolAadapterEntity.fiveMinutePrice = symbolList[0].lastPrice
            }
            symbolAadapterEntity.gain5m = ((symbolList[symbolList.size - 1].lastPrice) - symbolAadapterEntity.fiveMinutePrice).divide(symbolAadapterEntity.fiveMinutePrice, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100))
            AppConfig.instance.allSymbolMap.put(symbolAadapterEntity.coinEntity.symbol, symbolAadapterEntity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class EchoWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            mSocket = webSocket
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
            index++
            output("服务器端发送来的信息：" + text!!)
//            if (!text!!.contains("BTC")) {
//                output("服务器端发送来的信息：" + text!!)
//            } else {
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
                result.index = index
                result.setEventType(jsonWrapper.e)
                result.setEventTime(jsonWrapper.E)
                result.setSymbol(jsonWrapper.s)
                result.setPriceChange(jsonWrapper.p.toBigDecimal())
                result.setPriceChangePercent(jsonWrapper.P.toBigDecimal())
                result.setWeightedAvgPrice(jsonWrapper.w.toBigDecimal())
                result.setLastPrice(jsonWrapper.c.toBigDecimal())
                result.setLastQty(jsonWrapper.Q.toBigDecimal())
                result.setOpen(jsonWrapper.o.toBigDecimal())
                result.setHigh(jsonWrapper.h.toBigDecimal())
                result.setLow(jsonWrapper.l.toBigDecimal())
                result.setTotalTradedBaseAssetVolume(jsonWrapper.v.toBigDecimal())
                result.setTotalTradedQuoteAssetVolume(jsonWrapper.q.toBigDecimal())
                result.setOpenTime(jsonWrapper.O.toLong())
                result.setCloseTime(jsonWrapper.C.toLong())
                result.setFirstId(jsonWrapper.F.toLong())
                result.setLastId(jsonWrapper.L.toLong())
                result.setCount(jsonWrapper.n.toLong())
                mutableList.add(result)
            }
            try {
                handlerData(mutableList, index)
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
                allSymbol10AllMuMap.clear()
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
                allSymbol10AllMuMap.clear()
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
        socketListener.onClosed(mSocket, 0, "closeApp")
        if (mSocket != null) {
            mSocket!!.close(1000, null)
            KLog.i("关闭连接。。")
        }
        mOkHttpClient.dispatcher().cancelAll()
        super.onDestroy()
        System.exit(0)
    }


    private fun output(text: String) {
        runOnUiThread {
            KLog.e("text: $text")
        }
    }
}
