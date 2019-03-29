package com.ty.zbpet.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.SearchMessage
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

    // 领料类型 ：默认生产订单
    private var signType = false
    private var intType = 1

    override val activityLayout: Int
        get() = R.layout.activity_main_todo_and_done

    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

        intType = intent.getIntExtra(CodeConstant.ACTIVITY_TYPE, 1)

        val fragmentList = ArrayList<Fragment>()
        when (intType) {
            1 -> {
                initToolBar(R.string.label_purchase_storage)

                val todoFragment = ArrivalInFragment.newInstance(CodeConstant.FRAGMENT_TODO)
                val doneFragment = ArrivalInFragment.newInstance(CodeConstant.FRAGMENT_DONE)
                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            2 -> {
                initToolBar(R.string.label_pick_out_storage)

                val todoFragment = PickOutFragment.newInstance(CodeConstant.FRAGMENT_TODO)
                val doneFragment = PickOutFragment.newInstance(CodeConstant.FRAGMENT_DONE)

                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            3 -> {
                initToolBar(R.string.label_purchase_returns)

                val todoFragment = BackGoodsFragment.newInstance(CodeConstant.FRAGMENT_TODO)
                val doneFragment = BackGoodsFragment.newInstance(CodeConstant.FRAGMENT_DONE)
                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            4 -> {
                initToolBar(R.string.label_purchase_in_storage)

                val todoFragment = BuyInFragment.newInstance(CodeConstant.FRAGMENT_TODO)
                val doneFragment = BuyInFragment.newInstance(CodeConstant.FRAGMENT_DONE)
                //val doneFragment = BuyInDoneFragment.newInstance("doneFragment")
                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            5 -> {
                initToolBar(R.string.label_produce_in_storage)
                val todoFragment = ProductFragment.newInstance(CodeConstant.FRAGMENT_TODO)
                val doneFragment = ProductFragment.newInstance(CodeConstant.FRAGMENT_DONE)
                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            6 -> {
                initToolBar(R.string.label_send_out_storage)
                val todoFragment = SendOutFragment.newInstance(CodeConstant.FRAGMENT_TODO)
                val doneFragment = SendOutFragment.newInstance(CodeConstant.FRAGMENT_DONE)

                fragmentList.add(todoFragment)
                fragmentList.add(doneFragment)
            }
            7 -> {
                initToolBar(R.string.label_return_sell)
                val todoFragment = ReturnGoodsFragment.newInstance(CodeConstant.FRAGMENT_TODO)
                val doneFragment = ReturnGoodsFragment.newInstance(CodeConstant.FRAGMENT_DONE)

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
        //leftTime.text = "$currentYear-$month-1"
        leftTime.text = String.format("%s-%s-1", currentYear, month)
        rightTime.text = currentTime

        leftTime.setOnClickListener {
            ZBUiUtils.hideInputWindow(it.context, it)
            TimeWidget.showPickDate(it.context) { date, _ ->
                val selectTime = TimeWidget.getTime(CodeConstant.DATE_SIMPLE_Y_M_D, date)
                val rightString = rightTime.text.toString()
                val sapOrderNo = searchView.text.toString()

                val sign = when (signType) {
                    false -> CodeConstant.SIGN_S
                    true -> CodeConstant.SIGN_Y
                }

                val startTime = TimeWidget.StringToDate(selectTime)
                val endTime = TimeWidget.StringToDate(rightString)
                val result = TimeWidget.DateComparison(startTime, endTime)
                if (result) {
                    leftTime.text = selectTime
                    EventBus.getDefault().post(SearchMessage(sign, sapOrderNo, selectTime, rightString))
                } else {
                    ZBUiUtils.showWarning("开始时间不能大于结束时间")
                }
            }
        }

        rightTime.setOnClickListener {
            ZBUiUtils.hideInputWindow(it.context, it)
            TimeWidget.showPickDate(it.context) { date, _ ->
                val selectTime = TimeWidget.getTime(CodeConstant.DATE_SIMPLE_Y_M_D, date)
                val sapOrderNo = searchView.text.toString()
                val leftString = leftTime.text.toString()

                val sign = when (signType) {
                    false -> CodeConstant.SIGN_S
                    true -> CodeConstant.SIGN_Y
                }
                val startTime = TimeWidget.StringToDate(leftString)
                val endTime = TimeWidget.StringToDate(selectTime)
                val result = TimeWidget.DateComparison(startTime, endTime)
                if (result) {
                    rightTime.text = selectTime
                    EventBus.getDefault().post(SearchMessage(sign, sapOrderNo, leftString, selectTime))
                } else {
                    ZBUiUtils.showWarning("结束时间不能小于开始时间")
                }
            }
        }

        searchView.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchString = v.text.toString().trim { it <= ' ' }
                val sign = when (signType) {
                    false -> CodeConstant.SIGN_S
                    true -> CodeConstant.SIGN_Y
                }
                EventBus.getDefault().post(SearchMessage(sign, searchString, "", ""))
                ZBUiUtils.hideInputWindow(v.context, v)
            }
            true
        }

        et_search.setOnTouchListener(View.OnTouchListener { _, event ->
            // compoundDrawables：对应位置 左 0，上 1，右 2，下 3
            val drawable = et_search.compoundDrawables[0]
            if (event.actionMasked == MotionEvent.ACTION_UP) {
                // event.rawX:屏幕上获得触摸的实际位置
                if (event.rawX <= (drawable.bounds.width())) {
                    // 逻辑处理
                    if (signType) {
                        val d = resources.getDrawable(R.mipmap.search_s)
                        d.setBounds(0, 0, 32, 32)
                        et_search.setCompoundDrawables(d, null, null, null)
                        ZBUiUtils.showWarning("请输入生产订单号")
                    } else {
                        val d = resources.getDrawable(R.mipmap.search_y)
                        d.setBounds(0, 0, 32, 32)
                        et_search.setCompoundDrawables(d, null, null, null)
                        ZBUiUtils.showWarning("请输入预留单号")
                    }
                    signType = !signType
                }
                return@OnTouchListener false
            }
            false
        })

    }

}
