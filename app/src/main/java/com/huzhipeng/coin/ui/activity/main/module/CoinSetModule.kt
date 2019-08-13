package com.huzhipeng.coin.ui.activity.main.module

import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.CoinSetActivity
import com.huzhipeng.coin.ui.activity.main.contract.CoinSetContract
import com.huzhipeng.coin.ui.activity.main.presenter.CoinSetPresenter

import dagger.Module;
import dagger.Provides;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The moduele of CoinSetActivity, provide field for CoinSetActivity
 * @date 2019/05/12 12:30:51
 */
@Module
class CoinSetModule (private val mView: CoinSetContract.View) {

    @Provides
    @ActivityScope
    fun provideCoinSetPresenter(httpAPIWrapper: HttpAPIWrapper) :CoinSetPresenter {
        return CoinSetPresenter(httpAPIWrapper, mView)
    }

    @Provides
    @ActivityScope
    fun provideCoinSetActivity() : CoinSetActivity {
        return mView as CoinSetActivity
    }
}