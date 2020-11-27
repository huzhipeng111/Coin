package com.huzhipeng.coin.ui.activity.main

import android.content.Intent
import android.os.Bundle
import com.huzhipeng.coin.R

import com.huzhipeng.coin.application.AppConfig
import com.huzhipeng.coin.base.BaseActivity
import com.huzhipeng.coin.constant.ConstantValue
import com.huzhipeng.coin.db.CoinEntity
import com.huzhipeng.coin.ui.activity.main.component.DaggerConfigComponent
import com.huzhipeng.coin.ui.activity.main.contract.ConfigContract
import com.huzhipeng.coin.ui.activity.main.module.ConfigModule
import com.huzhipeng.coin.ui.activity.main.presenter.ConfigPresenter
import com.huzhipeng.coin.utils.SpUtil
import kotlinx.android.synthetic.main.activity_config.*
import org.greenrobot.eventbus.EventBus

import javax.inject.Inject;

/**
 * @author hzp
 * @Package com.huzhipeng.kotlindemo.ui.activity.main
 * @Description: $description
 * @date 2019/05/11 16:30:00
 * 配置文件页面
 */

class ConfigActivity : BaseActivity(), ConfigContract.View {

    @Inject
    internal lateinit var mPresenter: ConfigPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_config)
    }
    override fun initData() {
        title.text = "配置"
        Numberofalarms.setText(AppConfig.instance.Numberofalarms.toString())
        Number5Muniteofalarms.setText(AppConfig.instance.Number5Muniteofalarms.toString())
        PriceGainOfSencond.setText(AppConfig.instance.PriceGainOfSencond.toString())
        PriceGain.setText(AppConfig.instance.PriceGain.toString())
        TransactionGainOfSecond.setText(AppConfig.instance.TransactionGainOfSecond.toString())
        TransactionGain.setText(AppConfig.instance.TransactionGain.toString())
        alarmVoice.isChecked = AppConfig.instance.alarmVoice
        alarmVibrate.isChecked = AppConfig.instance.alarmVibrate
        showLaji.isChecked = AppConfig.instance.showLaji
        sleepModel.isChecked = AppConfig.instance.sleepModel
        showZhuliu.isChecked = AppConfig.instance.showZhuliu
        observationBtc.isChecked = AppConfig.instance.observationBtc
        alarmAutoDismiss.isChecked = AppConfig.instance.alarmAutoDismiss
        observationHuobi.isChecked = SpUtil.getBoolean(this, ConstantValue.observationHuobi, false)
        alarmVoice.setOnCheckedChangeListener { compoundButton, b ->
            AppConfig.instance.alarmVoice = b
            SpUtil.putBoolean(this, ConstantValue.alarmVoice, b)
        }
        alarmVibrate.setOnCheckedChangeListener { compoundButton, b ->
            AppConfig.instance.alarmVibrate = b
            SpUtil.putBoolean(this, ConstantValue.alarmVibrate, b)
        }
        showLaji.setOnCheckedChangeListener { compoundButton, b ->
            AppConfig.instance.showLaji = b
            SpUtil.putBoolean(this, ConstantValue.showLaji, b)
            EventBus.getDefault().post(CoinEntity())
        }
        sleepModel.setOnCheckedChangeListener { compoundButton, b ->
            AppConfig.instance.sleepModel = b
            SpUtil.putBoolean(this, ConstantValue.sleepModel, b)
//            EventBus.getDefault().post(CoinEntity())
        }
        showZhuliu.setOnCheckedChangeListener { compoundButton, b ->
            AppConfig.instance.showZhuliu = b
            SpUtil.putBoolean(this, ConstantValue.showZhuliu, b)
            EventBus.getDefault().post(CoinEntity())
        }
        observationBtc.setOnCheckedChangeListener { compoundButton, b ->
            AppConfig.instance.observationBtc = b
            SpUtil.putBoolean(this, ConstantValue.observationBtc, b)
            EventBus.getDefault().post(CoinEntity())
        }
        alarmAutoDismiss.setOnCheckedChangeListener { compoundButton, b ->
            AppConfig.instance.alarmAutoDismiss = b
            SpUtil.putBoolean(this, ConstantValue.alarmAutoDismiss, b)
        }
        observationHuobi.setOnCheckedChangeListener { compoundButton, b ->
            SpUtil.putBoolean(this, ConstantValue.observationHuobi, b)
            observationHuobi.postDelayed({
                val intent = Intent(this, SplashActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(0)
            }, 1000)
        }
    }

    override fun onDestroy() {
        SpUtil.putInt(this, ConstantValue.Numberofalarms, Numberofalarms.text.toString().toInt())
        SpUtil.putInt(this, ConstantValue.Number5Muniteofalarms, Number5Muniteofalarms.text.toString().toInt())
        SpUtil.putInt(this, ConstantValue.PriceGainOfSencond, PriceGainOfSencond.text.toString().toInt())
        SpUtil.putInt(this, ConstantValue.PriceGain, PriceGain.text.toString().toInt())
        SpUtil.putInt(this, ConstantValue.TransactionGainOfSecond, TransactionGainOfSecond.text.toString().toInt())
        SpUtil.putInt(this, ConstantValue.TransactionGain, TransactionGain.text.toString().toInt())
        AppConfig.instance.Numberofalarms = SpUtil.getInt(this, ConstantValue.Numberofalarms, 30)
        AppConfig.instance.Number5Muniteofalarms = SpUtil.getInt(this, ConstantValue.Number5Muniteofalarms, 10)
        AppConfig.instance.PriceGainOfSencond = SpUtil.getInt(this, ConstantValue.PriceGainOfSencond, 10)
        AppConfig.instance.PriceGain = SpUtil.getInt(this, ConstantValue.PriceGain, 10)
        AppConfig.instance.TransactionGainOfSecond = SpUtil.getInt(this, ConstantValue.TransactionGainOfSecond, 10)
        AppConfig.instance.TransactionGain = SpUtil.getInt(this, ConstantValue.TransactionGain, 10)
//        EventBus.getDefault().post(CoinEntity())
        super.onDestroy()
    }

    override fun setupActivityComponent() {
       DaggerConfigComponent
               .builder()
               .appComponent((application as AppConfig).applicationComponent)
               .configModule(ConfigModule(this))
               .build()
               .inject(this)
    }
    override fun setPresenter(presenter: ConfigContract.ConfigContractPresenter) {
            mPresenter = presenter as ConfigPresenter
        }

    override fun showProgressDialog() {
    }

    override fun closeProgressDialog() {
    }

}