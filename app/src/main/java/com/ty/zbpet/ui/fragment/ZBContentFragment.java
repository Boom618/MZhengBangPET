package com.ty.zbpet.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.bean.MaterialInWarehouseOrderList;
import com.ty.zbpet.ui.activity.ArrivalInStorageDetailActivity;
import com.ty.zbpet.ui.adapter.MaterialAdapter;
import com.ty.zbpet.ui.base.BaseFragment;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * @author TY
 */
public class ZBContentFragment extends BaseFragment{


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private MaterialAdapter adapter;

    public static ZBContentFragment newInstance() {

        return new ZBContentFragment();
    }


    /**
     * 加载的 inflater.inflate  的 View
     * @param view layout inflate 的 View
     */
    @Override
    protected void onBaseCreate(View view) {
        refreshLayout.setRefreshHeader(new MaterialHeader(this.getContext()));

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.zb_content_fragment;
    }


    @Override
    public void onStart() {
        super.onStart();

        ArrayList<MaterialInWarehouseOrderList.DataBean.ListBean> data = Utils.getData("待办");

        refreshUI(data);

    }


    private void refreshUI(List<MaterialInWarehouseOrderList.DataBean.ListBean> list) {
        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            recyclerView.setLayoutManager(manager);
            adapter = new MaterialAdapter(ResourceUtil.getContext(), R.layout.item_arrive_in_storage_complete,list);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new MaterialAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Intent intent=new Intent(getActivity(), ArrivalInStorageDetailActivity.class);
                    intent.putExtra("orderId",position);
                    intent.putExtra("sapOrderNo",position);
                    startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }

            });
        }
    }



}
