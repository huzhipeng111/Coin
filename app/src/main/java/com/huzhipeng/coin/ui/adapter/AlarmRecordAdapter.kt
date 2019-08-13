package com.huzhipeng.coin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huzhipeng.coin.R
import com.huzhipeng.coin.db.AlarmRecord
import com.huzhipeng.coin.entity.Symbol
import com.huzhipeng.coin.utils.TimeUtil

class AlarmRecordAdapter(arrayList: MutableList<AlarmRecord>) : BaseQuickAdapter<AlarmRecord, BaseViewHolder>(R.layout.item_alarm_record, arrayList) {
    override fun convert(helper: BaseViewHolder, item: AlarmRecord) {
        helper.setText(R.id.symbol, item.symbol)
        helper.setText(R.id.price, item.alarmPrice)
        helper.setText(R.id.alarmTime, "" + TimeUtil.getTime(item.alarmTime))
        helper.setText(R.id.gain, "" + item.gain + " %")
        helper.setText(R.id.gain24, "" + item.gain24 + " %")
    }
}