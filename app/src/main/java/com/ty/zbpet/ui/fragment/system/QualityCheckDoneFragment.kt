//package com.ty.zbpet.ui.fragment.system
//
//import android.content.Intent
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.view.View
//import com.scwang.smartrefresh.header.MaterialHeader
//import com.scwang.smartrefresh.layout.constant.SpinnerStyle
//import com.scwang.smartrefresh.layout.footer.BallPulseFooter
//import com.ty.zbpet.R
//import com.ty.zbpet.bean.system.QualityCheckTodoList
//import com.ty.zbpet.presenter.system.SystemPresenter
//import com.ty.zbpet.presenter.system.SystemUiListInterface
//import com.ty.zbpet.ui.activity.system.QualityCheckDoneDetailActivity
//import com.ty.zbpet.ui.adapter.system.QuaCheckDoneListAdapter
//import com.ty.zbpet.ui.base.BaseFragment
//import com.ty.zbpet.ui.widght.SpaceItemDecoration
//import com.ty.zbpet.util.ResourceUtil
//import com.ty.zbpet.util.ZBUiUtils
//import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
//import kotlinx.android.synthetic.main.zb_content_list_fragment.*
//import kotlinx.android.synthetic.main.zb_content_list_fragment.view.*
//
///**
// * 质检——已办列表
// *
// * @author TY
// */
//class QualityCheckDoneFragment : BaseFragment(), SystemUiListInterface<QualityCheckTodoList.DataBean> {
//
//
//    private val presenter = SystemPresenter()
//
//    private var adapter: QuaCheckDoneListAdapter? = null
//
//    override val fragmentLayout: Int
//        get() = R.layout.zb_content_list_fragment
//
//    override fun onBaseCreate(view: View): View {
//        // 设置 Header 样式
//        view.refreshLayout!!.setRefreshHeader(MaterialHeader(context!!))
//        // 设置 Footer 为 球脉冲 样式
//        view.refreshLayout!!.setRefreshFooter(BallPulseFooter(context!!).setSpinnerStyle(SpinnerStyle.Scale))
//        return view
//    }
//
//    override fun loadData() {
//        presenter.fetchQualityCheckDoneList()
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        refreshLayout!!.setOnRefreshListener { refreshLayout ->
//            // 传入 false 表示刷新失败
//            refreshLayout.finishRefresh(1000)
//            // 刷新数据
//            presenter.fetchQualityCheckDoneList()
//        }
//        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
//            // 传入 false 表示刷新失败
//            refreshLayout.finishLoadMore(1000)
//            ZBUiUtils.showToast("没有更多数据了")
//        }
//    }
//
//    override fun showSystem(list: List<QualityCheckTodoList.DataBean>) {
//
//        if (adapter == null) {
//            val manager = LinearLayoutManager(ResourceUtil.getContext())
//            recyclerView!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
//            recyclerView!!.layoutManager = manager
//            adapter = QuaCheckDoneListAdapter(ResourceUtil.getContext(), R.layout.item_quality_list, list)
//            recyclerView!!.adapter = adapter
//
//            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
//                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
//                    val intent = Intent(activity, QualityCheckDoneDetailActivity::class.java)
//                    intent.putExtra("arrivalOrderNo", list[position].arrivalOrderNo)
//                    startActivity(intent)
//                }
//
//                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
//                    return false
//                }
//            })
//        } else {
//            adapter!!.notifyDataSetChanged()
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        presenter.dispose()
//    }
//
//    companion object {
//
//        fun newInstance(tag: String): QualityCheckDoneFragment {
//            val fragment = QualityCheckDoneFragment()
//            val bundle = Bundle()
//            bundle.putString("someInt", tag)
//            fragment.arguments = bundle
//
//            return fragment
//        }
//    }
//}
