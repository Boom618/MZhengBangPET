package com.ty.zbpet.ui.activity.product

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.bean.product.ProductDoneSave
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.product.ProducePresenter
import com.ty.zbpet.presenter.product.ProductUiListInterface
import com.ty.zbpet.ui.adapter.product.ProductDoneDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.activity_content_reversal.*
import okhttp3.RequestBody
import java.util.*

/**
 * @author TY on 2018/11/22.
 * 生产入库 已办详情
 */
class ProductDoneDetailActivity : BaseActivity(), ProductUiListInterface<ProductDetails.ListBean> {


    private var adapter: ProductDoneDetailAdapter? = null

    /**
     * 仓库 ID
     */
    private val warehouseId: String? = null

    private var orderId: String? = null
    private var sapOrderNo: String? = null
    private var list: List<ProductDetails.ListBean> = ArrayList()


    private val presenter = ProducePresenter(this)


    /**
     * 用户信息
     */
    private val userInfo = DataUtils.getUserInfo()

    override val activityLayout: Int
        get() = R.layout.activity_content_reversal

    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

        orderId = intent.getStringExtra("orderId")
        sapOrderNo = intent.getStringExtra("sapOrderNo")

        presenter.fetchProductDoneInfo(orderId)
    }

    override fun initTwoView() {


        initToolBar(R.string.output_reversal)

        bt_reversal.setOnClickListener {
            productDoneSave(initDoneBody())
        }
    }

    /**
     * 冲销 保存
     */
    private fun productDoneSave(body: RequestBody?) {

        if (body == null) {
            return
        }
        presenter.productDoneSave(body)
    }

    private fun initDoneBody(): RequestBody? {

        val requestBody = ProductDoneSave()

        val size = list.size
        val beans = ArrayList<ProductDoneSave.DetailsBean>()
        for (i in 0 until size) {
            val detailsBean = ProductDoneSave.DetailsBean()

            val boxQrCodeList = list[i].boxQrCode
            val goodsId = list[i].goodsId
            val goodsNo = list[i].goodsNo
            val warehouseId = list[i].warehouseId
            val number = list[i].number

            detailsBean.id = list[i].id
            detailsBean.number = number
            detailsBean.unit = list[i].unit
            detailsBean.boxQrCode = boxQrCodeList
//            detailsBean.warehouseId = warehouseId

            detailsBean.goodsId = goodsId
            detailsBean.goodsNo = goodsNo

            val warehouseList = userInfo.warehouseList
            detailsBean.warehouseList = warehouseList
            beans.add(detailsBean)
        }
        if (beans.size == 0) {
            ZBUiUtils.showToast("请完善您要入库的信息")
            return null
        }

        requestBody.list = beans
        requestBody.orderId = orderId
        requestBody.sapOrderNo = sapOrderNo
        requestBody.moveType = "102"
        val json = DataUtils.toJson(requestBody, 1)

        return RequestBodyJson.requestBody(json)
    }

    override fun showProduct(lists: MutableList<ProductDetails.ListBean>?) {

        list = lists!!
//        tv_house!!.text = list[0].warehouseName

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            recycler_reversal.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            recycler_reversal.layoutManager = manager
            //adapter = ProductDoneDetailAdapter(this, R.layout.item_produce_detail_done, list)
            adapter = ProductDoneDetailAdapter(this, R.layout.item_reversal_check, list)
            recycler_reversal.adapter = adapter

        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun saveSuccess() {
        finish()
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showToast(msg)
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }
}
