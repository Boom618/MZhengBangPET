package com.ty.zbpet.ui.activity.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.adapter.ViewPagerAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.fragment.product.BuyInDoneFragment;
import com.ty.zbpet.ui.fragment.product.BuyInTodoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 发货出库
 *
 * @author TY
 */
public class SendOutActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private final static int NO_DOING = 0;
    private final static int COMPLETE = 1;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

        // activity_send_out_storage

        BuyInTodoFragment todoFragment = BuyInTodoFragment.newInstance("todoFragment");
        BuyInDoneFragment doneFragment = BuyInDoneFragment.newInstance("doneFragment");

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(todoFragment);
        fragmentList.add(doneFragment);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new SendOutActivity.PagerChangeListener());
        rg.setOnCheckedChangeListener(new SendOutActivity.RadioGroupListener());

        rg.check(R.id.rb_no_doing);
        viewpager.setCurrentItem(NO_DOING);

        initToolBar(R.string.label_purchase_in_storage);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_purchase_in_storage;
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
