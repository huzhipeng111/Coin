package com.huzhipeng.coin.ui.activity.main.contract

import com.huzhipeng.coin.ui.activity.base.BasePresenter
import com.huzhipeng.coin.ui.activity.base.BaseView
/**
 * @author hzp
 * @Package The contract for SplashActivity
 * @Description: $description
 * @date 2019/05/12 12:04:50
 */
interface SplashContract {
    interface View : BaseView<SplashContractPresenter> {
        /**
         *
         */
        fun showProgressDialog()

        /**
         *
         */
        fun closeProgressDialog()
    }

    interface SplashContractPresenter : BasePresenter {
//        /**
//         *
//         */
//        fun getBusinessInfo(map : Map)
    }
}