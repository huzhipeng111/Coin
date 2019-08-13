package com.huzhipeng.coin.ui.activity.main.module

import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.base.ActivityScope
import com.huzhipeng.coin.ui.activity.main.AlarmRecordActivity
import com.huzhipeng.coin.ui.activity.main.contract.AlarmRecordContract
import com.huzhipeng.coin.ui.activity.main.presenter.AlarmRecordPresenter

import dagger.Module;
import dagger.Provides;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: The moduele of AlarmRecordActivity, provide field for AlarmRecordActivity
 * @date 2019/05/13 12:55:42
 */
@Module
class AlarmRecordModule (private val mView: AlarmRecordContract.View) {

    @Provides
    @ActivityScope
    fun provideAlarmRecordPresenter(httpAPIWrapper: HttpAPIWrapper) :AlarmRecordPresenter {
        return AlarmRecordPresenter(httpAPIWrapper, mView)
    }

    @Provides
    @ActivityScope
    fun provideAlarmRecordActivity() : AlarmRecordActivity {
        return mView as AlarmRecordActivity
    }
}