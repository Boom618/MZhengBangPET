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
import com.ty.zbpet.presenter.material.BackGoodsPresenter;
import com.ty.zbpet.presenter.material.MaterialUiObjInterface;
import com.ty.zbpet.ui.adapter.material.BackGoodsTodoDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.constant.CodeConstant;
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
 * 采购退货 待办详情
 */
public class BackGoodsTodoDetailActivity extends BaseActivity implements MaterialUiObjInterface<MaterialDetailsIn>
        , ScanBoxInterface, BackGoodsTodoDetailAdapter.SaveEditListener {


    private RecyclerView reView;
    private TextView tvTime;
    private TextView titleName;
    private TextView tvPath;
    private TextView tvType;
    private EditText etDesc;

    private BackGoodsTodoDetailAdapter adapter;

    private String selectTime;
    private String sapOrderNo;

    private ArrayList<MaterialDetailsIn.ListBean> list = new ArrayList<>();

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
    private BackGoodsPresenter presenter = new BackGoodsPresenter(this);

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

        presenter.fetchBackTodoListInfo(sapOrderNo);
    }

    @Override
    protected void initTwoView() {


        initToolBar(R.string.back_goods, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZBUiUtils.hideInputWindow(BackGoodsTodoDetailActivity.this,view);
                pickOutTodoSave(initTodoBody());
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
    private void pickOutTodoSave(RequestBody body) {

        if (body == null) {
            return;
        }

        HttpMethods.getInstance().getBackTodoSave(new SingleObserver<ResponseInfo>() {
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
     * 构建保存 的 Body
     *
     * @return
     */
    private RequestBody initTodoBody() {

        MaterialTodoSave requestBody = new MaterialTodoSave();
        List<MaterialTodoSave.DetailsBean> detail = new ArrayList<>();

        int size = list.size();
        for (int i = 0; i < size; i++) {
            String bulkNum = bulkNumArray.get(i);
            String carCode = carCodeArray.get(i);
            String batchNo = batchNoArray.get(i);
            String id = positionId.get(i);

            String supplierId = list.get(i).getSupplierId();
            String concentration = list.get(i).getConcentration();
            String materialId = list.get(i).getMaterialId();
            String supplierNo = list.get(i).getSupplierNo();
            String zkg = list.get(i).getZKG();

            MaterialTodoSave.DetailsBean bean = new MaterialTodoSave.DetailsBean();
            if (!TextUtils.isEmpty(bulkNum) && !TextUtils.isEmpty(carCode)) {

                bean.setZKG(zkg);
                bean.setPositionId(id);
                bean.setMaterialId(materialId);
                bean.setSupplierId(supplierId);

                bean.setSupplierNo(supplierNo);
                bean.setConcentration(concentration);
                // 用户输入数据
                bean.setPositionNo(carCode);
                bean.setNumber(bulkNum);
                bean.setSapMaterialBatchNo(batchNo);

                detail.add(bean);
            } else {
                // 跳出当前一列、不处理
                continue;
            }
        }
        // 没有合法的操作数据,不请求网络
        if (detail.size() == 0) {
            ZBUiUtils.showToast("请完善您要退货的信息");
            return null;
        }

        String remark = etDesc.getText().toString().trim();
        String time = tvTime.getText().toString().trim();

        requestBody.setDetails(detail);
        requestBody.setWarehouseId(warehouseId);
        requestBody.setSapOrderNo(sapOrderNo);
        requestBody.setOutTime(time);
        requestBody.setRemark(remark);


        String json = DataUtils.toJson(requestBody, 1);
        ZBLog.INSTANCE.e("JSON " + json);
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

        //  服务器校验 库位码
        httpCheckCarCode(position, msg);
    }

    /**
     * Http 校验 库位码合法
     *
     * @param position   item 更新需要的 position
     * @param positionNo 扫码的编号
     */
    private void httpCheckCarCode(int position,String positionNo) {

        presenter.checkCarCode(position, positionNo);

    }

    @Override
    public void showCarSuccess(int position, CarPositionNoData carData) {
        int count = carData.getCount();
        if (count > 0) {
            String carId = carData.getList().get(0).getId();
            warehouseId = carData.getList().get(0).getWarehouseId();
            positionId.put(position, carId);

            adapter.notifyItemChanged(position);
        } else {
            ZBUiUtils.showToast("请扫正确的库位码");
        }
    }


    @Override
    public void detailObjData(MaterialDetailsIn details) {

        // list.clear();
        // 提交保存 需要用到
        list = details.getList();

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new BackGoodsTodoDetailAdapter(this, R.layout.item_material_detail_three_todo, list);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new BackGoodsTodoDetailAdapter.OnItemClickListener() {
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
     * @param editText 内容
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

//            String tempString = textContent;
//            // 【情况 ② 】 无焦点 有内容 http 校验
//            if (!TextUtils.isEmpty(textContent)) {
//                if (!hasFocus) {
//                    //httpCheckCarCode(currentPosition, textContent);
//                }
//            }

            carCodeArray.put(position, textContent);

        } else if (CodeConstant.ET_BATCH_NO.equals(etType)) {
            batchNoArray.put(position, textContent);
        }

    }
}
