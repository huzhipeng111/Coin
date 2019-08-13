package com.huzhipeng.coin.ui.activity.main.component

import com.huzhipeng.coin.application.AppComponent
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.SplashActivity
import com.huzhipeng.coin.ui.activity.main.module.SplashModule

import dagger.Component

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The component for SplashActivity
 * @date 2019/05/12 12:04:50
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(SplashModule::class))
interface SplashComponent {
    fun inject(SplashActivity: SplashActivity): SplashActivity
}