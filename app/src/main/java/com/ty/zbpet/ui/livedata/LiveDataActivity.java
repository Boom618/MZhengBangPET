package com.ty.zbpet.ui.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.util.ZBLog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TY on 2018/11/23.
 */
public class LiveDataActivity extends AppCompatActivity {

    private static final String TAG = "LiveDataFragment";

    private NameViewModel mNameViewModel;

    private TextView mTvName;


    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_livedata);
        mTvName = findViewById(R.id.tv_name);

        mNameViewModel = ViewModelProviders.of(this).get(NameViewModel.class);

        mNameViewModel.getCurrentName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String name) {
                mTvName.setText(name);

                ZBLog.INSTANCE.d(TAG, "name = " + name);
            }
        });

        mNameViewModel.getNameListData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                for (String name : strings) {
                    ZBLog.INSTANCE.d(TAG, "name = " + name);

                }
            }
        });

    }
    private boolean change = false;

    public void changeName(View view) {
        Log.e(TAG,"changeName  == " + mNameViewModel.getCurrentName());
        if (change) {
            mNameViewModel.getCurrentName().setValue("张三");
            change = false;
        }else{
            mNameViewModel.getCurrentName().postValue("李四");
            change = true;
        }
    }

    public void changeNameS(View view) {
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            names.add("Jane<" + i + ">");
        }

        mNameViewModel.getNameListData().setValue(names);

    }
}
