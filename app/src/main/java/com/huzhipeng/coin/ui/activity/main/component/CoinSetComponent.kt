package com.huzhipeng.coin.ui.activity.main.component

import com.huzhipeng.coin.application.AppComponent
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.CoinSetActivity
import com.huzhipeng.coin.ui.activity.main.module.CoinSetModule

import dagger.Component

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The component for CoinSetActivity
 * @date 2019/05/12 12:30:51
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(CoinSetModule::class))
interface CoinSetComponent {
    fun inject(CoinSetActivity: CoinSetActivity): CoinSetActivity
}