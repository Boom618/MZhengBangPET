package com.ty.zbpet.ui.adapter.material;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.material.MaterialDetailsOut;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 * <p>
 * 采购退货 已办详情
 */
public class BackGoodsDoneDetailAdapter extends CommonAdapter<MaterialDetailsOut.ListBean> {


    public BackGoodsDoneDetailAdapter(Context context, int layoutId, List<MaterialDetailsOut.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MaterialDetailsOut.ListBean list, final int position) {

        holder.setText(R.id.tv_name, list.getMaterialName())
                .setText(R.id.tv_num, list.getGiveNumber() + list.getUnitS())
                .setText(R.id.tv_box_num, "含量："  + list.getConcentration() + "%")
                .setText(R.id.tv_box_num_unit, "ZKG:" + list.getZKG())
                .setText(R.id.bulk_num, "库存量：" + list.getStockNumber())
                .setText(R.id.tv_code, "库位码：" + list.getPositionNo())
                .setText(R.id.tv_number, "出库数量：" + list.getGiveNumber())
                .setText(R.id.tv_batch_no, list.getSapMaterialBatchNo());

    }


}
