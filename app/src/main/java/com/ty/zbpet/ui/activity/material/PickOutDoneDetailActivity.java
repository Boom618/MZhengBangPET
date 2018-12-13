package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.material.MaterialDetailsOut;
import com.ty.zbpet.bean.material.MaterialDoneSave;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.material.MaterialUiListInterface;
import com.ty.zbpet.presenter.material.PickOutPresenter;
import com.ty.zbpet.ui.adapter.material.PickingDoneDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.RequestBody;

/**
 * @author TY on 2018/11/22.
 * 领料出库 已办详情
 */
public class PickOutDoneDetailActivity extends BaseActivity implements MaterialUiListInterface<MaterialDetailsOut.ListBean> {


    private RecyclerView reView;
    private TextView tvTime;
    private EditText etDesc;
    private Button addButton;

    private PickingDoneDetailAdapter adapter;

    private String selectTime;
    private String sapOrderNo;
    private String orderId;

    private String warehouseId;
    private String mOutWarehouseOrderId;
    private List<MaterialDetailsOut.ListBean> list = new ArrayList<>();


    private PickOutPresenter presenter = new PickOutPresenter(this);


    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_content_row_two;
    }

    @Override
    protected void initOneData() {
        sapOrderNo = getIntent().getStringExtra("sapOrderNo");
        mOutWarehouseOrderId = getIntent().getStringExtra("mOutWarehouseOrderId");

        orderId = getIntent().getStringExtra("orderId");

        presenter.fetchPickOutDoneListDetails(orderId);
    }

    @Override
    protected void initTwoView() {


        initToolBar(R.string.pick_out_storage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickOutDoneSave(initDoneBody());
            }
        });

        reView = findViewById(R.id.rv_in_storage_detail);
        tvTime = findViewById(R.id.tv_time);
        etDesc = findViewById(R.id.et_desc);
        addButton = findViewById(R.id.add_ship);
        addButton.setVisibility(View.GONE);

        SimpleDateFormat format = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = format.format(new Date());

        tvTime.setText(selectTime);

        etDesc.setInputType(InputType.TYPE_NULL);
    }

    /**
     * 出库 保存
     */
    private void pickOutDoneSave(RequestBody body) {

        HttpMethods.getInstance().pickOutDoneSave(new BaseSubscriber<ResponseInfo>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 入库成功（保存）
                    ZBUiUtils.showToast(responseInfo.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                } else {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                }
            }
        },body);
    }


    /**
     * 构建保存 body
     * @return
     */
    private RequestBody initDoneBody() {

        MaterialDoneSave requestBody = new MaterialDoneSave();

        requestBody.setOrderId(mOutWarehouseOrderId);
        requestBody.setWarehouseId(warehouseId);
        requestBody.setOutTime(selectTime);
        requestBody.setOrderId(orderId);
        String json = DataUtils.toJson(requestBody, 1);

        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }

    @Override
    public void showMaterial(List<MaterialDetailsOut.ListBean> list) {
        warehouseId = list.get(0).getWarehouseId();

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new PickingDoneDetailAdapter(this, R.layout.item_material_detail_three_done, list);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new PickingDoneDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.gone_view);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);

                    if (rlDetail.getVisibility() == View.VISIBLE) {
                        rlDetail.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.ic_collapse);
                    } else {
                        rlDetail.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.ic_expand);
                    }

                    ZBUiUtils.hideInputWindow(view.getContext(), view);

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
