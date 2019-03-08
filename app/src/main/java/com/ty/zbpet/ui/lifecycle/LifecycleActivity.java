package com.ty.zbpet.ui.lifecycle;

import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ty.zbpet.R;



/**
 * @author TY on 2018/11/23.
 */
public class LifecycleActivity extends AppCompatActivity implements LifecycleOwner {


    PickOutDoneDetailObserver pick = new PickOutDoneDetailObserver();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content_row_two);
        // 添加了这一行代码 注册监听
        getLifecycle().addObserver(pick);
        pick.connectListener();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pick.destroy();
    }
}
