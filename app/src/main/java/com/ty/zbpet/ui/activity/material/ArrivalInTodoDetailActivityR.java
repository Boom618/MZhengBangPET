package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.pda.scanner.ScanReader;
import com.pda.scanner.Scanner;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.material.MaterialDetailsIn;
import com.ty.zbpet.bean.material.MaterialTodoSave;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.material.MaterialPresenter;
import com.ty.zbpet.presenter.material.MaterialUiObjInterface;
import com.ty.zbpet.ui.adapter.material.MaterialTodoDetailAdapterR;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBLog;
import com.ty.zbpet.util.ZBUiUtils;
import com.ty.zbpet.util.scan.ScanBoxInterface;
import com.ty.zbpet.util.scan.ScanObservable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * 原辅料——到货入库详情、待办
 * <p>
 * MaterialUiObjInterface<MaterialTodoDetailsData> ：数据接口
 * MaterialTodoDetailAdapter.SaveEditListener      ：输入框接口
 * ScanBoxInterface                                ：扫码接口
 * <p>
 * 【采用 RecycleView 嵌套 RecycleView 展示详情】
 *
 * @author TY
 */
public class ArrivalInTodoDetailActivityR extends BaseActivity implements MaterialUiObjInterface<MaterialDetailsIn>
        , ScanBoxInterface {

    @BindView(R.id.recycler_main)
    RecyclerView recyclerView;

    private MaterialTodoDetailAdapterR adapter;
    private List<MaterialDetailsIn.ListBean> list = new ArrayList<>();

    private String sapOrderNo;
    private String supplierId; // 供应商 ID
    private String warehouseId;

    /**
     * 点击库位码输入框
     */
    private Boolean currentFocus = false;

    /**
     * list 中 Position
     */
    private int currentPosition = -1;

    private Scanner scanner = ScanReader.getScannerInstance();
    private ScanObservable scan = new ScanObservable(this);

    /**
     * 保存用户在输入框中的数据
     */
    private SparseArray<String> bulkNumArray = DataUtils.getNumber();
    private SparseArray<String> zkgArray = DataUtils.getZkg();
    private SparseArray<String> carCodeArray = DataUtils.getCode();
    private SparseArray<String> sapArray = DataUtils.getSap();

    /**
     * 库位码 ID
     */
    private SparseArray<String> positionId = new SparseArray(10);

    private MaterialPresenter materialPresenter = new MaterialPresenter(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {}

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main_detail_two;
    }

    @Override
    protected void initOneData() {

        sapOrderNo = getIntent().getStringExtra("sapOrderNo");
        supplierId = getIntent().getStringExtra("supplierId");

        materialPresenter.fetchTODOMaterialDetails(sapOrderNo);
    }

    @Override
    protected void initTwoView() {

        initToolBar(R.string.label_purchase_detail, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZBUiUtils.hideInputWindow(ArrivalInTodoDetailActivityR.this, view);
                // 冲销入库
                doPurchaseInRecallOut(initParam());

            }
        });

        TextView titleName = findViewById(R.id.in_storage_detail);
        titleName.setText("到货明细");
    }


    private RequestBody initParam() {

        MaterialTodoSave requestBody = new MaterialTodoSave();
        List<MaterialTodoSave.DetailsBean> detail = new ArrayList<>();

        int size = list.size();
        for (int i = 0; i < size; i++) {
            String bulkNum = bulkNumArray.get(i);
            String carCode = carCodeArray.get(i);
            String sap = sapArray.get(i);
            String zkg = zkgArray.get(i);

            String Id = positionId.get(i);

            MaterialTodoSave.DetailsBean bean = new MaterialTodoSave.DetailsBean();
            if (null != bulkNum && null != carCode) {

                bean.setNumber(bulkNum);
                bean.setPositionId(carCode);
                bean.setSapMaterialBatchNo(sap);
                bean.setPositionId(Id);
                bean.setZKG(zkg);
                bean.setSupplierId(supplierId);
                bean.setSupplierNo(list.get(i).getSupplierNo());
                bean.setMaterialId(list.get(i).getMaterialId());
                bean.setConcentration(list.get(i).getConcentration());

                detail.add(bean);
            }
        }

        // 没有合法的操作数据,不请求网络
        if (detail.size() == 0) {
            ZBUiUtils.showToast("请完善您要入库的信息");
            return null;
        }
//        String remark = etDesc.getText().toString().trim();
//        String time = tvTime.getText().toString().trim();

        requestBody.setDetails(detail);
        requestBody.setWarehouseId(warehouseId);
//        requestBody.setInTime(time);
        requestBody.setSapOrderNo(sapOrderNo);
//        requestBody.setRemark(remark);

        String json = DataUtils.toJson(requestBody, 1);
        ZBLog.e("JSON " + json);
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }

    /**
     * 入库保存
     *
     * @param body
     */
    private void doPurchaseInRecallOut(RequestBody body) {

        if (body == null) {
            return;
        }

        HttpMethods.getInstance().materialTodoInSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 入库成功（保存）
                    ZBUiUtils.showToast(responseInfo.getMessage());
                    finish();
                } else {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                }
            }
        }, body);
    }

    @Override
    public void detailObjData(MaterialDetailsIn obj) {

        list.clear();
        list.addAll(obj.getList());
        MaterialDetailsIn.ListBean listBean = new MaterialDetailsIn.ListBean();
        listBean.setTag("Bottom");
        list.add(listBean);

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            adapter = new MaterialTodoDetailAdapterR(this, list);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 扫描
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == CodeConstant.KEY_CODE_131
                || keyCode == CodeConstant.KEY_CODE_135
                || keyCode == CodeConstant.KEY_CODE_139) {
            String codeFocus = DataUtils.getCodeFocus();

            if (!TextUtils.isEmpty(codeFocus)) {
                String[] split = codeFocus.split("@");

                currentPosition = Integer.valueOf(split[0]);
                currentFocus = Boolean.valueOf(split[1]);

                if (currentFocus && currentPosition != -1) {
                    // 扫描
                    scanner.open(getApplicationContext());

                    scan.scanBox(scanner, currentPosition);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * scan.scanBox 成功回调
     * 【情况 ① 】
     * 有焦点 扫码  http 校验
     *
     * @param positionNo
     */
    @Override
    public void ScanSuccess(int position, String positionNo) {

        // http 校验
        materialPresenter.checkCarCode(position, positionNo);

    }

    @Override
    public void showCarSuccess(int position, CarPositionNoData carData) {
        if (carData.getCount() > 0) {
            String carId = carData.getList().get(0).getId();
            warehouseId = carData.getList().get(0).getWarehouseId();
            positionId.put(position, carId);

            adapter.notifyItemChanged(position);
        } else {
            ZBUiUtils.showToast("请扫正确的库位码");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanner.close();

        // 清除所有 SA 保存的数据
        DataUtils.clearId();
    }
}