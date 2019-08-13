package com.huzhipeng.coin.ui.activity.main

import android.content.Intent
import android.os.Bundle
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.huzhipeng.coin.R

import com.huzhipeng.coin.application.AppConfig
import com.huzhipeng.coin.base.BaseActivity
import com.huzhipeng.coin.db.CoinEntity
import com.huzhipeng.coin.entity.Symbol
import com.huzhipeng.coin.ui.activity.main.component.DaggerSplashComponent
import com.huzhipeng.coin.ui.activity.main.contract.SplashContract
import com.huzhipeng.coin.ui.activity.main.module.SplashModule
import com.huzhipeng.coin.ui.activity.main.presenter.SplashPresenter
import com.huzhipeng.coin.utils.FileUtil
import com.socks.library.KLog
import kotlinx.android.synthetic.main.activity_splash.*

import javax.inject.Inject;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: $description
 * @date 2019/05/12 12:04:50
 */

class SplashActivity : BaseActivity(), SplashContract.View {

    @Inject
    internal lateinit var mPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        needFront = true
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_splash)
    }
    override fun initData() {
        prepareData()
        appName.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }

    fun prepareData() {
        AppConfig.instance!!.daoSsesion.coinEntityDao.deleteAll()
        var list = AppConfig.instance!!.daoSsesion.coinEntityDao.loadAll()
        if (list == null || list.size == 0) {
            val coinJson = FileUtil.getAssetJson(AppConfig.instance, "defaultCoinSet.json")
            var coinEntitys = Gson().fromJson<MutableList<CoinEntity>>(coinJson)
            coinEntitys.sortBy { it.symbol }
//            KLog.i(Gson().toJson(coinEntitys))
            AppConfig.instance!!.daoSsesion.coinEntityDao.insertInTx(coinEntitys)
        }
    }

    override fun setupActivityComponent() {
       DaggerSplashComponent
               .builder()
               .appComponent((application as AppConfig).applicationComponent)
               .splashModule(SplashModule(this))
               .build()
               .inject(this)
    }
    override fun setPresenter(presenter: SplashContract.SplashContractPresenter) {
            mPresenter = presenter as SplashPresenter
        }

    override fun showProgressDialog() {

    }

    override fun closeProgressDialog() {

    }

}