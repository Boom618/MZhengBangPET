package com.ty.zbpet.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pda.scanner.ScanReader;
import com.pda.scanner.Scanner;
import com.ty.zbpet.R;
import com.ty.zbpet.ui.adapter.BindBoxCodeAdapter;
import com.ty.zbpet.ui.widght.DividerItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.UIUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author TY
 * 扫码功能
 */
public class ScanBoxCodeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private boolean isOpen;
    /**
     * 扫描成功提示音
     */
    ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM,
            ToneGenerator.MAX_VOLUME);
    /**
     * 箱码数据
      */
    private List<String> boxCodeList = new ArrayList<>();
    private BindBoxCodeAdapter adapter;
    private int position;
    private final static int REQUEST_SCAN_CODE = 1;
    private final static int RESULT_SCAN_CODE = 2;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scan_box_code);
        ButterKnife.bind(this);
        tvTitle.setText("箱码绑定");
        position = getIntent().getIntExtra("position", -1);
        ArrayList<String> codeList = getIntent().getStringArrayListExtra("boxCodeList");
        if (codeList != null) {
            boxCodeList.addAll(codeList);
        }
        //初始化扫描仪
        isOpen = scanInit();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ResourceUtil.getContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                ResourceUtil.dip2px(1), ResourceUtil.getColor(R.color.split_line)));
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new BindBoxCodeAdapter(ResourceUtil.getContext(), this.boxCodeList);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_right:
                Intent intent = new Intent();
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("boxCodeList", (ArrayList<String>) boxCodeList);
                setResult(RESULT_SCAN_CODE, intent);
                finish();
                break;
            default:
                break;
        }
    }

    /*------------------------------------解码-----------------------------------------------*/

    boolean busy = false;
    protected static final int MSG_SHOW_WAIT = 1;
    protected static final int MSG_HIDE_WAIT = 2;
    protected static final int MSG_SHOW_TIP = 3;
    protected static final int MSG_USER_BEG = 100;
    static final int MSG_UPDATE_ID = MSG_USER_BEG + 1;
    private Scanner scanReader = ScanReader.getScannerInstance();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_WAIT:
                    UIUtils.showToast((String) msg.obj);
                    break;
                case MSG_HIDE_WAIT:
                    Log.e("TAG", "MSG_HIDE_WAIT====");
                    break;
                case MSG_SHOW_TIP:
                    UIUtils.showToast((String) msg.obj);
                    break;
                case MSG_UPDATE_ID:
                    String resultStr = (String) msg.obj;
                    if (!TextUtils.isEmpty(resultStr)) {
                        if (boxCodeList.contains(resultStr)) {
                            UIUtils.showToast("箱码已扫过");
                        } else {
                            boxCodeList.add(resultStr);
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    protected void doDeCode() {
        if (busy) {
            handler.sendMessage(handler.obtainMessage(MSG_SHOW_TIP, ResourceUtil.getString(R.string.str_busy)));
            return;
        }

        busy = true;
        new Thread() {
            @Override
            public void run() {
                handler.sendMessage(handler.obtainMessage(MSG_SHOW_WAIT, ResourceUtil.getString(R.string.str_please_waiting)));
                byte[] id = scanReader.decode(10 * 1000);
                String resultStr;
                if (id != null) {
                    String utf8 = new String(id, Charset.forName("utf8"));
                    if (utf8.contains("\ufffd")) {
                        utf8 = new String(id, Charset.forName("gbk"));
                    }
                    resultStr = utf8 + "\n";
                    toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                } else {
                    resultStr = null;
                    handler.sendMessage(handler.obtainMessage(MSG_SHOW_TIP, ResourceUtil.getString(R.string.str_failed)));
                }
                //解码完成
                handler.sendMessage(handler.obtainMessage(MSG_UPDATE_ID, resultStr.replace("\n", "")));
                handler.sendMessage(handler.obtainMessage(MSG_HIDE_WAIT, null));
                busy = false;
            }
        }.start();

    }

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

        // 131 or 135 : K3A手柄键   139 : G3A F1键
        if (keyCode == 131 || keyCode == 135 || keyCode == 139) {
            if (isOpen) {
                doDeCode();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
