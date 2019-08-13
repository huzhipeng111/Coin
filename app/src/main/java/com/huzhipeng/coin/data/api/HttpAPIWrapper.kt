package com.huzhipeng.coin.data.api


import com.huzhipeng.coin.entity.BaseBack

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function

/**
 * @author hu
 * @desc 对Request实体(不执行)在执行时所调度的线程，以及得到ResponseBody后根据retCode对Result进行进一步处理
 * @date 2017/5/31 16:56
 */
class HttpAPIWrapper @Inject
constructor(private val mHttpAPI: HttpApi) {


    /**
     * 给任何Http的Observable加上通用的线程调度器
     */
    //    private static final ObservableTransformer SCHEDULERS_TRANSFORMER = new ObservableTransformer() {
    //        @Override
    //        public ObservableSource apply(@NonNull Observable upstream) {
    //            return upstream.subscribeOn(Schedulers.io())
    //                    .observeOn(AndroidSchedulers.mainThread());
    //        }
    //    };

    private fun <T : BaseBack> wrapper(resourceObservable: Observable<T>): Observable<T> {
        return resourceObservable
                .flatMap(Function<T, ObservableSource<out T>> { baseResponse ->
                    Observable.create { e ->
                        if (baseResponse.code != "0") {
                            e.onComplete()
                        } else {
                            e.onNext(baseResponse)
                        }
                    }
                })
                /**
                 * 网络错误： You've encountered a network error!
                 * 请打开网络：Please open your network.
                 * 请求超时：The request has timed out.
                 * 连接失败: Connection failed.
                 * 请求失败： The request has failed.
                 */
                .doOnError { }
    }


}
