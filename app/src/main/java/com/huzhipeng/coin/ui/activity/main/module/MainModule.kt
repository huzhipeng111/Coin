package com.huzhipeng.coin.ui.activity.main.module


import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.MainActivity
import com.huzhipeng.coin.ui.activity.main.contract.MainContract
import com.huzhipeng.coin.ui.activity.main.presenter.MainPresenter

import dagger.Module
import dagger.Provides

/**
 * @author hzp
 * @Package com.stratagile.qlink.ui.activity.main
 * @Description: The moduele of MainActivity, provide field for MainActivity
 * @date 2018/01/09 09:57:09
 */
@Module
class MainModule(private val mView: MainContract.View) {

    @Provides
    @ActivityScope
    fun provideMainPresenter(httpAPIWrapper: HttpAPIWrapper, mActivity: MainActivity): MainPresenter {
        return MainPresenter(httpAPIWrapper, mView, mActivity)
    }

    @Provides
    @ActivityScope
    fun provideMainActivity(): MainActivity {
        return mView as MainActivity
    }
}