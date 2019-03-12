//package com.ty.zbpet.ui.fragment.material
//
//import android.content.Intent
//import android.support.v7.widget.RecyclerView
//import android.view.View
//import com.scwang.smartrefresh.header.MaterialHeader
//import com.ty.zbpet.R
//import com.ty.zbpet.bean.CarPositionNoData
//import com.ty.zbpet.bean.SearchMessage
//import com.ty.zbpet.bean.material.MaterialList
//import com.ty.zbpet.constant.CodeConstant
//import com.ty.zbpet.presenter.material.MaterialPresenter
//import com.ty.zbpet.presenter.material.MaterialUiListInterface
//import com.ty.zbpet.ui.activity.material.ArrivalInDoneDetailActivity
//import com.ty.zbpet.ui.adapter.LayoutInit
//import com.ty.zbpet.ui.adapter.material.MaterialDoneAdapter
//import com.ty.zbpet.ui.base.BaseFragment
//import com.ty.zbpet.ui.widght.SpaceItemDecoration
//import com.ty.zbpet.util.ResourceUtil
//import com.ty.zbpet.util.ZBUiUtils
//import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
//import kotlinx.android.synthetic.main.fragment_recyclerview.*
//import kotlinx.android.synthetic.main.fragment_recyclerview.view.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//
///**
// * 原辅料——到货入库——已办
// *
// * @author TY
// */
//class MaterialDoneFragment : BaseFragment(),
//        MaterialUiListInterface<MaterialList.ListBean> {
//
//    override val fragmentLayout: Int
//        get() = R.layout.fragment_recyclerview
//
//    /**
//     * 下拉刷新 flag
//     */
//    private var refresh = false
//
//    private var materialAdapter: MaterialDoneAdapter? = null
//
//    private val materialPresenter = MaterialPresenter(this)
//
//
//    override fun onBaseCreate(view: View): View {
//        EventBus.getDefault().register(this)
//        view.refreshLayout!!.setRefreshHeader(MaterialHeader(view.context!!))
//        //设置 Footer 为 球脉冲 样式
//        //view.refreshLayout!!.setRefreshFooter(BallPulseFooter(view.context!!).setSpinnerStyle(SpinnerStyle.Scale))
//        return view
//    }
//
//    override fun loadData() {
//        // 第一次获取数据
//        materialPresenter.fetchDoneMaterial(CodeConstant.BUY_IN_TYPE,"","","")
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        /**设置 Header
//         * BezierRadarHeader  贝塞尔雷达
//         * WaterDropHeader 苹果水滴
//         * DeliveryHeader 交货
//         *
//         * MaterialHeader 材料 5.0 样式
//         * StoreHouseHeader 跑马灯
//         */
//        refreshLayout!!.setOnRefreshListener { refreshLayout ->
//            // 传入 false 表示刷新失败
//            refreshLayout.finishRefresh(1000)
//            // 刷新数据
//            materialPresenter.fetchDoneMaterial(CodeConstant.BUY_IN_TYPE,"","","")
//            refresh = true
//        }
////        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
////            // 传入 false 表示刷新失败
////            refreshLayout.finishLoadMore(1000)
////            ZBUiUtils.showToast("没有更多数据了")
////        }
//    }
//
//
//    override fun showMaterial(list: List<MaterialList.ListBean>) {
//        if (list.isEmpty()) {
//            ZBUiUtils.showToast("采购冲销没有找到结果")
//        }
//        if (materialAdapter == null || refresh) {
//            refresh = false
//            if (materialAdapter == null) {
//                LayoutInit.initLayoutManager(ResourceUtil.getContext(),recyclerView)
//                recyclerView.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
//            }
//
//            materialAdapter = MaterialDoneAdapter(this.context!!, R.layout.activity_content_list_three, list)
//            recyclerView!!.adapter = materialAdapter
//            materialAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
//                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
//                    val intent = Intent(activity, ArrivalInDoneDetailActivity::class.java)
//                    intent.putExtra("mInWarehouseOrderId", list[position].mInWarehouseOrderId)
//                    intent.putExtra("sapOrderNo", list[position].sapOrderNo)
//                    intent.putExtra("supplierName", list[position].supplierName)
//                    intent.putExtra("warehouseId", list[position].warehouseId)
//                    intent.putExtra("orderId", list[position].orderId)
//                    startActivity(intent)
//                }
//
//                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
//                    return false
//                }
//            })
//        }
//    }
//
//    /**
//     * 搜索,开始时间,结束时间
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onEventMainThread(event: SearchMessage) {
//        if (isVisble) {
//            refresh = true
//            val search = event.getSearch()
//            val startTime = event.leftTime()
//            val endTime = event.rightTime()
//            materialPresenter.fetchDoneMaterial(CodeConstant.BUY_IN_TYPE,search,startTime,endTime)
//        }
//    }
//    override fun showCarSuccess(position: Int, carData: CarPositionNoData?) {
//    }
//
//    override fun saveSuccess() {
//    }
//
//    override fun showError(msg: String?) {
//        ZBUiUtils.showToast(msg)
//    }
//
//    override fun showLoading() {
//
//    }
//
//    override fun hideLoading() {
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        materialPresenter.dispose()
//        EventBus.getDefault().unregister(this)
//    }
//}
