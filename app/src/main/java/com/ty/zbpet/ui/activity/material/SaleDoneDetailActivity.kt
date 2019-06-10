package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.bean.material.MaterialDoneSave
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.presenter.material.SaleOrderPresenter
import com.ty.zbpet.ui.adapter.material.SaleDoneDetailAdapter
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.SimpleCache
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_content_reversal.*
import okhttp3.RequestBody
import java.util.*

/**
 * @author TY on 2018/11/22.
 * 销售出库 已办详情
 */
class SaleDoneDetailActivity : BaseActivity(), MaterialUiListInterface<MaterialDetails.ListBean> {

    lateinit var adapter: SaleDoneDetailAdapter

    /**
     * 仓库 ID
     */
    lateinit var warehouseId: String

    lateinit var orderId: String

    private var listBean = mutableListOf<MaterialDetails.ListBean>()

    private val presenter = SaleOrderPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_content_reversal

    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {
        order_del.visibility = View.VISIBLE
        orderId = intent.getStringExtra("orderId")

        presenter.fetchBackDoneListInfo(orderId)
    }

    override fun initTwoView() {

        initToolBar(R.string.sales_out_of_stock_details)
        bt_reversal.setOnClickListener { backGoodsDoneSave(initDoneBody()) }
    }

    /**
     * 出库 保存
     */
    private fun backGoodsDoneSave(body: RequestBody?) {

        if (body == null) {
            return
        }
        presenter.saleInList(body)
    }

    private fun initDoneBody(): RequestBody? {

        val data = MaterialDoneSave()
        val list = ArrayList<MaterialDoneSave.ListBean>()
        val size = listBean.size
        val checked = order_del.isChecked

        for (i in 0 until size) {
//            val view = recycler_reversal.getChildAt(i)
//            val checkBox = view.findViewById<CheckBox>(R.id.check)
            val value = SimpleCache.getNumber(i.toString())
            if (value == "1") {
                val bean = MaterialDoneSave.ListBean()
                bean.id = listBean[i].id
                list.add(bean)
            }
        }

        if (list.size == 0) {
            ZBUiUtils.showWarning("请选择您要冲销的列表")
            return null
        }

        data.list = list
        data.orderId = orderId
        data.deleteSign = checked
        data.moveType = "102"
        val json = DataUtils.toJson(data, 1)

        return RequestBodyJson.requestBody(json)
    }

    override fun showMaterial(list: MutableList<MaterialDetails.ListBean>) {

        listBean = list
        warehouseId = listBean[0].warehouseId!!
        for (i in 0 until list.size) {
            SimpleCache.clearKey(i.toString())
        }

        val manager = LinearLayoutManager(ResourceUtil.getContext())
        recycler_reversal.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
        recycler_reversal.layoutManager = manager
        adapter = SaleDoneDetailAdapter(this, R.layout.item_reversal_check, listBean)
        recycler_reversal.adapter = adapter

    }

    override fun showCarSuccess(position: Int, carData: CarPositionNoData) {

    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(this@SaleDoneDetailActivity, "保存中")
    }

    override fun hideLoading() {
        dialog?.close()
    }

    override fun saveSuccess() {
        ZBUiUtils.showSuccess(TipString.success)
        finish()
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showError(msg)
    }


}
