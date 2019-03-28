package com.ty.zbpet.ui.fragment.product

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.scwang.smartrefresh.header.MaterialHeader
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.SearchMessage
import com.ty.zbpet.bean.product.ProductList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.product.BuyInPresenter
import com.ty.zbpet.presenter.product.ProductUiListInterface
import com.ty.zbpet.ui.activity.product.BuyInDoneDetailActivity
import com.ty.zbpet.ui.activity.product.BuyInTodoDetailActivity
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.product.BuyInDoneListAdapter
import com.ty.zbpet.ui.adapter.product.BuyInTodoListAdapter
import com.ty.zbpet.ui.base.BaseFragment
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
 * 成品——外采入库——未办
 *
 * @author TY
 */
class BuyInFragment : BaseFragment(), ProductUiListInterface<ProductList.ListBean> {


    private val presenter = BuyInPresenter(this)

    private var adapterTodo: BuyInTodoListAdapter? = null
    private var adapterDone: BuyInDoneListAdapter? = null
    private lateinit var fragmentType: String

    override val fragmentLayout: Int
        get() = R.layout.zb_content_list_fragment

    override fun onBaseCreate(view: View): View {
        EventBus.getDefault().register(this)
        // 设置 Header 样式
        view.refreshLayout!!.setRefreshHeader(MaterialHeader(context!!))
        // 设置 Footer 为 球脉冲 样式
        //view.refreshLayout!!.setRefreshFooter(BallPulseFooter(context!!).setSpinnerStyle(SpinnerStyle.Scale))
        return view
    }

    override fun loadData() {
        fragmentType = arguments!!.getString(CodeConstant.FRAGMENT_TYPE)!!
        when (fragmentType) {
            CodeConstant.FRAGMENT_TODO -> presenter.fetchBuyInTodoList("", "", "")
            CodeConstant.FRAGMENT_DONE -> presenter.fetchBuyInDoneList(CodeConstant.BUY_IN_TYPE, "", "", "")
        }
    }

    override fun onResume() {
        super.onResume()

        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.fetchBuyInTodoList("", "", "")
                CodeConstant.FRAGMENT_DONE -> presenter.fetchBuyInDoneList(CodeConstant.BUY_IN_TYPE, "", "", "")
            }
            // 刷新数据
            //presenter.fetchBuyInTodoList()
        }
//        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
//            // 传入 false 表示刷新失败
//            refreshLayout.finishLoadMore(1000)
//            ZBUiUtils.showToast("没有更多数据了")
//        }
    }

    override fun showProduct(list: List<ProductList.ListBean>) {
        if (list.isEmpty()) {
            ZBUiUtils.showToast("外采入库没有找到结果")
        }

        LayoutInit.initLayoutManager(ResourceUtil.getContext(), recyclerView)
        if (adapterTodo == null && adapterDone== null) {
            recyclerView!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        }
        when (fragmentType) {
            CodeConstant.FRAGMENT_TODO -> {
                adapterTodo = BuyInTodoListAdapter(ResourceUtil.getContext(), R.layout.item_material_todo, list)
                recyclerView!!.adapter = adapterTodo

                adapterTodo!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                        val intent = Intent(activity, BuyInTodoDetailActivity::class.java)
                        intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                        intent.putExtra("supplierId", list[position].supplierId)
                        intent.putExtra("sapFirmNo", list[position].sapFirmNo)
                        intent.putExtra("supplierName", list[position].supplierName)
                        intent.putExtra("content", list[position].content)
                        intent.putExtra("supplierNo", list[position].supplierNo)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                        return false
                    }
                })
            }
            CodeConstant.FRAGMENT_DONE -> {
                adapterDone = BuyInDoneListAdapter(ResourceUtil.getContext(), R.layout.activity_content_list_two, list)
                recyclerView!!.adapter = adapterDone

                adapterDone?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                        val intent = Intent(activity, BuyInDoneDetailActivity::class.java)
                        intent.putExtra("orderId", list[position].id)
                        intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                        return false
                    }
                })
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: SearchMessage) {
        if (isVisble) {
            val search = event.getSearch()
            val startTime = event.leftTime()
            val endTime = event.rightTime()
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.fetchBuyInTodoList(search, startTime, endTime)
                CodeConstant.FRAGMENT_DONE -> presenter.fetchBuyInDoneList(CodeConstant.BUY_IN_TYPE, search, startTime, endTime)
            }
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun saveSuccess() {
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showToast(msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        presenter.dispose()
    }

    companion object {

        fun newInstance(type: String): BuyInFragment {
            val fragment = BuyInFragment()
            val bundle = Bundle()
            bundle.putString(CodeConstant.FRAGMENT_TYPE, type)
            fragment.arguments = bundle

            return fragment
        }
    }
}
