package com.ty.zbpet.util

import com.alibaba.fastjson.JSONObject

/**
 * @author TY on 2019/2/21.
 * String json1 = "{\"a\":\"aaa\",\"b1\":\"bb1\"}";
 * String json2 = "{\"b\":\"bbb\"}";
 *
 * 字符串合并
 */
class JsonStringMerge {

    /**
     * 字符串合并
     */
    fun StringMerge(vararg string: String):String{
        val json = JSONObject()
        val size = string.size

        for (i in 0 until size){
            val s = string[i]
            json.putAll(JSONObject.parseObject(s))
        }

        return json.toString()
    }

}