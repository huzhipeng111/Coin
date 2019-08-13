package com.huzhipeng.coin.ui.activity.main.module

import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.SplashActivity
import com.huzhipeng.coin.ui.activity.main.contract.SplashContract
import com.huzhipeng.coin.ui.activity.main.presenter.SplashPresenter

import dagger.Module;
import dagger.Provides;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The moduele of SplashActivity, provide field for SplashActivity
 * @date 2019/05/12 12:04:50
 */
@Module
class SplashModule (private val mView: SplashContract.View) {

    @Provides
    @ActivityScope
    fun provideSplashPresenter(httpAPIWrapper: HttpAPIWrapper) :SplashPresenter {
        return SplashPresenter(httpAPIWrapper, mView)
    }

    @Provides
    @ActivityScope
    fun provideSplashActivity() : SplashActivity {
        return mView as SplashActivity
    }
}