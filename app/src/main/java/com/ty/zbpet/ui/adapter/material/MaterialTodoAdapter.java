package com.ty.zbpet.ui.adapter.material;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.material.MaterialTodoList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/10/26.
 * <p>
 * 到货入库 Adapter
 */
public class MaterialTodoAdapter extends CommonAdapter<MaterialTodoList.ListBean> {

    private List<MaterialTodoList.ListBean> infoList;


    public MaterialTodoAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);

        infoList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, MaterialTodoList.ListBean listBean, int position) {

        holder.setText(R.id.tv_no,listBean.getSapOrderNo())
                .setText(R.id.tv_supplier,"入库")
                .setText(R.id.tv_type,listBean.getType())
                .setText(R.id.tv_status,listBean.getState())
                .setText(R.id.tv_supplier,listBean.getSupplierName())
                .setText(R.id.tv_date,listBean.getOrderTime());

    }


//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
//
//        MaterialTodoList.ListBean info = infoList.get(position);
//
//
//        TextView tvNo = holder.itemView.findViewById(R.id.tv_no);
//        tvNo.setText(info.getSapOrderNo());
//
//        TextView tvType = holder.itemView.findViewById(R.id.tv_type);
//        tvType.setText(info.getType());
//
//        TextView tvStatus = holder.itemView.findViewById(R.id.tv_status);
//        tvStatus.setText(info.getState());
//
//        TextView tvSupplier = holder.itemView.findViewById(R.id.tv_supplier);
//        tvSupplier.setText(info.getSupplierName());
//
//        TextView tvOperator = holder.itemView.findViewById(R.id.tv_operator);
//        tvOperator.setText("入库");
//
//        TextView tvDate = holder.itemView.findViewById(R.id.tv_date);
//        tvDate.setText(info.getOrderTime());
//
//
//    }
}
