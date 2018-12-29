package com.ty.zbpet.ui.activity.product

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.product.ProductDetailsOut
import com.ty.zbpet.bean.product.ProductDoneSave
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.presenter.product.ProducePresenter
import com.ty.zbpet.presenter.product.ProductUiObjInterface
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.adapter.product.ProductDoneDetailAdapter
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
import okhttp3.RequestBody

/**
 * @author TY on 2018/11/22.
 * 生产入库 已办详情
 */
class ProductDoneDetailActivity : BaseActivity(), ProductUiObjInterface<ProductDetailsOut> {


    private var reView: RecyclerView? = null
    private var tvTime: TextView? = null
    private var etDesc: EditText? = null

    private var titleName: TextView? = null
    private var selectHouse: TextView? = null

    private var adapter: ProductDoneDetailAdapter? = null

    private var selectTime: String? = null
    /**
     * 仓库 ID
     */
    private val warehouseId: String? = null

    private var orderId: String? = null
    private var sapOrderNo: String? = null
    private var list: List<ProductDetailsOut.ListBean>? = ArrayList()


    private val presenter = ProducePresenter(this)


    /**
     * 用户信息
     */
    private val userInfo = DataUtils.getUserInfo()

    override val activityLayout: Int
        get() = R.layout.activity_content_row_three

    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

        orderId = intent.getStringExtra("orderId")
        sapOrderNo = intent.getStringExtra("sapOrderNo")

        presenter.fetchProductDoneInfo(orderId)
    }

    override fun initTwoView() {


        initToolBar(R.string.pick_out_storage, View.OnClickListener { productDoneSave(initDoneBody()) })

        reView = findViewById(R.id.rv_in_storage_detail)
        tvTime = findViewById(R.id.tv_time)
        etDesc = findViewById(R.id.et_desc)
        titleName = findViewById(R.id.in_storage_detail)
        selectHouse = findViewById(R.id.tv_house)

        titleName!!.text = "入库明细"

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tvTime!!.text = selectTime

        etDesc!!.inputType = InputType.TYPE_NULL
    }

    /**
     * 冲销 保存
     */
    private fun productDoneSave(body: RequestBody) {

        HttpMethods.getInstance().getProduceDoneSave(object : SingleObserver<ResponseInfo> {
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

            val warehouseList = userInfo.warehouseList
            detailsBean.warehouseList = warehouseList
            beans.add(detailsBean)
        }


        requestBody.details = beans
        requestBody.orderId = orderId
        requestBody.sapOrderNo = sapOrderNo
        requestBody.outTime = selectTime
        val json = DataUtils.toJson(requestBody, 1)

        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json)
    }

    override fun detailObjData(obj: ProductDetailsOut) {

        list = obj.list
        selectHouse!!.text = list!![0].warehouseName

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            reView!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            reView!!.layoutManager = manager
            adapter = ProductDoneDetailAdapter(this, R.layout.item_produce_detail_done, list)
            reView!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {

                    val rlDetail = holder.itemView.findViewById<View>(R.id.rl_detail)
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
