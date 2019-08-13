package com.huzhipeng.coin.entity

import com.google.gson.JsonElement


data class PlatformResponse(val result: JsonElement)

data class Symbols(var data: Array<Symbol>)
