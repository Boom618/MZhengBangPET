package com.ty.zbpet.ui.activity.system

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ty.zbpet.R
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.system.QualityCheckFragment
import com.ty.zbpet.util.TabLayoutViewPage
import kotlinx.android.synthetic.main.activity_quality_check_tab.*
import java.util.*

/**
 * @author TY
 * 质检
 */
class QualityCheckTabActivity : BaseActivity() {


    private val mFragments = ArrayList<Fragment>()

    override val activityLayout: Int
        get() = R.layout.activity_quality_check_tab


    override fun onBaseCreate(savedInstanceState: Bundle?) {

        val todoFragment = QualityCheckFragment.newInstance(CodeConstant.FRAGMENT_TODO)
        val doneFragment = QualityCheckFragment.newInstance(CodeConstant.FRAGMENT_DONE)

        mFragments.add(todoFragment)
        mFragments.add(doneFragment)

        initToolBar(R.string.label_check)

    }

    override fun initOneData() {

    }

    override fun initTwoView() {

        val viewPage = TabLayoutViewPage()
        viewPage.setViewPageToTab(supportFragmentManager, viewpager, stl, mFragments)

    }

}
