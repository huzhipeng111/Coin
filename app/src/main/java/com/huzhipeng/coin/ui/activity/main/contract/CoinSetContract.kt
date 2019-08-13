package com.huzhipeng.coin.ui.activity.main.contract

import com.huzhipeng.coin.ui.activity.base.BasePresenter
import com.huzhipeng.coin.ui.activity.base.BaseView
/**
 * @author hzp
 * @Package The contract for CoinSetActivity
 * @Description: $description
 * @date 2019/05/12 12:30:51
 */
interface CoinSetContract {
    interface View : BaseView<CoinSetContractPresenter> {
        /**
         *
         */
        fun showProgressDialog()

        /**
         *
         */
        fun closeProgressDialog()
    }

    interface CoinSetContractPresenter : BasePresenter {
//        /**
//         *
//         */
//        fun getBusinessInfo(map : Map)
    }
}