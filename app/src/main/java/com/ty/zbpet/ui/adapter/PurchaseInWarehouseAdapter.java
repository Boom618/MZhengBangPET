package com.ty.zbpet.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.GoodsPurchaseOrderList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 成品——采购入库待办列表
 */
public class PurchaseInWarehouseAdapter extends RecyclerView.Adapter {

    private List<GoodsPurchaseOrderList.DataBean.ListBean> infoList;
    private OnItemClickListener mListener;
    private LayoutInflater mInflater;

    public PurchaseInWarehouseAdapter(Context context, List<GoodsPurchaseOrderList.DataBean.ListBean> deviceList) {
        this.infoList = deviceList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(mInflater.inflate(R.layout.item_purchase_in_storage, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final GoodsPurchaseOrderList.DataBean.ListBean info = infoList.get(position);
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.tvNo.setText(info.getSapOrderNo());
        itemHolder.tvType.setText(info.getType());
        itemHolder.tvStatus.setText(info.getState());
        itemHolder.tvDate.setText(info.getOrderTime());
        itemHolder.tvSupplier.setText(info.getSupplierName());
        itemHolder.tvOperator.setText("入库");
        //todo 到货信息待确认
        itemHolder.tvArrivalMsg.setText("到货信息待确认");
        if (mListener != null) {
            itemHolder.rlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position, info);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return infoList.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_no)
        TextView tvNo;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_arrival_msg)
        TextView tvArrivalMsg;
        @BindView(R.id.tv_supplier)
        TextView tvSupplier;
        @BindView(R.id.tv_operator)
        TextView tvOperator;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.rl_root)
        RelativeLayout rlRoot;

        private ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, GoodsPurchaseOrderList.DataBean.ListBean data);
    }

    public void setOnItemClickLisener(OnItemClickListener lisener) {
        this.mListener = lisener;
    }
}
