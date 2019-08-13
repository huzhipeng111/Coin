package com.huzhipeng.coin.ui.activity.main.contract

import com.huzhipeng.coin.ui.activity.base.BasePresenter
import com.huzhipeng.coin.ui.activity.base.BaseView
/**
 * @author hzp
 * @Package The contract for CourseActivity
 * @Description: $description
 * @date 2019/05/15 10:19:42
 */
interface CourseContract {
    interface View : BaseView<CourseContractPresenter> {
        /**
         *
         */
        fun showProgressDialog()

        /**
         *
         */
        fun closeProgressDialog()
    }

    interface CourseContractPresenter : BasePresenter {
//        /**
//         *
//         */
//        fun getBusinessInfo(map : Map)
    }
}