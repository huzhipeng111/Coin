package com.huzhipeng.coin.log


/**
 * Created by tony on 2017/11/24.
 */
interface Parser<T> {

    fun parseString(t: T): String
}