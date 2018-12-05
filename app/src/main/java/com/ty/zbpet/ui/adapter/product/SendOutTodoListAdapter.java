package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductTodoList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 发货出库 待办列表
 * @author TY
 */
public class SendOutTodoListAdapter extends CommonAdapter<ProductTodoList.ListBean> {


    public SendOutTodoListAdapter(Context context, int layoutId, List<ProductTodoList.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductTodoList.ListBean list, int position) {
        holder.setText(R.id.tv_operator, "出库")
                .setText(R.id.tv_no, list.getSapOrderNo())
                .setText(R.id.tv_client_msg, list.getCustomerInfo() + "客户 ？")
                .setText(R.id.tv_send_msg, list.getProductInfo() + " 发货 ？")
                .setText(R.id.tv_product_msg, list.getProductInfo() + " 成品 ？")
                .setText(R.id.tv_date, list.getOrderTime())
                .setText(R.id.tv_status, list.getState());
    }

}