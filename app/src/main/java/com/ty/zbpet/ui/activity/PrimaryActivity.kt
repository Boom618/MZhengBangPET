package com.ty.zbpet.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.ty.zbpet.R
import com.ty.zbpet.bean.SearchMessage
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.material.*
import com.ty.zbpet.ui.fragment.product.*
import com.ty.zbpet.util.TabLayoutViewPage
import com.ty.zbpet.util.TimeWidget
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.activity_main_todo_and_done.*
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * 七大模块 第一界面
 *
 * @author TY
 */
class PrimaryActivity : BaseActivity() {

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
                initToolBar(R.string.label_pick_out_storage)

                val todoFragment = PickOutTodoFragment.newInstance("todoFragment")
                val doneFragment = PickOutDoneFragment.newInstance("doneFragment")

                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            3 -> {
                initToolBar(R.string.label_purchase_returns)

                val todoFragment = BackGoodsTodoFragment.newInstance("todoFragment")
                val doneFragment = BackGoodsDoneFragment.newInstance("doneFragment")
                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            4 ->{
                initToolBar(R.string.label_purchase_in_storage)

                val todoFragment = BuyInTodoFragment.newInstance("todoFragment")
                val doneFragment = BuyInTodoFragment.newInstance("doneFragment")
                //val doneFragment = BuyInDoneFragment.newInstance("doneFragment")
                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            5 ->{
                initToolBar(R.string.label_produce_in_storage)
                val todoFragment = ProductTodoFragment.newInstance("todoFragment")
                val doneFragment = ProductDoneFragment.newInstance("doneFragment")
                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            6 ->{
                initToolBar(R.string.label_send_out_storage)
                val todoFragment = SendOutTodoFragment.newInstance("todoFragment")
                val doneFragment = SendOutDoneFragment.newInstance("doneFragment")

                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            7 ->{
                initToolBar(R.string.label_return_sell)
                val todoFragment = ReturnGoodsTodoFragment.newInstance("todoFragment")
                val doneFragment = ReturnGoodsDoneFragment.newInstance("doneFragment")

                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
        }

        val viewPage = TabLayoutViewPage()
        // FragmentManager ,关联的 viewpager , tabLayout 关联的标签 ,加载的 Fragments
        viewPage.setViewPageToTab(supportFragmentManager, main_viewpager, main_stl, fragmentList)
    }

    override fun initTwoView() {
        val searchView = findViewById<EditText>(R.id.et_search)
        val leftTime = findViewById<TextView>(R.id.tv_time_left)
        val rightTime = findViewById<TextView>(R.id.tv_time_right)

        val currentTime = TimeWidget.getCurrentTime()
        var currentYear = TimeWidget.getCurrentYear()
        var currentMonth = TimeWidget.getCurrentMonth()
        if (currentMonth == 1) {
            currentYear--
            currentMonth = 12
        } else {
            currentMonth--
        }
        val month = if (currentMonth < 10) {
            "0$currentMonth"
        } else {
            "$currentMonth"
        }
        leftTime.text = "$currentYear-$month-1"
        rightTime.text = currentTime

        leftTime.setOnClickListener {
            ZBUiUtils.hideInputWindow(it.context, it)
            TimeWidget.showPickDate(it.context) { date, v ->
                val selectTime = TimeWidget.getTime(CodeConstant.DATE_SIMPLE_Y_M_D, date)
                val rightString = rightTime.text.toString()
                val sapOrderNo = searchView.text.toString()

                val startTime = TimeWidget.StringToDate(selectTime)
                val endTime = TimeWidget.StringToDate(rightString)
                val result = TimeWidget.DateComparison(startTime, endTime)
                if (result) {
                    leftTime.text = selectTime
                    EventBus.getDefault().post(SearchMessage(sapOrderNo,selectTime,rightString))
                } else {
                    ZBUiUtils.showToast("开始时间不能大于结束时间")
                }
            }
        }

        rightTime.setOnClickListener {
            ZBUiUtils.hideInputWindow(it.context, it)
            TimeWidget.showPickDate(it.context) { date, v ->
                val selectTime = TimeWidget.getTime(CodeConstant.DATE_SIMPLE_Y_M_D, date)
                val sapOrderNo = searchView.text.toString()
                val leftString = leftTime.text.toString()

                val startTime = TimeWidget.StringToDate(leftString)
                val endTime = TimeWidget.StringToDate(selectTime)
                val result = TimeWidget.DateComparison(startTime, endTime)
                if (result) {
                    rightTime.text = selectTime
                    EventBus.getDefault().post(SearchMessage(sapOrderNo,leftString,selectTime))
                } else {
                    ZBUiUtils.showToast("结束时间不能小于开始时间")
                }
            }
        }

        searchView.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchString = v.text.toString().trim { it <= ' ' }
                EventBus.getDefault().post(SearchMessage(searchString,"",""))
                ZBUiUtils.hideInputWindow(v.context, v)
            }
            true
        }

    }

}
