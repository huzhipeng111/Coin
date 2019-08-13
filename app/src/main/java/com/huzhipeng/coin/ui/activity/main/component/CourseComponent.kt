package com.huzhipeng.coin.ui.activity.main.component

import com.huzhipeng.coin.application.AppComponent
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.CourseActivity
import com.huzhipeng.coin.ui.activity.main.module.CourseModule

import dagger.Component

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The component for CourseActivity
 * @date 2019/05/15 10:19:42
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(CourseModule::class))
interface CourseComponent {
    fun inject(CourseActivity: CourseActivity): CourseActivity
}