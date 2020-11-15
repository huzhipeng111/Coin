package com.huzhipeng.coin.ui.activity.main.presenter
import com.huzhipeng.coin.data.api.HttpAPIWrapper
import com.huzhipeng.coin.ui.activity.main.contract.CourseContract
import com.huzhipeng.coin.ui.activity.main.CourseActivity
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: presenter of CourseActivity
 * @date 2019/05/15 10:19:42
 */
class CoursePresenter @Inject
constructor(internal var httpAPIWrapper: HttpAPIWrapper, private val mView: CourseContract.View) : CourseContract.CourseContractPresenter {

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