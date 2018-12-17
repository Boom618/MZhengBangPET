package com.ty.zbpet.net;

import com.ty.zbpet.ui.MainApp;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.SimpleCache;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @author TY on 2018/12/14.
 * <p>
 * sessionId 头部 拦截器
 */
public class SessionInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        String sessionId = "";
        try {
            sessionId = SimpleCache.getString(CodeConstant.SESSION_ID_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Request authorised = originalRequest.newBuilder()
                .header(CodeConstant.SESSION_ID_KEY, "202cb962ac59075b964b07152d234b70")
                .header(CodeConstant.SYSTEM_KEY, CodeConstant.SYSTEM_VALUE)
                .build();
        return chain.proceed(authorised);
    }
}
