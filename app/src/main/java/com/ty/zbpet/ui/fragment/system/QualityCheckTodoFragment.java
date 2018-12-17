package com.ty.zbpet.ui.fragment.system;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.ty.zbpet.bean.system.QualityCheckTodoList;
import com.ty.zbpet.presenter.system.SystemPresenter;
import com.ty.zbpet.presenter.system.SystemUiListInterface;
import com.ty.zbpet.ui.activity.system.QualityCheckTodoDetailActivity;
import com.ty.zbpet.ui.adapter.system.QuaCheckTodoListAdapter;
import com.ty.zbpet.ui.base.BaseFragment;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 质检——未办
 *
 * @author TY
 */
public class QualityCheckTodoFragment extends BaseFragment implements SystemUiListInterface<QualityCheckTodoList.DataBean> {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private SystemPresenter presenter = new SystemPresenter(this);

    private QuaCheckTodoListAdapter adapter;

    public static QualityCheckTodoFragment newInstance(String tag) {
        QualityCheckTodoFragment fragment = new QualityCheckTodoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("someInt", tag);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.zb_content_list_fragment;
    }

    @Override
    protected View onBaseCreate(View view) {
        // 设置 Header 样式
        refreshLayout.setRefreshHeader(new MaterialHeader(view.getContext()));
        // 设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(view.getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.fetchQualityCheckTodoList();

    }

    @Override
    public void onResume() {
        super.onResume();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 传入 false 表示刷新失败
                refreshLayout.finishRefresh(1000);
                // 刷新数据
                presenter.fetchQualityCheckTodoList();
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
    public void showSystem(final List<QualityCheckTodoList.DataBean> list) {

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            recyclerView.setLayoutManager(manager);
            adapter = new QuaCheckTodoListAdapter(ResourceUtil.getContext(), R.layout.item_quality_list, list);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new QuaCheckTodoListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Intent intent = new Intent(getActivity(), QualityCheckTodoDetailActivity.class);
                    intent.putExtra("arrivalOrderNo", list.get(position).getArrivalOrderNo());
                    startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
