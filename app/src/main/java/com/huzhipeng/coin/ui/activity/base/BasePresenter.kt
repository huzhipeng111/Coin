package com.huzhipeng.coin.ui.activity.base

/**
 * @author 基本控制器接口
 * @desc 功能描述
 * @date 2016/7/20 16:22
 */
interface BasePresenter {

    fun subscribe()

    //此方法不调用会造成内存泄漏
    fun unsubscribe()

}
