package com.huzhipeng.coin.ui.activity.main.module

import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.CoinDetailActivity
import com.huzhipeng.coin.ui.activity.main.contract.CoinDetailContract
import com.huzhipeng.coin.ui.activity.main.presenter.CoinDetailPresenter

import dagger.Module;
import dagger.Provides;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The moduele of CoinDetailActivity, provide field for CoinDetailActivity
 * @date 2019/05/31 17:30:27
 */
@Module
class CoinDetailModule (private val mView: CoinDetailContract.View) {

    @Provides
    @ActivityScope
    fun provideCoinDetailPresenter(httpAPIWrapper: HttpAPIWrapper) :CoinDetailPresenter {
        return CoinDetailPresenter(httpAPIWrapper, mView)
    }

    @Provides
    @ActivityScope
    fun provideCoinDetailActivity() : CoinDetailActivity {
        return mView as CoinDetailActivity
    }
}