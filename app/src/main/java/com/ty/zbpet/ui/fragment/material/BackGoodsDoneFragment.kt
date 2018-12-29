package com.ty.zbpet.ui.fragment.material

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDoneList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.material.BackGoodsPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.activity.material.BackGoodsDoneDetailActivity
import com.ty.zbpet.ui.adapter.material.BackGoodsDoneListAdapter
import com.ty.zbpet.ui.base.BaseFragment
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.fragment_recyclerview.view.*

/**
 * @author TY on 2018/11/26.
 *
 *
 * 采购退货 已办列表
 */
class BackGoodsDoneFragment : BaseFragment(), MaterialUiListInterface<MaterialDoneList.ListBean> {

    private var materialAdapter: BackGoodsDoneListAdapter? = null

    /**
     * 下拉刷新 flag
     */
    private var refresh = false


    private val presenter = BackGoodsPresenter(this)

    override val fragmentLayout: Int
        get() = R.layout.fragment_recyclerview

    override fun onBaseCreate(view: View): View {

        view.refreshLayout!!.setRefreshHeader(MaterialHeader(view.context))
        //设置 Footer 为 球脉冲 样式
        view.refreshLayout!!.setRefreshFooter(BallPulseFooter(view.context).setSpinnerStyle(SpinnerStyle.Scale))

        return view
    }


    override fun onStart() {
        super.onStart()

        // 第一次获取数据
        presenter.fetchBackDoneList(CodeConstant.BACK_GOODS_TYPE)

    }

    override fun onResume() {
        super.onResume()

        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            // 刷新数据
            presenter.fetchBackDoneList(CodeConstant.BACK_GOODS_TYPE)
            refresh = true
        }
        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishLoadMore(1000)
            ZBUiUtils.showToast("没有更多数据了")
        }
    }


    override fun showMaterial(list: List<MaterialDoneList.ListBean>) {
        if (materialAdapter == null || refresh) {
            refresh = false
            if (materialAdapter == null) {
                val manager = LinearLayoutManager(ResourceUtil.getContext())
                recyclerView!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
                recyclerView!!.layoutManager = manager
            }
            materialAdapter = BackGoodsDoneListAdapter(this.context!!, R.layout.activity_content_list_three, list)
            recyclerView!!.adapter = materialAdapter
            materialAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                    val intent = Intent(activity, BackGoodsDoneDetailActivity::class.java)
                    intent.putExtra("mOutWarehouseOrderId", list[position].mOutWarehouseOrderId)
                    intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                    intent.putExtra("warehouseId", list[position].warehouseId)
                    intent.putExtra("orderId", list[position].orderId)
                    startActivity(intent)
                }

                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                    return false
                }
            })
        } else {
            // 刷新列表
            materialAdapter!!.notifyDataSetChanged()
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    companion object {

        fun newInstance(tag: String): BackGoodsDoneFragment {
            val fragment = BackGoodsDoneFragment()
            val bundle = Bundle()
            bundle.putString("someInt", tag)
            fragment.arguments = bundle

            return fragment
        }
    }

}