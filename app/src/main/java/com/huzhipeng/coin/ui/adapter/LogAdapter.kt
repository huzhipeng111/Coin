package com.huzhipeng.coin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huzhipeng.coin.R

class LogAdapter(arrayList: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_item_log, arrayList) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tvLog, item)
    }

}