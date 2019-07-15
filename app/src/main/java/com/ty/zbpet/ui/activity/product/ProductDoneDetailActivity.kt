package com.ty.zbpet.ui.activity.product

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.CheckBox
import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.bean.product.ProductDoneSave
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.product.ProducePresenter
import com.ty.zbpet.presenter.product.ProductUiListInterface
import com.ty.zbpet.ui.adapter.product.ProductDoneDetailAdapter
import com.ty.zbpet.base.BaseActivity
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
 * 生产入库 已办详情
 */
class ProductDoneDetailActivity : BaseActivity(), ProductUiListInterface<ProductDetails.ListBean> {

    private var adapter: ProductDoneDetailAdapter? = null

    private var orderId: String? = null
    private var sapOrderNo: String? = null
    private var list: List<ProductDetails.ListBean> = ArrayList()

    private val presenter = ProducePresenter(this)

    /**
     * 用户信息
     */
    private val userInfo = SimpleCache.getUserInfo(CodeConstant.USER_DATA)

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
            val view = recycler_reversal.getChildAt(i)
            val checkBox = view.findViewById<CheckBox>(R.id.check)

            if (checkBox.isChecked){
                val detailsBean = ProductDoneSave.DetailsBean()

                detailsBean.id = list[i].id
                detailsBean.unit = list[i].unit
                detailsBean.number = list[i].number
                detailsBean.goodsId = list[i].goodsId
                detailsBean.goodsNo = list[i].goodsNo
                detailsBean.warehouseNo = list[i].warehouseNo
                detailsBean.boxQrCode = list[i].boxQrCode

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
        requestBody.moveType = "102"
        val json = DataUtils.toJson(requestBody, 1)

        return RequestBodyJson.requestBody(json)
    }

    override fun showProduct(lists: MutableList<ProductDetails.ListBean>?) {

        list = lists!!

        if (adapter == null) {
            val manager = androidx.recyclerview.widget.LinearLayoutManager(ResourceUtil.getContext())
            recycler_reversal.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            recycler_reversal.layoutManager = manager
            adapter = ProductDoneDetailAdapter(this, R.layout.item_reversal_check, list)
            recycler_reversal.adapter = adapter

        }
    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(this@ProductDoneDetailActivity, "保存中")
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }
}
