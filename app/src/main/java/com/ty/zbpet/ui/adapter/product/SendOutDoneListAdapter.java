package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDoneList;
import com.ty.zbpet.bean.product.ProductTodoList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 发货出库 已办列表
 *
 * @author TY
 */
public class SendOutDoneListAdapter extends CommonAdapter<ProductDoneList.ListBean> {


    public SendOutDoneListAdapter(Context context, int layoutId, List<ProductDoneList.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDoneList.ListBean list, int position) {
//        holder.setText(R.id.tv_operator, "冲销")
//                .setText(R.id.tv_no, list.getSapOrderNo())
//                .setText(R.id.tv_produce_msg, "生产信息")
////                .setText(R.id.tv_client_msg, "客户 ？")
//                .setText(R.id.tv_date, list.getInTime())
//                .setText(R.id.tv_status, list.getState() + "");

        holder.setText(R.id.tv_operator, "冲销")
                .setText(R.id.tv_no, list.getSapOrderNo())
                .setText(R.id.tv_client_msg, list.getCustomerInfo())
                .setText(R.id.tv_send_msg, list.getBackInfo())
                .setText(R.id.tv_product_msg, list.getBackInfo())
                .setText(R.id.tv_date, list.getOutTime())
                .setText(R.id.tv_status, list.getState());
    }

}
