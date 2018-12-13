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
import com.ty.zbpet.bean.product.ProductDetailsOut;
import com.ty.zbpet.bean.product.ProductDoneSave;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.product.ProducePresenter;
import com.ty.zbpet.presenter.product.ProductUiObjInterface;
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity;
import com.ty.zbpet.ui.adapter.product.ProductDoneDetailAdapter;
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
 * 生产入库 已办详情
 */
public class ProductDoneDetailActivity extends BaseActivity implements ProductUiObjInterface<ProductDetailsOut> {


    private RecyclerView reView;
    private TextView tvTime;
    private EditText etDesc;

    private TextView titleName;
    private TextView selectHouse;

    private ProductDoneDetailAdapter adapter;

    private String selectTime;
    /**
     * 仓库 ID
     */
    private String warehouseId;

    private String orderId;
    private String sapOrderNo;
    private List<ProductDetailsOut.ListBean> list = new ArrayList<>();


    private ProducePresenter presenter = new ProducePresenter(this);


    /**
     * 用户信息
     */
    private UserInfo userInfo = DataUtils.getUserInfo();

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_content_row_three;
    }

    @Override
    protected void initOneData() {

        orderId = getIntent().getStringExtra("orderId");
        sapOrderNo = getIntent().getStringExtra("sapOrderNo");

        presenter.fetchProductDoneInfo(orderId);
    }

    @Override
    protected void initTwoView() {


        initToolBar(R.string.pick_out_storage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDoneSave(initDoneBody());
            }
        });

        reView = findViewById(R.id.rv_in_storage_detail);
        tvTime = findViewById(R.id.tv_time);
        etDesc = findViewById(R.id.et_desc);
        titleName = findViewById(R.id.in_storage_detail);
        selectHouse = findViewById(R.id.tv_house);

        titleName.setText("入库明细");
        //selectHouse.setText(userInfo.getWarehouseList().get(0).getWarehouseName());

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
    private void productDoneSave(RequestBody body) {

        HttpMethods.getInstance().getProduceDoneSave(new BaseSubscriber<ResponseInfo>() {
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

        ProductDoneSave requestBody = new ProductDoneSave();

        int size = list.size();
        ArrayList<ProductDoneSave.DetailsBean> beans = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ProductDoneSave.DetailsBean detailsBean = new ProductDoneSave.DetailsBean();

            ArrayList<String> boxQrCodeList = list.get(i).getBoxQrCode();
            String goodsId = list.get(i).getGoodsId();
            String goodsNo = list.get(i).getGoodsNo();
            String warehouseId = list.get(i).getWarehouseId();
            String number = list.get(i).getNumber();

            detailsBean.setNumber(number);
            detailsBean.setBoxQrCode(boxQrCodeList);
            detailsBean.setWarehouseId(warehouseId);

            detailsBean.setGoodsId(goodsId);
            detailsBean.setGoodsNo(goodsNo);

            List<UserInfo.WarehouseListBean> warehouseList = userInfo.getWarehouseList();
            detailsBean.setWarehouseList(warehouseList);
            beans.add(detailsBean);
        }

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
        selectHouse.setText(list.get(0).getWarehouseName());

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new ProductDoneDetailAdapter(this, R.layout.item_produce_detail_done, list);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new ProductDoneDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.rl_detail);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);

                    final ArrayList<String> boxQrCodeList = list.get(position).getBoxQrCode();

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
                            Intent intent = new Intent(ProductDoneDetailActivity.this, ScanBoxCodeActivity.class);
                            intent.putExtra(CodeConstant.PAGE_STATE,false);
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
