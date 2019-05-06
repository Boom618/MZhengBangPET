package com.ty.zbpet.net

import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.SimpleCache

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response
import android.support.v4.content.ContextCompat.startActivity
import com.ty.zbpet.ui.activity.system.LoginActivity
import android.content.Intent
import com.google.gson.Gson
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.ui.ActivitiesHelper
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
        // 重要 bug ，chain.proceed(chain.request()) 导致网络会调用多次
//        val response = chain.proceed(chain.request())

        var sessionId = ""
        try {
            sessionId = SimpleCache.getString(CodeConstant.SESSION_ID_KEY)
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        if (sessionId.isEmpty()) {
//            val activity = ActivitiesHelper.get().lastActivity
//            val intent = Intent(activity, LoginActivity::class.java)
//            activity.startActivity(intent)
//        }
//        val content = response.body()!!.string()
//        val fromJson = Gson().fromJson(content, ResponseInfo::class.java)
//        if (fromJson.tag == "500") {// || fromJson.status == "500"
//            val activity = ActivitiesHelper.get().lastActivity
//            val intent = Intent(activity, LoginActivity::class.java)
//            activity.startActivity(intent)
//            throw Throwable(TipString.loginRetry)
//        }

        val authorised = originalRequest.newBuilder()
                //.header(CodeConstant.SESSION_ID_KEY, "uIXH5MJxyDY4TXQXUL_BL4WvHLZyy4Vf")
                .header(CodeConstant.SESSION_ID_KEY, sessionId)
                .header(CodeConstant.SYSTEM_KEY, CodeConstant.SYSTEM_VALUE)
                .build()
        return chain.proceed(authorised)
    }
}
