package com.ty.zbpet.ui.activity;

import android.os.Bundle;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.base.BaseActivity;

/**
 * 采购退货
 * @author TY
 */
public class PurchaseReturnsActivity extends BaseActivity {

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

        initToolBar(R.string.label_purchase_returns,null);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_purchase_returns;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {

    }
}
