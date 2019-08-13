package com.huzhipeng.coin.ui.activity.main.contract

import com.huzhipeng.coin.ui.activity.base.BasePresenter
import com.huzhipeng.coin.ui.activity.base.BaseView
/**
 * @author hzp
 * @Package The contract for ConfigActivity
 * @Description: $description
 * @date 2019/05/11 16:30:00
 */
interface ConfigContract {
    interface View : BaseView<ConfigContractPresenter> {
        /**
         *
         */
        fun showProgressDialog()

        /**
         *
         */
        fun closeProgressDialog()
    }

    interface ConfigContractPresenter : BasePresenter {
//        /**
//         *
//         */
//        fun getBusinessInfo(map : Map)
    }
}