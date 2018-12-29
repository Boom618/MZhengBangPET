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
import com.ty.zbpet.bean.product.ProductDoneList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.product.BuyInPresenter
import com.ty.zbpet.presenter.product.ProductUiObjInterface
import com.ty.zbpet.ui.activity.product.BuyInDoneDetailActivity
import com.ty.zbpet.ui.adapter.product.BuyInDoneListAdapter
import com.ty.zbpet.ui.base.BaseFragment
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.zb_content_list_fragment.*
import kotlinx.android.synthetic.main.zb_content_list_fragment.view.*

/**
 * 成品——外采入库——已办
 * @author TY
 */
class BuyInDoneFragment : BaseFragment(), ProductUiObjInterface<ProductDoneList> {


    private val presenter = BuyInPresenter(this)

    private var adapter: BuyInDoneListAdapter? = null

    /**
     * 下拉刷新 flag
     */
    private var refresh = false

    override val fragmentLayout: Int
        get() = R.layout.zb_content_list_fragment

    override fun onBaseCreate(view: View): View {
        // 设置 Header 样式
        view.refreshLayout!!.setRefreshHeader(MaterialHeader(view.context))
        // 设置 Footer 为 球脉冲 样式
        view.refreshLayout!!.setRefreshFooter(BallPulseFooter(view.context).setSpinnerStyle(SpinnerStyle.Scale))
        return view
    }

    override fun onStart() {
        super.onStart()

        presenter.fetchBuyInDoneList(CodeConstant.BUY_IN_TYPE)

    }

    override fun onResume() {
        super.onResume()

        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            // 刷新数据
            presenter.fetchBuyInDoneList(CodeConstant.BUY_IN_TYPE)
            refresh = true
        }
        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishLoadMore(1000)
            ZBUiUtils.showToast("没有更多数据了")
        }
    }

    override fun detailObjData(obj: ProductDoneList) {

        val list = obj.list

        if (adapter == null || refresh) {
            refresh = false
            if (adapter == null) {
                val manager = LinearLayoutManager(ResourceUtil.getContext())
                recyclerView!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
                recyclerView!!.layoutManager = manager
            }
            adapter = BuyInDoneListAdapter(ResourceUtil.getContext(), R.layout.activity_content_list_two, list)
            recyclerView!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                    val intent = Intent(activity, BuyInDoneDetailActivity::class.java)
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

        fun newInstance(tag: String): BuyInDoneFragment {
            val fragment = BuyInDoneFragment()
            val bundle = Bundle()
            bundle.putString("someInt", tag)
            fragment.arguments = bundle

            return fragment
        }
    }

}