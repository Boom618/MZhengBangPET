package com.ty.zbpet.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ty.zbpet.R;

/**
 * 领料出库
 * @author TY
 */
public class PickOutStorageActivity extends BaseActivity {

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pick_out_storage);

        initToolBar(R.string.label_pick_out_storage,null);
    }
}
