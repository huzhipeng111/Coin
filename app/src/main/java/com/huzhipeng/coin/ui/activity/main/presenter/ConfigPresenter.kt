package com.huzhipeng.coin.ui.activity.main.presenter
import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.main.contract.ConfigContract
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable

/**
 * @author hzp
 * @Package com.huzhipeng.kotlindemo.ui.activity.main
 * @Description: presenter of ConfigActivity
 * @date 2019/05/11 16:30:00
 */
class ConfigPresenter @Inject
constructor(internal var httpAPIWrapper: HttpAPIWrapper, private val mView: ConfigContract.View) : ConfigContract.ConfigContractPresenter {

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