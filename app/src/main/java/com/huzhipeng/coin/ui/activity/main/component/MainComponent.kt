package com.huzhipeng.coin.ui.activity.main.component


import com.huzhipeng.coin.application.AppComponent
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.MainActivity
import com.huzhipeng.coin.ui.activity.main.module.MainModule

import dagger.Component

/**
 * @author hzp
 * @Package com.stratagile.qlink.ui.activity.main
 * @Description: The component for MainActivity
 * @date 2018/01/09 09:57:09
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(Activity: MainActivity): MainActivity
}