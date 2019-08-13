package com.huzhipeng.coin.ui.activity.main.presenter
import android.support.annotation.NonNull
import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.main.contract.SplashContract
import com.huzhipeng.coin.ui.activity.main.SplashActivity
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: presenter of SplashActivity
 * @date 2019/05/12 12:04:50
 */
class SplashPresenter @Inject
constructor(internal var httpAPIWrapper: HttpAPIWrapper, private val mView: SplashContract.View) : SplashContract.SplashContractPresenter {

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