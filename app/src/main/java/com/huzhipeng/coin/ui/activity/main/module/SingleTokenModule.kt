package com.huzhipeng.coin.ui.activity.main.module

import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.SingleTokenActivity
import com.huzhipeng.coin.ui.activity.main.contract.SingleTokenContract
import com.huzhipeng.coin.ui.activity.main.presenter.SingleTokenPresenter

import dagger.Module;
import dagger.Provides;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The moduele of SingleTokenActivity, provide field for SingleTokenActivity
 * @date 2020/05/31 16:15:31
 */
@Module
class SingleTokenModule (private val mView: SingleTokenContract.View) {

    @Provides
    @ActivityScope
    fun provideSingleTokenPresenter(httpAPIWrapper: HttpAPIWrapper) :SingleTokenPresenter {
        return SingleTokenPresenter(httpAPIWrapper, mView)
    }

    @Provides
    @ActivityScope
    fun provideSingleTokenActivity() : SingleTokenActivity {
        return mView as SingleTokenActivity
    }
}