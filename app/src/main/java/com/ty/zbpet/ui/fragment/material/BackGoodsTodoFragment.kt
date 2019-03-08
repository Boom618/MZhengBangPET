package com.ty.zbpet.ui.fragment.material

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.material.MaterialList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.material.BackGoodsPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.activity.material.BackGoodsTodoDetailActivity
import com.ty.zbpet.ui.adapter.material.BackGoodsTodoListAdapter
import com.ty.zbpet.ui.base.BaseFragment
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.TimeWidget
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.zb_content_list_fragment.*
import kotlinx.android.synthetic.main.zb_content_list_fragment.view.*

/**
 * @author TY on 2018/11/26.
 */
class BackGoodsTodoFragment : BaseFragment(), MaterialUiListInterface<MaterialList.ListBean> {

    private var refresh = false

    private val presenter = BackGoodsPresenter(this)

    private var adapter: BackGoodsTodoListAdapter? = null

    override val fragmentLayout: Int
        get() = R.layout.zb_content_list_fragment

    override fun onBaseCreate(view: View): View {
        val searchView = activity!!.findViewById<EditText>(R.id.et_search)
        val leftTime = activity!!.findViewById<TextView>(R.id.tv_time_left)
        val rightTime = activity!!.findViewById<TextView>(R.id.tv_time_right)

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
                    presenter.fetchBackTodoList(sapOrderNo,selectTime,rightString)
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
                    presenter.fetchBackTodoList(sapOrderNo,leftString,selectTime)
                } else {
                    ZBUiUtils.showToast("结束时间不能小于开始时间")
                }
            }
        }

        searchView.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchString = v.text.toString().trim { it <= ' ' }
                presenter.fetchBackTodoList(searchString,"","")
                ZBUiUtils.hideInputWindow(v.context, v)
            }
            true
        }

        // 设置 Header 样式
        view.refreshLayout!!.setRefreshHeader(MaterialHeader(view.context))
        // 设置 Footer 为 球脉冲 样式
        //view.refreshLayout!!.setRefreshFooter(BallPulseFooter(view.context).setSpinnerStyle(SpinnerStyle.Scale))
        return view
    }

    override fun loadData() {
        presenter.fetchBackTodoList("","","")
    }

    override fun onResume() {
        super.onResume()

        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            // 刷新数据
            presenter.fetchBackTodoList("","","")
            refresh = true
        }
//        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
//            // 传入 false 表示刷新失败
//            refreshLayout.finishLoadMore(1000)
//            ZBUiUtils.showToast("没有更多数据了")
//        }
    }

    override fun showMaterial(list: List<MaterialList.ListBean>) {

        if (adapter == null || refresh) {
            refresh = false
            if (adapter == null) {
                val manager = LinearLayoutManager(ResourceUtil.getContext())
                recyclerView!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
                recyclerView!!.layoutManager = manager
            }
            adapter = BackGoodsTodoListAdapter(ResourceUtil.getContext(), R.layout.item_material_todo, list)
            recyclerView!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                    val intent = Intent(activity, BackGoodsTodoDetailActivity::class.java)
                    intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                    intent.putExtra("sapFirmNo", list[position].sapFirmNo)
                    intent.putExtra("supplierNo", list[position].supplierNo)
                    intent.putExtra("creatorNo", list[position].creatorNo)
                    intent.putExtra("content", list[position].content)
                    startActivity(intent)
                }

                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                    return false
                }
            })
        }
    }

    override fun showCarSuccess(position: Int, carData: CarPositionNoData?) {
    }

    override fun saveSuccess() {
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showToast(msg)
    }
    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    companion object {

        fun newInstance(tag: String): BackGoodsTodoFragment {
            val fragment = BackGoodsTodoFragment()
            val bundle = Bundle()
            bundle.putString("someInt", tag)
            fragment.arguments = bundle

            return fragment
        }
    }
}
