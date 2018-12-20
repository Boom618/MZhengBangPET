package com.ty.zbpet.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.activity.system.PersonCenterActivity;
import com.ty.zbpet.ui.activity.system.QualityCheckActivity;
import com.ty.zbpet.ui.base.BaseActivity;

/**
 * @author TY on 2018/12/20.
 * <p>
 * 组织代码 主页
 */
public class MainCompanyActivity extends BaseActivity {

    private TextView inventory;
    private TextView personCenter;


    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initOneData() {
        inventory = findViewById(R.id.tv_inventory);
        personCenter = findViewById(R.id.tv_person_center);
    }

    @Override
    protected void initTwoView() {

        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(QualityCheckActivity.class);
            }
        });

        personCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(PersonCenterActivity.class);
            }
        });


    }
}
