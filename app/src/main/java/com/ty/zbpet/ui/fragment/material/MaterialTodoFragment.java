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
import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.bean.PickOutDetailInfo;
import com.ty.zbpet.presenter.material.MaterialPresenter;
import com.ty.zbpet.presenter.material.MaterialUiInterface;
import com.ty.zbpet.ui.activity.material.ArrivalInStorageDetailActivity;
import com.ty.zbpet.ui.adapter.MaterialAdapter;
import com.ty.zbpet.ui.base.BaseFragment;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.UIUtils;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * @author TY
 *
 * 待办 Fragment
 */
public class MaterialTodoFragment extends BaseFragment implements MaterialUiInterface<MaterialData.ListBean> {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private MaterialAdapter adapter;

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
     * @param view layout inflate 的 View
     */
    @Override
    protected void onBaseCreate(View view) {

        // 设置 Header 样式
        refreshLayout.setRefreshHeader(new MaterialHeader(this.getContext()));
        // 设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(this.getContext()).setSpinnerStyle(SpinnerStyle.Scale));

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.zb_content_fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        materialPresenter.fetchMaterial();

    }

    @Override
    public void onResume() {
        super.onResume();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 传入 false 表示刷新失败
                refreshLayout.finishRefresh(2000);
                // 刷新数据
                materialPresenter.fetchMaterial();

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 传入 false 表示刷新失败
                refreshLayout.finishLoadMore(2000);
                UIUtils.showToast("没有更多数据了");
            }
        });
    }

    @Override
    public void showMaterial(final List<MaterialData.ListBean> list) {

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
                    // TODO 传参
//                    intent.putExtra("orderId",list.get(position).getSupplierId());
//                    intent.putExtra("sapOrderNo",list.get(position).getMaterialId());
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