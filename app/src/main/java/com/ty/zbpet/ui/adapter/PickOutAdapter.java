package com.ty.zbpet.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.PickOutDetailInfo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/10/29.
 */
public class PickOutAdapter extends CommonAdapter {

    private List<PickOutDetailInfo> list;

    public PickOutAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.list = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        PickOutDetailInfo info = list.get(position);

        TextView tvNo = holder.itemView.findViewById(R.id.tv_no);
        tvNo.setText(info.getSapPlantNo());

        TextView tvType = holder.itemView.findViewById(R.id.tv_type);
        tvType.setText(info.getOutStoreDate());
        System.out.println("info.getType() = " + info.getOutStoreDate());

        TextView tvStatus = holder.itemView.findViewById(R.id.tv_status);
        tvStatus.setText(info.getOutStoreDate());

        TextView tv_supplier = holder.itemView.findViewById(R.id.tv_supplier);
        tv_supplier.setText(info.getOutStoreDate());

        TextView tvOperator = holder.itemView.findViewById(R.id.tv_operator);
        tvOperator.setText("冲销");
        if (!"采购入库".equals(info.getOutStoreDate())) {
            tvOperator.setText("入库");
        }

        TextView tvDate = holder.itemView.findViewById(R.id.tv_date);
        tvDate.setText(info.getOutStoreDate());

    }
}
