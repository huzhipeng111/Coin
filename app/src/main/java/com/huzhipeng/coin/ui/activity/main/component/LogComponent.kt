package com.huzhipeng.coin.ui.activity.main.component

import com.huzhipeng.coin.application.AppComponent
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.LogActivity
import com.huzhipeng.coin.ui.activity.main.module.LogModule

import dagger.Component

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The component for LogActivity
 * @date 2019/05/24 21:46:46
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(LogModule::class))
interface LogComponent {
    fun inject(LogActivity: LogActivity): LogActivity
}