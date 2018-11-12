package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.base.BaseActivity;

import butterknife.BindView;


/**
 *
 * @author TY
 * 领料出库详情
 */
public class PickOutStorageDetailActivity extends BaseActivity {

    @BindView(R.id.rc_pick_out_list)
    RecyclerView pickOutList;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        // activity_arrival_in_storage_detail
        return R.layout.activity_pick_out_list;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {

    }

}
