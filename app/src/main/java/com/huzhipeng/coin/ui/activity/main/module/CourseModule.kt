package com.huzhipeng.coin.ui.activity.main.module

import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.CourseActivity
import com.huzhipeng.coin.ui.activity.main.contract.CourseContract
import com.huzhipeng.coin.ui.activity.main.presenter.CoursePresenter

import dagger.Module;
import dagger.Provides;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The moduele of CourseActivity, provide field for CourseActivity
 * @date 2019/05/15 10:19:42
 */
@Module
class CourseModule (private val mView: CourseContract.View) {

    @Provides
    @ActivityScope
    fun provideCoursePresenter(httpAPIWrapper: HttpAPIWrapper) :CoursePresenter {
        return CoursePresenter(httpAPIWrapper, mView)
    }

    @Provides
    @ActivityScope
    fun provideCourseActivity() : CourseActivity {
        return mView as CourseActivity
    }
}