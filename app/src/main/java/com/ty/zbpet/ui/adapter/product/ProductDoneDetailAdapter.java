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
 * 生产入库 已办详情
 */
public class ProductDoneDetailAdapter extends CommonAdapter<ProductDetailsOut.ListBean> {


    public ProductDoneDetailAdapter(Context context, int layoutId, List<ProductDetailsOut.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetailsOut.ListBean list, int position) {
        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_content,"文本：" + list.getContent())
                .setText(R.id.tv_start_code,"开始码：" + list.getStartQrCode())
                .setText(R.id.tv_end_code,"结束码：" + list.getEndQrCode())
                .setText(R.id.tv_box_num,"入库数量：" + list.getNumber())
                .setText(R.id.tv_sap,list.getSapMaterialBatchNo())
                .setText(R.id.tv_num, list.getNumber() + list.getUnitS());

    }


}
