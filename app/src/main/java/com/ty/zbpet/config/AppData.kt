package com.ty.zbpet.config

import android.os.AsyncTask
import com.horizon.lightkv.DataType
import com.horizon.lightkv.KVData
import com.horizon.lightkv.LightKV

/**
 * @author TY on 2019/4/10.
 * 简单 K-V 存值方式
 */
object AppData:KVData() {
    override val data: LightKV
        get() = LightKV.Builder(GlobalConfig.appContext,"app_data")
//                .logger()
                .executor(AsyncTask.THREAD_POOL_EXECUTOR)
//                .encoder(GzipSink)
                .async()


    var showCount by int(1)
    var account by string(2)
    var token by string(3)
    var secret by array(4 or DataType.ENCODE)

    var name by int(8)
}