package com.ty.zbpet.ui.adapter.system

import android.content.Context
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2019/3/11.
 * 箱码查询
 */
class BoxCodeQueryAdapter (context: Context, layout:Int, datas:MutableList<String>):
        CommonAdapter<String>(context,layout,datas) {

    override fun convert(holder: ViewHolder, t: String, position: Int) {

    }
}