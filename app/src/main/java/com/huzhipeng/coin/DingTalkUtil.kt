package com.huzhipeng.coin

import com.huzhipeng.coin.utils.TimeUtil
import com.socks.library.KLog
import okhttp3.*
import org.apache.commons.codec.binary.Base64
import org.json.JSONObject
import java.io.IOException
import java.net.URLEncoder
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


object DingTalkUtil {

    fun signMessage(timestamp: Long, secret: String): String? {
        try {
            val stringToSign = """
                $timestamp
                $secret
                """.trimIndent()
            val mac = Mac.getInstance("HmacSHA256")
            mac.init(SecretKeySpec(secret.toByteArray(charset("UTF-8")), "HmacSHA256"))
            val signData = mac.doFinal(stringToSign.toByteArray(charset("UTF-8")))
            return URLEncoder.encode(String(Base64.encodeBase64(signData)), "UTF-8")
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null
    }


    private fun getUrl(coinType: CoinType) : String {
        val timestamp = System.currentTimeMillis()
        val sign = signMessage(timestamp, coinType.getSecret())
        val newUrl = "${coinType.getUrl()}&timestamp=${timestamp}&sign=${sign}"
        return newUrl
    }

    private fun send(newUrl: String, textMsg: String): String? {
        try {
            val client = OkHttpClient() //创建OkHttpClient对象。
            val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8") //数据类型为json格式，
            val body = RequestBody.create(JSON, textMsg)
            val request: Request = Request.Builder().url(newUrl).post(body).build()
            val response: Response = client.newCall(request).execute()
            if (response.code() === 200 && response.body() != null) {
                val result: String = Objects.requireNonNull(response.body())!!.string()
                KLog.e(result)
                return result
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
        return "发送钉钉消息失败"
    }


    //发送text格式的消息
    @Throws(IOException::class)
    fun sendMessage(mag: String, coinType: CoinType): String? {
        val `object` = JSONObject()
        val object_text = JSONObject()
        val object_at = JSONObject()
        var mag1 = mag +  "\n时间：${TimeUtil.getTime()}"
        object_text.put("content", mag1)
        object_at.put("atMobiles", null)
        object_at.put("isAtAll", false)
        `object`.put("msgtype", "text")
        `object`.put("text", object_text)
        `object`.put("at", object_at)
        return send(getUrl(coinType), java.lang.String.valueOf(`object`))
    }

}