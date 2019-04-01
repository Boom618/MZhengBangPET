package com.ty.zbpet.ui.adapter.wareroom

import android.content.Context
import android.widget.Button
import android.widget.TextView
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.AdapterButtonClick
import com.ty.zbpet.bean.system.ProductInventorList
import com.ty.zbpet.constant.CodeConstant
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import org.greenrobot.eventbus.EventBus

/**
 * @author TY on 2019/3/18.
 * 成品单据删除 列表
 */
class ProductDeleteAdapter(context: Context, layout: Int, datas: MutableList<ProductInventorList.ListBean>)
    : CommonAdapter<ProductInventorList.ListBean>(context, layout, datas) {

    override fun convert(holder: ViewHolder, list: ProductInventorList.ListBean, position: Int) {


    }

}