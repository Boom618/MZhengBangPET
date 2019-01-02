package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ty.zbpet.R
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.material.MaterialDoneFragment
import com.ty.zbpet.ui.fragment.material.MaterialTodoFragment
import com.ty.zbpet.util.TabLayoutViewPage
import kotlinx.android.synthetic.main.activity_main_todo_and_done.*
import java.util.*

/**
 * 原辅料——到货入库
 *
 * @author TY
 */
class ArrivalInStorageActivity : BaseActivity() {
    override val activityLayout: Int
        //        get() = R.layout.activity_arrival_in_storage
        get() = R.layout.activity_main_todo_and_done

    override fun onBaseCreate(savedInstanceState: Bundle?) {

        val noDoingFg = MaterialTodoFragment.newInstance("noDoingFg")
        val completeFg = MaterialDoneFragment()

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(noDoingFg)
        fragmentList.add(completeFg)

        val viewPage = TabLayoutViewPage()
        viewPage.setViewPageToTab(supportFragmentManager, main_viewpager, main_stl, fragmentList)

        initToolBar(R.string.label_purchase_storage)
    }


    override fun initOneData() {

    }

    override fun initTwoView() {

    }


}
