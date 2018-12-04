package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDoneList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 退货入库 已办列表
 * @author TY
 */
public class ReturnGoodsDoneListAdapter extends CommonAdapter<ProductDoneList.ListBean> {


    public ReturnGoodsDoneListAdapter(Context context, int layoutId, List<ProductDoneList.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDoneList.ListBean list, int position) {
        holder.setText(R.id.tv_operator, "冲销")
                .setText(R.id.tv_no, list.getSapOrderNo())
                .setText(R.id.tv_client_msg,  "客户 ？")
                .setText(R.id.tv_send_msg, " 退货 ？")
                .setText(R.id.tv_date, list.getInTime())
                .setText(R.id.tv_status, list.getState() + "");
    }

}
