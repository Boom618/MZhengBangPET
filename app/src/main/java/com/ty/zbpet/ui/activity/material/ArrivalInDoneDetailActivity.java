package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialDoneDetailsData;
import com.ty.zbpet.bean.MaterialDoneSaveData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.material.MaterialPresenter;
import com.ty.zbpet.presenter.material.MaterialUiListInterface;
import com.ty.zbpet.ui.adapter.MaterialDoneDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;
import com.ty.zbpet.util.DataUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

import butterknife.BindView;
import okhttp3.RequestBody;

/**
 * @author TY on 2018/11/14.
 * <p>
 * 已办 详情
 */
public class ArrivalInDoneDetailActivity extends BaseActivity implements MaterialUiListInterface<MaterialDoneDetailsData.ListBean> {

    @BindView(R.id.rc_done_detail_list)
    RecyclerView detailRc;


    @BindView(R.id.et_desc)
    EditText etDesc;


    private String mInWarehouseOrderId;
    private String warehouseId;
    private String sapProcOrder;
    private String positionId;

    private MaterialDoneDetailAdapter adapter;
    private MaterialPresenter materialPresenter = new MaterialPresenter(this);


    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_material_done_detail;
    }

    @Override
    protected void initOneData() {

        mInWarehouseOrderId = getIntent().getStringExtra("mInWarehouseOrderId");
        sapProcOrder = getIntent().getStringExtra("sapOrderNo");
        warehouseId = getIntent().getStringExtra("warehouseId");

    }

    @Override
    protected void initTwoView() {

        initToolBar(R.string.material_reversal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                materialDoneInSave(initRequestBody());

            }
        });

    }

    /**
     * 已办 保存
     * @param body
     */
    public void materialDoneInSave(RequestBody body) {
        HttpMethods.getInstance().materialDoneInSave(new BaseSubscriber<ResponseInfo>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(ResponseInfo responseInfo) {

                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                    finish();
                } else {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                }
            }

        }, body);

    }

    /**
     * 初始化 请求参数
     *
     * @return
     */
    private RequestBody initRequestBody() {

        MaterialDoneSaveData bean = new MaterialDoneSaveData();

        bean.setWarehouseId(warehouseId);
        bean.setInOutOrderId(mInWarehouseOrderId);
        bean.setSapProcOrder(sapProcOrder);
        bean.setPositionId(positionId);
        bean.setRemark(etDesc.getText().toString().trim());

        String json = DataUtils.toJson(bean, 1);

        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);

    }

    @Override
    protected void onStart() {
        super.onStart();
        materialPresenter.fetchDoneMaterialDetails(mInWarehouseOrderId);
    }

    @Override
    public void showMaterial(List<MaterialDoneDetailsData.ListBean> list) {

        positionId = list.get(0).getPositionId();

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            detailRc.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            detailRc.setLayoutManager(manager);

            adapter = new MaterialDoneDetailAdapter(this, R.layout.item_material_done_detail, list);
            detailRc.setAdapter(adapter);
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
