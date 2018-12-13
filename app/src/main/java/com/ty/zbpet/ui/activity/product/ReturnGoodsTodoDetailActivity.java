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
import com.ty.zbpet.bean.UserInfo;
import com.ty.zbpet.bean.product.ProductTodoDetails;
import com.ty.zbpet.bean.product.ProductTodoSave;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.product.ProductUiListInterface;
import com.ty.zbpet.presenter.product.ReturnPresenter;
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity;
import com.ty.zbpet.ui.adapter.product.ReturnGoodsTodoDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBLog;
import com.ty.zbpet.util.ZBUiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * @author TY on 2018/11/22.
 * 退货入库 待办详情
 */
public class ReturnGoodsTodoDetailActivity extends BaseActivity implements ProductUiListInterface<ProductTodoDetails.ListBean>,ReturnGoodsTodoDetailAdapter.SaveEditListener {


    private RecyclerView reView;
    private TextView tvTime;
    private TextView titleName;
    private TextView tvPath;
    private TextView tvType;
    private EditText etDesc;

    private ReturnGoodsTodoDetailAdapter adapter;

    private String selectTime;
    private String sapOrderNo;

    private List<ProductTodoDetails.ListBean> oldList = new ArrayList<>();

    private final static int REQUEST_SCAN_CODE = 1;
    private final static int RESULT_SCAN_CODE = 2;

    private ReturnPresenter presenter = new ReturnPresenter(this);

    /**
     * 箱码
     */
    private ArrayList<String> boxCodeList = new ArrayList<>();

    /**
     * 列表 ID
     */
    private int itemId = -1;

    /**
     * 保存用户在输入框中的数据
     */
    private SparseArray<String> numberArray = new SparseArray(10);
    private SparseArray<String> startCodeArray = new SparseArray(10);
    private SparseArray<String> endCodeArray = new SparseArray(10);
    private SparseArray<String> sapArray = new SparseArray(10);
    private SparseArray<ArrayList<String>> carCodeArray = new SparseArray(10);
    /**
     * 库位码 ID
     */
    private SparseArray<String> positionId = new SparseArray(10);

    /**
     * 仓库 ID
     */
    private String warehouseId;


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

        presenter.fetchReturnOrderInfo(sapOrderNo);
    }

    @Override
    protected void initTwoView() {

        initToolBar(R.string.label_return_sell, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZBUiUtils.hideInputWindow(ReturnGoodsTodoDetailActivity.this,view);
                returnGoodsTodoSave(initTodoBody());
            }
        });

        reView = findViewById(R.id.rv_in_storage_detail);
        tvTime = findViewById(R.id.tv_time);
        titleName = findViewById(R.id.in_storage_detail);

        tvPath = findViewById(R.id.tv_path);
        tvType = findViewById(R.id.tv_type);
        etDesc = findViewById(R.id.et_desc);
        findViewById(R.id.add_ship).setVisibility(View.GONE);

        SimpleDateFormat format = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = format.format(new Date());

        tvTime.setText(selectTime);
        titleName.setText("退货明细");

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
    private void returnGoodsTodoSave(RequestBody body) {

        if (body == null) {
            return;
        }

        HttpMethods.getInstance().getReturnTodoSave(new SingleObserver<ResponseInfo>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
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

        ProductTodoSave requestBody = new ProductTodoSave();

        List<ProductTodoSave.DetailsBean> detail = new ArrayList<>();


        int size = oldList.size();
        for (int i = 0; i < size; i++) {
            List<String> boxQrCode = carCodeArray.get(i);
            String number = numberArray.get(i);
            String startCode = startCodeArray.get(i);
            String endCode = endCodeArray.get(i);
            String sap = sapArray.get(i);
            String id = positionId.get(i);

            String goodsId = oldList.get(i).getGoodsId();
            String goodsNo = oldList.get(i).getGoodsNo();
            String warehouseId = oldList.get(i).getWarehouseId();
            String warehouseName = oldList.get(i).getWarehouseName();
            String orderNumber = oldList.get(i).getOrderNumber();

            ProductTodoSave.DetailsBean bean = new ProductTodoSave.DetailsBean();
            if (!TextUtils.isEmpty(number) && boxQrCode != null) {

                bean.setPositionId(id);
                bean.setNumber(number);
                bean.setGoodsId(goodsId);
                bean.setGoodsNo(goodsNo);
                bean.setWarehouseId(warehouseId);
                bean.setWarehouseName(warehouseName);
                bean.setOrderNumber(orderNumber);
                bean.setStartQrCode(startCode);
                bean.setEndQrCode(endCode);
                bean.setSapMaterialBatchNo(sap);

                bean.setBoxQrCode(boxQrCode);

                detail.add(bean);
            } else{
                // 跳出当前循环、不处理
                continue;
            }
        }
        // 没有合法的操作数据,不请求网络
        if (detail.size() == 0) {
            ZBUiUtils.showToast("请完善您要保存的信息");
            return null;
        }
        String remark = etDesc.getText().toString().trim();
        String time = tvTime.getText().toString().trim();

        requestBody.setDetails(detail);
        requestBody.setWarehouseId(warehouseId);
        requestBody.setSapOrderNo(sapOrderNo);
        requestBody.setInTime(time);
        requestBody.setRemark(remark);


        String json = DataUtils.toJson(requestBody, 1);
        ZBLog.e("JSON " + json);
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }


    @Override
    public void showProduct(List<ProductTodoDetails.ListBean> list) {
        oldList = list;
        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new ReturnGoodsTodoDetailAdapter(this, R.layout.item_product_detail_two_todo, list);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new ReturnGoodsTodoDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final View view, RecyclerView.ViewHolder holder, final int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.gone_view);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);
                    Button bindingCode = holder.itemView.findViewById(R.id.btn_binding_code);
                    final TextView selectHouse = holder.itemView.findViewById(R.id.tv_select_ware);

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
                            Intent intent = new Intent(ReturnGoodsTodoDetailActivity.this, ScanBoxCodeActivity.class);
                            intent.putExtra("itemId", itemId);
                            intent.putExtra(CodeConstant.PAGE_STATE, true);
                            intent.putStringArrayListExtra("boxCodeList", carCodeArray.get(itemId));
                            startActivityForResult(intent, REQUEST_SCAN_CODE);
                        }
                    });

                    UserInfo userInfo = DataUtils.getUserInfo();

                    List<UserInfo.WarehouseListBean> warehouseList = userInfo.getWarehouseList();
                    final ArrayList<String> houseName = new ArrayList<>();

                    int size = warehouseList.size();
                    for (int i = 0; i < size; i++) {
                        houseName.add(warehouseList.get(i).getWarehouseName());
                    }

                    selectHouse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ZBUiUtils.selectDialog(view.getContext(), houseName, selectHouse);
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
            warehouseId = data.getStringExtra("warehouseId");
            boxCodeList = data.getStringArrayListExtra("boxCodeList");
            carCodeArray.put(itemId, boxCodeList);
        }
    }

    @Override
    public void saveEditAndGetHasFocusPosition(String etType, Boolean hasFocus, int position, EditText editText) {
        String textContent = editText.getText().toString().trim();

        if (CodeConstant.ET_NUMBER.equals(etType)) {
            numberArray.put(position, textContent);
        } else if (CodeConstant.ET_BATCH_NO.equals(etType)) {
            sapArray.put(position, textContent);
        } else if (CodeConstant.ET_START_CODE.equals(etType)) {
            startCodeArray.put(position, textContent);
        } else if (CodeConstant.ET_END_CODE.equals(etType)) {
            endCodeArray.put(position, textContent);
        }
    }

}
