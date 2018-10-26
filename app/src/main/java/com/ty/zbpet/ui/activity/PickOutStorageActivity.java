package com.ty.zbpet.ui.activity;

import android.os.Bundle;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.base.BaseActivity;

/**
 * 领料出库
 * @author TY
 */
public class PickOutStorageActivity extends BaseActivity {

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

        initToolBar(R.string.label_pick_out_storage,null);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_pick_out_storage;
    }
}
