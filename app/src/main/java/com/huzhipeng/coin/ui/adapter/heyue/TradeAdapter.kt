package com.huzhipeng.coin.ui.adapter.heyue

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huzhipeng.coin.R
import com.huzhipeng.coin.entity.Trade
import com.huzhipeng.coin.utils.TimeUtil

class TradeAdapter(arrayList : MutableList<Trade>) : BaseQuickAdapter<Trade, BaseViewHolder>(R.layout.layout_trade, arrayList) {
    override fun convert(helper: BaseViewHolder, item: Trade) {
        helper.setText(R.id.tvTime, TimeUtil.getTradeTimeMill(item.e))
        helper.setText(R.id.price, item.p.toBigDecimal().setScale(2).toPlainString())
        if (item.ism()) {
            helper.setTextColor(R.id.price, mContext.resources.getColor(R.color.color_down))
        } else {
            helper.setTextColor(R.id.price, mContext.resources.getColor(R.color.color_up))
        }
        helper.setText(R.id.tvAmount, item.q.toBigDecimal().setScale(6).toPlainString())
    }

}