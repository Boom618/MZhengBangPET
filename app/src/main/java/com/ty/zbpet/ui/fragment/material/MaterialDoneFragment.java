package com.ty.zbpet.ui.fragment.material;

import android.content.Intent;
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
import com.ty.zbpet.bean.material.MaterialDoneList;
import com.ty.zbpet.presenter.material.MaterialPresenter;
import com.ty.zbpet.presenter.material.MaterialUiListInterface;
import com.ty.zbpet.ui.activity.material.ArrivalInDoneDetailActivity;
import com.ty.zbpet.ui.adapter.SwipeItemLayout;
import com.ty.zbpet.ui.adapter.material.MaterialDoneAdapter;
import com.ty.zbpet.ui.base.BaseFragment;
import com.ty.zbpet.ui.base.EmptyLayout;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 原辅料——到货入库——已办
 *
 * @author TY
 */
public class MaterialDoneFragment extends BaseFragment implements MaterialUiListInterface<MaterialDoneList.ListBean> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;


    private EmptyLayout emptyLayout;

    /**
     * 下拉刷新 flag
     */
    private boolean refresh = false;

    private MaterialDoneAdapter materialAdapter;

    private MaterialPresenter materialPresenter = new MaterialPresenter(this);


    @Override
    protected View onBaseCreate(View view) {
        refreshLayout.setRefreshHeader(new MaterialHeader(this.getContext()));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(this.getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        return view;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_recyclerview;
    }


    @Override
    public void onStart() {
        super.onStart();

        // 第一次获取数据
        materialPresenter.fetchDoneMaterial(CodeConstant.BUY_IN_TYPE);

    }

    @Override
    public void onResume() {
        super.onResume();

        /**设置 Header
         * BezierRadarHeader  贝塞尔雷达
         * WaterDropHeader 苹果水滴
         * DeliveryHeader 交货
         *
         * MaterialHeader 材料 5.0 样式
         * StoreHouseHeader 跑马灯
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 传入 false 表示刷新失败
                refreshLayout.finishRefresh(1000);
                // 刷新数据
                materialPresenter.fetchDoneMaterial(CodeConstant.BUY_IN_TYPE);
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
    public void onDestroy() {
        super.onDestroy();

        // 关闭网络请求
    }


    @Override
    public void showMaterial(final List<MaterialDoneList.ListBean> list) {
        if (materialAdapter == null || refresh) {
            refresh = false;
            if (materialAdapter == null) {
                LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
                recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
                recyclerView.setLayoutManager(manager);
            }

            materialAdapter = new MaterialDoneAdapter(this.getContext(), R.layout.activity_content_list_three, list);
            recyclerView.setAdapter(materialAdapter);
            materialAdapter.setOnItemClickListener(new MaterialDoneAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Intent intent = new Intent(getActivity(), ArrivalInDoneDetailActivity.class);
//                    Intent intent = new Intent(getActivity(), ArrivalInDoneDetailActivityK.class);
                    intent.putExtra("mInWarehouseOrderId", list.get(position).getMInWarehouseOrderId());
                    intent.putExtra("sapOrderNo", list.get(position).getSapOrderNo());
                    intent.putExtra("warehouseId", list.get(position).getWarehouseId());
                    intent.putExtra("orderId", list.get(position).getOrderId());
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
