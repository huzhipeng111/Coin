package com.huzhipeng.coin.ui.adapter

import android.widget.CompoundButton
import android.widget.Switch
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huzhipeng.coin.R
import com.huzhipeng.coin.application.AppConfig
import com.huzhipeng.coin.db.AlarmRecord
import com.huzhipeng.coin.db.CoinEntity
import com.huzhipeng.coin.entity.Symbol
import com.huzhipeng.coin.utils.TimeUtil
import org.greenrobot.eventbus.EventBus

class CoinSetAdapter(arrayList: MutableList<CoinEntity>) : BaseQuickAdapter<CoinEntity, BaseViewHolder>(R.layout.item_coin_set, arrayList) {
    override fun convert(helper: BaseViewHolder, item: CoinEntity) {
        helper.setText(R.id.symbol, item.symbol)
        helper.setChecked(R.id.follow, item.inIgnore)
        when(item.coinType) {
            2 -> {
                helper.setText(R.id.coinType, "山寨币")
                helper.setVisible(R.id.follow, true)
                helper.setOnCheckedChangeListener(R.id.follow, object : CompoundButton.OnCheckedChangeListener {
                    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                        item.inIgnore = p1
                        AppConfig.instance.daoSsesion.coinEntityDao.update(item)
                        EventBus.getDefault().post(item)
                    }
                })
            }
            1 -> {
                helper.setText(R.id.coinType, "垃圾币")
                helper.setVisible(R.id.follow, false)
            }
            0 -> {
                helper.setText(R.id.coinType, "主流币")
                helper.setVisible(R.id.follow, true)
                helper.setOnCheckedChangeListener(R.id.follow, object : CompoundButton.OnCheckedChangeListener {
                    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                        item.inIgnore = p1
                        AppConfig.instance.daoSsesion.coinEntityDao.update(item)
                        EventBus.getDefault().post(item)
                    }
                })
            }
        }
    }
}