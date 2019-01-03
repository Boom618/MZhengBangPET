package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ty.zbpet.R
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.material.PickOutDoneFragment
import com.ty.zbpet.ui.fragment.material.PickOutTodoFragment
import com.ty.zbpet.util.TabLayoutViewPage
import kotlinx.android.synthetic.main.activity_main_todo_and_done.*
import java.util.*

/**
 * 领料出库
 *
 * @author TY
 */
class PickOutStorageActivity : BaseActivity() {

    override val activityLayout: Int
        get() = R.layout.activity_main_todo_and_done

    override fun onBaseCreate(savedInstanceState: Bundle?) {

        val todoFragment = PickOutTodoFragment.newInstance("todoFragment")
        val doneFragment = PickOutDoneFragment.newInstance("doneFragment")

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(todoFragment)
        fragmentList.add(doneFragment)

        val viewPage = TabLayoutViewPage()
        viewPage.setViewPageToTab(supportFragmentManager, main_viewpager, main_stl, fragmentList)

        initToolBar(R.string.label_pick_out_storage)
    }

    override fun initOneData() {

    }

    override fun initTwoView() {

    }
}
