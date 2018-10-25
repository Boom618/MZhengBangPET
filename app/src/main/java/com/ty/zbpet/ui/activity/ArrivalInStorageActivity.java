package com.ty.zbpet.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.adapter.ViewPagerAdapter;
import com.ty.zbpet.ui.fragment.ArrivalInStorageCompleteFragment;
import com.ty.zbpet.ui.fragment.ArrivalInStorageNoDoingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 原辅料——到货入库
 *
 * @author TY
 */
public class ArrivalInStorageActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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
        setContentView(R.layout.activity_arrival_in_storage);
        ButterKnife.bind(this);

        ArrivalInStorageNoDoingFragment noDoingFg = new ArrivalInStorageNoDoingFragment();
        ArrivalInStorageCompleteFragment completeFg = new ArrivalInStorageCompleteFragment();

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

        initToolBar(R.string.label_arrival_storage,null);
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
