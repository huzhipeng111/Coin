package com.huzhipeng.coin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huzhipeng.coin.R
import com.huzhipeng.coin.entity.Symbol
import java.math.BigDecimal
import java.math.RoundingMode

class AllSymbolNameAdapter(arrayList: MutableList<SymbolAdapterEntity>) : BaseQuickAdapter<SymbolAdapterEntity, BaseViewHolder>(R.layout.item_allsymbol_name, arrayList) {
    override fun convert(helper: BaseViewHolder, item: SymbolAdapterEntity?) {
        helper.setText(R.id.symbol, item?.symbol?.symbol)
    }
}