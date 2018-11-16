package com.ty.zbpet.net;

import com.ty.zbpet.util.ZBLog;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * @author TY
 * <p>
 * okhttp3 日志拦截器
 */
public class LogInterceptor implements Interceptor {

    public static String TAG = "Url = ";

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String requestMethod = request.method();
        ZBLog.e(TAG,requestMethod + " : " + request.url());
        long startTime = System.currentTimeMillis();

        okhttp3.Response response = chain.proceed(chain.request());
//        Logger.e("返回 Response " + response.message());
//        Logger.e("返回 Response " + response.body().string());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        ZBLog.d(TAG, "\n");
        ZBLog.d(TAG, "----------Start----------------");
        ZBLog.d(TAG, "| " + request.toString());
        String method = request.method();
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                ZBLog.d(TAG, "| RequestParams:{" + sb.toString() + "}");
            }
        }
        ZBLog.d(TAG, "| Response:" + content);
        ZBLog.d(TAG, "----------End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
