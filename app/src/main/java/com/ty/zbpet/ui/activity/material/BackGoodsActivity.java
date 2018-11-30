package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.adapter.ViewPagerAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.fragment.material.BackGoodsDoneFragment;
import com.ty.zbpet.ui.fragment.material.BackGoodsTodoFragment;
import com.ty.zbpet.ui.fragment.material.MaterialTodoFragment;
import com.ty.zbpet.ui.fragment.material.PickOutDoneFragment;
import com.ty.zbpet.ui.fragment.material.PickOutTodoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 采购退货
 * @author TY
 */
public class BackGoodsActivity extends BaseActivity {

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

        BackGoodsTodoFragment todoFragment = BackGoodsTodoFragment.newInstance("todoFragment");
        BackGoodsDoneFragment doneFragment = BackGoodsDoneFragment.newInstance("doneFragment");

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(todoFragment);
        fragmentList.add(doneFragment);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new BackGoodsActivity.PagerChangeListener());
        rg.setOnCheckedChangeListener(new RadioGroupListener());

        // 默认待办 Fragment
        rg.check(R.id.rb_no_doing);
        viewPager.setCurrentItem(NO_DOING);

        initToolBar(R.string.label_purchase_returns);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_back_goods;
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