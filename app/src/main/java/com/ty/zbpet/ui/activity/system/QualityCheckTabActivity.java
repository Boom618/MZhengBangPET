package com.ty.zbpet.ui.activity.system;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import com.flyco.tablayout.SlidingTabLayout;
import com.ty.zbpet.R;
import com.ty.zbpet.ui.adapter.MainListAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.fragment.system.QualityCheckDoneFragment;
import com.ty.zbpet.ui.fragment.system.QualityCheckTodoFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author TY
 * 质检
 */
public class QualityCheckTabActivity extends BaseActivity {


    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private MainListAdapter mAdapter;
    private SlidingTabLayout mStl;
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

        mStl = findViewById(R.id.stl);

        QualityCheckTodoFragment todoFragment = QualityCheckTodoFragment.newInstance("todoFragment");
        QualityCheckDoneFragment doneFragment = QualityCheckDoneFragment.newInstance("doneFragment");

        mFragments.add(todoFragment);
        mFragments.add(doneFragment);

        mAdapter = new MainListAdapter(getSupportFragmentManager(), mFragments);
        viewpager.setAdapter(mAdapter);

        mStl.setViewPager(viewpager);

        initToolBar(R.string.label_check);

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_quality_check_tab;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {

    }

}
