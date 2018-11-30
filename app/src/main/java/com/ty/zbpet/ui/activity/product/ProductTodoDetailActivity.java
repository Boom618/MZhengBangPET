package com.ty.zbpet.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.material.MaterialTodoSave;
import com.ty.zbpet.bean.product.ProductDetailsIn;
import com.ty.zbpet.bean.product.ProductTodoDetails;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.product.ProducePresenter;
import com.ty.zbpet.presenter.product.ProductUiObjInterface;
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity;
import com.ty.zbpet.ui.adapter.product.ProductTodoDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBLog;
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
 * 生产入库 待办详情
 *
 * @author TY
 */
public class ProductTodoDetailActivity extends BaseActivity implements ProductUiObjInterface<ProductTodoDetails> {


    private RecyclerView reView;
    private TextView tvTime;
    private TextView backGoods;
    private TextView selectHouse;
    private TextView tvPath;
    private TextView tvType;
    private EditText etDesc;

    private ProductTodoDetailAdapter adapter;

    private String selectTime;
    private String sapOrderNo;

    private List<ProductTodoDetails.ListBean> oldList = new ArrayList<>();
    private List<ProductTodoDetails.WarehouseListBean> houseList = new ArrayList<>();

    private final static int REQUEST_SCAN_CODE = 1;
    private final static int RESULT_SCAN_CODE = 2;

    private ProducePresenter presenter = new ProducePresenter(this);

    /**
     * 保存用户在输入框中的数据
     */
    private SparseArray<String> bulkNumArray = new SparseArray(10);
    private SparseArray<String> batchNoArray = new SparseArray(10);
    private SparseArray<ArrayList<String>> carCodeArray = new SparseArray(10);
    /**
     * 库位码 ID
     */
    private SparseArray<String> positionId = new SparseArray(10);

    /**
     * 仓库 ID
     */
    private String warehouseId;

    /**
     * 列表 ID
     */
    private int itemId = -1;

    /**
     * 箱码
     */
    private ArrayList<String> boxCodeList = new ArrayList<>();


    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_content_row_three;
    }

    @Override
    protected void initOneData() {
        sapOrderNo = getIntent().getStringExtra("sapOrderNo");

        presenter.fetchProductTodoInfo(sapOrderNo);
    }

    @Override
    protected void initTwoView() {

        // in_storage_detail 到货明细

        initToolBar(R.string.label_purchase_in_storage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyInTodoSave(initTodoBody());
            }
        });

        reView = findViewById(R.id.rv_in_storage_detail);
        tvTime = findViewById(R.id.tv_time);
        backGoods = findViewById(R.id.in_storage_detail);
        selectHouse = findViewById(R.id.tv_house);

        tvPath = findViewById(R.id.tv_path);
        tvType = findViewById(R.id.tv_type);
        etDesc = findViewById(R.id.et_desc);

        SimpleDateFormat format = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = format.format(new Date());

        tvTime.setText(selectTime);
        backGoods.setText("入库明细");


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
     * 出库 保存
     */
    private void BuyInTodoSave(RequestBody body) {

        if (body == null) {
            return;
        }

        HttpMethods.getInstance().getBackTodoSave(new BaseSubscriber<ResponseInfo>() {
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

    /**
     * 构建 保存 的 Body
     *
     * @return
     */
    private RequestBody initTodoBody() {

        MaterialTodoSave requestBody = new MaterialTodoSave();
        List<MaterialTodoSave.DetailsBean> detail = new ArrayList<>();

        int size = oldList.size();
        for (int i = 0; i < size; i++) {
            List<String> boxQrCode = carCodeArray.get(i);
            String bulkNum = bulkNumArray.get(i);
            String batchNo = batchNoArray.get(i);
            String Id = positionId.get(i);

            MaterialTodoSave.DetailsBean bean = new MaterialTodoSave.DetailsBean();
            if (!TextUtils.isEmpty(bulkNum) && boxQrCode.size() > 0) {

                bean.setPositionId(Id);
                bean.setNumber(bulkNum);
                bean.setSapMaterialBatchNo(batchNo);

                detail.add(bean);
            } else if (null == bulkNum && null == boxQrCode) {
                // 跳出当前一列、不处理
                continue;
            } else {
                // 车库数量或者库位码其中一项为空
                ZBUiUtils.showToast("车库数量或库位码信息不全");
                break;
            }
        }
        // 没有合法的操作数据,不请求网络
        if (detail.size() == 0) {
            return null;
        }

        requestBody.setDetails(detail);
        requestBody.setWarehouseId(warehouseId);
        requestBody.setSapOrderNo(sapOrderNo);
        requestBody.setOutWarehouseTime(tvTime.getText().toString().trim());
        requestBody.setRemark(etDesc.getText().toString().trim());


        String json = DataUtils.toJson(requestBody, 1);
        ZBLog.e("JSON " + json);
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }


    @Override
    public void detailObjData(ProductTodoDetails details) {

        houseList = details.getWarehouseList();
        oldList = details.getList();

        int size = houseList.size();
        final ArrayList<String> houseName = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            houseName.add(houseList.get(i).getWarehouseName());
        }
        selectHouse.setText(houseName.get(0));
        selectHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZBUiUtils.selectDialog(v.getContext(), houseName, selectHouse);
            }
        });

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new ProductTodoDetailAdapter(this, R.layout.item_produce_detail_todo, oldList);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new ProductTodoDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.rl_detail);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);
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
                            itemId = position;
                            Intent intent = new Intent(ProductTodoDetailActivity.this, ScanBoxCodeActivity.class);
                            intent.putExtra("itemId", itemId);
                            intent.putExtra(CodeConstant.PAGE_STATE, true);
                            intent.putStringArrayListExtra("boxCodeList", carCodeArray.get(itemId));
                            startActivityForResult(intent, REQUEST_SCAN_CODE);
                        }
                    });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SCAN_CODE && resultCode == RESULT_SCAN_CODE) {
            itemId = data.getIntExtra("itemId", -1);
            boxCodeList = data.getStringArrayListExtra("boxCodeList");
            carCodeArray.put(itemId, boxCodeList);
        }
    }

}