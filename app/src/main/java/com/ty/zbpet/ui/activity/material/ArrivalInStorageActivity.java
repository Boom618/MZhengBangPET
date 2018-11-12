package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.ty.zbpet.R;
import com.ty.zbpet.ui.adapter.ViewPagerAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.fragment.material.ArrivalInStorageCompleteFragment;
import com.ty.zbpet.ui.fragment.material.MaterialTodoFragment;
import com.ty.zbpet.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 原辅料——到货入库
 *
 * @author TY
 */
public class ArrivalInStorageActivity extends BaseActivity {

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rb_no_doing)
    RadioButton rbNoDoing;
    @BindView(R.id.rb_complete)
    RadioButton rbComplete;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private final static int NO_DOING = 0;
    private final static int COMPLETE = 1;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

        ArrivalInStorageCompleteFragment completeFg = new ArrivalInStorageCompleteFragment();
        MaterialTodoFragment noDoingFg = MaterialTodoFragment.newInstance("noDoingFg");

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(noDoingFg);
        fragmentList.add(completeFg);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new PagerChangeListener());
        rg.setOnCheckedChangeListener(new RadioGroupListener());

        // 默认待办 Fragment
        rg.check(R.id.rb_no_doing);
        viewpager.setCurrentItem(NO_DOING);

        initToolBar(R.string.label_purchase_storage);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_arrival_in_storage;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {

    }

    class PagerChangeListener extends ViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case NO_DOING:
                    rg.check(R.id.rb_no_doing);
                    break;
                case COMPLETE:
                    rg.check(R.id.rb_complete);
                    break;
                default:
                    break;
            }
        }

    }

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_no_doing:
                    viewpager.setCurrentItem(NO_DOING);
                    break;
                case R.id.rb_complete:
                    viewpager.setCurrentItem(COMPLETE);
                    break;
                default:
                    break;
            }
        }
    }

}
