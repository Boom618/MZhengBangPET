package com.ty.zbpet.ui.adapter.product;

import android.content.Context;
import android.view.View;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.BuyInTodoDetails;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 * <p>
 * 退货入库 待办详情
 */
public class ReturnGoodsTodoDetailAdapter extends CommonAdapter<BuyInTodoDetails.ListBean> {


    public ReturnGoodsTodoDetailAdapter(Context context, int layoutId, List<BuyInTodoDetails.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, BuyInTodoDetails.ListBean list, final int position) {
        holder.itemView.findViewById(R.id.tv_select_ware).setVisibility(View.INVISIBLE);
        holder.itemView.findViewById(R.id.tv_warehouse).setVisibility(View.VISIBLE);


        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_warehouse, list.getWarehouseList().get(0).getWarehouseName() + " ? 号仓")
                .setText(R.id.tv_num, list.getOrderNumber() + "  " + list.getUnitS());

    }


}
