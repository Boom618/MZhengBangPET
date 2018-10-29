package com.ty.zbpet.ui.fragment.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.GoodsPurchaseOrderList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.activity.PurchaseInStorageDetailActivity;
import com.ty.zbpet.ui.adapter.PurchaseInWarehouseAdapter;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.UIUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 成品——外采入库——未办
 * @author TY
 */
public class PurchaseInStorageNoDoingFragment extends Fragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PurchaseInWarehouseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);
        getGoodsPurchaseOrderList();
        return view;
    }

    private void getGoodsPurchaseOrderList() {
        HttpMethods.getInstance().getGoodsPurchaseOrderList(new BaseSubscriber<GoodsPurchaseOrderList>() {
            @Override
            public void onError(ApiException e) {
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(GoodsPurchaseOrderList infoList) {
                if (infoList.getData() != null) {
                    List<GoodsPurchaseOrderList.DataBean.ListBean> list = infoList.getData().getList();
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

    private void refreshUI(List<GoodsPurchaseOrderList.DataBean.ListBean> list) {
        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            recyclerView.setLayoutManager(manager);
            adapter = new PurchaseInWarehouseAdapter(ResourceUtil.getContext(), list);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new PurchaseInWarehouseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, GoodsPurchaseOrderList.DataBean.ListBean data) {
                    Intent intent = new Intent(getActivity(), PurchaseInStorageDetailActivity.class);
                    intent.putExtra("sapOrderNo", data.getSapOrderNo());
                    startActivity(intent);
                }
            });
        }
    }

}
