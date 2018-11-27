//package com.ty.zbpet.ui.adapter.material
//
//import android.content.Context
//import com.ty.zbpet.R
//import com.ty.zbpet.bean.PickOutTodoDetailsData
//import com.zhy.adapter.recyclerview.CommonAdapter
//import com.zhy.adapter.recyclerview.base.ViewHolder
//
///**
// * @author TY on 2018/11/21.
// *
// * 领料出库 待办详情
// */
//class PickOutTodoDetailAdapter(context: Context, layout: Int, datas: List<PickOutTodoDetailsData.ListBean>?)
//    : CommonAdapter<PickOutTodoDetailsData.ListBean>(context, layout, datas) {
//
//
//    override fun convert(holder: ViewHolder, list: PickOutTodoDetailsData.ListBean, position: Int) {
//
//        holder.setText(R.id.tv_name, list.materialName)
//                .setText(R.id.tv_num, list.orderNumber + "  " + list.unitS)
//                .setText(R.id.tv_box_num, "含量：" + list.concentration)
//                .setText(R.id.tv_box_num_unit, "ZKG ：? ")
//                .setText(R.id.bulk_num, "出库量：? ")
//                .setText(R.id.inventory, "库存量：? ")
//
//    }
//
//}