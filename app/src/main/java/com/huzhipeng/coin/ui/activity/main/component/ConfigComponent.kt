package com.huzhipeng.coin.ui.activity.main.component

import com.huzhipeng.coin.application.AppComponent
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.ConfigActivity
import com.huzhipeng.coin.ui.activity.main.module.ConfigModule

import dagger.Component

/**
 * @author hzp
 * @Package com.huzhipeng.kotlindemo.ui.activity.main
 * @Description: The component for ConfigActivity
 * @date 2019/05/11 16:30:00
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ConfigModule::class))
interface ConfigComponent {
    fun inject(ConfigActivity: ConfigActivity): ConfigActivity
}