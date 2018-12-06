package com.ty.zbpet.ui.adapter.product;

import android.content.Context;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDoneList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 生产入库 已办列表
 *
 * @author TY
 */
public class ProductDoneListAdapter extends CommonAdapter<ProductDoneList.ListBean> {


    public ProductDoneListAdapter(Context context, int layoutId, List<ProductDoneList.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDoneList.ListBean list, int position) {
        holder.setText(R.id.tv_operator, "冲销")
                .setText(R.id.tv_no, list.getSapOrderNo())
                .setText(R.id.tv_produce_msg, "生产信息 ?")
                .setText(R.id.tv_date, list.getInTime())
                .setText(R.id.tv_status, list.getState() + "");
    }

}
