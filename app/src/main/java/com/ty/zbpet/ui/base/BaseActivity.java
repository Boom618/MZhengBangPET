package com.ty.zbpet.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.util.ACache;
import com.ty.zbpet.util.CodeConstant;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author TY
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;
    private ACache mCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());

        mUnbinder = ButterKnife.bind(this);
        mCache = ACache.get(getApplication());

        initOneData();

        onBaseCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initTwoView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化 View
     * @param savedInstanceState
     */
    protected  abstract void onBaseCreate(Bundle savedInstanceState);

    /**
     * Activity Layout 布局
     * @return
     */
    protected abstract int getActivityLayout();

    /**
     * 初始化 Data
     *
     */
    protected abstract void initOneData();

    /**
     * 初始化 View
     */
    protected abstract void initTwoView();


    /**
     * 打开一个Activity 默认 不关闭当前activity
     */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        Intent intent = new Intent(this, clz);
        if (ex != null) {
            intent.putExtras(ex);
        }
        startActivity(intent);
        if (isCloseCurrentActivity) {
            finish();
        }
    }

    protected void initToolBar(int intId){

        initToolBar(intId,null);
    }



    protected void initToolBar(int intId, View.OnClickListener listener){

        // 左边返回
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCache();
                finish();
            }
        });

        // 中间标题
        TextView topText = findViewById(R.id.tv_title);
        topText.setText(intId);

        // 右边监听事件
        TextView right = findViewById(R.id.tv_right);

        if (null == listener) {
            right.setVisibility(View.GONE);
        }else{
            right.setOnClickListener(listener);
        }

    }

    /**
     *
     * @param intId         中间标题
     * @param rightText     右边文字
     * @param listener      右边 listener
     */
    protected void initToolBar(int intId,String rightText,View.OnClickListener listener){
        // 左边返回
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCache();
                finish();
            }
        });

        // 中间标题
        TextView topText = findViewById(R.id.tv_title);
        topText.setText(intId);

        // 右边监听事件
        TextView right = findViewById(R.id.tv_right);
        right.setText(rightText);
        right.setOnClickListener(listener);
    }

    /**
     * 重置 ACache 中保存的的数据
     */
    private void clearCache(){
        // 库位码 内容
        mCache.put(CodeConstant.SCAN_BOX_KEY,"");
        mCache.put(CodeConstant.ET_ZKG,"");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearCache();

        mUnbinder.unbind();
    }
}
