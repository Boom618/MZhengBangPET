package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.adapter.ViewPagerAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.fragment.material.PickOutDoneFragment;
import com.ty.zbpet.ui.fragment.material.PickOutTodoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 领料出库
 *
 * @author TY
 */
public class PickOutStorageActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rg)
    RadioGroup rg;

    @BindView(R.id.pick_viewpager)
    ViewPager viewPager;

    private final static int NO_DOING = 0;
    private final static int COMPLETE = 1;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {


        PickOutTodoFragment todoFragment = PickOutTodoFragment.newInstance("todoFragment");
        PickOutDoneFragment doneFragment = PickOutDoneFragment.newInstance("doneFragment");

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(todoFragment);
        fragmentList.add(doneFragment);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new PickOutStorageActivity.PagerChangeListener());
        rg.setOnCheckedChangeListener(this);

        // 默认待办 Fragment
        rg.check(R.id.todo_radio);
        viewPager.setCurrentItem(NO_DOING);


        initToolBar(R.string.label_pick_out_storage);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_pick_out_storage;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.todo_radio:
                viewPager.setCurrentItem(NO_DOING);
                break;
            case R.id.done_radio:
                viewPager.setCurrentItem(COMPLETE);
                break;
            default:
                break;
        }
    }


    class PagerChangeListener extends ViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case NO_DOING:
                    rg.check(R.id.todo_radio);
                    break;
                case COMPLETE:
                    rg.check(R.id.done_radio);
                    break;
                default:
                    break;
            }
        }

    }
}
