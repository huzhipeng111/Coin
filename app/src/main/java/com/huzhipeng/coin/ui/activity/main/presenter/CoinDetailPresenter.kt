package com.huzhipeng.coin.ui.activity.main.presenter
import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.main.contract.CoinDetailContract
import com.huzhipeng.coin.ui.activity.main.CoinDetailActivity
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: presenter of CoinDetailActivity
 * @date 2019/05/31 17:30:27
 */
class CoinDetailPresenter @Inject
constructor(internal var httpAPIWrapper: HttpAPIWrapper, private val mView: CoinDetailContract.View) : CoinDetailContract.CoinDetailContractPresenter {

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