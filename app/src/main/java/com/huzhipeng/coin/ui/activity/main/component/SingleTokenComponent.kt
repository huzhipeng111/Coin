package com.huzhipeng.coin.ui.activity.main.component

import com.huzhipeng.coin.application.AppComponent
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.SingleTokenActivity
import com.huzhipeng.coin.ui.activity.main.module.SingleTokenModule

import dagger.Component

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The component for SingleTokenActivity
 * @date 2020/05/31 16:15:31
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(SingleTokenModule::class))
interface SingleTokenComponent {
    fun inject(SingleTokenActivity: SingleTokenActivity): SingleTokenActivity
}