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
import com.ty.zbpet.bean.product.ProductDetailsIn;
import com.ty.zbpet.bean.product.ProductTodoSave;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.product.BuyInPresenter;
import com.ty.zbpet.presenter.product.ProductUiListInterface;
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity;
import com.ty.zbpet.ui.adapter.product.BuyInTodoDetailAdapter;
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
 * @author TY on 2018/11/22.
 * 外采入库 待办详情
 */
public class BuyInTodoDetailActivity extends BaseActivity implements ProductUiListInterface<ProductDetailsIn.ListBean>
        , BuyInTodoDetailAdapter.SaveEditListener {


    private RecyclerView reView;
    private TextView tvTime;
    private TextView titleName;
    private TextView tvPath;
    private TextView tvType;
    private EditText etDesc;

    private BuyInTodoDetailAdapter adapter;

    private String selectTime;
    private String sapOrderNo;

    private List<ProductDetailsIn.ListBean> oldList = new ArrayList<>();

    private final static int REQUEST_SCAN_CODE = 1;
    private final static int RESULT_SCAN_CODE = 2;

    private BuyInPresenter presenter = new BuyInPresenter(this);

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
    private SparseArray<String> bulkNumArray = new SparseArray(10);
    private SparseArray<String> batchNoArray = new SparseArray(10);
    private SparseArray<String> startCodeArray = new SparseArray(10);
    private SparseArray<String> endCodeArray = new SparseArray(10);
    private SparseArray<ArrayList<String>> carCodeArray = new SparseArray(10);
    /**
     * 库位码 ID
     */
    private SparseArray<String> positionId = new SparseArray(10);

    /**
     * 仓库 ID
     */
    private String warehouseId;
    private String supplierId;

    /**
     * 用户信息
     */
    private UserInfo userInfo = DataUtils.getUserInfo();

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
        supplierId = getIntent().getStringExtra("supplierId");

        presenter.fetchBuyInTodoListDetails(sapOrderNo);
    }

    @Override
    protected void initTwoView() {

        // in_storage_detail 到货明细

        initToolBar(R.string.label_purchase_in_storage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyInTodoSave(initTodoBody());
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
        titleName.setText("到货明细");

        // TODO　仓库默认值设置　
        DataUtils.setHouseId(0, 0);

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
    private void buyInTodoSave(RequestBody body) {

        if (body == null) {
            return;
        }

        HttpMethods.getInstance().getBuyInTodoSave(new BaseSubscriber<ResponseInfo>() {
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

        ProductTodoSave requestBody = new ProductTodoSave();

        List<ProductTodoSave.DetailsBean> detail = new ArrayList<>();

        SparseArray<Integer> houseId = DataUtils.getHouseId();

        List<UserInfo.WarehouseListBean> warehouseList = userInfo.getWarehouseList();


        int size = oldList.size();
        for (int i = 0; i < size; i++) {
            List<String> boxQrCode = carCodeArray.get(i);
            String bulkNum = bulkNumArray.get(i);
            String batchNo = batchNoArray.get(i);
            String Id = positionId.get(i);

            // 仓库信息
            String warehouseId;
            String warehouseNo;
            String warehouseName;

            // houseId == null ： 是判断用户全部没有选择仓库信息,默认都是第一个，
            // houseId.get(i) == null : 是判断用户部分没选择仓库信息默认第一个
            if (houseId == null || houseId.get(i) == null) {
                warehouseId = warehouseList.get(0).getWarehouseId();
                warehouseNo = warehouseList.get(0).getWarehouseNo();
                warehouseName = warehouseList.get(0).getWarehouseName();
            } else {
                Integer which = houseId.get(i);
                warehouseId = warehouseList.get(which).getWarehouseId();
                warehouseNo = warehouseList.get(which).getWarehouseNo();
                warehouseName = warehouseList.get(which).getWarehouseName();
            }


            ProductTodoSave.DetailsBean bean = new ProductTodoSave.DetailsBean();
            if (!TextUtils.isEmpty(bulkNum) && boxQrCode.size() != 0) {
                String goodsId = oldList.get(i).getGoodsId();
                String goodsNo = oldList.get(i).getGoodsNo();
                String orderNumber = oldList.get(i).getOrderNumber();

                String startCode = startCodeArray.get(i);
                String endCode = endCodeArray.get(i);

                bean.setPositionId(Id);
                bean.setNumber(bulkNum);
                bean.setSapMaterialBatchNo(batchNo);

                bean.setGoodsId(goodsId);
                bean.setGoodsNo(goodsNo);

                bean.setStartQrCode(startCode);
                bean.setEndQrCode(endCode);
                bean.setOrderNumber(orderNumber);

                bean.setWarehouseId(warehouseId);
                bean.setWarehouseNo(warehouseNo);
                bean.setWarehouseName(warehouseName);

                bean.setBoxQrCode(boxQrCode);

                detail.add(bean);
            } else {
                // 跳出当前一列、不处理
                continue;
            }
        }
        // 没有合法的操作数据,不请求网络
        if (detail.size() == 0) {
            ZBUiUtils.showToast("请完善您要入库的信息");
            return null;
        }

        // warehouseId,inTime,sapOrderNo,productionBatchNo,remark

        String remark = etDesc.getText().toString().trim();
        String time = tvTime.getText().toString().trim();

        requestBody.setDetails(detail);
        requestBody.setInTime(time);
        requestBody.setWarehouseId(warehouseId);
        requestBody.setSapOrderNo(sapOrderNo);
        requestBody.setRemark(remark);


        String json = DataUtils.toJson(requestBody, 1);
        ZBLog.e("JSON " + json);
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }


    @Override
    public void showProduct(final List<ProductDetailsIn.ListBean> list) {

        // BuyInTodoDetails  含仓库信息 bean
        // ProductDetailsIn  不含仓库信息 bean
        oldList = list;

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new BuyInTodoDetailAdapter(this, R.layout.item_product_detail_two_todo, list);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new BuyInTodoDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final View view, RecyclerView.ViewHolder holder, final int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.gone_view);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);
                    Button bindingCode = holder.itemView.findViewById(R.id.btn_binding_code);
                    TextView tvName = holder.itemView.findViewById(R.id.tv_name);
                    final TextView selectHouse = holder.itemView.findViewById(R.id.tv_select_ware);

                    //List<UserInfo.WarehouseListBean> houses = userInfo.getWarehouseList();
                    List<ProductDetailsIn.ListBean.WarehouseListBean> houses = list.get(position).getWarehouseList();
                    final ArrayList<String> houseName = new ArrayList<>();

                    int size = houses.size();
                    for (int i = 0; i < size; i++) {
                        houseName.add(houses.get(i).getWarehouseName());
                    }

                    selectHouse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ZBUiUtils.selectDialog(view.getContext(), position, houseName, selectHouse);

                        }
                    });

                    //SparseArray<Integer> houseId = DataUtils.getHouseId();
                    // 获取当前 item 中，用户选择的是哪个 仓库位置 ID（先不显示）
