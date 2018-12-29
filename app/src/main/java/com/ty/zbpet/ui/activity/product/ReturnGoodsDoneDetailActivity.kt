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
import com.ty.zbpet.presenter.product.ProductUiListInterface
import com.ty.zbpet.presenter.product.ReturnPresenter
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.adapter.product.ReturnGoodsDoneDetailAdapter
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
 * 退货入库 已办详情
 */
class ReturnGoodsDoneDetailActivity : BaseActivity(), ProductUiListInterface<ProductDetailsOut.ListBean> {


    private var reView: RecyclerView? = null
    private var tvTime: TextView? = null
    private var titleName: TextView? = null
    private var selectHouse: TextView? = null
    private var etDesc: EditText? = null

    private var adapter: ReturnGoodsDoneDetailAdapter? = null

    private var selectTime: String? = null

    private var orderId: String? = null
    private var sapOrderNo: String? = null
    private var oldList: List<ProductDetailsOut.ListBean> = ArrayList()


    private val presenter = ReturnPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_content_row_three


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

        orderId = intent.getStringExtra("orderId")
        sapOrderNo = intent.getStringExtra("sapOrderNo")

    }

    override fun onStart() {
        super.onStart()
        presenter.fetchReturnOrderDoneInfo(orderId)
    }

    override fun initTwoView() {


        initToolBar(R.string.label_return_sell, View.OnClickListener { returnGoodsDoneSave(initDoneBody()) })

        reView = findViewById(R.id.rv_in_storage_detail)
        tvTime = findViewById(R.id.tv_time)
        etDesc = findViewById(R.id.et_desc)
        titleName = findViewById(R.id.in_storage_detail)
        selectHouse = findViewById(R.id.tv_house)

        etDesc!!.inputType = InputType.TYPE_NULL

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tvTime!!.text = selectTime
        titleName!!.text = "入库明细"

    }

    /**
     * 冲销 保存
     */
    private fun returnGoodsDoneSave(body: RequestBody) {

        HttpMethods.getInstance().getReturnDoneSave(object : SingleObserver<ResponseInfo> {

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                ZBUiUtils.showToast(e.message)
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

        val size = oldList.size
        val beans = ArrayList<ProductDoneSave.DetailsBean>()
        for (i in 0 until size) {
            val detailsBean = ProductDoneSave.DetailsBean()

            val boxQrCodeList = oldList[i].boxQrCode
            val goodsId = oldList[i].goodsId
            val goodsNo = oldList[i].goodsNo
            val warehouseId = oldList[i].warehouseId
            val warehouseNo = oldList[i].warehouseNo
            val warehouseName = oldList[i].warehouseName
            val number = oldList[i].number

            detailsBean.number = number
            detailsBean.boxQrCode = boxQrCodeList
            detailsBean.warehouseId = warehouseId
            detailsBean.warehouseNo = warehouseNo
            detailsBean.warehouseName = warehouseName

            detailsBean.goodsId = goodsId
            detailsBean.goodsNo = goodsNo

            beans.add(detailsBean)
        }

        //String remark = etDesc.getText().toString().trim();

        requestBody.details = beans
        requestBody.orderId = orderId
        requestBody.sapOrderNo = sapOrderNo
        requestBody.outTime = selectTime
        //requestBody.setRemark(remark);
        val json = DataUtils.toJson(requestBody, 1)

        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json)
    }

    override fun showProduct(list: List<ProductDetailsOut.ListBean>) {

        oldList = list
        selectHouse!!.text = list[0].warehouseName

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            reView!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            reView!!.layoutManager = manager
            adapter = ReturnGoodsDoneDetailAdapter(this, R.layout.item_product_detail_two_done, list)
            reView!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {

                    val rlDetail = holder.itemView.findViewById<View>(R.id.gone_view)
                    val ivArrow = holder.itemView.findViewById<ImageView>(R.id.iv_arrow)

                    val boxQrCodeList = list[position].boxQrCode

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
