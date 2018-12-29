package com.ty.zbpet.ui.activity.product

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ty.zbpet.R
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.product.ReturnGoodsDoneFragment
import com.ty.zbpet.ui.fragment.product.ReturnGoodsTodoFragment
import com.ty.zbpet.util.TabLayoutViewPage
import kotlinx.android.synthetic.main.activity_main_todo_and_done.*
import java.util.*

/**
 * 成品 退货入库
 * @author TY
 */
class ReturnGoodsActivity : BaseActivity() {

    override val activityLayout: Int
//        get() = R.layout.activity_purchase_in_storage
        get() = R.layout.activity_main_todo_and_done


    override fun onBaseCreate(savedInstanceState: Bundle?) {

        val todoFragment = ReturnGoodsTodoFragment.newInstance("todoFragment")
        val doneFragment = ReturnGoodsDoneFragment.newInstance("doneFragment")

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(todoFragment)
        fragmentList.add(doneFragment)


        val viewPage = TabLayoutViewPage()
        viewPage.setViewPageToTab(supportFragmentManager, main_viewpager, main_stl, fragmentList)

        initToolBar(R.string.label_return_sell)
    }

    override fun initOneData() {

    }

    override fun initTwoView() {

    }


}
