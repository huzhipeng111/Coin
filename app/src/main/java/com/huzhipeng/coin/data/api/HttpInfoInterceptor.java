package com.huzhipeng.coin.data.api;

/**
 * Created by hu on 2016/11/11.
 */


import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @Description 异常处理 拦截器
 * Created by EthanCo on 2016/7/14.
 */
public class HttpInfoInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
//        KLog.i("网络请求的网址为:" + request.toString());
        long contentLength = responseBody.contentLength();
//        KLog.i("请求的地址为：" + request.url());
//        KLog.i("reqeust的body为:" + request.body());
        //注意 >>>>>>>>> okhttp3.4.1这里变成了 !HttpHeader.hasBody(response)
        //!HttpEngine.hasBody(response)
        if (false) {
//        if(!HttpHeader.hasBody(response)){
            //END HTTP
        } else if (bodyEncoded(response.headers())) {
            //HTTP (encoded body omitted)
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    //Couldn't decode the response body; charset is likely malformed.
                    return response;
                }
            }

            if (!isPlaintext(buffer)) {
                return response;
            }

            if (contentLength != 0) {
                //获取到response的body的string字符串
                //do something .... <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                String result = buffer.clone().readString(charset);
//                KLog.i("拦截到的okhttp结果:" + result);
            }
        }
        return response;
    }

    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}