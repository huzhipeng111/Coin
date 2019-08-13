package com.huzhipeng.coin.ui.activity.main.contract


import com.huzhipeng.coin.ui.activity.base.BasePresenter
import com.huzhipeng.coin.ui.activity.base.BaseView

/**
 * @author hzp
 * @Package The contract for MainActivity
 * @Description: $description
 * @date 2018/01/09 09:57:09
 */
interface MainContract {
    interface View : BaseView<MainContractPresenter> {
        /**
         *
         */
        fun showProgressDialog()

        /**
         *
         */
        fun closeProgressDialog()

        fun showToast()
    }

    interface MainContractPresenter : BasePresenter {
        fun latlngParseCountry(map: Map<*, *>)
        fun showToast()
    }
}