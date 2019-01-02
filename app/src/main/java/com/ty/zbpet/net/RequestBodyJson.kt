package com.ty.zbpet.net

import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * @author TY on 2018/12/27.
 * POST 请求
 */
object RequestBodyJson {

    /**
     * RequestBody--json 数据提交
     *
     * @param json
     * @return
     */
    fun requestBody(json: String): RequestBody {

        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), json)
    }

    // FromBody---表单提交
    // MultipartBody---文件上传
}
