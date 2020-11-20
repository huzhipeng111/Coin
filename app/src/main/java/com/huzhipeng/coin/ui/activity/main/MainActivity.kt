package com.huzhipeng.coin.ui.activity.main

import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.media.Ringtone
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.provider.Settings
import android.view.InflateException
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.LayoutInflaterFactory
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
import com.huzhipeng.coin.ui.adapter.SymbolAdapterEntity
import com.huzhipeng.coin.utils.SpUtil
import com.huzhipeng.coin.view.BottomSheet
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
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * https://blog.csdn.net/Jeff_YaoJie/article/details/79164507
 */
class MainActivity : BaseActivity(), MainContract.View {
    lateinit var coinList: MutableList<CoinEntity>
    lateinit var mOkHttpClient: OkHttpClient
    lateinit var adapterList: MutableList<SymbolAdapterEntity>
    var mVibrator: Vibrator? = null
    var sortOrder = SortOrder.zhangfu5s
    val timeJianju = 30
    var index = 0
    lateinit var ringtone: Ringtone
    lateinit var mediaPlayer: MediaPlayer
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

        EventBus.getDefault().register(this)
        mVibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        setMonitorCoin(CoinEntity())
        mediaPlayer = MediaPlayer.create(this, R.raw.music1)
        mediaPlayer.setVolume(1f, 1f)
        mediaPlayer.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer?) {
                mediaPlayer.start()
            }

        })
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
        coinList = AppConfig.instance.daoSsesion.coinEntityDao.loadAll()
        coinList.forEach {
            adapterList.add(SymbolAdapterEntity(it, SymbolTickerEvent(it.symbol)))
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
        adapterList.forEach { symbolAadapterEntity ->
            mutableList.forEach {
                if (it.symbol.equals(symbolAadapterEntity.coinEntity!!.symbol)) {
                    symbolAadapterEntity.symbol = it
                    symbolAadapterEntity.update(index)
                    //破新高报警
                    if (symbolAadapterEntity.fiveMinuteHighTimes >= 5 && symbolAadapterEntity.highPriceList!!.size > 20 && (symbolAadapterEntity.highPriceList!!.last().lastPrice > symbolAadapterEntity.highPriceList!![symbolAadapterEntity!!.highPriceList!!.size - 2].lastPrice) && symbolAadapterEntity.symbol.lastPrice >= symbolAadapterEntity.symbol.high) {
                        runOnUiThread {
                            addAlarm(symbolAadapterEntity, 0, index)
                        }
                    }
                    //破新低报警
                    if (symbolAadapterEntity.fiveMinuteLowTimes >= 5 && symbolAadapterEntity.lowPriceList!!.size > 20 && (symbolAadapterEntity.lowPriceList!!.last().lastPrice < symbolAadapterEntity.lowPriceList!![symbolAadapterEntity!!.highPriceList!!.size - 2].lastPrice) && symbolAadapterEntity.symbol.lastPrice <= symbolAadapterEntity.symbol.low) {
                        runOnUiThread {
                            addAlarm(symbolAadapterEntity, 1, index)
                        }
                    }
                    //达到设定的低价报警
                    if (symbolAadapterEntity.coinEntity.lowPrice != 0.toFloat() && symbolAadapterEntity.symbol.lastPrice <= symbolAadapterEntity.coinEntity.lowPrice.toBigDecimal()) {
                        runOnUiThread {
                            addAlarm(symbolAadapterEntity, 2, index)
                        }
                    }
                    //达到设定的高价报警
                    if (symbolAadapterEntity.coinEntity.highPrice != 0.toFloat() && symbolAadapterEntity.symbol.lastPrice >= symbolAadapterEntity.coinEntity.highPrice.toBigDecimal()) {
                        runOnUiThread {
                            addAlarm(symbolAadapterEntity, 3, index)
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

        if (AppConfig.instance.sortByCoin) {
            adapterList.sortBy { it.symbol!!.symbol }
        } else if (AppConfig.instance.sortByGain24) {
            adapterList.sortByDescending { it.gain24 }
        } else {
            adapterList.sortWith(c1)
        }
        adapterList.forEachIndexed { index1, it ->
            if (it.symbol != null && index1 == 0) {
                var xiangchaShiJian = (System.currentTimeMillis() - it.symbol!!.eventTime) / 1000
                if (xiangchaShiJian > 20) {
                    mSocket?.close(1000, "timeout")
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
    private fun addAlarm(symbolAadapterEntity: SymbolAdapterEntity, type: Int, index: Int) {
        KLog.i("触发报警了: " + symbolAadapterEntity.symbol.symbol)
        var alarmRecord = AlarmRecord()
        alarmRecord.symbol = symbolAadapterEntity.symbol.symbol
        alarmRecord.alarmTime = System.currentTimeMillis()
        alarmRecord.alarmPrice = symbolAadapterEntity.symbol.lastPrice.toPlainString()
        alarmRecord.alarmType = type
        alarmRecord.index = index
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
            symbolAadapterEntity.coinEntity.highPrice = etHighPrice.text.toString().toFloat()
            AppConfig.instance.daoSsesion.coinEntityDao.update(symbolAadapterEntity.coinEntity)
            toast("更新成功")
        }

        tvConfirmLow.setOnClickListener {
            symbolAadapterEntity.coinEntity.lowPrice = etLowPrice.text.toString().toFloat()
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
    private fun showBaojingDialog(symbolAadapterEntity: SymbolAdapterEntity, type: Int, symbol: String) {
        if (System.currentTimeMillis() < symbolAadapterEntity.coinEntity.unIngnoreTime) {
            return
        }
        if (AppConfig.instance.alarmVibrate) {
            showAlarmVibrate()
        }
        if (AppConfig.instance.alarmVoice) {
            showAlarmVoice(type)
        }
        var textWindow = TitleTextWindow(this)
        textWindow.coinEntity = symbolAadapterEntity.coinEntity
        textWindow.show("报警的交易对为：" + symbolAadapterEntity.symbol.symbol + "-> " + symbolAadapterEntity.coinEntity!!.decimal + "\n 报警时价格为：" + symbolAadapterEntity.symbol.lastPrice.setScale(symbolAadapterEntity.coinEntity!!.decimal, BigDecimal.ROUND_HALF_UP).toPlainString() + "\n " + "5分钟涨幅为：" + symbolAadapterEntity.gain5m + "%\n 24小时涨幅为：" + symbolAadapterEntity.gain24 + "%\n 报警类型为：" + getType(type, symbolAadapterEntity))
    }

    private fun getType(alarmType: Int, symbolAadapterEntity: SymbolAdapterEntity): String {
        if (alarmType == 0) {
            return "破新高"
        } else if (alarmType == 1) {
            return "破新低"
        } else if (alarmType == 2) {
            return "达到设定低价"
        } else if (alarmType == 3) {
            return "达到设定高价"
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
    fun showAlarmVoice(type: Int) {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.seekTo(0)
            try {
                when(type) {
                    0 -> {
                        val afd: AssetFileDescriptor = getResources().openRawResourceFd(R.raw.music5)
                        mediaPlayer.setDataSource(afd)
                    }
                    1 -> {
                        val afd: AssetFileDescriptor = getResources().openRawResourceFd(R.raw.music2)
                        mediaPlayer.setDataSource(afd)
                    }
                    2 -> {
                        val afd: AssetFileDescriptor = getResources().openRawResourceFd(R.raw.music3)
                        mediaPlayer.setDataSource(afd)
                    }
                    3 -> {
                        val afd: AssetFileDescriptor = getResources().openRawResourceFd(R.raw.music4)
                        mediaPlayer.setDataSource(afd)
                    }
                }
                mediaPlayer.prepareAsync()
            } catch (e :java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }



    override fun onBackPressed() {
        moveTaskToBack(true)
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
                result.index = index

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
