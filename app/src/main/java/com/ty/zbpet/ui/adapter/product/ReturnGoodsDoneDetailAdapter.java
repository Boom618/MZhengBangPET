package com.ty.zbpet.ui.adapter.product;

import android.content.Context;
import android.view.View;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDetailsOut;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 * <p>
 * 退货入库 已办详情
 */
public class ReturnGoodsDoneDetailAdapter extends CommonAdapter<ProductDetailsOut.ListBean> {

    public ReturnGoodsDoneDetailAdapter(Context context, int layoutId, List<ProductDetailsOut.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetailsOut.ListBean list, final int position) {
        // 共用 一个布局：下拉选择隐藏,应发数量显示
        holder.itemView.findViewById(R.id.tv_select_ware).setVisibility(View.INVISIBLE);
        holder.itemView.findViewById(R.id.tv_warehouse).setVisibility(View.VISIBLE);

        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_number, "入库数量：" + list.getNumber())
                .setText(R.id.tv_start_code, "开始码：" + list.getStartQrCode())
                .setText(R.id.tv_end_code, "结束码：" + list.getEndQrCode())
                .setText(R.id.tv_sap, list.getSapMaterialBatchNo())
                .setText(R.id.tv_warehouse, "所在仓库：" + list.getWarehouseName())
                .setText(R.id.tv_num, list.getNumber() + "  " + list.getUnitS());

    }


}
