package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ty.zbpet.R
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.material.BackGoodsDoneFragment
import com.ty.zbpet.ui.fragment.material.BackGoodsTodoFragment
import com.ty.zbpet.util.TabLayoutViewPage
import kotlinx.android.synthetic.main.activity_main_todo_and_done.*
import java.util.*

/**
 * 采购退货
 * @author TY
 */
class BackGoodsActivity : BaseActivity() {

    override val activityLayout: Int
        //        get() = R.layout.activity_back_goods
        get() = R.layout.activity_main_todo_and_done

    override fun onBaseCreate(savedInstanceState: Bundle?) {

        val todoFragment = BackGoodsTodoFragment.newInstance("todoFragment")
        val doneFragment = BackGoodsDoneFragment.newInstance("doneFragment")

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(todoFragment)
        fragmentList.add(doneFragment)

        val viewPage = TabLayoutViewPage()
        viewPage.setViewPageToTab(supportFragmentManager, main_viewpager, main_stl, fragmentList)

        initToolBar(R.string.label_purchase_returns)
    }

    override fun initOneData() {

    }

    override fun initTwoView() {


    }



}
