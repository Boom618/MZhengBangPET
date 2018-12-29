package com.ty.zbpet.ui.activity.product

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.ImageView

import com.ty.zbpet.R
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.bean.product.ProductDoneSave
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.presenter.product.ProductUiObjInterface
import com.ty.zbpet.presenter.product.SendOutPresenter
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.adapter.product.SendOutDoneDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_content_row_two.*
import okhttp3.RequestBody

/**
 * @author TY on 2018/11/22.
 * 发货出库 已办详情
 */
class SendOutDoneDetailActivity : BaseActivity(), ProductUiObjInterface<ProductDetails> {

    private var adapter: SendOutDoneDetailAdapter? = null

    private var selectTime: String? = null
    /**
     * 仓库 ID
     */
    private val warehouseId: String? = null

    /**
     * 请求详情 orderId
     */
    private var orderId: String? = null
    /**
     * 冲销 sapOrderNo
     */
    private var sapOrderNo: String? = null
    private var list: List<ProductDetails.ListBean>? = ArrayList()


    private val presenter = SendOutPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_content_row_two


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

        orderId = intent.getStringExtra("orderId")
        sapOrderNo = intent.getStringExtra("sapOrderNo")

        presenter.fetchSendOutDoneInfo(orderId)
    }

    override fun initTwoView() {


        initToolBar(R.string.pick_out_storage, View.OnClickListener { sendOutDoneSave(initDoneBody()) })

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time!!.text = selectTime
        in_storage_detail!!.text = "发货明细"

        et_desc!!.inputType = InputType.TYPE_NULL
    }

    /**
     * 冲销 保存
     */
    private fun sendOutDoneSave(body: RequestBody) {

        HttpMethods.getInstance().getShipDoneSave(object : SingleObserver<ResponseInfo> {
            override fun onError(e: Throwable) {
                ZBUiUtils.showToast(e.message)
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onSuccess(responseInfo: ResponseInfo) {
                if (CodeConstant.SERVICE_SUCCESS == responseInfo.tag) {
                    // 入库成功（保存）
                    ZBUiUtils.showToast(responseInfo.message)
                    runOnUiThread { finish() }
                } else {
                    ZBUiUtils.showToast(responseInfo.message)
                }
            }
        }, body)
    }

    private fun initDoneBody(): RequestBody {

        val requestBody = ProductDoneSave()

        val size = list!!.size
        val beans = ArrayList<ProductDoneSave.DetailsBean>()
        for (i in 0 until size) {
            val detailsBean = ProductDoneSave.DetailsBean()

            val boxQrCodeList = list!![i].boxQrCode
            val goodsId = list!![i].goodsId
            val goodsNo = list!![i].goodsNo
            val warehouseId = list!![i].warehouseId
            val number = list!![i].number

            detailsBean.number = number
            detailsBean.boxQrCode = boxQrCodeList
            detailsBean.warehouseId = warehouseId

            detailsBean.goodsId = goodsId
            detailsBean.goodsNo = goodsNo
            beans.add(detailsBean)
        }

        requestBody.details = beans
        requestBody.orderId = orderId
        requestBody.sapOrderNo = sapOrderNo
        requestBody.outTime = selectTime
        val json = DataUtils.toJson(requestBody, 1)

        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json)
    }

    override fun detailObjData(obj: ProductDetails) {

        list = obj.list

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            rv_in_storage_detail!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            rv_in_storage_detail!!.layoutManager = manager
            adapter = SendOutDoneDetailAdapter(this, R.layout.item_product_detail_send_out_done, list)
            rv_in_storage_detail!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {

                    val rlDetail = holder.itemView.findViewById<View>(R.id.gone_view)
                    val ivArrow = holder.itemView.findViewById<ImageView>(R.id.iv_arrow)
                    val boxQrCodeList = list!![position].boxQrCode

                    val bindingCode = holder.itemView.findViewById<Button>(R.id.btn_binding_code)

                    if (rlDetail.visibility == View.VISIBLE) {
                        rlDetail.visibility = View.GONE
                        ivArrow.setImageResource(R.mipmap.ic_collapse)
                    } else {
                        rlDetail.visibility = View.VISIBLE
                        ivArrow.setImageResource(R.mipmap.ic_expand)

                    }

                    bindingCode.setOnClickListener {
                        val intent = Intent(it.context, ScanBoxCodeActivity::class.java)
                        intent.putExtra(CodeConstant.PAGE_STATE, false)
                        intent.putStringArrayListExtra("boxCodeList", boxQrCodeList)
                        startActivity(intent)
                    }

                }

                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                    return false
                }
            })
        } else {
            adapter!!.notifyDataSetChanged()
        }

    }
}
