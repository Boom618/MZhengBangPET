package com.ty.zbpet.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
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
import com.ty.zbpet.bean.product.ProductTodoDetails;
import com.ty.zbpet.bean.product.ProductTodoSave;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.product.ProductUiListInterface;
import com.ty.zbpet.presenter.product.SendOutPresenter;
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity;
import com.ty.zbpet.ui.adapter.diffadapter.SendOutDiffUtil;
import com.ty.zbpet.ui.adapter.product.SendOutTodoDetailAdapter;
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
 * 发货出库 待办详情
 *
 * @author TY
 */
public class SendOutTodoDetailActivity extends BaseActivity implements ProductUiListInterface<ProductTodoDetails.ListBean> {


    private RecyclerView reView;
    private TextView tvTime;
    private TextView backGoods;
    private TextView tvPath;
    private TextView tvType;
    private EditText etDesc;
    private Button addShip;

    private SendOutTodoDetailAdapter adapter;

    private String selectTime;
    private String sapOrderNo;

    private List<ProductTodoDetails.ListBean> oldList = new ArrayList<>();

    private final static int REQUEST_SCAN_CODE = 1;
    private final static int RESULT_SCAN_CODE = 2;

    private SendOutPresenter presenter = new SendOutPresenter(this);

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

        presenter.fetchSendOutTodoInfo(sapOrderNo);
    }

    @Override
    protected void initTwoView() {

        // in_storage_detail 到货明细

        initToolBar(R.string.label_send_out_storage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendOutTodoSave(initTodoBody());
            }
        });

        reView = findViewById(R.id.rv_in_storage_detail);
        tvTime = findViewById(R.id.tv_time);
        backGoods = findViewById(R.id.in_storage_detail);
        addShip = findViewById(R.id.add_ship);

        tvPath = findViewById(R.id.tv_path);
        tvType = findViewById(R.id.tv_type);
        etDesc = findViewById(R.id.et_desc);

        SimpleDateFormat format = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = format.format(new Date());

        tvTime.setText(selectTime);
        backGoods.setText("发货明细");

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

        addShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProductTodoDetails.ListBean> tempList = new ArrayList<>(oldList);
                ProductTodoDetails.ListBean bean = new ProductTodoDetails.ListBean();

                bean.setSapOrderNo("SAP0001");
                bean.setGoodsName("新 40+30）g*100");
                bean.setGoodsId("20");
                bean.setGoodsNo("90000933");
                bean.setUnitS("件");
                bean.setOrderNumber("100");
                //bean.setWarehouseList(oldList.get(0).getWarehouseList());


                tempList.add(bean);

                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SendOutDiffUtil(oldList, tempList),false);
                diffResult.dispatchUpdatesTo(adapter);

                // 清除原数据,更新原数据,清除临时保存数据
                oldList.clear();
                oldList.addAll(tempList);
                tempList.clear();

                ZBUiUtils.showToast("添加发货出库");
            }
        });


    }

    /**
     * 出库 保存
     */
    private void SendOutTodoSave(RequestBody body) {

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

        ProductTodoSave requestBody = new ProductTodoSave();

        List<ProductTodoSave.DetailsBean> detail = new ArrayList<>();


        int size = oldList.size();
        for (int i = 0; i < size; i++) {
            List<String> boxQrCode = carCodeArray.get(i);
            String bulkNum = bulkNumArray.get(i);
            String batchNo = batchNoArray.get(i);
            String Id = positionId.get(i);

            ProductTodoSave.DetailsBean bean = new ProductTodoSave.DetailsBean();
            if (!TextUtils.isEmpty(bulkNum) && boxQrCode.size() != 0) {

                bean.setPositionId(Id);
                bean.setNumber(bulkNum);
                bean.setSapMaterialBatchNo(batchNo);

                bean.setBoxQrCode(boxQrCode);

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

        String remark = etDesc.getText().toString().trim();
        String time = tvTime.getText().toString().trim();

        requestBody.setDetails(detail);
        requestBody.setWarehouseId("仓库ID");
        requestBody.setSapOrderNo("管联单据");
        requestBody.setProductionBatchNo("生产批次号");
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
            adapter = new SendOutTodoDetailAdapter(this, R.layout.item_product_detail_two_todo, list);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new SendOutTodoDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final View view, RecyclerView.ViewHolder holder, final int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.gone_view);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);
                    Button bindingCode = holder.itemView.findViewById(R.id.btn_binding_code);
                    bindingCode.setText("箱码出库");
                    final TextView selectGoods = holder.itemView.findViewById(R.id.tv_select_ware);

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
                            Intent intent = new Intent(SendOutTodoDetailActivity.this, ScanBoxCodeActivity.class);
                            intent.putExtra("itemId", itemId);
                            intent.putExtra(CodeConstant.PAGE_STATE, true);
                            intent.putStringArrayListExtra("boxCodeList", carCodeArray.get(itemId));
                            startActivityForResult(intent, REQUEST_SCAN_CODE);
                        }
                    });

                    //List<BuyInTodoDetails.ListBean.WarehouseListBean> warehouseList = list.get(position).getWarehouseList();
                    UserInfo userInfo = DataUtils.getUserInfo();

                    List<UserInfo.WarehouseListBean> warehouseList = userInfo.getWarehouseList();
                    final ArrayList<String> houseName = new ArrayList<>();

                    int size = warehouseList.size();
                    for (int i = 0; i < size; i++) {
                        houseName.add(warehouseList.get(i).getWarehouseName());
                    }

                    selectGoods.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ZBUiUtils.selectDialog(view.getContext(), houseName, selectGoods);
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