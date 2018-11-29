package com.ty.zbpet.ui.activity.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.adapter.ViewPagerAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.fragment.product.ProductDoneFragment;
import com.ty.zbpet.ui.fragment.product.ProductTodoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 生产入库
 * @author TY
 */
public class ProductInStorageActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.back_goods_viewpager)
    ViewPager viewPager;

    private final static int NO_DOING = 0;
    private final static int COMPLETE = 1;


    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

        ProductTodoFragment todoFragment = ProductTodoFragment.newInstance("todoFragment");
        ProductDoneFragment doneFragment = ProductDoneFragment.newInstance("doneFragment");

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(todoFragment);
        fragmentList.add(doneFragment);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ProductInStorageActivity.PagerChangeListener());
        rg.setOnCheckedChangeListener(new ProductInStorageActivity.RadioGroupListener());

        // 默认待办 Fragment
        rg.check(R.id.rb_no_doing);
        viewPager.setCurrentItem(NO_DOING);

        initToolBar(R.string.label_produce_in_storage);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_produce_in_storage;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {


    }

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_no_doing:
                    viewPager.setCurrentItem(NO_DOING);
                    break;
                case R.id.rb_complete:
                    viewPager.setCurrentItem(COMPLETE);
                    break;
                default:
                    break;
            }
        }
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
}
