package com.ty.zbpet.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.bean.MaterialInWarehouseOrderList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.activity.ArrivalInStorageDetailActivity;
import com.ty.zbpet.ui.adapter.MaterialAdapter;
import com.ty.zbpet.ui.base.EmptyLayout;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.UIUtils;
import com.ty.zbpet.util.Utils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 原辅料——到货入库——已办
 *
 * @author TY
 */
public class ArrivalInStorageCompleteFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;


    private EmptyLayout emptyLayout;

    private MaterialAdapter materialAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);

        getMaterialInWarehouseOrderList();

        return view;
    }

    private void getMaterialInWarehouseOrderList() {
        HttpMethods.getInstance().getMaterialInWarehouseOrderList(new BaseSubscriber<MaterialInWarehouseOrderList>() {
            @Override
            public void onError(ApiException e) {
                UIUtils.showToast("原辅料——到货入库——已办 == onError ");
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(MaterialInWarehouseOrderList infoList) {
                UIUtils.showToast("原辅料——到货入库——已办 == success ");

                if (infoList.getData() != null) {
                    List<MaterialInWarehouseOrderList.DataBean.ListBean> list = infoList.getData().getList();
                    if (list != null && list.size() != 0) {
                        refreshUI(list);
                    } else {
                        UIUtils.showToast("没有信息");
                    }
                } else {
                    UIUtils.showToast(infoList.getMessage());
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        //ArrayList<MaterialInWarehouseOrderList.DataBean.ListBean> data = Utils.getData("已办");
        //refreshUI(data);

        /**设置 Header
         * BezierRadarHeader  贝塞尔雷达
         * WaterDropHeader 苹果水滴
         * DeliveryHeader 交货
         *
         * MaterialHeader 材料 5.0 样式
         * StoreHouseHeader 跑马灯
         */
        refreshLayout.setRefreshHeader(new MaterialHeader(this.getContext()));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(this.getContext()).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 传入 false 表示刷新失败
                refreshLayout.finishRefresh(1000);
                // 刷新数据
                getMaterialInWarehouseOrderList();

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

    private void refreshUI(List<MaterialInWarehouseOrderList.DataBean.ListBean> list) {
        if (materialAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            recyclerView.setLayoutManager(manager);
            materialAdapter = new MaterialAdapter(this.getContext(), R.layout.item_arrive_in_storage_complete, list);
            recyclerView.setAdapter(materialAdapter);
            materialAdapter.setOnItemClickListener(new MaterialAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Intent intent = new Intent(getActivity(), ArrivalInStorageDetailActivity.class);
//                    intent.putExtra("orderId", data.getOrderId());
//                    intent.putExtra("sapOrderNo", data.getSapOrderNo());
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
    public void onDestroy() {
        super.onDestroy();

        // 关闭网络请求
    }



}
