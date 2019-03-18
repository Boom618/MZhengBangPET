package com.ty.zbpet.ui.adapter.wareroom

import android.content.Context
import com.ty.zbpet.R
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2019/3/18.
 * 成品移库
 */
class ProductAdapter(context: Context, layout: Int, datas: MutableList<String>)
    : CommonAdapter<String>(context, layout, datas) {

    override fun convert(holder: ViewHolder, t: String?, position: Int) {

        holder.setText(R.id.tv_productNo, "成品代码：")
                .setText(R.id.tv_product_name, "成品名称：")
                .setText(R.id.tv_former_house, "原仓库：")
                .setText(R.id.tv_number, "库存数量：")
                .setText(R.id.tv_content, "含量：")
                .setText(R.id.tv_select_house, "正邦仓库：")
                .setText(R.id.tv_move_number, "移库数量：")

    }

}