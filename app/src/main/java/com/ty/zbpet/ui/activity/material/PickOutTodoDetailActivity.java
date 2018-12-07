package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
import com.ty.zbpet.presenter.material.MaterialUiObjInterface;
import com.ty.zbpet.presenter.material.PickOutPresenter;
import com.ty.zbpet.ui.adapter.material.PickingTodoDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBLog;
import com.ty.zbpet.util.ZBUiUtils;
import com.ty.zbpet.util.scan.ScanBoxInterface;
import com.ty.zbpet.util.scan.ScanObservable;

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
 * 领料出库 待办详情
 */
public class PickOutTodoDetailActivity extends BaseActivity implements MaterialUiObjInterface<MaterialDetailsIn>
        , ScanBoxInterface, PickingTodoDetailAdapter.SaveEditListener {


    private RecyclerView reView;
    private TextView tvTime;
    private EditText etDesc;
    private Button addButton;

    // Kotlin 版
    //private PickOutTodoDetailAdapter adapter;
    private PickingTodoDetailAdapter adapter;

    private String selectTime;
    private String sapOrderNo;
    private String supplierId; // 供应商 ID

    private String warehouseId;
    private List<MaterialDetailsIn.ListBean> list = new ArrayList<>();

    /**
     * 点击库位码输入框
     */
    private Boolean currentFocus = false;

    /**
     * list 中 Position
     */
    private int currentPosition = -1;

    private Scanner scanner = ScanReader.getScannerInstance();
    private ScanObservable scanObservable = new ScanObservable(this);
    private PickOutPresenter presenter = new PickOutPresenter(this);

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

        presenter.fetchPickOutTodoListDetails(sapOrderNo);
    }

    @Override
    protected void initTwoView() {


        initToolBar(R.string.pick_out_storage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickOutTodoSave(initTodoBody());
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
    private void pickOutTodoSave(RequestBody body) {

        if (body == null) {
            return;
        }

        HttpMethods.getInstance().pickOutTodoSave(new SingleObserver<ResponseInfo>() {

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

    private RequestBody initTodoBody() {

        //  TODO 暂时先用原辅料 的保存 Bean
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
                continue;
            }
        }
        // 没有合法的操作数据,不请求网络
        if (detail.size() == 0) {
            ZBUiUtils.showToast("请完善您要出库的信息");
            return null;
        }

        String remark = etDesc.getText().toString().trim();
        String time = tvTime.getText().toString().trim();

        requestBody.setDetails(detail);
        requestBody.setWarehouseId(warehouseId);
        requestBody.setOutTime(time);
        requestBody.setSapOrderNo(sapOrderNo);
        requestBody.setRemark(remark);


        String json = DataUtils.toJson(requestBody, 1);
        ZBLog.e("JSON " + json);
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
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

        scanObservable.scanBox(scanner, currentPosition);

    }

    @Override
    public void ScanSuccess(int position, String msg) {
        ZBUiUtils.showToast("库位码 ：" + msg);
        //adapter.notifyItemChanged(position);

        //  服务器校验 库位码
        httpCheckCarCode(position, msg);
    }

    /**
     * Http 校验 库位码合法
     *
     * @param position   item 更新需要的 position
     * @param positionNo 扫码的编号
     */
    private void httpCheckCarCode(final int position, final String positionNo) {

        presenter.checkCarCode(position, positionNo);

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


    @Override
    public void detailObjData(MaterialDetailsIn obj) {

        warehouseId = obj.getSapOrderNo();
        list.clear();

        list = obj.getList();
        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new PickingTodoDetailAdapter(this, R.layout.item_material_detail_three_todo, list);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new PickingTodoDetailAdapter.OnItemClickListener() {
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


    /**
     * @param etType   输入框标识
     * @param hasFocus 有无焦点
     * @param position 位置
     * @param editText 控件
     */
    @Override
    public void saveEditAndGetHasFocusPosition(String etType, Boolean hasFocus, int position, EditText editText) {
        // 用户在 EditText 中输入的数据
        currentPosition = position;

        String textContent = editText.getText().toString().trim();

        if (CodeConstant.ET_BULK_NUM.equals(etType)) {
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
}
