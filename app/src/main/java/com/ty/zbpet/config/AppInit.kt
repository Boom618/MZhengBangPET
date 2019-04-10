package com.ty.zbpet.config

import android.content.Context

/**
 * @author TY on 2019/4/10.internal
 */
internal object AppInit {

    @JvmStatic
    fun init(context: Context){
        GlobalConfig.appContext = context

        AppData.data
    }
}