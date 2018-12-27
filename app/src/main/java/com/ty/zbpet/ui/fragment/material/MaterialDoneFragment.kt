package com.ty.zbpet.ui.fragment.material

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDoneList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.activity.material.ArrivalInDoneDetailActivity
import com.ty.zbpet.ui.adapter.material.MaterialDoneAdapter
import com.ty.zbpet.ui.base.BaseFragment
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.fragment_recyclerview.view.*

/**
 * 原辅料——到货入库——已办
 *
 * @author TY
 */
class MaterialDoneFragment : BaseFragment(), MaterialUiListInterface<MaterialDoneList.ListBean> {
    override val fragmentLayout: Int
        get() = R.layout.fragment_recyclerview

    /**
     * 下拉刷新 flag
     */
    private var refresh = false

    private var materialAdapter: MaterialDoneAdapter? = null

    private val materialPresenter = MaterialPresenter(this)


    override fun onBaseCreate(view: View): View {
        view.refreshLayout!!.setRefreshHeader(MaterialHeader(view.context!!))
        //设置 Footer 为 球脉冲 样式
        view.refreshLayout!!.setRefreshFooter(BallPulseFooter(view.context!!).setSpinnerStyle(SpinnerStyle.Scale))
        return view
    }

    override fun onStart() {
        super.onStart()

        // 第一次获取数据
        materialPresenter.fetchDoneMaterial(CodeConstant.BUY_IN_TYPE)

    }

    override fun onResume() {
        super.onResume()

        /**设置 Header
         * BezierRadarHeader  贝塞尔雷达
         * WaterDropHeader 苹果水滴
         * DeliveryHeader 交货
         *
         * MaterialHeader 材料 5.0 样式
         * StoreHouseHeader 跑马灯
         */
        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            // 刷新数据
            materialPresenter.fetchDoneMaterial(CodeConstant.BUY_IN_TYPE)
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

            materialAdapter = MaterialDoneAdapter(this.context!!, R.layout.activity_content_list_three, list)
            recyclerView!!.adapter = materialAdapter
            materialAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                    val intent = Intent(activity, ArrivalInDoneDetailActivity::class.java)
                    //                    Intent intent = new Intent(getActivity(), ArrivalInDoneDetailActivityK.class);
                    intent.putExtra("mInWarehouseOrderId", list[position].mInWarehouseOrderId)
                    intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                    intent.putExtra("warehouseId", list[position].warehouseId)
                    intent.putExtra("orderId", list[position].orderId)
                    startActivity(intent)
                }

                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                    return false
                }
            })
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}
