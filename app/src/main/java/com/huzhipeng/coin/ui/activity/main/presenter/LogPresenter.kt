package com.huzhipeng.coin.ui.activity.main.presenter
import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.main.contract.LogContract
import com.huzhipeng.coin.ui.activity.main.LogActivity
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: presenter of LogActivity
 * @date 2019/05/24 21:46:46
 */
class LogPresenter @Inject
constructor(internal var httpAPIWrapper: HttpAPIWrapper, private val mView: LogContract.View) : LogContract.LogContractPresenter {

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