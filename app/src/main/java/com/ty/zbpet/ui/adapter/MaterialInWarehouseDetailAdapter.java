package com.ty.zbpet.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialInWarehouseOrderInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author TY
 * 入库详情 - 到货明细 list adapter
 */
public class MaterialInWarehouseDetailAdapter extends RecyclerView.Adapter {


    private List<MaterialInWarehouseOrderInfo.DataBean.ListBean> infoList;
    private LayoutInflater mInflater;

    public MaterialInWarehouseDetailAdapter(Context context, List<MaterialInWarehouseOrderInfo.DataBean.ListBean> deviceList) {
        this.infoList = deviceList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(mInflater.inflate(R.layout.item_arrive_in_storage_detail, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MaterialInWarehouseOrderInfo.DataBean.ListBean info = infoList.get(position);
        final ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.tvName.setText(info.getMaterialName());
        itemHolder.tvNum.setText(info.getOrderNumber() + info.getUnit());
        itemHolder.tvInNum.setText("冲销量："+info.getOrderNumber());
        itemHolder.tvBoxNum.setText("含量：" + info.getConcentration());
        itemHolder.tvCode.setText("库位码："+info.getPositionId());

      itemHolder.rlSummary.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (itemHolder.rlDetail.getVisibility()==View.VISIBLE){
                  itemHolder.rlDetail.setVisibility(View.GONE);
                  itemHolder.ivArrow.setImageResource(R.mipmap.ic_collapse);
              }else {
                  itemHolder.rlDetail.setVisibility(View.VISIBLE);
                  itemHolder.ivArrow.setImageResource(R.mipmap.ic_expand);
              }
          }
      });
    }


    @Override
    public int getItemCount() {
        return infoList.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_arrow)
        ImageView ivArrow;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.rl_summary)
        RelativeLayout rlSummary;
        @BindView(R.id.tv_box_num)
        TextView tvBoxNum;
        @BindView(R.id.et_bulk_num)
        TextView tvInNum;
        @BindView(R.id.tv_code)
        TextView tvCode;
        @BindView(R.id.rl_detail)
        RelativeLayout rlDetail;
        @BindView(R.id.ll_root)
        LinearLayout llRoot;

        private ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
