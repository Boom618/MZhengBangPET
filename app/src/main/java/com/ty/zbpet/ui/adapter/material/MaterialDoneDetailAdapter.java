package com.ty.zbpet.ui.adapter.material;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.material.MaterialDetailsOut;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/14.
 * 已办详情
 */
public class MaterialDoneDetailAdapter extends CommonAdapter<MaterialDetailsOut.ListBean> {


    public MaterialDoneDetailAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MaterialDetailsOut.ListBean listBean, int position) {

        holder.setText(R.id.tv_name, listBean.getMaterialName())
                .setText(R.id.tv_num, listBean.getNumber())
                .setText(R.id.tv_bulk, "含量：" + listBean.getConcentration() + "%")
                .setText(R.id.tv_zkg, listBean.getZKG())
                .setText(R.id.tv_count_number, listBean.getNumber())
                .setText(R.id.tv_sap, listBean.getSapMaterialBatchNo())
                .setText(R.id.tv_code, listBean.getPositionNo());

    }

}
