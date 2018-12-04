package com.ty.zbpet.ui.adapter.material;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialDoneDetailsData;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/14.
 * 已办详情
 */
public class MaterialDoneDetailAdapter extends CommonAdapter<MaterialDoneDetailsData.ListBean> {


    public MaterialDoneDetailAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MaterialDoneDetailsData.ListBean listBean, int position) {

        holder.setText(R.id.tv_name, listBean.getMaterialName())
                .setText(R.id.tv_num, listBean.getNumber())
                .setText(R.id.tv_zkg, "ZKG ?")
                .setText(R.id.tv_count_number, "入库数量 ？")
                .setText(R.id.tv_sap, " SAP 物料")
                .setText(R.id.tv_code, listBean.getPositionNo());

    }

}
