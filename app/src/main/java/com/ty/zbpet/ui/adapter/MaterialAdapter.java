package com.ty.zbpet.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.bean.MaterialInWarehouseOrderList;
import com.ty.zbpet.ui.base.BaseResponse;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;


import java.util.List;

/**
 * @author TY on 2018/10/26.
 * <p>
 * 到货入库 Adapter
 */
public class MaterialAdapter extends CommonAdapter {

    private List<MaterialData.ListBean> infoList;


    public MaterialAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);

        infoList = datas;
    }


    @Override
    protected void convert(ViewHolder holder, Object o, int position) {


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        MaterialData.ListBean info = infoList.get(position);


        TextView tvNo = holder.itemView.findViewById(R.id.tv_no);
        tvNo.setText(info.getSapOrderNo());

        TextView tvType = holder.itemView.findViewById(R.id.tv_type);
        tvType.setText(info.getType());
        System.out.println("info.getType() = " + info.getType());

        TextView tvStatus = holder.itemView.findViewById(R.id.tv_status);
        tvStatus.setText(info.getState());

        TextView tv_supplier = holder.itemView.findViewById(R.id.tv_supplier);
        tv_supplier.setText(info.getSupplierName());

        TextView tvOperator = holder.itemView.findViewById(R.id.tv_operator);
        tvOperator.setText("冲销");
        if (!"采购入库".equals(info.getType())) {
            tvOperator.setText("入库");
        }

        TextView tvDate = holder.itemView.findViewById(R.id.tv_date);
        tvDate.setText(info.getOrderTime());


        RelativeLayout relativeLayout = holder.itemView.findViewById(R.id.ll_root);

    }
}