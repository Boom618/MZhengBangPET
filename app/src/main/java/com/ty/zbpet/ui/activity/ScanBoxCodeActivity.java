package com.ty.zbpet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pda.scanner.ScanReader;
import com.pda.scanner.Scanner;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.adapter.BindBoxCodeAdapter;
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
    private List<String> boxCodeList = new ArrayList<>();
    private BindBoxCodeAdapter adapter;
    private int position;
    private final static int REQUEST_SCAN_CODE = 1;
    private final static int RESULT_SCAN_CODE = 2;

    private Scanner scanReader = ScanReader.getScannerInstance();
    private ScanObservable scanObservable = new ScanObservable(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_scan_box_code;
    }

    @Override
    protected void initOneData() {
        position = getIntent().getIntExtra("position", -1);
        ArrayList<String> codeList = getIntent().getStringArrayListExtra("boxCodeList");

        if (codeList != null) {
            boxCodeList.addAll(codeList);
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ResourceUtil.getContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                ResourceUtil.dip2px(1), ResourceUtil.getColor(R.color.split_line)));
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new BindBoxCodeAdapter(ResourceUtil.getContext(), this.boxCodeList);
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
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("boxCodeList", (ArrayList<String>) boxCodeList);
                setResult(RESULT_SCAN_CODE, intent);
                finish();
            }
        });
    }

//    @OnClick({R.id.iv_back, R.id.tv_right})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
//
//            case R.id.tv_right:
//                Intent intent = new Intent();
//                intent.putExtra("position", position);
//                intent.putStringArrayListExtra("boxCodeList", (ArrayList<String>) boxCodeList);
//                setResult(RESULT_SCAN_CODE, intent);
//                finish();
//                break;
//            default:
//                break;
//        }
//    }

    /*------------------------------------解码-----------------------------------------------*/

//    boolean busy = false;
//    protected static final int MSG_SHOW_WAIT = 1;
//    protected static final int MSG_HIDE_WAIT = 2;
//    protected static final int MSG_SHOW_TIP = 3;
//    protected static final int MSG_USER_BEG = 100;
//    static final int MSG_UPDATE_ID = MSG_USER_BEG + 1;

//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case MSG_SHOW_WAIT:
//                    ZBUiUtils.showToast((String) msg.obj);
//                    break;
//                case MSG_HIDE_WAIT:
//                    Log.e("TAG", "MSG_HIDE_WAIT====");
//                    break;
//                case MSG_SHOW_TIP:
//                    ZBUiUtils.showToast((String) msg.obj);
//                    break;
//                case MSG_UPDATE_ID:
//                    String resultStr = (String) msg.obj;
//                    if (!TextUtils.isEmpty(resultStr)) {
//                        if (boxCodeList.contains(resultStr)) {
//                            ZBUiUtils.showToast("箱码已扫过");
//                        } else {
//                            boxCodeList.add(resultStr);
//                            if (adapter != null) {
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

//    protected void doDeCode() {
//        if (busy) {
//            handler.sendMessage(handler.obtainMessage(MSG_SHOW_TIP, ResourceUtil.getString(R.string.str_busy)));
//            return;
//        }

//        busy = true;
//        new Thread() {
//            @Override
//            public void run() {
//                handler.sendMessage(handler.obtainMessage(MSG_SHOW_WAIT, ResourceUtil.getString(R.string.str_please_waiting)));
//                byte[] id = scanReader.decode(10 * 1000);
//                String resultStr;
//                if (id != null) {
//                    String utf8 = new String(id, Charset.forName("utf8"));
//                    if (utf8.contains("\ufffd")) {
//                        utf8 = new String(id, Charset.forName("gbk"));
//                    }
//                    resultStr = utf8 + "\n";
//                    toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
//                } else {
//                    resultStr = null;
//                    handler.sendMessage(handler.obtainMessage(MSG_SHOW_TIP, ResourceUtil.getString(R.string.str_failed)));
//                }
//                //解码完成
//                handler.sendMessage(handler.obtainMessage(MSG_UPDATE_ID, resultStr.replace("\n", "")));
//                handler.sendMessage(handler.obtainMessage(MSG_HIDE_WAIT, null));
//                busy = false;
//            }
//        }.start();

//        ScanObservable.scanBox(scanReader);


//    }

    private boolean scanInit() {
        return null != scanReader && scanReader.open(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanReader.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if (keyCode == CodeConstant.KEY_CODE_131
                || keyCode == CodeConstant.KEY_CODE_135
                || keyCode == CodeConstant.KEY_CODE_139) {

            // 扫描
            if (isOpen) {
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

        HttpMethods.getInstance().checkCarCode(new BaseSubscriber<CarPositionNoData>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(CarPositionNoData responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 库位码合法
                    boxCodeList.add(positionNo);
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                }

            }
        }, positionNo);


    }
}
