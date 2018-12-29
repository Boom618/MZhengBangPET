package com.ty.zbpet.ui.activity.product

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ty.zbpet.R
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.product.SendOutDoneFragment
import com.ty.zbpet.ui.fragment.product.SendOutTodoFragment
import com.ty.zbpet.util.TabLayoutViewPage
import kotlinx.android.synthetic.main.activity_main_todo_and_done.*
import java.util.*

/**
 * 发货出库
 *
 * @author TY
 */
class SendOutActivity : BaseActivity() {

    override val activityLayout: Int
//        get() = R.layout.activity_purchase_in_storage
        get() = R.layout.activity_main_todo_and_done

    override fun onBaseCreate(savedInstanceState: Bundle?) {

        val todoFragment = SendOutTodoFragment.newInstance("todoFragment")
        val doneFragment = SendOutDoneFragment.newInstance("doneFragment")

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(todoFragment)
        fragmentList.add(doneFragment)

        val viewPage = TabLayoutViewPage()
        viewPage.setViewPageToTab(supportFragmentManager, main_viewpager, main_stl, fragmentList)


        initToolBar(R.string.label_send_out_storage)
    }

    override fun initOneData() {

    }

    override fun initTwoView() {

    }

}