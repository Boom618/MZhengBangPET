package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.material.MaterialDoneList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 外采入库 已办列表
 * @author TY
 */
public class PurchaseDoneListAdapter extends CommonAdapter<MaterialDoneList.ListBean> {


    public PurchaseDoneListAdapter(Context context, int layoutId, List<MaterialDoneList.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MaterialDoneList.ListBean list, int position) {
        holder.setText(R.id.tv_operator, "冲销")
                .setText(R.id.tv_no, list.getSapOrderNo())
                .setText(R.id.tv_type, list.getType())
                .setText(R.id.tv_supplier, list.getMaterialName())
                .setText(R.id.tv_date, list.getOutTime())
                .setText(R.id.tv_status, list.getState());
    }

}
