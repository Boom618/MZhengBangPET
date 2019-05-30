package com.ty.zbpet.ui.fragment.material

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.scwang.smartrefresh.header.MaterialHeader
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseFragment
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.eventbus.SearchMessage
import com.ty.zbpet.bean.material.MaterialList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.presenter.material.SaleOrderPresenter
import com.ty.zbpet.ui.activity.material.SaleDoneDetailActivity
import com.ty.zbpet.ui.activity.material.SaleTodoDetailActivity
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.material.SaleDoneListAdapter
import com.ty.zbpet.ui.adapter.material.SaleTodoListAdapter
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
 * @author TY on 2018/11/26.
 * 销售出库
 */
class SaleOrderFragment : BaseFragment(), MaterialUiListInterface<MaterialList.ListBean> {

    private lateinit var fragmentType: String
    private val presenter = SaleOrderPresenter(this)

    private var adapterTodo: SaleTodoListAdapter? = null
    private var adapterDone: SaleDoneListAdapter? = null

    override val fragmentLayout: Int
        get() = R.layout.zb_content_list_fragment

    override fun onBaseCreate(view: View): View {
        EventBus.getDefault().register(this)

        // 设置 Header 样式
        view.refreshLayout?.setRefreshHeader(MaterialHeader(view.context))

        return view
    }

    override fun loadData() {
        fragmentType = arguments!!.getString(CodeConstant.FRAGMENT_TYPE)!!
        when (fragmentType) {
            CodeConstant.FRAGMENT_TODO -> presenter.getSaleOrderList( "", "", "")
            CodeConstant.FRAGMENT_DONE -> presenter.fetchPickOutDoneList(CodeConstant.SALE_ORDER_TYPE, "", "", "")
        }
    }

    override fun onStart() {
        super.onStart()
        if (isVisble) {
            fragmentType = arguments!!.getString(CodeConstant.FRAGMENT_TYPE)!!
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.getSaleOrderList(  "","", "")
                CodeConstant.FRAGMENT_DONE -> presenter.fetchPickOutDoneList(CodeConstant.SALE_ORDER_TYPE, "", "", "")
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
                CodeConstant.FRAGMENT_TODO -> presenter.getSaleOrderList("", "", "")
                CodeConstant.FRAGMENT_DONE -> presenter.fetchPickOutDoneList(CodeConstant.SALE_ORDER_TYPE, "", "", "")
            }
        }
    }

    override fun showMaterial(list: List<MaterialList.ListBean>) {

        if (list.isEmpty()) {
            ZBUiUtils.showWarning("销售出库没有找到结果")
            return
        }
        LayoutInit.initLayoutManager(ResourceUtil.getContext(), recyclerView)
        if (adapterTodo == null && adapterDone == null) {
            recyclerView.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
        }
        when (fragmentType) {
            CodeConstant.FRAGMENT_TODO -> {
                adapterTodo = SaleTodoListAdapter(this.context!!, R.layout.item_material_todo, list)
                recyclerView.adapter = adapterTodo

                adapterTodo?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                        val intent = Intent(activity, SaleTodoDetailActivity::class.java)
                        intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                        intent.putExtra("sapFirmNo", list[position].sapFirmNo)
                        intent.putExtra("supplierNo", list[position].supplierNo)
                        intent.putExtra("supplierName", list[position].supplierName)
                        intent.putExtra("content", list[position].content)
                        intent.putExtra("sign", list[position].sign)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                        return false
                    }
                })
            }
            CodeConstant.FRAGMENT_DONE -> {
                adapterDone = SaleDoneListAdapter(this.context!!, R.layout.activity_content_list_three, list)
                recyclerView.adapter = adapterDone

                adapterDone?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                        val intent = Intent(activity, SaleDoneDetailActivity::class.java)
                        intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                        intent.putExtra("warehouseId", list[position].warehouseId)
                        intent.putExtra("orderId", list[position].orderId)
                        intent.putExtra("sign", list[position].sign)
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
            //val sign = event.sign()
            val search = event.getSearch()
            val startTime = event.leftTime()
            val endTime = event.rightTime()
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.getSaleOrderList(search, startTime, endTime)
                CodeConstant.FRAGMENT_DONE -> presenter.fetchPickOutDoneList(CodeConstant.SALE_ORDER_TYPE, "", "", "")
            }
        }
    }

    override fun showCarSuccess(position: Int, carData: CarPositionNoData) {
    }

    override fun saveSuccess() {
    }

    override fun showError(msg: String) {
        ZBUiUtils.showError(msg)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
        EventBus.getDefault().unregister(this)
    }

    companion object {
        fun newInstance(type: String): SaleOrderFragment {
            val fragment = SaleOrderFragment()
            val bundle = Bundle()
            bundle.putString(CodeConstant.FRAGMENT_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }
}
