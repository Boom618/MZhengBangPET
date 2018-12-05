package com.ty.zbpet.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.UserInfo;
import com.ty.zbpet.bean.material.MaterialDoneSave;
import com.ty.zbpet.bean.product.ProductDetailsOut;
import com.ty.zbpet.bean.product.ProductDoneSave;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.product.BuyInPresenter;
import com.ty.zbpet.presenter.product.ProductUiObjInterface;
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity;
import com.ty.zbpet.ui.adapter.product.BuyInDoneDetailAdapter;
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
 * 外采入库 已办详情
 */
public class BuyInDoneDetailActivity extends BaseActivity implements ProductUiObjInterface<ProductDetailsOut> {


    private RecyclerView reView;
    private TextView tvTime;
    private EditText etDesc;

    private BuyInDoneDetailAdapter adapter;

    private String selectTime;
    /**
     * 仓库 ID
     */
    private String warehouseId;

    private String orderId;
    private String sapOrderNo;

    /**
     * 用户信息
     */
    private UserInfo userInfo = DataUtils.getUserInfo();

    private List<ProductDetailsOut.ListBean> list = new ArrayList<>();


    private BuyInPresenter presenter = new BuyInPresenter(this);


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
        orderId = getIntent().getStringExtra("orderId");

        presenter.fetchBuyInDoneListDetails(orderId);
    }

    @Override
    protected void initTwoView() {


        initToolBar(R.string.pick_out_storage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyInDoneSave(initDoneBody());
            }
        });

        reView = findViewById(R.id.rv_in_storage_detail);
        tvTime = findViewById(R.id.tv_time);
        etDesc = findViewById(R.id.et_desc);
        findViewById(R.id.add_ship).setVisibility(View.GONE);

        SimpleDateFormat format = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = format.format(new Date());

        tvTime.setText(selectTime);

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZBUiUtils.showPickDate(v.getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        selectTime = ZBUiUtils.getTime(date);
                        tvTime.setText(selectTime);

                        ZBUiUtils.showToast(selectTime);
                    }
                });
            }
        });
    }

    /**
     * 冲销 保存
     */
    private void BuyInDoneSave(RequestBody body) {

        HttpMethods.getInstance().getBuyInDoneSave(new BaseSubscriber<ResponseInfo>() {
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
        }, body);
    }

    private RequestBody initDoneBody() {

//        MaterialDoneSave requestBody = new MaterialDoneSave();
        ProductDoneSave requestBody = new ProductDoneSave();

        int size = list.size();
        ArrayList<ProductDoneSave.DetailsBean> beans = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ProductDoneSave.DetailsBean detailsBean = new ProductDoneSave.DetailsBean();

            ArrayList<String> boxQrCodeList = list.get(i).getBoxQrCodeList();
            String goodsId = list.get(i).getGoodsId();
            String goodsNo = list.get(i).getGoodsNo();
            String warehouseId = list.get(i).getWarehouseId();

            detailsBean.setBoxQrCodeList(boxQrCodeList);
            detailsBean.setWarehouseId(warehouseId);

            detailsBean.setGoodsId(goodsId);
            detailsBean.setGoodsNo(goodsNo);

            List<UserInfo.WarehouseListBean> warehouseList = userInfo.getWarehouseList();
            detailsBean.setWarehouseList(warehouseList);
        }

        // warehouseId,outTime,orderId,sapOrderNo,remark,details:[]
        String remark = etDesc.getText().toString().trim();

        requestBody.setDetails(beans);
        requestBody.setOrderId(orderId);
        requestBody.setSapOrderNo(sapOrderNo);
        requestBody.setOutTime(selectTime);
        requestBody.setRemark(remark);
        String json = DataUtils.toJson(requestBody, 1);

        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }

    @Override
    public void detailObjData(ProductDetailsOut obj) {

        list = obj.getList();

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new BuyInDoneDetailAdapter(this, R.layout.item_product_detail_two_done, list);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new BuyInDoneDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.gone_view);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);

                    final ArrayList<String> boxQrCodeList = list.get(position).getBoxQrCodeList();

                    Button bindingCode = holder.itemView.findViewById(R.id.btn_binding_code);

                    if (rlDetail.getVisibility() == View.VISIBLE) {
                        rlDetail.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.ic_collapse);
                    } else {
                        rlDetail.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.ic_expand);

                    }

                    bindingCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(BuyInDoneDetailActivity.this, ScanBoxCodeActivity.class);
                            intent.putExtra(CodeConstant.PAGE_STATE, false);
                            intent.putStringArrayListExtra("boxCodeList", boxQrCodeList);
                            startActivity(intent);
                        }
                    });

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
