package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.gson.Gson
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import com.ty.zbpet.bean.eventbus.system.FragmentScanEvent
import com.ty.zbpet.bean.system.PositionCode
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.move.ComplexInterface
import com.ty.zbpet.presenter.move.MovePresenter
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.wareroom.SourceAdapter
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.fragment_move_source.*
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author TY on 2019/4/24.
 * 源库位
 */
class SourceLocationFrg : BaseSupFragment(), ComplexInterface<PositionCode> {

    private var adapter: SourceAdapter? = null
    private var warehouseNo = ""
    private var positionNo = ""
    private var list = mutableListOf<PositionCode.StockListBean>()
    private val presenter: MovePresenter = MovePresenter(this)

    override val fragmentLayout: Int
        get() = R.layout.fragment_move_source

    override fun onBaseCreate(view: View): View {
        EventBus.getDefault().register(this)

        return view
    }

    override fun onStart() {
        super.onStart()
        initToolBar(R.string.move_room_source, TipString.submit, View.OnClickListener {
            createOrder(initReqBody())
        })
    }

    private fun initReqBody(): RequestBody? {
        val data = PositionCode()
        val listBean = mutableListOf<PositionCode.StockListBean>()
        val size = list.size
        for (i in 0 until size) {
            val view = recycler_source.getChildAt(i)
            val number = view.findViewById<EditText>(R.id.edit_number).text.toString().trim()
            if (number.isNotEmpty()) {
                val bean = PositionCode.StockListBean()

                bean.unit = list[i].unit
                bean.stockId = list[i].stockId
                bean.sapOrderNo = list[i].sapOrderNo
                bean.positionNo = list[i].positionNo
                bean.supplierNo = list[i].supplierNo
                bean.sapBatchNo = list[i].sapBatchNo
                bean.supplierName = list[i].supplierName
                bean.moveNumber = number
                bean.materialNo = list[i].materialNo
                bean.materialName = list[i].materialName
                bean.concentration = list[i].concentration
                listBean.add(bean)
            } /*else {
                ZBUiUtils.showWarning("移库数量不能为空")
                return null
            }*/
        }
        data.list = listBean
        data.positionNo = positionNo
        //data.time = "2019-02-26"
        data.warehouseNo = warehouseNo

        val json = Gson().toJson(data)
        return RequestBodyJson.requestBody(json)

    }

    private fun createOrder(body: RequestBody?) {

        if (body == null) {
            return
        }
        presenter.materialMoveOrder(body)
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

    override fun showListData(list: MutableList<PositionCode>) {


    }

    override fun showObjData(obj: PositionCode) {
        // 接收查询的库位码信息
        list = obj.list!!
        warehouseNo = obj.warehouseNo
        positionNo = obj.positionNo
        context?.let { LayoutInit.initLayoutManager(it, recycler_source) }
        recycler_source.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))

        adapter = context?.let { SourceAdapter(it, R.layout.item_move_source_frg, list) }
        recycler_source.adapter = adapter
    }

    override fun responseSuccess() {
        pop()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
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