package com.huzhipeng.coin.ui.activity.main.presenter
import android.support.annotation.NonNull
import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.main.contract.AlarmRecordContract
import com.huzhipeng.coin.ui.activity.main.AlarmRecordActivity
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: presenter of AlarmRecordActivity
 * @date 2019/05/13 12:55:42
 */
class AlarmRecordPresenter @Inject
constructor(internal var httpAPIWrapper: HttpAPIWrapper, private val mView: AlarmRecordContract.View) : AlarmRecordContract.AlarmRecordContractPresenter {

    private val mCompositeDisposable: CompositeDisposable

    init {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }
}