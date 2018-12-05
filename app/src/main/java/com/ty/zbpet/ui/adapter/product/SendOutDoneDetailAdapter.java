package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDetailsOut;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 * <p>
 * 发货出库 已办详情
 */
public class SendOutDoneDetailAdapter extends CommonAdapter<ProductDetailsOut.ListBean> {


    public SendOutDoneDetailAdapter(Context context, int layoutId, List<ProductDetailsOut.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetailsOut.ListBean list, final int position) {
        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_select_ware, "商品1")
                .setText(R.id.tv_num, "  数量 " + list.getUnitS());

    }


}
