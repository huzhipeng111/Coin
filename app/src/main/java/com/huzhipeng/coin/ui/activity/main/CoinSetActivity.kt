package com.huzhipeng.coin.ui.activity.main

import android.os.Bundle
import com.huzhipeng.coin.R

import com.huzhipeng.coin.application.AppConfig
import com.huzhipeng.coin.base.BaseActivity
import com.huzhipeng.coin.db.CoinEntity
import com.huzhipeng.coin.ui.activity.main.component.DaggerCoinSetComponent
import com.huzhipeng.coin.ui.activity.main.contract.CoinSetContract
import com.huzhipeng.coin.ui.activity.main.module.CoinSetModule
import com.huzhipeng.coin.ui.activity.main.presenter.CoinSetPresenter
import com.huzhipeng.coin.ui.adapter.CoinSetAdapter
import kotlinx.android.synthetic.main.activity_coin_set.*

import javax.inject.Inject;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: $description
 * @date 2019/05/12 12:30:51
 */

class CoinSetActivity : BaseActivity(), CoinSetContract.View {

    @Inject
    internal lateinit var mPresenter: CoinSetPresenter
    lateinit var coinList: MutableList<CoinEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_coin_set)
    }
    override fun initData() {
        title.text = getString(R.string.coinset)
        coinList = AppConfig.instance.daoSsesion.coinEntityDao.loadAll()
        recyclerview.adapter = CoinSetAdapter(coinList)
    }

    override fun setupActivityComponent() {
       DaggerCoinSetComponent
               .builder()
               .appComponent((application as AppConfig).applicationComponent)
               .coinSetModule(CoinSetModule(this))
               .build()
               .inject(this)
    }
    override fun setPresenter(presenter: CoinSetContract.CoinSetContractPresenter) {
            mPresenter = presenter as CoinSetPresenter
        }

    override fun showProgressDialog() {

    }

    override fun closeProgressDialog() {

    }

}