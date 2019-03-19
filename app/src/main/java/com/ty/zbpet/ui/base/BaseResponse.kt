package com.ty.zbpet.ui.base

import java.io.Serializable

/**
 * @author TY on 2018/10/26.
 * 响应基类
 */
class BaseResponse<T> : Serializable {

    var tag: String? = null
    var message: String? = null
    var data: T? = null
}
