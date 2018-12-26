package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.widget.RadioGroup
import com.ty.zbpet.R
import com.ty.zbpet.ui.adapter.ViewPagerAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.material.MaterialDoneFragment
import com.ty.zbpet.ui.fragment.material.MaterialTodoFragment
import kotlinx.android.synthetic.main.activity_arrival_in_storage.*
import java.util.*

/**
 * 原辅料——到货入库
 *
 * @author TY
 */
class ArrivalInStorageActivity : BaseActivity() {

    override fun onBaseCreate(savedInstanceState: Bundle?) {

        val completeFg = MaterialDoneFragment()
        val noDoingFg = MaterialTodoFragment.newInstance("noDoingFg")

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(noDoingFg)
        fragmentList.add(completeFg)

        val adapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        viewpager!!.adapter = adapter
        viewpager!!.addOnPageChangeListener(PagerChangeListener())

        rg!!.setOnCheckedChangeListener(RadioGroupListener())

        // 默认待办 Fragment
        rg!!.check(R.id.rb_no_doing)
        viewpager!!.currentItem = NO_DOING

        initToolBar(R.string.label_purchase_storage)
    }

    override fun getActivityLayout(): Int {
        return R.layout.activity_arrival_in_storage
    }

    override fun initOneData() {

    }

    override fun initTwoView() {

    }

    internal inner class PagerChangeListener : ViewPager.SimpleOnPageChangeListener() {

        override fun onPageSelected(position: Int) {
            when (position) {
                NO_DOING -> rg!!.check(R.id.rb_no_doing)
                COMPLETE -> rg!!.check(R.id.rb_complete)
            }
        }

    }

    internal inner class RadioGroupListener : RadioGroup.OnCheckedChangeListener {

        override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
            when (checkedId) {
                R.id.rb_no_doing -> viewpager!!.currentItem = NO_DOING
                R.id.rb_complete -> viewpager!!.currentItem = COMPLETE
            }
        }
    }

    companion object {

        private val NO_DOING = 0
        private val COMPLETE = 1
    }

}
