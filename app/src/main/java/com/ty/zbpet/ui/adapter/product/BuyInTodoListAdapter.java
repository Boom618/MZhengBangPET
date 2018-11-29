package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.material.MaterialTodoList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 外采入库 待办列表
 * @author TY
 */
public class BuyInTodoListAdapter extends CommonAdapter<MaterialTodoList.ListBean> {


    public BuyInTodoListAdapter(Context context, int layoutId, List<MaterialTodoList.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MaterialTodoList.ListBean list, int position) {
        holder.setText(R.id.tv_operator, "入库")
                .setText(R.id.tv_no, list.getSapOrderNo())
                .setText(R.id.tv_type, list.getType())
                .setText(R.id.tv_supplier, list.getSupplierName())
                .setText(R.id.tv_date, list.getOrderTime())
                .setText(R.id.tv_status, list.getState());
    }

}
