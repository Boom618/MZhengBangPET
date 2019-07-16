package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.google.gson.Gson
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import com.ty.zbpet.bean.eventbus.system.FragmentScanEvent
import com.ty.zbpet.bean.system.PositionCode
import com.ty.zbpet.bean.system.PositionQuery
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.move.ComplexInterface
import com.ty.zbpet.presenter.move.MovePresenter
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.wareroom.SourceAdapter
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import kotlinx.android.synthetic.main.fragment_move_source.*
import kotlinx.android.synthetic.main.item_move_source_frg.view.*
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author TY on 2019/4/24.
 * 源库位
 */
class SourceLocationFrg : BaseSupFragment(), ComplexInterface<PositionQuery> {

    private var adapter: SourceAdapter? = null
    private var warehouseNo = ""
    private var positionNo = ""
    private var list = mutableListOf<PositionQuery>()
    private val presenter: MovePresenter = MovePresenter(this)

    override val fragmentLayout: Int
        get() = R.layout.fragment_move_source

    override fun onBaseCreate(view: View): View {
        EventBus.getDefault().register(this)

        return view
    }

    override fun onStart() {
        super.onStart()
        initToolBar(R.string.move_room_source, TipString.submit, View.OnClickListener { _ ->
            initReqBody()?.let { presenter.materialMoveOrder(it) }
        })
    }

    private fun initReqBody(): RequestBody? {
        val data = PositionCode()
        val listBean = mutableListOf<PositionCode.StockListBean>()
        val size = list.size
        for (i in 0 until size) {
            val view = recycler_source.getChildAt(i)
            val number = view?.edit_number?.text.toString().trim()
            if (!TextUtils.isEmpty(number) && number != "null") {
                val bean = PositionCode.StockListBean()

                bean.unit = list[i].unit
//                bean.stockId = list[i].stockId
//                bean.sapOrderNo = list[i].sapOrderNo
                bean.positionNo = list[i].positionNo
                bean.supplierNo = list[i].supplierNo
                bean.sapBatchNo = list[i].sapBatchNo
                bean.supplierName = list[i].supplierName
                bean.moveNumber = number
                bean.materialNo = list[i].materialNo
                bean.materialName = list[i].materialName
                bean.concentration = list[i].concentration
                listBean.add(bean)
            }
        }
        if (listBean.size == 0) {
            ZBUiUtils.showWarning(TipString.moveSelect)
            return null
        }
        data.list = listBean
        data.positionNo = positionNo
        data.warehouseNo = warehouseNo

        val json = Gson().toJson(data)
        return RequestBodyJson.requestBody(json)

    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        ZBUiUtils.showSuccess(TipString.scanLocationSource)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun FragmentMsg(event: FragmentScanEvent) {
        val type = event.getType()
        if (type.equals("source")) {
            val positionNo = event.getPositionNo()
            presenter.positionStock(positionNo)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.dispose()
    }

    override fun showListData(lists: MutableList<PositionQuery>) {
        // 接收查询的库位码信息
        list = lists
        lists[0].warehouseNo?.let { warehouseNo = it }
        lists[0].positionNo?.let { positionNo = it }
        context?.let { LayoutInit.initLayoutManager(it, recycler_source) }
        recycler_source.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))

        adapter = context?.let { SourceAdapter(it, R.layout.item_move_source_frg, list) }
        recycler_source.adapter = adapter

    }

    override fun showObjData(obj: PositionQuery) {
        // 接收查询的库位码信息
//        list = obj.list!!
//        warehouseNo = obj.warehouseNo
//        positionNo = obj.positionNo
//        context?.let { LayoutInit.initLayoutManager(it, recycler_source) }
//        recycler_source.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
//
//        adapter = context?.let { SourceAdapter(it, R.layout.item_move_source_frg, list) }
//        recycler_source.adapter = adapter
    }

    override fun responseSuccess() {
        pop()
        ZBUiUtils.showSuccess(TipString.moveSuccess)
    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(_mActivity, TipString.moveHouseIng)
    }

    override fun hideLoading() {
        dialog?.close()
    }


    override fun showError(msg: String) {
        ZBUiUtils.showError(msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    companion object {
        fun newInstance(): SourceLocationFrg {
            val fragment = SourceLocationFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}