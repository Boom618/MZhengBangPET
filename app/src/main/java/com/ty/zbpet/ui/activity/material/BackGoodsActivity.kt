package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.widget.EditText
import android.widget.RadioGroup

import com.ty.zbpet.R
import com.ty.zbpet.ui.adapter.ViewPagerAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.material.BackGoodsDoneFragment
import com.ty.zbpet.ui.fragment.material.BackGoodsTodoFragment

import java.util.ArrayList

import butterknife.BindView
import kotlinx.android.synthetic.main.activity_back_goods.*

/**
 * 采购退货
 * @author TY
 */
class BackGoodsActivity : BaseActivity() {

    override val activityLayout: Int
        get() = R.layout.activity_back_goods


    override fun onBaseCreate(savedInstanceState: Bundle?) {

        val todoFragment = BackGoodsTodoFragment.newInstance("todoFragment")
        val doneFragment = BackGoodsDoneFragment.newInstance("doneFragment")

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(todoFragment)
        fragmentList.add(doneFragment)

        val adapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        back_goods_viewpager!!.adapter = adapter
        back_goods_viewpager!!.addOnPageChangeListener(PagerChangeListener())
        rg!!.setOnCheckedChangeListener(RadioGroupListener())

        // 默认待办 Fragment
        rg!!.check(R.id.rb_no_doing)
        back_goods_viewpager!!.currentItem = NO_DOING

        initToolBar(R.string.label_purchase_returns)
    }

    override fun initOneData() {

    }

    override fun initTwoView() {


    }

    internal inner class RadioGroupListener : RadioGroup.OnCheckedChangeListener {

        override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
            when (checkedId) {
                R.id.rb_no_doing -> back_goods_viewpager!!.currentItem = NO_DOING
                R.id.rb_complete -> back_goods_viewpager!!.currentItem = COMPLETE
                else -> {
                }
            }
        }
    }

    internal inner class PagerChangeListener : ViewPager.SimpleOnPageChangeListener() {

        override fun onPageSelected(position: Int) {
            when (position) {
                NO_DOING -> rg!!.check(R.id.rb_no_doing)
                COMPLETE -> rg!!.check(R.id.rb_complete)
            }
        }

    }

    companion object {

        private val NO_DOING = 0
        private val COMPLETE = 1
    }

}
