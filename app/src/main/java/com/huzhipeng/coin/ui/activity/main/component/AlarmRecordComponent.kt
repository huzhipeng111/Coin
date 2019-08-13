package com.huzhipeng.coin.ui.activity.main.component

import com.huzhipeng.coin.application.AppComponent
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.AlarmRecordActivity
import com.huzhipeng.coin.ui.activity.main.module.AlarmRecordModule

import dagger.Component

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The component for AlarmRecordActivity
 * @date 2019/05/13 12:55:42
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(AlarmRecordModule::class))
interface AlarmRecordComponent {
    fun inject(AlarmRecordActivity: AlarmRecordActivity): AlarmRecordActivity
}