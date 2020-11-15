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