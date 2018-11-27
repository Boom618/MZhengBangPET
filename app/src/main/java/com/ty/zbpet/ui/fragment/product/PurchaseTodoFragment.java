package com.ty.zbpet.ui.fragment.product;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.GoodsPurchaseOrderList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.activity.product.PurchaseInStorageDetailActivity;
import com.ty.zbpet.ui.adapter.PurchaseInWarehouseAdapter;
import com.ty.zbpet.ui.base.BaseFragment;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

import butterknife.BindView;

/**
 * 成品——外采入库——未办
 * @author TY
 */
public class PurchaseTodoFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PurchaseInWarehouseAdapter adapter;

    @Override
    protected View onBaseCreate(View view) {

        getGoodsPurchaseOrderList();
        return view;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_recyclerview;
    }



    private void getGoodsPurchaseOrderList() {
        HttpMethods.getInstance().getPurchaseOrderList(new BaseSubscriber<GoodsPurchaseOrderList>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(GoodsPurchaseOrderList infoList) {
                if (infoList.getData() != null) {
                    List<GoodsPurchaseOrderList.DataBean.ListBean> list = infoList.getData().getList();
                    if (list != null && list.size() != 0) {
                        refreshUI(list);
                    } else {
                        ZBUiUtils.showToast("没有信息");
                    }
                } else {
                    ZBUiUtils.showToast(infoList.getMessage());
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
