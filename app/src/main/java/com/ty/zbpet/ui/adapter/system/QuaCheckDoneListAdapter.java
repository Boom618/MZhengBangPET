package com.ty.zbpet.ui.adapter.system;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.system.QualityCheckTodoList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 质检 待办列表
 *
 * @author TY
 */
public class QuaCheckDoneListAdapter extends CommonAdapter<QualityCheckTodoList.DataBean> {


    public QuaCheckDoneListAdapter(Context context, int layoutId, List<QualityCheckTodoList.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, QualityCheckTodoList.DataBean list, int position) {
        holder.setText(R.id.tv_operator, "修改")
                .setText(R.id.tv_no,list.getArrivalOrderNo() )
//                .setText(R.id.tv_msg, list.getGoodsInfo())
                .setText(R.id.tv_date, list.getArrivalTime())
                .setText(R.id.tv_type, list.getType())
                .setText(R.id.tv_status, list.getState());
    }

}
