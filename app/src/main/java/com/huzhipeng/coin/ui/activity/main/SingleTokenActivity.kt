package com.huzhipeng.coin.ui.activity.main

import android.content.Intent
import android.os.Bundle
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.huzhipeng.coin.R

import com.huzhipeng.coin.application.AppConfig
import com.huzhipeng.coin.base.BaseActivity
import com.huzhipeng.coin.entity.Symbol
import com.huzhipeng.coin.entity.Ticker
import com.huzhipeng.coin.entity.Trade
import com.huzhipeng.coin.service.BackGroundService
import com.huzhipeng.coin.ui.activity.main.component.DaggerSingleTokenComponent
import com.huzhipeng.coin.ui.activity.main.contract.SingleTokenContract
import com.huzhipeng.coin.ui.activity.main.module.SingleTokenModule
import com.huzhipeng.coin.ui.activity.main.presenter.SingleTokenPresenter
import com.huzhipeng.coin.ui.adapter.heyue.TradeAdapter
import com.huzhipeng.coin.utils.TimeUtil
import com.socks.library.KLog
import kotlinx.android.synthetic.main.activity_single_token.*
import okhttp3.*
import okio.ByteString
import org.greenrobot.eventbus.EventBus
import java.lang.Exception
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

import javax.inject.Inject;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: $description
 * @date 2020/05/31 16:15:31
 */

class SingleTokenActivity : BaseActivity(), SingleTokenContract.View {

    @Inject
    internal lateinit var mPresenter: SingleTokenPresenter
    lateinit var tickerList: MutableList<Ticker>
    lateinit var tradeList : MutableList<Trade>
    lateinit var tradeAdapter: TradeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    //全市场所有交易对的完整Ticker
//    val singleTicker = "wss://stream.binance.com:9443/ws/btcusdt@ticker"
    val singleTicker = "wss://stream.binance.com:9443/ws/btcusdt@trade"
    var index = 0

    override fun initView() {
        setContentView(R.layout.activity_single_token)
        title.text = getString(R.string.app_name)
        tradePair.text = "btcusdt"
    }

    override fun initData() {
        tickerList = arrayListOf()
        tradeList = arrayListOf()
        tradeAdapter = TradeAdapter(tradeList)
        recyclerView.adapter = tradeAdapter
        setListener()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }


    private var mSocket: WebSocket? = null
    var socketListener = EchoWebSocketListener()
    private fun setListener() {
        mOkHttpClient = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build()
        val request = Request.Builder().url(singleTicker).build()
        mOkHttpClient.newWebSocket(request, socketListener)
        mOkHttpClient.dispatcher().executorService().shutdown()
    }

    /**
     * 查看最新的10条买量和卖量
     */
    fun handlerNew10() {
        try {
            var buyAmount = 0.toFloat()
            var sellAmount = 0.toFloat()
            tradeList.take(10).forEach {
                if (it.ism()) {
                    sellAmount += it.q.toFloat()
                } else {
                    buyAmount += it.q.toFloat()
                }
            }
            tvBuy.text = buyAmount.toBigDecimal().setScale(6, BigDecimal.ROUND_HALF_UP).toPlainString()
            tvSell.text = sellAmount.toBigDecimal().setScale(6, BigDecimal.ROUND_HALF_UP).toPlainString()
        } catch (ee : Exception) {
            ee.printStackTrace()
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
            var ticker = Gson().fromJson<Trade>(text!!)
            tradeList.add(0, ticker)
            runOnUiThread {
                if (tradeList.size > 11) {
                    handlerNew10()
                }
                tvTime.text = TimeUtil.getTradeTimeMill(ticker.e)
//                tradeAdapter.addData(0, ticker)
//                if (tradeAdapter.data.size > 100) {
//                    tradeAdapter.data.removeAt(100)
//                }
                tradeAdapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(0)
            }
//            handlerTicker()
            output("服务器端发送来的信息：" + text!!)
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

    lateinit var mOkHttpClient: OkHttpClient
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

    override fun setupActivityComponent() {
        DaggerSingleTokenComponent
                .builder()
                .appComponent((application as AppConfig).applicationComponent)
                .singleTokenModule(SingleTokenModule(this))
                .build()
                .inject(this)
    }

    override fun setPresenter(presenter: SingleTokenContract.SingleTokenContractPresenter) {
        mPresenter = presenter as SingleTokenPresenter
    }

    override fun showProgressDialog() {
    }

    override fun closeProgressDialog() {
    }

}