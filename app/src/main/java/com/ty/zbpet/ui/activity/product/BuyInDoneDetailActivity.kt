package com.ty.zbpet.ui.activity.product

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.CheckBox
import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.bean.product.ProductDoneSave
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.product.BuyInPresenter
import com.ty.zbpet.presenter.product.ProductUiListInterface
import com.ty.zbpet.ui.adapter.product.BuyInDoneDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_content_reversal.*
import okhttp3.RequestBody
import java.util.*

/**
 * @author TY on 2018/11/22.
 * 外采入库 已办详情
 */
class BuyInDoneDetailActivity : BaseActivity(), ProductUiListInterface<ProductDetails.ListBean> {

    private var adapter: BuyInDoneDetailAdapter? = null

    private var selectTime: String? = null

    private var orderId: String? = null
    private var sapOrderNo: String? = null

    /**
     * 用户信息
     */
    private val userInfo = DataUtils.getUserInfo()

    private var list: List<ProductDetails.ListBean>? = ArrayList()


    private val presenter = BuyInPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_content_reversal//activity_content_row_two


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

        sapOrderNo = intent.getStringExtra("sapOrderNo")
        orderId = intent.getStringExtra("orderId")

        presenter.fetchBuyInDoneListDetails(orderId)
    }

    override fun initTwoView() {

        initToolBar(R.string.label_purchase_in_storage_reversal)

        bt_reversal.setOnClickListener {
            buyInDoneSave(initDoneBody())
        }
    }

    /**
     * 冲销 保存
     */
    private fun buyInDoneSave(body: RequestBody?) {
        if (body == null) {
            return
        }
        presenter.buyInDoneSave(body)
    }

    private fun initDoneBody(): RequestBody? {

        val requestBody = ProductDoneSave()

        val size = list!!.size
        val beans = ArrayList<ProductDoneSave.DetailsBean>()
        for (i in 0 until size) {
            val view = recycler_reversal.getChildAt(i)
            val checkBox = view.findViewById<CheckBox>(R.id.check)

            if (checkBox.isChecked) {

                val detailsBean = ProductDoneSave.DetailsBean()

                val boxQrCodeList = list!![i].boxQrCode
                val goodsId = list!![i].goodsId
                val goodsNo = list!![i].goodsNo
                val warehouseId = list!![i].warehouseId
                val number = list!![i].number

                val subContent = list!![i].content

                detailsBean.id = list!![i].id
                detailsBean.unit = list!![i].unit
                detailsBean.content = subContent
                detailsBean.number = number
                detailsBean.boxQrCode = boxQrCodeList
                detailsBean.warehouseId = warehouseId

                detailsBean.goodsId = goodsId
                detailsBean.goodsNo = goodsNo

                val warehouseList = userInfo.warehouseList
                detailsBean.warehouseList = warehouseList
                beans.add(detailsBean)
            }
        }
        if (beans.size == 0) {
            ZBUiUtils.showWarning("请完善您要入库的信息")
            return null
        }


        requestBody.list = beans
        requestBody.orderId = orderId
        requestBody.sapOrderNo = sapOrderNo
        requestBody.moveType = "106"
//        requestBody.outTime = selectTime
        val json = DataUtils.toJson(requestBody, 1)

        return RequestBodyJson.requestBody(json)
    }

    override fun showProduct(lists: MutableList<ProductDetails.ListBean>?) {

        list = lists

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            recycler_reversal.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            recycler_reversal.layoutManager = manager
//            adapter = BuyInDoneDetailAdapter(this, R.layout.item_product_detail_two_done, list!!)
            adapter = BuyInDoneDetailAdapter(this, R.layout.item_reversal_check, list!!)
            recycler_reversal.adapter = adapter
        }
    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(this@BuyInDoneDetailActivity, "保存中")
    }

    override fun hideLoading() {
        dialog?.close()
    }

    override fun saveSuccess() {
        finish()
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showError(msg)
    }
}
