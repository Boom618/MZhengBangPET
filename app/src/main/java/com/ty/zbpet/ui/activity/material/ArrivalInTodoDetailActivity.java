package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
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
import com.ty.zbpet.ui.MainApp;
import com.ty.zbpet.ui.adapter.material.MaterialTodoDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ACache;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBLog;
import com.ty.zbpet.util.ZBUiUtils;
import com.ty.zbpet.util.scan.ScanBoxInterface;
import com.ty.zbpet.util.scan.ScanObservable;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * 原辅料——到货入库详情、待办
 * <p>
 * MaterialUiObjInterface<MaterialTodoDetailsData> ：数据接口
 * MaterialTodoDetailAdapter.SaveEditListener      ：输入框接口
 * ScanBoxInterface                                ：扫码接口
 *
 * @author TY
 */
public class ArrivalInTodoDetailActivity extends BaseActivity implements MaterialUiObjInterface<MaterialDetailsIn>
        , MaterialTodoDetailAdapter.SaveEditListener, ScanBoxInterface {

    @BindView(R.id.rv_in_storage_detail)
    RecyclerView recyclerView;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_desc)
    EditText etDesc;

    private MaterialTodoDetailAdapter adapter;
    private List<MaterialDetailsIn.ListBean> list = new ArrayList<>();

    private String sapOrderNo;
    private String supplierId; // 供应商 ID
    private String warehouseId;

    /**
     * 时间选择
     */
    private String selectTime;

    /**
     * 点击库位码输入框
     */
    private Boolean currentFocus = false;

    /**
     * list 中 Position
     */
    private int currentPosition = -1;

    private Disposable disposable;
    private Scanner scanner = ScanReader.getScannerInstance();
    private ScanObservable scan = new ScanObservable(this);

    /**
     * 保存用户在输入框中的数据
     */
    private SparseArray<String> bulkNumArray = new SparseArray(10);
    private SparseArray<String> carCodeArray = new SparseArray(10);
    private SparseArray<String> batchNoArray = new SparseArray(10);

    /**
     * 库位码 ID
     */
    private SparseArray<String> positionId = new SparseArray(10);

    private MaterialPresenter materialPresenter = new MaterialPresenter(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        SimpleDateFormat sdf = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = sdf.format(new Date());
        tvTime.setText(selectTime);

    }

    @Override
    protected int getActivityLayout() {
//        return R.layout.activity_arrival_in_storage_detail;
        return R.layout.activity_content_row_two;
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
            public void onClick(View v) {
                // 冲销入库
                doPurchaseInRecallOut(initParam());

            }
        });

        findViewById(R.id.add_ship).setVisibility(View.GONE);
        TextView titleName = findViewById(R.id.in_storage_detail);
        titleName.setText("到货明细");

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ZBUiUtils.showPickDate(ArrivalInTodoDetailActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        //选中事件回调
                        selectTime = ZBUiUtils.getTime(date);
                        tvTime.setText(selectTime);
                        ZBUiUtils.showToast(selectTime);
                    }
                });
            }
        });
    }


    private RequestBody initParam() {

        MaterialTodoSave requestBody = new MaterialTodoSave();
        List<MaterialTodoSave.DetailsBean> detail = new ArrayList<>();

        int size = list.size();
        for (int i = 0; i < size; i++) {
            String bulkNum = bulkNumArray.get(i);
            String carCode = carCodeArray.get(i);
            String batchNo = batchNoArray.get(i);

            String Id = positionId.get(i);

            MaterialTodoSave.DetailsBean bean = new MaterialTodoSave.DetailsBean();
            if (null != bulkNum && null != carCode) {
                if (!bulkNum.isEmpty()) {
                    bean.setNumber(bulkNum);
                } else if (!carCode.isEmpty()) {
                    bean.setPositionId(carCode);
                } else if (!batchNo.isEmpty()) {
                    bean.setSapMaterialBatchNo(batchNo);
                }
                bean.setPositionId(Id);
                bean.setSupplierId(supplierId);
                bean.setSupplierNo(list.get(i).getSupplierNo());
                bean.setMaterialId(list.get(i).getMaterialId());
                bean.setConcentration(list.get(i).getConcentration());

                detail.add(bean);
            } else {
                // 不处理
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
        requestBody.setInTime(time);
        requestBody.setSapOrderNo(sapOrderNo);
        requestBody.setRemark(remark);


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

        HttpMethods.getInstance().materialTodoInSave(new BaseSubscriber<ResponseInfo>() {
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

    @Override
    public void detailObjData(MaterialDetailsIn obj) {

        list.clear();
        list.addAll(obj.getList());


        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            recyclerView.setLayoutManager(manager);
            adapter = new MaterialTodoDetailAdapter(this, R.layout.item_matterial_todo_detail, obj.getList());
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new MaterialTodoDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.rl_detail);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);

                    if (rlDetail.getVisibility() == View.VISIBLE) {
                        rlDetail.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.ic_collapse);
                    } else {
                        rlDetail.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.ic_expand);

                    }

                    ZBUiUtils.hideInputWindow(ArrivalInTodoDetailActivity.this, view);

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
    public void saveEditAndGetHasFocusPosition(String etType, Boolean hasFocus, int position, EditText editText) {
        // 用户在 EditText 中输入的数据
        currentPosition = position;
        String textContent = editText.getText().toString().trim();

        if (CodeConstant.ET_ZKG.equals(etType)) {
            // 和 库位码 一样的存值方法
            MainApp.mCache.put(CodeConstant.ET_ZKG, position + "@" + textContent);

        } else if (CodeConstant.ET_BULK_NUM.equals(etType)) {
            bulkNumArray.put(position, textContent);

            // 库位码 需要判断合法性
        } else if (CodeConstant.ET_CODE.equals(etType)) {
            currentFocus = hasFocus;

            // 【情况 ② 】 无焦点 有内容 http 校验
            if (!TextUtils.isEmpty(textContent)) {
                if (!hasFocus) {
                    //httpCheckCarCode(currentPosition, textContent);
                }
            }

            carCodeArray.put(position, textContent);

        } else if (CodeConstant.ET_BATCH_NO.equals(etType)) {
            batchNoArray.put(position, textContent);

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

            if (currentFocus && currentPosition != -1) {
                // 扫描
                doDeCode();
            }


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void doDeCode() {

        scanner.open(getApplicationContext());

        disposable = scan.scanBox(scanner, currentPosition);

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
        ZBUiUtils.showToast("库位码 ：" + positionNo);
        //adapter.notifyItemChanged(position);

        //  服务器校验 库位码
        httpCheckCarCode(position, positionNo);

    }

    @Override
    public void showCarSuccess(int position, CarPositionNoData carData) {
        if (carData.getCount() > 0) {
            ZBUiUtils.showToast("扫码成功 === showCarSuccess ");
            String carId = carData.getList().get(0).getId();
            warehouseId = carData.getList().get(0).getWarehouseId();
            positionId.put(position, carId);

            adapter.notifyItemChanged(position);
        } else {
            ZBUiUtils.showToast("请扫正确的库位码");
        }
    }

    /**
     * Http 校验 库位码合法
     *
     * @param position   item 更新需要的 position
     * @param positionNo 扫码的编号
     */
    private void httpCheckCarCode(final int position, final String positionNo) {

        materialPresenter.checkCarCode(position, positionNo);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        scanner.close();

    }
}
