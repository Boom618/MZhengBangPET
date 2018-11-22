package com.ty.zbpet.ui.fragment.material;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.PickOutDoneData;
import com.ty.zbpet.presenter.material.MaterialUiListInterface;
import com.ty.zbpet.presenter.material.PickOutPresenter;
import com.ty.zbpet.ui.activity.material.PickOutTodoDetailActivity;
import com.ty.zbpet.ui.adapter.PickOutAdapter;
import com.ty.zbpet.ui.adapter.PickOutDoneAdapter;
import com.ty.zbpet.ui.base.BaseFragment;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * @author TY
 * 领料出库 已办列表
 */
public class PickOutDoneFragment extends BaseFragment implements MaterialUiListInterface<PickOutDoneData.ListBean> {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private PickOutDoneAdapter adapter;

    private PickOutPresenter presenter = new PickOutPresenter(this);

    private static final String ARG_PARAM = "ARG_PARAM";

    public PickOutDoneFragment() {
        // Required empty public constructor

    }

    public static PickOutDoneFragment newInstance(String tag) {
        PickOutDoneFragment fragment = new PickOutDoneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, tag);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View onBaseCreate(View view) {
        // 设置 Header 样式
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        // 设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.fetchPickOutDoneList();
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 传入 false 表示刷新失败
                refreshLayout.finishRefresh(1000);
                adapter = null;
                // 刷新数据
                presenter.fetchPickOutDoneList();

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 传入 false 表示刷新失败
                refreshLayout.finishLoadMore(1000);
                ZBUiUtils.showToast("没有更多数据了");
            }
        });
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.zb_content_fragment;
    }


    @Override
    public void showMaterial(final List<PickOutDoneData.ListBean> list) {

        if (adapter == null) {

            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            recyclerView.setLayoutManager(manager);
            adapter = new PickOutDoneAdapter(getContext(), R.layout.item_pick_out_done, list);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new PickOutAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Intent intent = new Intent(getActivity(), PickOutTodoDetailActivity.class);
                    intent.putExtra("sapOrderNo", list.get(position).getSapOrderNo());
                    startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
