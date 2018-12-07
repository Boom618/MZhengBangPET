//package com.ty.zbpet.ui.adapter;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.ty.zbpet.R;
//import com.ty.zbpet.bean.GoodsPurchaseOrderInfo;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * 成品采购入库待办详情
// */
//public class PurchaseInStorageDetailAdapter extends RecyclerView.Adapter {
//
//
//    private List<GoodsPurchaseOrderInfo.DataBean.ListBean> infoList;
//    private LayoutInflater mInflater;
//    private OnItemClickListener mListener;
//
//    public PurchaseInStorageDetailAdapter(Context context, List<GoodsPurchaseOrderInfo.DataBean.ListBean> deviceList) {
//        this.infoList = deviceList;
//        this.mInflater = LayoutInflater.from(context);
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new ItemHolder(mInflater.inflate(R.layout.item_purchase_instorage_detail, null));
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        final GoodsPurchaseOrderInfo.DataBean.ListBean info = infoList.get(position);
//        final ItemHolder itemHolder = (ItemHolder) holder;
//        itemHolder.tvName.setText(info.getGoodsName());
//        itemHolder.tvNum.setText(info.getOrderNumber() + info.getUnit());
//        String boxNum = (!TextUtils.isEmpty(info.getBoxNum())) ? info.getBoxNum() : "0";
//        itemHolder.tvBoxNum.setText("箱码数量：" +boxNum);
//        /*itemHolder.rlSummary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (itemHolder.rlDetail.getVisibility() == View.VISIBLE) {
//                    itemHolder.rlDetail.setVisibility(View.GONE);
//                    itemHolder.ivArrow.setImageResource(R.mipmap.ic_collapse);
//                } else {
//                    itemHolder.rlDetail.setVisibility(View.VISIBLE);
//                    itemHolder.ivArrow.setImageResource(R.mipmap.ic_expand);
//                }
//            }
//        });*/
//        if (mListener != null) {
//            itemHolder.btnBindingCode.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mListener.onItemClick(position, info);
//                }
//            });
//        }
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return infoList.size();
//    }
//
//    static class ItemHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_name)
//        TextView tvName;
//        @BindView(R.id.iv_arrow)
//        ImageView ivArrow;
//        @BindView(R.id.tv_num)
//        TextView tvNum;
//        @BindView(R.id.rl_summary)
//        RelativeLayout rlSummary;
//        @BindView(R.id.tv_box_num)
//        TextView tvBoxNum;
//        @BindView(R.id.btn_binding_code)
//        Button btnBindingCode;
//        @BindView(R.id.rl_detail)
//        RelativeLayout rlDetail;
//
//        private ItemHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(int position, GoodsPurchaseOrderInfo.DataBean.ListBean data);
//    }
//
//    public void setOnItemClickLisener(OnItemClickListener lisener) {
//        this.mListener = lisener;
//    }
//
//}
