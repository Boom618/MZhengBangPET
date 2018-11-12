package com.ty.zbpet.ui.activity.material;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.google.gson.Gson;
import com.pda.scanner.ScanReader;
import com.pda.scanner.Scanner;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialDetailsData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.constant.ApiNameConstant;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.material.MaterialUiObjlInterface;
import com.ty.zbpet.presenter.material.MaterialPresenter;
import com.ty.zbpet.ui.adapter.MaterialDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.TLog;
import com.ty.zbpet.util.UIUtils;
import com.ty.zbpet.util.Utils;
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
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.RequestBody;

/**
 * 原辅料——到货入库详情
 *
 * @author TY
 */
public class ArrivalInStorageDetailActivity extends BaseActivity implements MaterialUiObjlInterface<MaterialDetailsData>
        , MaterialDetailAdapter.SaveEditListener, ScanBoxInterface {

    @BindView(R.id.rv_in_storage_detail)
    RecyclerView recyclerView;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_desc)
    EditText etDesc;

    private MaterialDetailAdapter adapter;
    private List<MaterialDetailsData.DetailsBean> list = new ArrayList<>();

    private String orderId;
    private String sapOrderNo;
    private String warehouseId;

    /**
     * 时间选择
     */
    private String selectTime;

    /**
     * 点击车库码输入框
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
        orderId = getIntent().getStringExtra("orderId");

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

                UIUtils.showPickDate(ArrivalInStorageDetailActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        //选中事件回调
                        selectTime = UIUtils.getTime(date);
                        tvTime.setText(selectTime);
                        UIUtils.showToast(selectTime);
                    }
                });
            }
        });
    }


    private RequestBody initParam() {

        MaterialDetailsData requestBody = new MaterialDetailsData();
        List<MaterialDetailsData.DetailsBean> detail = new ArrayList<>();
//        "warehouseId": "3",
//         "inStoreDate": "2018-09-06",
//         "sapProcOrder": "SAP00009",
//         "remark": "1",

        int size = list.size();
        for (int i = 0; i < size; i++) {
            String bulkNum = bulkNumArray.get(i);
            String carCode = carCodeArray.get(i);
            String batchNo = batchNoArray.get(i);

            MaterialDetailsData.DetailsBean bean = new MaterialDetailsData.DetailsBean();
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
            }
        }

        requestBody.setDetails(detail);
        requestBody.setWarehouseId(warehouseId);
        requestBody.setInStoreDate(tvTime.getText().toString().trim());
        requestBody.setSapOrderNo(sapOrderNo);
        requestBody.setRemark(etDesc.getText().toString().trim());


        String json = Utils.toJson(requestBody, 1);
        TLog.e("JSON " + json);
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }

    // 入库保存
    private void doPurchaseInRecallOut(RequestBody body) {

        HttpMethods.getInstance().materialInSave(new BaseSubscriber<ResponseInfo>() {
            @Override
            public void onError(ApiException e) {
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 入库成功（保存）
                    UIUtils.showToast(responseInfo.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                } else {
                    UIUtils.showToast(responseInfo.getMessage());
                }
            }
        }, body);
    }


    @Override
    public void detailObjData(MaterialDetailsData obj) {

        warehouseId = obj.getWarehouseId();
        list.clear();
        list.addAll(obj.getDetails());


        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            recyclerView.setLayoutManager(manager);
            adapter = new MaterialDetailAdapter(this, R.layout.item_arrive_in_storage_detail, obj.getDetails());
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new MaterialDetailAdapter.OnItemClickListener() {
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

                    // 关闭软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

            // 车库码 需要判断合法性
        } else if (CodeConstant.ET_CODE.equals(etType)) {
            String tempString = textContent;

            // 【情况 ② 】 无焦点 有内容 http 校验
            if (!TextUtils.isEmpty(textContent)) {
                if (!hasFocus) {
                    //httpCheckCarCode(currentPosition, textContent);
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

            if (!currentFocus && currentPosition != -1) {
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
     * @param carCode
     */
    @Override
    public void ScanSuccess(int position, String carCode) {

        adapter.notifyItemChanged(position);

        //  服务器校验 车库码
        //httpCheckCarCode(position, carCode);

    }


    /**
     * Http 校验 车库码合法
     *
     * @return
     */
    private void httpCheckCarCode(final int position, final String carCode) {

        Disposable observable = Observable.create(new ObservableOnSubscribe<Response>() {

            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
                // 1、数据请求
                Gson gson = new Gson();

                String string = "{id:'12',code:'3545435'}" + carCode;

                String content = gson.toJson(string);
                RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), content);

                // 方式一 Retrofit 请求网络
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(ApiNameConstant.CHECK_CAR_CODE)
//                        .build();
//
//                ApiService apiService = retrofit.create(ApiService.class);
//                Observable<ResponseInfo> infoObservable = apiService.checkCarCode("1234",carCode);


                // 方式二 OkHttpClient 请求网络
                Builder builder = new Builder()
                        .url(ApiNameConstant.CHECK_CAR_CODE)
                        .post(body);

                Request request = builder.build();
                Call call = new OkHttpClient().newCall(request);
                Response response = call.execute();

                emitter.onNext(response);

            }
        })
                .map(new Function<Response, ResponseInfo>() {
                    @Override
                    public ResponseInfo apply(Response response) throws Exception {

                        // 2、数据转换
                        ResponseBody body = response.body();

                        return new Gson().fromJson(body.string(), ResponseInfo.class);
                    }
                })
                .doOnNext(new Consumer<ResponseInfo>() {
                    @Override
                    public void accept(ResponseInfo responseInfo) throws Exception {
                        // 3、保存数据
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseInfo>() {
                    @Override
                    public void accept(ResponseInfo responseInfo) throws Exception {
                        // 4、 数据成功 UI 处理
                        if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                            UIUtils.showToast("车库码合法");
                            // 更新列表 adapter
                            adapter.notifyItemChanged(position);

                        } else {
                            UIUtils.showToast(responseInfo.getMessage());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        UIUtils.showToast(throwable.getMessage());
                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        scanner.close();
        //disposable.dispose();


    }
}
