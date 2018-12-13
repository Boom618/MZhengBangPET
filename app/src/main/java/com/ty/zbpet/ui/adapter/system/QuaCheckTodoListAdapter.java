package com.ty.zbpet.ui.adapter.system;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductTodoList;
import com.ty.zbpet.bean.system.QualityCheckTodoList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 质检 待办列表
 *
 * @author TY
 */
public class QuaCheckTodoListAdapter extends CommonAdapter<QualityCheckTodoList.ListBean> {


    public QuaCheckTodoListAdapter(Context context, int layoutId, List<QualityCheckTodoList.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, QualityCheckTodoList.ListBean list, int position) {
        holder.setText(R.id.tv_operator, "质检")
                .setText(R.id.tv_no, list.getSapOrderNo())
                .setText(R.id.tv_msg, list.getGoodsInfo())
                .setText(R.id.tv_date, list.getOrderTime())
                .setText(R.id.tv_type, list.getType())
                .setText(R.id.tv_status, list.getState());
    }

}
