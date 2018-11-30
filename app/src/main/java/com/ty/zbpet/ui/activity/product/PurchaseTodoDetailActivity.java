//package com.ty.zbpet.ui.activity.product;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.ty.zbpet.R;
//import com.ty.zbpet.bean.GoodsPurchaseOrderInfo;
//import com.ty.zbpet.bean.PostPurchaseInRecallOutInfo;
//import com.ty.zbpet.bean.ResponseInfo;
//import com.ty.zbpet.bean.WarehouseInfo;
//import com.ty.zbpet.constant.ApiNameConstant;
//import com.ty.zbpet.net.HttpMethods;
//import com.ty.zbpet.ui.activity.ScanBoxCodeActivity;
//import com.ty.zbpet.ui.adapter.PurchaseInStorageDetailAdapter;
//import com.ty.zbpet.ui.base.BaseActivity;
//import com.ty.zbpet.ui.widght.SpaceItemDecoration;
//import com.ty.zbpet.util.ResourceUtil;
//import com.ty.zbpet.util.ZBUiUtils;
//import com.ty.zbpet.util.DataUtils;
//import com.zhouyou.http.exception.ApiException;
//import com.zhouyou.http.subsciber.BaseSubscriber;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//import okhttp3.RequestBody;
//
///**
// * @author TY
// * 外采详情 待办
// */
//public class PurchaseTodoDetailActivity extends BaseActivity {
//
//    @BindView(R.id.et_batch_no)
//    EditText etBatchNo;
//    @BindView(R.id.tv_warehouse)
//    TextView tvWarehouse;
//    @BindView(R.id.rv_in_storage_detail)
//    RecyclerView recyclerView;
//    @BindView(R.id.tv_time)
//    TextView tvTime;
//    @BindView(R.id.et_desc)
//    EditText etDesc;
//    private String sapOrderNo;
//    private PurchaseInStorageDetailAdapter adapter;
//    private List<GoodsPurchaseOrderInfo.DataBean.ListBean> list = new ArrayList<>();
//    private List<GoodsPurchaseOrderInfo.DataBean.ListBean> listCopy = new ArrayList<>();
//    private List<String> warehouseNameList = new ArrayList<>();
//    private List<String> warehouseNoList = new ArrayList<>();
//    private final static int REQUEST_SCAN_CODE = 1;
//    private final static int RESULT_SCAN_CODE = 2;
//
//    @Override
//    protected void onBaseCreate(Bundle savedInstanceState) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//        String now = sdf.format(new Date());
//        tvTime.setText(now + ":00");
//        sapOrderNo = getIntent().getStringExtra("sapOrderNo");
//        getWarehouseInfo();
////        getBuyInOrderInfo();
//
//        initToolBar(R.string.label_purchase_in_storage);
//    }
//
//    @Override
//    protected int getActivityLayout() {
//        return R.layout.activity_purchase_in_storage_detail;
//    }
//
//    @Override
//    protected void initOneData() {
//
//    }
//
//    @Override
//    protected void initTwoView() {
//
//    }
//
////    private void getBuyInOrderInfo() {
////        HttpMethods.getInstance().getBuyInOrderInfo(new BaseSubscriber<GoodsPurchaseOrderInfo>() {
////            @Override
////            public void onError(ApiException e) {
////                ZBUiUtils.showToast(e.getMessage());
////            }
////
////            @Override
////            public void onNext(GoodsPurchaseOrderInfo info) {
////                if (info.getData() != null) {
////                    List<GoodsPurchaseOrderInfo.DataBean.ListBean> infos = info.getData().getList();
////                    if (infos != null && infos.size() != 0) {
////                        list.clear();
////                        list.addAll(infos);
////                        refreshUI();
////                    } else {
////                        ZBUiUtils.showToast("没有信息");
////                    }
////                } else {
////                    ZBUiUtils.showToast(info.getMessage());
////                }
////            }
////        }, sapOrderNo);
////    }
//
//    private void getWarehouseInfo() {
//        new HttpMethods(ApiNameConstant.BASE_URL1).getWarehouseList(new BaseSubscriber<WarehouseInfo>() {
//            @Override
//            public void onError(ApiException e) {
//                ZBUiUtils.showToast(e.getMessage());
//            }
//
//            @Override
//            public void onNext(WarehouseInfo warehouseInfo) {
//                super.onNext(warehouseInfo);
//                if (warehouseInfo.getList() != null) {
//                    for (WarehouseInfo.ListBean bean : warehouseInfo.getList()) {
//                        warehouseNameList.add(bean.getWarehouseName());
//                        warehouseNoList.add(bean.getId() + "");
//                    }
//                }
//            }
//        });
//    }
//
//    private void refreshUI() {
//        if (adapter == null) {
//            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
//            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
//            recyclerView.setLayoutManager(manager);
//            adapter = new PurchaseInStorageDetailAdapter(ResourceUtil.getContext(), list);
//            adapter.setOnItemClickLisener(new PurchaseInStorageDetailAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(int position, GoodsPurchaseOrderInfo.DataBean.ListBean data) {
//                    Intent intent = new Intent(PurchaseTodoDetailActivity.this, ScanBoxCodeActivity.class);
//                    intent.putExtra("position", position);
//                    intent.putStringArrayListExtra("boxCodeList", (ArrayList<String>) data.getBoxCodeList());
//                    startActivityForResult(intent, REQUEST_SCAN_CODE);
//                }
//            });
//            recyclerView.setAdapter(adapter);
//        } else {
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//    private RequestBody initParam() {
//        PostPurchaseInRecallOutInfo info = new PostPurchaseInRecallOutInfo();
//        String warehouseName = tvWarehouse.getText().toString();
//        if (TextUtils.isEmpty(warehouseName)) {
//            ZBUiUtils.showToast("请选择仓库");
//            return null;
//        }
//        info.setWarehouseId(warehouseNoList.get(warehouseNameList.indexOf(warehouseName)));
//        info.setSapMaterialBatchNo(etBatchNo.getText().toString().trim());
//        info.setInStoreDate(tvTime.getText().toString().trim());
//        info.setRemark(etDesc.getText().toString().trim());
//        info.setSapOrderNo(sapOrderNo);
//        //初始化箱码参数
//        List<PostPurchaseInRecallOutInfo.BoxCodeInfo> boxCodeInfoList = new ArrayList<>();
//        for (GoodsPurchaseOrderInfo.DataBean.ListBean bean : list) {
//            PostPurchaseInRecallOutInfo.BoxCodeInfo boxCodeInfo = new PostPurchaseInRecallOutInfo.BoxCodeInfo();
//            List<String> stringList = bean.getBoxCodeList();
//            boxCodeInfo.setNumber(bean.getBoxNum());
//            boxCodeInfo.setGoodsId(bean.getGoodsId());
//            if (stringList != null && stringList.size() > 0) {
//                boxCodeInfo.setBoxQrCode(bean.getBoxCodeList());
//                boxCodeInfoList.add(boxCodeInfo);
//            }
//
//        }
//        info.setDetails(boxCodeInfoList);
//        String json = DataUtils.toJson(info, 1);
//        Log.e("TAG", json);
//        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
//    }
//
//    private void doGoodsPurchaseInStorage(RequestBody body) {
//        HttpMethods.getInstance().doGoodsPurchaseInStorage(new BaseSubscriber<ResponseInfo>() {
//            @Override
//            public void onError(ApiException e) {
//                ZBUiUtils.showToast(e.getMessage());
//            }
//
//            @Override
//            public void onNext(ResponseInfo responseInfo) {
//                ZBUiUtils.showToast(responseInfo.getMessage());
//            }
//        }, body);
//    }
//
//    @OnClick({R.id.iv_back, R.id.tv_right, R.id.tv_warehouse, R.id.tv_time})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
//
//            case R.id.tv_right:
//                doGoodsPurchaseInStorage(initParam());
//                break;
//
//            case R.id.tv_warehouse:
//                selectWarehouse(warehouseNameList);
//                break;
//
//            case R.id.tv_time:
//                ZBUiUtils.showTimeDialog(tvTime, this);
//                break;
//            default:
//                break;
//        }
//    }
//
//    /**
//     *
//     * @param datas
//     */
//    public void selectWarehouse(final List<String> datas) {
//        ZBUiUtils.showToast("选择仓库");
//        ZBUiUtils.selectDialog(this,datas,tvWarehouse);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SCAN_CODE && resultCode == RESULT_SCAN_CODE) {
//            int position = data.getIntExtra("position", -1);
//            List<String> codeList = data.getStringArrayListExtra("boxCodeList");
//            if (codeList != null) {
//                GoodsPurchaseOrderInfo.DataBean.ListBean listBean = list.get(position);
//                listBean.setBoxCodeList(codeList);
//                listBean.setBoxNum(codeList.size() + "");
//                listCopy.addAll(list);
//                list.clear();
//                list.addAll(listCopy);
//                adapter.notifyDataSetChanged();
//            }
//        }
//    }
//}
