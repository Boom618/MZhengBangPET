package com.ty.zbpet.ui.fragment.system

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.scwang.smartrefresh.header.MaterialHeader
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.ErrorMessage
import com.ty.zbpet.bean.eventbus.SearchMessage
import com.ty.zbpet.bean.material.MaterialList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.system.SystemPresenter
import com.ty.zbpet.presenter.system.SystemUiListInterface
import com.ty.zbpet.ui.activity.system.QualityCheckDoneDetailActivity
import com.ty.zbpet.ui.activity.system.QualityCheckTodoDetailActivity
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.system.QuaCheckDoneListAdapter
import com.ty.zbpet.ui.adapter.system.QuaCheckTodoListAdapter
import com.ty.zbpet.base.BaseFragment
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.zb_content_list_fragment.*
import kotlinx.android.synthetic.main.zb_content_list_fragment.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 质检——列表
 *
 * @author TY
 */
class QualityCheckFragment : BaseFragment(), SystemUiListInterface<MaterialList.ListBean> {


    private val presenter = SystemPresenter(this)

    private var adapterTodo: QuaCheckTodoListAdapter? = null
    private var adapterDone: QuaCheckDoneListAdapter? = null

    private lateinit var fragmentType: String

    override val fragmentLayout: Int
        get() = R.layout.zb_content_list_fragment

    override fun onBaseCreate(view: View): View {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        view.recyclerView.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))

        // 设置 Header 样式
        view.refreshLayout.setRefreshHeader(MaterialHeader(view.context))
        // 设置 Footer 为 球脉冲 样式
        //view.refreshLayout!!.setRefreshFooter(BallPulseFooter(view.context).setSpinnerStyle(SpinnerStyle.Scale))
        return view
    }

    override fun loadData() {
        fragmentType = arguments!!.getString(CodeConstant.FRAGMENT_TYPE)!!
        when (fragmentType) {
            //CodeConstant.FRAGMENT_TODO -> presenter.fetchQualityCheckTodoList("", "", "")
            CodeConstant.FRAGMENT_DONE -> presenter.fetchQualityCheckDoneList("", "", "")
        }
    }

    override fun onStart() {
        super.onStart()
        if (isVisble) {
            fragmentType = arguments!!.getString(CodeConstant.FRAGMENT_TYPE)!!
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.fetchQualityCheckTodoList("", "", "")
                CodeConstant.FRAGMENT_DONE -> presenter.fetchQualityCheckDoneList("", "", "")
            }
        }
    }

    override fun onResume() {
        super.onResume()

        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            // 刷新数据
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.fetchQualityCheckTodoList("", "", "")
                CodeConstant.FRAGMENT_DONE -> presenter.fetchQualityCheckDoneList("", "", "")
            }
        }
//        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
//            // 传入 false 表示刷新失败
//            refreshLayout.finishLoadMore(1000)
//            ZBUiUtils.showSuccess("没有更多数据了")
//        }
    }

    override fun showSystem(list: MutableList<MaterialList.ListBean>) {

        LayoutInit.initLayoutManager(ResourceUtil.getContext(), recyclerView)
        if (list.isEmpty()) {
            ZBUiUtils.showWarning("质检没有找到结果")
        }

        when (fragmentType) {
            CodeConstant.FRAGMENT_TODO -> {
                adapterTodo = QuaCheckTodoListAdapter(ResourceUtil.getContext(), R.layout.item_quality_list, list)
                recyclerView.adapter = adapterTodo
                adapterTodo?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
                        val intent = Intent(activity, QualityCheckTodoDetailActivity::class.java)
                        intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                        intent.putExtra("sapFirmNo", list[position].sapFirmNo)
                        intent.putExtra("supplierNo", list[position].supplierNo)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int): Boolean {
                        return false
                    }
                })
            }
            CodeConstant.FRAGMENT_DONE -> {
                adapterDone = QuaCheckDoneListAdapter(ResourceUtil.getContext(), R.layout.item_quality_list, list)
                recyclerView.adapter = adapterDone

                adapterDone?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
                        val intent = Intent(activity, QualityCheckDoneDetailActivity::class.java)
                        intent.putExtra("id", list[position].id)
                        intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                        intent.putExtra("supplierName", list[position].supplierName)
                        intent.putExtra("warehouseId", list[position].warehouseId)
                        intent.putExtra("orderId", list[position].orderId)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int): Boolean {
                        return false
                    }
                })
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ErrorEvnet(event: ErrorMessage) {
        ZBUiUtils.showError(event.error())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: SearchMessage) {
        if (isVisble) {
            val search = event.getSearch()
            val startTime = event.leftTime()
            val endTime = event.rightTime()
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.fetchQualityCheckTodoList(search, startTime, endTime)
                CodeConstant.FRAGMENT_DONE -> presenter.fetchQualityCheckDoneList(search, startTime, endTime)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
        EventBus.getDefault().unregister(this)
    }

    companion object {

        fun newInstance(type: String): QualityCheckFragment {
            val fragment = QualityCheckFragment()
            val bundle = Bundle()
            bundle.putString(CodeConstant.FRAGMENT_TYPE, type)
            fragment.arguments = bundle

            return fragment
        }
    }
}