//                    Integer which = houseId.get(position);
//                    String warehouseName = warehouseList.get(which).getWarehouseName();
                    if (rlDetail.getVisibility() == View.VISIBLE) {
                        rlDetail.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.ic_collapse);
//                        tvName.setText(warehouseName);
                    } else {
                        rlDetail.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.ic_expand);
                    }

                    bindingCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemId = position;
                            Intent intent = new Intent(BuyInTodoDetailActivity.this, ScanBoxCodeActivity.class);
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

    /**
     * 扫码 成功的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SCAN_CODE && resultCode == RESULT_SCAN_CODE) {
            itemId = data.getIntExtra("itemId", -1);
            warehouseId = data.getStringExtra("warehouseId");
            boxCodeList = data.getStringArrayListExtra("boxCodeList");
            carCodeArray.put(itemId, boxCodeList);
        }
    }

    /**
     * 保存用户在列表中输入的信息
     *
     * @param etType   输入框标识
     * @param hasFocus 有无焦点
     * @param position 位置
     * @param editText 控件
     */
    @Override
    public void saveEditAndGetHasFocusPosition(String etType, Boolean hasFocus, int position, EditText editText) {

        String textContent = editText.getText().toString().trim();

        if (CodeConstant.ET_BULK_NUM.equals(etType)) {
            bulkNumArray.put(position, textContent);
        } else if (CodeConstant.ET_BATCH_NO.equals(etType)) {
            batchNoArray.put(position, textContent);
        } else if (CodeConstant.ET_START_CODE.equals(etType)) {
            startCodeArray.put(position, textContent);
        } else if (CodeConstant.ET_END_CODE.equals(etType)) {
            endCodeArray.put(position, textContent);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // TODO  清除仓库数据
        DataUtils.clearId();
    }
}
