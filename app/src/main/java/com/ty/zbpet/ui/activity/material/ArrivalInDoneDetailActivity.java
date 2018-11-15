package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.presenter.material.MaterialUiListInterface;

import java.util.List;

import butterknife.BindView;

/**
 * @author TY on 2018/11/14.
 *
 * 已办 详情
 *
 */
public class ArrivalInDoneDetailActivity extends BaseActivity implements MaterialUiListInterface {

    @BindView(R.id.rc_done_detail_list)
    RecyclerView doneDetail;


    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_material_done_detail;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {

    }

    @Override
    public void showMaterial(List list) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
