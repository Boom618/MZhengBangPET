package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDetails;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 * <p>
 * 退货入库 待办详情
 */
public class ReturnGoodsTodoDetailAdapter extends CommonAdapter<ProductDetails.ListBean> {


    public ReturnGoodsTodoDetailAdapter(Context context, int layoutId, List<ProductDetails.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetails.ListBean list, final int position) {

        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_select_ware, "所在仓库：" + list.getWarehouseNo())
                .setText(R.id.tv_num, list.getNumber() + "  " + list.getUnit());

    }

}
