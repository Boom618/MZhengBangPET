package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDetailsOut;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 *
 * 退货入库 已办详情
 */
public class ReturnGoodsDoneDetailAdapter extends CommonAdapter<ProductDetailsOut.ListBean> {

    public ReturnGoodsDoneDetailAdapter(Context context, int layoutId, List<ProductDetailsOut.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetailsOut.ListBean list, final int position) {
//        holder.setText(R.id.tv_name, list.getGoodsName())
//                .setText(R.id.et_number,list.getNumber() + "")
//                .setText(R.id.et_batch_no,"皮吃好")
//                .setText(R.id.tv_num, list.getNumber() + "  " + list.getUnitS());

        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_number,list.getNumber() + "")
                .setText(R.id.tv_start_code,  "开始码")
                .setText(R.id.tv_end_code, "结束码")
                .setText(R.id.tv_sap,"SAP 物料")
                .setText(R.id.tv_select_ware,"========")
                .setText(R.id.tv_num, list.getNumber() + "  " + list.getUnitS());

    }


}
