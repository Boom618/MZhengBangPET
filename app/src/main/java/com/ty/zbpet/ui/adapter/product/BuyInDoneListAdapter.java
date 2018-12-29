package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 外采入库 已办列表
 *
 * @author TY
 */
public class BuyInDoneListAdapter extends CommonAdapter<ProductList.ListBean> {


    public BuyInDoneListAdapter(Context context, int layoutId, List<ProductList.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductList.ListBean list, int position) {
        holder.setText(R.id.tv_operator, "冲销")
                .setText(R.id.tv_no, list.getSapOrderNo())
                .setText(R.id.tv_type, list.getType())
                .setText(R.id.tv_supplier, list.getSupplierName())
                .setText(R.id.tv_date, list.getInTime())
                .setText(R.id.tv_status, list.getState());
    }

}
