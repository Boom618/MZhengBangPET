package com.ty.zbpet.ui.fragment.product

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.product.ProductUiObjInterface
import com.ty.zbpet.presenter.product.ReturnPresenter
import com.ty.zbpet.ui.activity.product.ReturnGoodsDoneDetailActivity
import com.ty.zbpet.ui.adapter.product.ReturnGoodsDoneListAdapter
import com.ty.zbpet.ui.base.BaseFragment
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.zb_content_list_fragment.*
import kotlinx.android.synthetic.main.zb_content_list_fragment.view.*

/**
 * 成品——退货入库——已办
 *
 * @author TY
 */
class ReturnGoodsDoneFragment : BaseFragment(), ProductUiObjInterface<ProductList> {


    private val presenter = ReturnPresenter(this)

    private var adapter: ReturnGoodsDoneListAdapter? = null

    /**
     * 下拉刷新 flag
     */
    private var refresh = false

    override val fragmentLayout: Int
        get() = R.layout.zb_content_list_fragment

    override fun onBaseCreate(view: View): View {
        // 设置 Header 样式
        view.refreshLayout.setRefreshHeader(MaterialHeader(view.context))
        // 设置 Footer 为 球脉冲 样式
        view.refreshLayout.setRefreshFooter(BallPulseFooter(view.context).setSpinnerStyle(SpinnerStyle.Scale))
        return view
    }

    override fun onStart() {
        super.onStart()

        presenter.fetchReturnGoodsDoneList(CodeConstant.RETURN_TYPE)

    }

    override fun onResume() {
        super.onResume()

        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            // 刷新数据
            presenter.fetchReturnGoodsDoneList(CodeConstant.PRODUCT_TYPE)
            refresh = true
        }
        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishLoadMore(1000)
            ZBUiUtils.showToast("没有更多数据了")
        }
    }

    override fun detailObjData(obj: ProductList) {

        val list = obj.list

        if (adapter == null || refresh) {
            refresh = false
            if (adapter == null) {
                val manager = LinearLayoutManager(ResourceUtil.getContext())
                recyclerView.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
                recyclerView!!.layoutManager = manager
            }
            adapter = ReturnGoodsDoneListAdapter(ResourceUtil.getContext(), R.layout.item_send_out_list_todo, list!!)
            recyclerView!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                    val intent = Intent(activity, ReturnGoodsDoneDetailActivity::class.java)
                    intent.putExtra("orderId", list!![position].id)
                    intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                    startActivity(intent)
                }

                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                    return false
                }
            })

        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    companion object {

        fun newInstance(tag: String): ReturnGoodsDoneFragment {
            val fragment = ReturnGoodsDoneFragment()
            val bundle = Bundle()
            bundle.putString("someInt", tag)
            fragment.arguments = bundle

            return fragment
        }
    }

}
