package com.huzhipeng.coin.ui.activity.main.presenter
import android.support.annotation.NonNull
import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.main.contract.SingleTokenContract
import com.huzhipeng.coin.ui.activity.main.SingleTokenActivity
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: presenter of SingleTokenActivity
 * @date 2020/05/31 16:15:31
 */
class SingleTokenPresenter @Inject
constructor(internal var httpAPIWrapper: HttpAPIWrapper, private val mView: SingleTokenContract.View) : SingleTokenContract.SingleTokenContractPresenter {

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