package com.ty.zbpet.net;

import com.ty.zbpet.util.CodeConstant;

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

        Request authorised = originalRequest.newBuilder()
                .header("sessionId", CodeConstant.SESSION_ID_KEY)
                .header("system", CodeConstant.SYSTEM)
                .build();
        return chain.proceed(authorised);
    }
}
