package com.huzhipeng.coin.ui.activity.main.component

import com.huzhipeng.coin.application.AppComponent
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.CoinDetailActivity
import com.huzhipeng.coin.ui.activity.main.module.CoinDetailModule

import dagger.Component

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The component for CoinDetailActivity
 * @date 2019/05/31 17:30:27
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(CoinDetailModule::class))
interface CoinDetailComponent {
    fun inject(CoinDetailActivity: CoinDetailActivity): CoinDetailActivity
}