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
import com.ty.zbpet.bean.MaterialTodoDetailsData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.material.MaterialPresenter;
import com.ty.zbpet.presenter.material.MaterialUiObjInterface;
import com.ty.zbpet.ui.adapter.material.MaterialTodoDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
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
public class ArrivalInTodoDetailActivity extends BaseActivity implements MaterialUiObjInterface<MaterialTodoDetailsData>
        , MaterialTodoDetailAdapter.SaveEditListener, ScanBoxInterface {

    @BindView(R.id.rv_in_storage_detail)
    RecyclerView recyclerView;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_desc)
    EditText etDesc;

    private MaterialTodoDetailAdapter adapter;
    private List<MaterialTodoDetailsData.DetailsBean> list = new ArrayList<>();

    private String sapOrderNo;
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

    private MaterialPresenter materialPresenter = new MaterialPresenter(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        SimpleDateFormat sdf = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = sdf.format(new Date());
        tvTime.setText(selectTime);

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_arrival_in_storage_detail;
    }

    @Override
    protected void initOneData() {

        sapOrderNo = getIntent().getStringExtra("sapOrderNo");

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

        MaterialTodoDetailsData requestBody = new MaterialTodoDetailsData();
        List<MaterialTodoDetailsData.DetailsBean> detail = new ArrayList<>();

        int size = list.size();
        for (int i = 0; i < size; i++) {
            String bulkNum = bulkNumArray.get(i);
            String carCode = carCodeArray.get(i);
            String batchNo = batchNoArray.get(i);

            MaterialTodoDetailsData.DetailsBean bean = new MaterialTodoDetailsData.DetailsBean();
            if (null != bulkNum && null != carCode) {
                if (!bulkNum.isEmpty()) {
                    bean.setNumber(bulkNum);
                } else if (!carCode.isEmpty()) {
                    bean.setPositionId(carCode);
                } else if (!batchNo.isEmpty()) {
                    bean.setSapMaterialBatchNo(batchNo);
                }
                bean.setMaterialId(list.get(i).getMaterialId());
                bean.setPositionId(list.get(i).getPositionId());
                detail.add(bean);
            } else if (null == bulkNum && null == carCode) {
                // 不处理
                break;
            } else {
                // 车库数量和库位码必须一致
                ZBUiUtils.showToast("车库数量或库位码信息不全");
                break;
            }
        }

        requestBody.setDetails(detail);
        requestBody.setWarehouseId(warehouseId);
        requestBody.setInStoreDate(tvTime.getText().toString().trim());
        requestBody.setSapOrderNo(sapOrderNo);
        requestBody.setRemark(etDesc.getText().toString().trim());


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

    /**
     * 获取供应商数据
     *
     * @return
     */
    public static ArrayList<String> getItems() {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("供应商 1");
        arrayList.add("供应商 2");
        arrayList.add("供应商 3");


        return arrayList;

    }


    @Override
    public void detailObjData(MaterialTodoDetailsData obj) {

        warehouseId = obj.getWarehouseId();
        list.clear();
        list.addAll(obj.getDetails());


        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            recyclerView.setLayoutManager(manager);
            adapter = new MaterialTodoDetailAdapter(this, R.layout.item_matterial_todo_detail, obj.getDetails());
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new MaterialTodoDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.rl_detail);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);

                    final TextView tvSupplier = holder.itemView.findViewById(R.id.select_supplier);

                    if (rlDetail.getVisibility() == View.VISIBLE) {
                        rlDetail.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.ic_collapse);
                    } else {
                        rlDetail.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.ic_expand);

                        tvSupplier.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ZBUiUtils.selectDialog(ArrivalInTodoDetailActivity.this, getItems(), tvSupplier);
                            }
                        });

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
    public void saveEditAndGetHasFocusPosition(String etType, Boolean hasFocus, int position, String textContent) {
        // 用户在 EditText 中输入的数据
        currentPosition = position;

        if (CodeConstant.ET_BULK_NUM.equals(etType)) {
            bulkNumArray.put(position, textContent);

            // 库位码 需要判断合法性
        } else if (CodeConstant.ET_CODE.equals(etType)) {
            currentFocus = hasFocus;

            String tempString = textContent;
            // 【情况 ② 】 无焦点 有内容 http 校验
            if (!TextUtils.isEmpty(textContent)) {
                if (!hasFocus) {
                    httpCheckCarCode(currentPosition, textContent);
                }
            }

            carCodeArray.put(position, tempString);

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
