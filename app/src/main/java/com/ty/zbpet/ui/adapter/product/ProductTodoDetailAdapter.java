package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDetailsIn;
import com.ty.zbpet.bean.product.ProductTodoDetails;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 *
 * 生产入库 待办详情
 */
public class ProductTodoDetailAdapter extends CommonAdapter<ProductTodoDetails.ListBean> {


    public ProductTodoDetailAdapter(Context context, int layoutId, List<ProductTodoDetails.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductTodoDetails.ListBean list, final int position) {
        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_num, list.getOrderNumber() + "  " + list.getUnitS());

    }


}
