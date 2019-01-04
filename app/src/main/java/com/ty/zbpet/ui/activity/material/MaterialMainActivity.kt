package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ty.zbpet.R
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.material.*
import com.ty.zbpet.util.TabLayoutViewPage
import kotlinx.android.synthetic.main.activity_main_todo_and_done.*
import java.util.*

/**
 * 原辅料——到货入库
 *
 * @author TY
 */
class MaterialMainActivity : BaseActivity() {

    override val activityLayout: Int
        get() = R.layout.activity_main_todo_and_done

    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }


    override fun initOneData() {

        val intType = intent.getIntExtra(CodeConstant.ACTIVITY_TYPE, 1)

        val fragmentList = ArrayList<Fragment>()
        when (intType) {
            1 -> {
                initToolBar(R.string.label_purchase_storage)

                val noDoingFg = MaterialTodoFragment.newInstance("noDoingFg")
                val completeFg = MaterialDoneFragment()
                fragmentList.add(noDoingFg)
                fragmentList.add(completeFg)
            }
            2 -> {
                initToolBar(R.string.label_purchase_returns)

                val todoFragment = BackGoodsTodoFragment.newInstance("todoFragment")
                val doneFragment = BackGoodsDoneFragment.newInstance("doneFragment")
                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            3 -> {
                initToolBar(R.string.label_pick_out_storage)

                val todoFragment = PickOutTodoFragment.newInstance("todoFragment")
                val doneFragment = PickOutDoneFragment.newInstance("doneFragment")
                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
        }

        val viewPage = TabLayoutViewPage()
        // FragmentManager ,关联的 viewpager , tabLayout 关联的标签 ,加载的 Fragments
        viewPage.setViewPageToTab(supportFragmentManager, main_viewpager, main_stl, fragmentList)
    }

    override fun initTwoView() {

    }

}
