package com.ty.zbpet.ui.fragment.product;

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
import com.ty.zbpet.bean.product.ProductDoneList;
import com.ty.zbpet.presenter.product.ProductUiObjInterface;
import com.ty.zbpet.presenter.product.ReturnPresenter;
import com.ty.zbpet.ui.activity.product.ReturnGoodsDoneDetailActivity;
import com.ty.zbpet.ui.adapter.product.ReturnGoodsDoneListAdapter;
import com.ty.zbpet.ui.base.BaseFragment;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 成品——退货入库——已办
 *
 * @author TY
 */
public class ReturnGoodsDoneFragment extends BaseFragment implements ProductUiObjInterface<ProductDoneList> {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private ReturnPresenter presenter = new ReturnPresenter(this);

    private ReturnGoodsDoneListAdapter adapter;

    /**
     * 下拉刷新 flag
     */
    private boolean refresh = false;

    public static ReturnGoodsDoneFragment newInstance(String tag) {
        ReturnGoodsDoneFragment fragment = new ReturnGoodsDoneFragment();
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

        presenter.fetchReturnGoodsDoneList(CodeConstant.RETURN_TYPE);

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
                presenter.fetchReturnGoodsDoneList(CodeConstant.RETURN_TYPE);
                refresh = true;
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
    public void detailObjData(final ProductDoneList obj) {

        final List<ProductDoneList.ListBean> list = obj.getList();

        if (adapter == null || refresh) {
            refresh = false;
            if (adapter == null) {
                LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
                recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
                recyclerView.setLayoutManager(manager);
            }
            adapter = new ReturnGoodsDoneListAdapter(ResourceUtil.getContext(), R.layout.item_send_out_list_todo, list);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new ReturnGoodsDoneListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Intent intent = new Intent(getActivity(), ReturnGoodsDoneDetailActivity.class);
                    intent.putExtra("orderId", list.get(position).getId());
                    intent.putExtra("sapOrderNo", list.get(position).getSapOrderNo());
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

}
