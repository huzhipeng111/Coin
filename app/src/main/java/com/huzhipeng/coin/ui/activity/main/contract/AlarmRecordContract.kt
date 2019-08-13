package com.huzhipeng.coin.ui.activity.main.contract

import com.huzhipeng.coin.ui.activity.base.BasePresenter
import com.huzhipeng.coin.ui.activity.base.BaseView
/**
 * @author hzp
 * @Package The contract for AlarmRecordActivity
 * @Description: $description
 * @date 2019/05/13 12:55:42
 */
interface AlarmRecordContract {
    interface View : BaseView<AlarmRecordContractPresenter> {
        /**
         *
         */
        fun showProgressDialog()

        /**
         *
         */
        fun closeProgressDialog()
    }

    interface AlarmRecordContractPresenter : BasePresenter {
//        /**
//         *
//         */
//        fun getBusinessInfo(map : Map)
    }
}