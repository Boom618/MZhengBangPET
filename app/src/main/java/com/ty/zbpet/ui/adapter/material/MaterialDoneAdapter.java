package com.ty.zbpet.ui.adapter.material;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.material.MaterialDoneList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/13.
 * <p>
 * 原材料 已办 列表
 */
public class MaterialDoneAdapter extends CommonAdapter<MaterialDoneList.ListBean> {


    public MaterialDoneAdapter(Context context, int layoutId, List<MaterialDoneList.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MaterialDoneList.ListBean listBean, int position) {

        holder.setText(R.id.tv_no, listBean.getSapOrderNo())
                .setText(R.id.tv_status, listBean.getState())
                .setText(R.id.tv_type, "type :" + listBean.getType())
                .setText(R.id.tv_date, listBean.getOutTime())
                .setText(R.id.tv_supplier, "供应商：? ");

    }
}
