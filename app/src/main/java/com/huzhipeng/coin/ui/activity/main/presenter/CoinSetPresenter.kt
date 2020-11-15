package com.huzhipeng.coin.ui.activity.main.presenter
import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.main.contract.CoinSetContract
import com.huzhipeng.coin.ui.activity.main.CoinSetActivity
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: presenter of CoinSetActivity
 * @date 2019/05/12 12:30:51
 */
class CoinSetPresenter @Inject
constructor(internal var httpAPIWrapper: HttpAPIWrapper, private val mView: CoinSetContract.View) : CoinSetContract.CoinSetContractPresenter {

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