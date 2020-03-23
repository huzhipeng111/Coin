package com.huzhipeng.coin.ui.activity.main

import android.os.Bundle
import com.huzhipeng.coin.R

import com.huzhipeng.coin.application.AppConfig
import com.huzhipeng.coin.base.BaseActivity
import com.huzhipeng.coin.db.CoinEntityDao
import com.huzhipeng.coin.ui.activity.main.component.DaggerCoinDetailComponent
import com.huzhipeng.coin.ui.activity.main.contract.CoinDetailContract
import com.huzhipeng.coin.ui.activity.main.module.CoinDetailModule
import com.huzhipeng.coin.ui.activity.main.presenter.CoinDetailPresenter
import com.socks.library.KLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_coin_detail.*
import java.util.concurrent.TimeUnit

import javax.inject.Inject;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: $description
 * @date 2019/05/31 17:30:27
 */

class CoinDetailActivity : BaseActivity(), CoinDetailContract.View {

    @Inject
    internal lateinit var mPresenter: CoinDetailPresenter

    private var mDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_coin_detail)
    }
    override fun initData() {
        title.text = intent.getStringExtra("coin")
        AppConfig.instance.allSymbolTradMuniteVulm.forEach { s, mutableList ->
            if (s.equals(intent.getStringExtra("coin"))) {
                tvCoinAmount.text = AppConfig.instance.daoSsesion.coinEntityDao.queryBuilder().where(CoinEntityDao.Properties.Symbol.eq(s)).list()[0].amount.toString() + " 亿"
                var str = StringBuilder()
                str.append(s)
                mutableList.forEach {
                    str.append("  " + it)
                }
                tvContent.text = str
                var symbolAadapterEntity = AppConfig.instance.allSymbolMap.get(s)!!
                coinHangQing.text = " 价格为：" + symbolAadapterEntity.symbol.getc() + "\n " + "5分钟涨幅为：" + symbolAadapterEntity.gain5m + "%\n 24小时" +
                        "涨幅为：" + symbolAadapterEntity.gain24 + "%\n 10秒钟的涨幅为：" + symbolAadapterEntity.tenMinuteGain + "%\n 5秒平均交易量为：" + symbolAadapterEntity.fiveSecondsAverageTradingVolume + " \n 5秒交易量标准差为：" + symbolAadapterEntity.fiveSecondStandardDeviation
            }
        }

        KLog.i("看每个token的交易情况")
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(1000)//设置截取前20次
                .doOnSubscribe(object : Consumer<Disposable> {
                    override fun accept(t: Disposable?) {
                        this@CoinDetailActivity.mDisposable = t
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    AppConfig.instance.allSymbolTradMuniteVulm.forEach { s, mutableList ->
                        if (s.equals(intent.getStringExtra("coin"))) {
                            var str = StringBuilder()
                            str.append(s)
                            var list = arrayListOf<Int>()
                            list.addAll(mutableList)
                            list.reverse()
                            list.forEach {
                                str.append("  " + it)
                            }
                            tvContent.text = str.toString()
                            var symbolAadapterEntity = AppConfig.instance.allSymbolMap.get(s)!!
                            coinHangQing.text = " 价格为：" + symbolAadapterEntity.symbol.getc() + "\n " + "5分钟涨幅为：" + symbolAadapterEntity.gain5m + "%\n 24小时" +
                                    "涨幅为：" + symbolAadapterEntity.gain24 + "%\n 10秒钟的涨幅为：" + symbolAadapterEntity.tenMinuteGain + "%\n 5秒平均交易量为：" + symbolAadapterEntity.fiveSecondsAverageTradingVolume + " \n 5秒交易量标准差为：" + symbolAadapterEntity.fiveSecondStandardDeviation
                        }
                    }
                }
    }

    override fun setupActivityComponent() {
       DaggerCoinDetailComponent
               .builder()
               .appComponent((application as AppConfig).applicationComponent)
               .coinDetailModule(CoinDetailModule(this))
               .build()
               .inject(this)
    }
    override fun setPresenter(presenter: CoinDetailContract.CoinDetailContractPresenter) {
            mPresenter = presenter as CoinDetailPresenter
        }

    override fun onDestroy() {
        mDisposable?.dispose()
        super.onDestroy()
    }

    override fun showProgressDialog() {
    }

    override fun closeProgressDialog() {
    }

}