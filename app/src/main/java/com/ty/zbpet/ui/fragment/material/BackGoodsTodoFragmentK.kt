package com.ty.zbpet.ui.fragment.material


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialTodoList
import com.ty.zbpet.presenter.material.BackGoodsPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.base.BaseFragment
import com.ty.zbpet.util.ZBUiUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 * 采购退货 待办列表
 *
 */
class BackGoodsTodoFragmentK : BaseFragment(), MaterialUiListInterface<List<MaterialTodoList>> {


    var refreshLayout: RefreshLayout? = null
    var recyclerView: RecyclerView? = null

    val presenter: BackGoodsPresenter = BackGoodsPresenter(this)

    fun newInstance(tag: String): BackGoodsTodoFragmentK {
        val fragment = BackGoodsTodoFragmentK()
        val bundle = Bundle()
        bundle.putString("someInt", tag)
        fragment.arguments = bundle

        return fragment
    }


    override fun getFragmentLayout(): Int {
        return R.layout.zb_content_list_fragment
    }

    override fun onBaseCreate(view: View): View {

        refreshLayout = view.findViewById<SmartRefreshLayout>(R.id.refreshLayout)
        recyclerView = view.findViewById(R.id.recyclerView)

        // 设置 Header 样式
        refreshLayout!!.setRefreshHeader(MaterialHeader(view.context))
        // 设置 Footer 为 球脉冲 样式
        refreshLayout!!.setRefreshFooter(BallPulseFooter(view.context!!).setSpinnerStyle(SpinnerStyle.Scale))

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.fetchBackTodoList()
    }

    override fun onResume() {
        super.onResume()
        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            refreshLayout.finishRefresh(1000)
            presenter.fetchBackTodoList()
        }

        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
            refreshLayout.finishLoadMore(1000)
            ZBUiUtils.showToast("没有更多数据")
        }
    }

    override fun showMaterial(list: MutableList<List<MaterialTodoList>>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
