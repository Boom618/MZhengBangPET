package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialDetailsData;
import com.ty.zbpet.bean.PostArrivalInRecallOutInfo;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.material.MaterialPresenter;
import com.ty.zbpet.presenter.material.MaterialUiInterface;
import com.ty.zbpet.ui.adapter.MaterialDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.UIUtils;
import com.ty.zbpet.util.Utils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import okhttp3.RequestBody;

/**
 * 原辅料——到货入库详情
 *
 * @author TY
 */
public class ArrivalInStorageDetailActivity extends BaseActivity implements MaterialUiInterface<MaterialDetailsData.ListBean> {

    @BindView(R.id.rv_in_storage_detail)
    RecyclerView recyclerView;
    //@BindView(R.id.pick_view)
    TimePickerView pickView;
    @BindView(R.id.et_desc)
    EditText etDesc;
    private String sapOrderNo;
    private MaterialDetailAdapter adapter;
    private List<MaterialDetailsData.ListBean> list = new ArrayList<>();
    private String orderId;
    private String warehouseId;

    private MaterialPresenter materialPresenter = new MaterialPresenter(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        //tvTime.setText(now + ":00");
//        PickerOptions options = new PickerOptions(2);
//        pickView = findViewById(R.id.pick_view);
        sapOrderNo = getIntent().getStringExtra("sapOrderNo");
        orderId = getIntent().getStringExtra("orderId");
        //getMaterialInWarehouseOrderInfo();

        initToolBar(R.string.label_purchase_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 冲销入库
                doPurchaseInRecallOut(initParam());
                finish();
            }
        });

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_arrival_in_storage_detail;
    }

    private void getMaterialInWarehouseOrderInfo() {
//        HttpMethods.getInstance().getMaterialInWarehouseOrderInfo(new BaseSubscriber<MaterialInWarehouseOrderInfo>() {
//            @Override
//            public void onError(ApiException e) {
//                UIUtils.showToast(e.getMessage());
//            }
//
//            @Override
//            public void onNext(MaterialInWarehouseOrderInfo info) {
//                if (info.getData() != null) {
//                    warehouseId = info.getData().getWarehouseId();
//                    List<MaterialInWarehouseOrderInfo.DataBean.ListBean> infos = info.getData().getList();
//                    if (infos != null && infos.size() != 0) {
//                        list.clear();
//                        list.addAll(infos);
//                        refreshUI();
//                    } else {
//                        UIUtils.showToast("没有信息");
//                    }
//                } else {
//                    UIUtils.showToast(info.getMessage());
//                }
//            }
//        }, sapOrderNo);
    }

//    private void refreshUI() {
//        if (adapter == null) {
//            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
//            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
//            recyclerView.setLayoutManager(manager);
//            adapter = new MaterialInWarehouseDetailAdapter(ResourceUtil.getContext(), list);
//            recyclerView.setAdapter(adapter);
//        } else {
//            adapter.notifyDataSetChanged();
//        }
//    }


    private RequestBody initParam() {
        PostArrivalInRecallOutInfo info = new PostArrivalInRecallOutInfo();
        info.setInOutOrderId(orderId);
        info.setSapProcOrder(sapOrderNo);
        info.setWarehouseId(warehouseId);
        //info.setSapMaterialBatchNo(etBatchNo.getText().toString().trim());
        // TODO 当前选择时间
        //info.setOutStoreDate(tvTime.getText().toString().trim());
        info.setRemark(etDesc.getText().toString().trim());
        String json = Utils.toJson(info, 1);
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }

    private void doPurchaseInRecallOut(RequestBody body) {

        HttpMethods.getInstance().purchaseInRecallOut(new BaseSubscriber<ResponseInfo>() {
            @Override
            public void onError(ApiException e) {
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(ResponseInfo responseInfo) {
                UIUtils.showToast(responseInfo.getMessage());
            }
        }, body);
    }

    @Override
    public void showMaterial(List<MaterialDetailsData.ListBean> DetailsLists) {
        materialPresenter.fetchTODOMaterialDetails(sapOrderNo);
        // TODO warehouseId 值获取未完成
        //warehouseId = DetailsLists.getWarehouseId();
        list.clear();
        list.addAll(DetailsLists);



        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            recyclerView.setLayoutManager(manager);
            //adapter = new MaterialInWarehouseDetailAdapter(this, DetailsLists);
            adapter = new MaterialDetailAdapter(this, R.layout.item_arrive_in_storage_detail, DetailsLists);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new MaterialDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.rl_detail);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);
                    if (rlDetail.getVisibility() == View.VISIBLE) {
                        rlDetail.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.ic_collapse);
                    } else {
                        rlDetail.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.ic_expand);
                    }

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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
