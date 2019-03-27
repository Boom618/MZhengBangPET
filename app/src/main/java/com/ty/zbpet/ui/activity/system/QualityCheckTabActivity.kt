package com.ty.zbpet.ui.activity.system

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.SearchMessage
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.fragment.system.QualityCheckFragment
import com.ty.zbpet.util.TabLayoutViewPage
import com.ty.zbpet.util.TimeWidget
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.activity_quality_check_tab.*
import org.greenrobot.eventbus.EventBus
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

        val page = TabLayoutViewPage()
        page.setViewPageToTab(supportFragmentManager, viewpager, stl, mFragments)

    }

    override fun initOneData() {
    }

    override fun initTwoView() {

    }

    override fun onStart() {
        super.onStart()
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

                val startTime = TimeWidget.StringToDate(selectTime)
                val endTime = TimeWidget.StringToDate(rightString)
                val result = TimeWidget.DateComparison(startTime, endTime)
                if (result) {
                    leftTime.text = selectTime
                    EventBus.getDefault().post(SearchMessage("", sapOrderNo, selectTime, rightString))
                } else {
                    ZBUiUtils.showToast("开始时间不能大于结束时间")
                }
            }
        }

        rightTime.setOnClickListener {
            ZBUiUtils.hideInputWindow(it.context, it)
            TimeWidget.showPickDate(it.context) { date, _ ->
                val selectTime = TimeWidget.getTime(CodeConstant.DATE_SIMPLE_Y_M_D, date)
                val sapOrderNo = searchView.text.toString()
                val leftString = leftTime.text.toString()

                val startTime = TimeWidget.StringToDate(leftString)
                val endTime = TimeWidget.StringToDate(selectTime)
                val result = TimeWidget.DateComparison(startTime, endTime)
                if (result) {
                    rightTime.text = selectTime
                    EventBus.getDefault().post(SearchMessage("", sapOrderNo, leftString, selectTime))
                } else {
                    ZBUiUtils.showToast("结束时间不能小于开始时间")
                }
            }
        }

        searchView.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchString = v.text.toString().trim { it <= ' ' }
                EventBus.getDefault().post(SearchMessage("", searchString, "", ""))
                ZBUiUtils.hideInputWindow(v.context, v)
            }
            true
        }
    }

}
