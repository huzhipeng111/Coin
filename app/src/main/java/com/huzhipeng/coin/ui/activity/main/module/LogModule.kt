package com.huzhipeng.coin.ui.activity.main.module

import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.LogActivity
import com.huzhipeng.coin.ui.activity.main.contract.LogContract
import com.huzhipeng.coin.ui.activity.main.presenter.LogPresenter

import dagger.Module;
import dagger.Provides;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The moduele of LogActivity, provide field for LogActivity
 * @date 2019/05/24 21:46:46
 */
@Module
class LogModule (private val mView: LogContract.View) {

    @Provides
    @ActivityScope
    fun provideLogPresenter(httpAPIWrapper: HttpAPIWrapper) :LogPresenter {
        return LogPresenter(httpAPIWrapper, mView)
    }

    @Provides
    @ActivityScope
    fun provideLogActivity() : LogActivity {
        return mView as LogActivity
    }
}