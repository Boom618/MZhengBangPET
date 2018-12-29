package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDetails;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 *
 * 外采入库 已办详情
 */
public class BuyInDoneDetailAdapter extends CommonAdapter<ProductDetails.ListBean> {

    public BuyInDoneDetailAdapter(Context context, int layoutId, List<ProductDetails.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetails.ListBean list, int position) {
        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_number,"入库数量：" + list.getNumber())
                .setText(R.id.tv_start_code,  "开始码：" + list.getStartQrCode())
                .setText(R.id.tv_end_code, "结束码：" + list.getEndQrCode())
                .setText(R.id.tv_sap,list.getSapMaterialBatchNo())
                .setText(R.id.tv_select_ware,list.getWarehouseName())
                .setText(R.id.tv_num, list.getNumber() + "  " + list.getUnitS());

    }


}
