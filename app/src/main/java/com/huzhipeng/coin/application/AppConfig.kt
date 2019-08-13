package com.huzhipeng.coin.application

import android.support.multidex.MultiDexApplication
import com.huzhipeng.coin.BuildConfig
import com.huzhipeng.coin.constant.ConstantValue
import com.huzhipeng.coin.db.DaoMaster
import com.huzhipeng.coin.db.DaoSession
import com.huzhipeng.coin.db.MySQLiteOpenHelper
import com.huzhipeng.coin.ui.adapter.SymbolAdapterEntity
import com.huzhipeng.coin.utils.SpUtil
import com.socks.library.KLog
import com.tencent.bugly.crashreport.CrashReport
import java.util.*

/**
 * 作者：Android on 2017/8/1
 * 邮箱：365941593@qq.com
 * 描述：
 */

class AppConfig : MultiDexApplication() {
    var isBackGroud = false
    var Numberofalarms = 30
    var Number5Muniteofalarms = 10
    var PriceGainOfSencond = 10
    var PriceGain = 10
    var TransactionGainOfSecond = 10
    var TransactionGain = 10
    var alarmVoice = true
    var alarmVibrate = true
    var observationBtc = true
    var sortByCoin = false
    var sortByGain24 = false
    var sortByGain5m = false
    var showLaji = true
    var showZhuliu = true
    var alarmAutoDismiss = true
    var showSymbol = ""
    lateinit var allSymbolTradMuniteVulm : HashMap<String, MutableList<Int>>
    lateinit var allSymbolMap : HashMap<String, SymbolAdapterEntity>
    lateinit var daoSsesion : DaoSession
    var applicationComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        allSymbolTradMuniteVulm = hashMapOf()
        allSymbolMap = hashMapOf()
        setupApplicationComponent()
        CrashReport.initCrashReport(this, "41de61a0b3", BuildConfig.LOG_DEBUG)
        Numberofalarms = SpUtil.getInt(this, ConstantValue.Numberofalarms, 60)
        Number5Muniteofalarms = SpUtil.getInt(this, ConstantValue.Number5Muniteofalarms, 10)
        PriceGainOfSencond = SpUtil.getInt(this, ConstantValue.PriceGainOfSencond, 10)
        PriceGain = SpUtil.getInt(this, ConstantValue.PriceGain, 10)
        TransactionGainOfSecond = SpUtil.getInt(this, ConstantValue.TransactionGainOfSecond, 10)
        TransactionGain = SpUtil.getInt(this, ConstantValue.TransactionGain, 10)
        alarmVoice = SpUtil.getBoolean(this, ConstantValue.alarmVoice, true)
        alarmVibrate = SpUtil.getBoolean(this, ConstantValue.alarmVibrate, true)
        observationBtc = SpUtil.getBoolean(this, ConstantValue.observationBtc, true)
        showLaji = SpUtil.getBoolean(this, ConstantValue.showLaji, true)
        showZhuliu = SpUtil.getBoolean(this, ConstantValue.showZhuliu, true)
        sortByCoin = SpUtil.getBoolean(this, ConstantValue.sortByCoin, false)
        sortByGain24 = SpUtil.getBoolean(this, ConstantValue.sortByGain24, false)
        sortByGain5m = SpUtil.getBoolean(this, ConstantValue.sortByGain5m, false)
        alarmAutoDismiss = SpUtil.getBoolean(this, ConstantValue.alarmAutoDismiss, false)
        val helper = MySQLiteOpenHelper(this, "coin-db", null)
        daoSsesion = DaoMaster(helper.getWritableDatabase()).newSession()
        initResumeListener()
    }


    protected fun setupApplicationComponent() {
        applicationComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .aPIModule(APIModule(this))
                .build()
        applicationComponent!!.inject(this)
    }

    companion object {
        lateinit var instance: AppConfig
    }

    private fun initResumeListener() {
        ForegroundCallbacks.init(this)
        ForegroundCallbacks.getInstance().addListener(object : ForegroundCallbacks.Listener {
            override fun onBecameForeground() {
                KLog.i("当前程序切换到前台")
                isBackGroud = false
            }

            override fun onBecameBackground() {
                KLog.i("当前程序切换到后台")
                isBackGroud = true
            }
        })
    }

    @Synchronized
    fun getInstance(): AppConfig {
        if (null == instance) {
            instance = AppConfig()
        }
        return instance as AppConfig
    }


}
