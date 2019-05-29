package com.ty.zbpet.ui.adapter.product

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 *
 * 外采入库 待办详情
 */
class BuyInTodoDetailAdapter(context: Context, layoutId: Int, datas: List<ProductDetails.ListBean>) : CommonAdapter<ProductDetails.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: ProductDetails.ListBean, position: Int) {
        // 隐藏 扫箱码按钮
        holder.itemView.findViewById<Button>(R.id.btn_binding_code).visibility = View.GONE

        // 选择订单
        val orderList = datas[position].deliveryOrderList

        holder.setText(R.id.tv_name, list.goodsName)
                .setText(R.id.tv_num, list.orderNumber + "/" + list.unit)
                .setText(R.id.tv_select_ware, "仓库编号：" + list.warehouseNo)

        val select = holder.itemView.findViewById<TextView>(R.id.tv_select_order_pro)
        if (orderList == null) {
            select.visibility = View.GONE
        } else {
            select.visibility = View.VISIBLE
            // 【这个控件不可见，主要用途是 存 deliveryOrderLine 这个值 。 偷懒.png】
            val orderLineView = holder.itemView.findViewById<TextView>(R.id.tv_invisible_line)
            val numberView = holder.itemView.findViewById<EditText>(R.id.et_number)
            val batchNoView = holder.itemView.findViewById<EditText>(R.id.et_sap)

            val orderNoStr = ArrayList<String>(16)
            for (i in 0 until orderList.size) {
                val number = orderList[i].number
                val sapBatchNo = orderList[i].sapBatchNo
                val orderNo = orderList[i].deliveryOrderNo
                val temp = "$orderNo-$number-$sapBatchNo"
                orderNoStr.add(temp)
            }
            // 默认值
            select.text = orderList[0].deliveryOrderNo
            orderLineView.text = orderList[0].deliveryOrderLine
            numberView.setText(orderList[0].number)
            batchNoView.setText(orderList[0].sapBatchNo)

            select.setOnClickListener { v -> ZBUiUtils.selectOrderPro(v.context, orderNoStr, orderList, select, orderLineView, numberView, batchNoView) }

        }


    }


}
