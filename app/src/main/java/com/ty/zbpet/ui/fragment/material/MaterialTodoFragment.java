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
import com.ty.zbpet.bean.material.MaterialTodoList;
import com.ty.zbpet.presenter.material.MaterialPresenter;
import com.ty.zbpet.presenter.material.MaterialUiListInterface;
import com.ty.zbpet.ui.activity.material.ArrivalInTodoDetailActivity;
import com.ty.zbpet.ui.adapter.material.MaterialTodoAdapter;
import com.ty.zbpet.ui.base.BaseFragment;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author TY
 * <p>
 * 待办 （ 入库 ） Fragment
 */
public class MaterialTodoFragment extends BaseFragment implements MaterialUiListInterface<MaterialTodoList.ListBean> {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private MaterialTodoAdapter adapter;

    private MaterialPresenter materialPresenter = new MaterialPresenter(this);

    public static MaterialTodoFragment newInstance(String tag) {
        MaterialTodoFragment fragment = new MaterialTodoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("someInt", tag);
        fragment.setArguments(bundle);

        return fragment;
    }


    /**
     * 加载的 inflater.inflate  的 View
     *
     * @param view layout inflate 的 View
     */
    @Override
    protected View onBaseCreate(View view) {

        // 设置 Header 样式
        refreshLayout.setRefreshHeader(new MaterialHeader(this.getContext()));
        // 设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(this.getContext()).setSpinnerStyle(SpinnerStyle.Scale));

        return view;

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.zb_content_list_fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        materialPresenter.fetchTODOMaterial();

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
                materialPresenter.fetchTODOMaterial();

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
    public void showMaterial(final List<MaterialTodoList.ListBean> list) {

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            recyclerView.setLayoutManager(manager);
            adapter = new MaterialTodoAdapter(ResourceUtil.getContext(), R.layout.item_material_todo, list);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new MaterialTodoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Intent intent = new Intent(getActivity(), ArrivalInTodoDetailActivity.class);
                    intent.putExtra("sapOrderNo", list.get(position).getSapOrderNo());
                    intent.putExtra("supplierId", list.get(position).getSupplierId());
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
