package com.ty.zbpet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pda.scanner.ScanReader;
import com.pda.scanner.Scanner;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.adapter.BindBoxCodeAdapter;
import com.ty.zbpet.ui.adapter.material.MaterialDiffUtil;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.DividerItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;
import com.ty.zbpet.util.scan.ScanBoxInterface;
import com.ty.zbpet.util.scan.ScanObservable;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @author TY
 * 扫码功能
 */
public class ScanBoxCodeActivity extends BaseActivity implements ScanBoxInterface {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private boolean isOpen;
    /**
     * 箱码数据
     */
    private ArrayList<String> boxCodeList = new ArrayList<>();
    private BindBoxCodeAdapter adapter;
    private int itemId;
    private final static int REQUEST_SCAN_CODE = 1;
    private final static int RESULT_SCAN_CODE = 2;

    private boolean state;
    private Scanner scanReader = ScanReader.getScannerInstance();
    private ScanObservable scanObservable = new ScanObservable(this);

    private Disposable disposable;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_scan_box_code;
    }

    @Override
    protected void initOneData() {
        itemId = getIntent().getIntExtra("itemId", -1);
        state = getIntent().getBooleanExtra(CodeConstant.PAGE_STATE, false);
        boxCodeList = getIntent().getStringArrayListExtra("boxCodeList");
        if (boxCodeList == null) {
            boxCodeList = new ArrayList<>();
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ResourceUtil.getContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST
                , ResourceUtil.dip2px(1), ResourceUtil.getColor(R.color.split_line)));
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new BindBoxCodeAdapter(ResourceUtil.getContext(), boxCodeList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initTwoView() {

        //初始化扫描仪
        isOpen = scanInit();

        initToolBar(R.string.box_binding_list, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存箱码数据
                Intent intent = new Intent();
                intent.putExtra("itemId", itemId);
                intent.putStringArrayListExtra("boxCodeList", boxCodeList);
                setResult(RESULT_SCAN_CODE, intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private boolean scanInit() {
        return null != scanReader && scanReader.open(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanReader.close();
        disposable.dispose();
    }

    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if (keyCode == CodeConstant.KEY_CODE_131
                || keyCode == CodeConstant.KEY_CODE_135
                || keyCode == CodeConstant.KEY_CODE_139) {

            // 扫描
            if (isOpen && state) {
                scanObservable.scanBox(scanReader, -1);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    /**
     * 扫描成功
     *
     * @param position
     * @param positionNo
     */
    @Override
    public void ScanSuccess(int position, final String positionNo) {

        HttpMethods.getInstance().checkCarCode(new SingleObserver<CarPositionNoData>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(CarPositionNoData responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 库位码校验
                    if (responseInfo.getList().size() > -1) {
                        // 找到库位码
                        if (boxCodeList.contains(positionNo)) {
                            ZBUiUtils.showToast("该库位码扫码过");
                        } else {

                            ArrayList<String> oldList = new ArrayList<>(boxCodeList);
                            boxCodeList.add(positionNo);
                            if (adapter != null) {

                                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MaterialDiffUtil(oldList, boxCodeList));
                                diffResult.dispatchUpdatesTo(adapter);
                            }
                        }
                    }


                } else {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                }

            }
        }, positionNo);


    }
}
