package com.ty.zbpet.net

import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.SimpleCache

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response
import android.support.v4.content.ContextCompat.startActivity
import com.ty.zbpet.ui.activity.system.LoginActivity
import android.content.Intent
import com.ty.zbpet.ui.MainApp


/**
 * @author TY on 2018/12/14.
 *
 *
 * sessionId 头部 拦截器
 */
class SessionInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        var sessionId = ""
        try {
            sessionId = SimpleCache.getString(CodeConstant.SESSION_ID_KEY)
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        if (sessionId.isEmpty()) {
//            val intent = Intent(MainApp.context, LoginActivity::class.java)
//            MainApp.context.startActivity(intent)
//        }

        val authorised = originalRequest.newBuilder()
                //.header(CodeConstant.SESSION_ID_KEY, "uIXH5MJxyDY4TXQXUL_BL4WvHLZyy4Vf")
                .header(CodeConstant.SESSION_ID_KEY, sessionId)
                .header(CodeConstant.SYSTEM_KEY, CodeConstant.SYSTEM_VALUE)
                .build()
        return chain.proceed(authorised)
    }
}
