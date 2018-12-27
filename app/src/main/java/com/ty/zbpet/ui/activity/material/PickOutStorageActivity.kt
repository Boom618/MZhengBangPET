package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.widget.RadioGroup
import com.ty.zbpet.R
import com.ty.zbpet.ui.adapter.ViewPagerAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.material.PickOutDoneFragment
import com.ty.zbpet.ui.fragment.material.PickOutTodoFragment
import kotlinx.android.synthetic.main.activity_pick_out_storage.*
import java.util.*

/**
 * 领料出库
 *
 * @author TY
 */
class PickOutStorageActivity : BaseActivity(), RadioGroup.OnCheckedChangeListener {

    override val activityLayout: Int
        get() = R.layout.activity_pick_out_storage

    override fun onBaseCreate(savedInstanceState: Bundle?) {


        val todoFragment = PickOutTodoFragment.newInstance("todoFragment")
        val doneFragment = PickOutDoneFragment.newInstance("doneFragment")

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(todoFragment)
        fragmentList.add(doneFragment)

        val adapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        pick_viewpager!!.adapter = adapter
        pick_viewpager!!.addOnPageChangeListener(PagerChangeListener())
        rg!!.setOnCheckedChangeListener(this)

        // 默认待办 Fragment
        rg!!.check(R.id.todo_radio)
        pick_viewpager!!.currentItem = NO_DOING


        initToolBar(R.string.label_pick_out_storage)
    }

    override fun initOneData() {

    }

    override fun initTwoView() {

    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.todo_radio -> pick_viewpager!!.currentItem = NO_DOING
            R.id.done_radio -> pick_viewpager!!.currentItem = COMPLETE
        }
    }


    internal inner class PagerChangeListener : ViewPager.SimpleOnPageChangeListener() {

        override fun onPageSelected(position: Int) {
            when (position) {
                NO_DOING -> rg!!.check(R.id.todo_radio)
                COMPLETE -> rg!!.check(R.id.done_radio)
                else -> {
                }
            }
        }

    }

    companion object {

        private val NO_DOING = 0
        private val COMPLETE = 1
    }
}
