package com.ty.zbpet.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ty.zbpet.R;

/**
 * 采购退货
 * @author TY
 */
public class PurchaseReturnsActivity extends BaseActivity {

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_purchase_returns);

        initToolBar(R.string.label_purchase_returns,null);
    }
}
