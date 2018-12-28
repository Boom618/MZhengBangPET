package com.ty.zbpet.ui.activity.system

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.flyco.tablayout.SegmentTabLayout
import com.flyco.tablayout.listener.OnTabSelectListener
import com.ty.zbpet.R
import com.ty.zbpet.ui.adapter.MainListAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.system.QualityCheckDoneFragment
import com.ty.zbpet.ui.fragment.system.QualityCheckTodoFragment
import kotlinx.android.synthetic.main.activity_quality_check_tab.*
import java.util.*

/**
 * @author TY
 * 质检
 */
class QualityCheckTabActivity : BaseActivity() {


    private val mFragments = ArrayList<Fragment>()

    private val mTitles = arrayOf("待办", "已办")

    override val activityLayout: Int
        get() = R.layout.activity_quality_check_tab


    override fun onBaseCreate(savedInstanceState: Bundle?) {

        val todoFragment = QualityCheckTodoFragment.newInstance("todoFragment")
        val doneFragment = QualityCheckDoneFragment.newInstance("doneFragment")

        mFragments.add(todoFragment)
        mFragments.add(doneFragment)

        initToolBar(R.string.label_check)

    }

    override fun initOneData() {

    }

    override fun initTwoView() {

        // TabLayout
        viewpager!!.adapter = MainListAdapter(supportFragmentManager,mTitles, mFragments)
        stl.setTabData(mTitles)
        stl.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabReselect(position: Int) {

            }

            override fun onTabSelect(position: Int) {

                viewpager.currentItem = position
            }
        })

        viewpager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(position: Int) {
                stl.currentTab = position
            }
        })

        viewpager.currentItem = 0
    }

}
