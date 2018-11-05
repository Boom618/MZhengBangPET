package com.ty.zbpet.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialDetailsData;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * @author TY on 2018/11/5.
 * <p>
 * 到货入库 （待办/已办 详情）
 */
public class MaterialDetailAdapter extends CommonAdapter {

    private List<MaterialDetailsData.ListBean> infoList;


    public MaterialDetailAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.infoList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        View itemView = holder.itemView;
        MaterialDetailsData.ListBean info = infoList.get(position);

        TextView tvName = itemView.findViewById(R.id.tv_name);
        tvName.setText(info.getMaterialName());

        ImageView ivArrow = itemView.findViewById(R.id.iv_arrow);

        TextView tvNum = itemView.findViewById(R.id.tv_num);
//        tvNum.setText(info.getOrderNumber() + info.getUnit());
        tvNum.setText("Number + Unit");

        RelativeLayout rlSummary = itemView.findViewById(R.id.rl_summary);

        TextView tvBoxNum = itemView.findViewById(R.id.tv_box_num);
        tvBoxNum.setText("含量：" + info.getConcentration());

        TextView tvInNum = itemView.findViewById(R.id.et_bulk_num);
        tvInNum.setText("冲销量：? ");

        TextView tvCode = itemView.findViewById(R.id.tv_code);
        tvCode.setText("库位码：? ");

        RelativeLayout rlDetail = itemView.findViewById(R.id.rl_detail);

        LinearLayout llRoot = itemView.findViewById(R.id.ll_root);

    }
}
