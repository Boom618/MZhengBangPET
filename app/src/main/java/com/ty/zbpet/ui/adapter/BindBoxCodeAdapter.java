package com.ty.zbpet.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BindBoxCodeAdapter extends RecyclerView.Adapter {

    private List<String> info;
    private LayoutInflater mInflater;
    private View view;

    public BindBoxCodeAdapter(Context mContext, List<String> mList) {
        this.info = mList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = mInflater.inflate(R.layout.item_box_code, null);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final String data = info.get(position);
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.tvBoxCode.setText(data);
        itemHolder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_box_code)
        TextView tvBoxCode;
        @BindView(R.id.iv_del)
        ImageView ivDel;
        @BindView(R.id.rl_root)
        RelativeLayout rlRoot;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * 删除条目
     *
     * @param pos
     */
    private void deleteItem(int pos) {
        if (pos == info.size()) {
            UIUtils.showToast("请重新添加数据！");
            return;
        }
        try {
            info.remove(pos);
            notifyItemRemoved(pos);
            //刷新列表，否则回出现删除错位
            if (pos != info.size()) {
                notifyItemRangeChanged(pos, info.size() - pos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            UIUtils.showToast("角标越界异常！");
        }

    }
}
