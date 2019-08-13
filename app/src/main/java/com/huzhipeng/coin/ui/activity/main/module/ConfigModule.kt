package com.huzhipeng.coin.ui.activity.main.module

import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.ConfigActivity
import com.huzhipeng.coin.ui.activity.main.contract.ConfigContract
import com.huzhipeng.coin.ui.activity.main.presenter.ConfigPresenter

import dagger.Module;
import dagger.Provides;

/**
 * @author hzp
 * @Package com.huzhipeng.kotlindemo.ui.activity.main
 * @Description: The moduele of ConfigActivity, provide field for ConfigActivity
 * @date 2019/05/11 16:30:00
 */
@Module
class ConfigModule (private val mView: ConfigContract.View) {

    @Provides
    @ActivityScope
    fun provideConfigPresenter(httpAPIWrapper: HttpAPIWrapper) :ConfigPresenter {
        return ConfigPresenter(httpAPIWrapper, mView)
    }

    @Provides
    @ActivityScope
    fun provideConfigActivity() : ConfigActivity {
        return mView as ConfigActivity
    }
}