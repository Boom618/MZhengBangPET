package com.ty.zbpet.ui.activity.product

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.bean.product.ProductDoneSave
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.product.ProductUiListInterface
import com.ty.zbpet.presenter.product.SendOutPresenter
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.adapter.product.SendOutDoneDetailAdapter
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.activity_content_row_two.*
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author TY on 2018/11/22.
 * 发货出库 已办详情 (整单发货，整单冲销)
 */
class SendOutDoneDetailActivity : BaseActivity(), ProductUiListInterface<ProductDetails.ListBean> {

    private var adapter: SendOutDoneDetailAdapter? = null

    private var selectTime: String? = null

    /**
     * 请求详情 orderId
     */
    private var orderId: String? = null
    /**
     * 冲销 sapOrderNo
     */
    private var sapOrderNo: String? = null
    private var list: List<ProductDetails.ListBean> = ArrayList()


    private val presenter = SendOutPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_content_row_two


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {
        checkbox_del.visibility = View.VISIBLE
        orderId = intent.getStringExtra("orderId")
        sapOrderNo = intent.getStringExtra("sapOrderNo")

        presenter.fetchSendOutDoneInfo(orderId)
    }

    override fun initTwoView() {


        initToolBar(R.string.send_out_storage_reversal, "冲销", View.OnClickListener { sendOutDoneSave(initDoneBody()) })

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time.text = selectTime
        in_storage_detail.text = TipString.sendOutDetail

        et_desc.inputType = InputType.TYPE_NULL
    }

    /**
     * 冲销 保存
     */
    private fun sendOutDoneSave(body: RequestBody?) {

        if (body == null) {
            return
        }

        presenter.sendOutDoneSave(body)

    }

    private fun initDoneBody(): RequestBody? {

        val requestBody = ProductDoneSave()
        val checkDel = checkbox_del.isChecked
        val size = list.size
        val beans = ArrayList<ProductDoneSave.DetailsBean>()
        for (i in 0 until size) {
            val view = rv_in_storage_detail.getChildAt(i)
            val startCode = view.findViewById<EditText>(R.id.tv_start_code).text.toString().trim { it <= ' ' }
            val endCode = view.findViewById<EditText>(R.id.tv_end_code).text.toString().trim { it <= ' ' }
            val sap = view.findViewById<EditText>(R.id.tv_sap).text.toString().trim { it <= ' ' }

            val detailsBean = ProductDoneSave.DetailsBean()

            val boxQrCodeList = list[i].boxQrCode
            val goodsId = list[i].goodsId
            val goodsNo = list[i].goodsNo
            val warehouseId = list[i].warehouseId
            val number = list[i].number

            detailsBean.content = list[i].content
            detailsBean.id = list[i].id
            detailsBean.unit = list[i].unit
            detailsBean.goodsName = list[i].goodsName
            detailsBean.number = number
            detailsBean.boxQrCode = boxQrCodeList
            detailsBean.warehouseId = warehouseId
            detailsBean.startQrCode = startCode
            detailsBean.endQrCode = endCode
            detailsBean.sapMaterialBatchNo = sap

            detailsBean.goodsId = goodsId
            detailsBean.goodsNo = goodsNo
            beans.add(detailsBean)
        }

        if (beans.size == 0) {
            ZBUiUtils.showWarning("请完善您要发货的信息")
            return null
        }

        requestBody.list = beans
        requestBody.orderId = orderId
        requestBody.moveType = "602"
        requestBody.sapOrderNo = sapOrderNo
        requestBody.deleteSign = checkDel
        requestBody.inTime = selectTime
        val json = DataUtils.toJson(requestBody, 1)

        return RequestBodyJson.requestBody(json)
    }

    override fun showProduct(lists: MutableList<ProductDetails.ListBean>?) {
        list = lists!!

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            rv_in_storage_detail.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            rv_in_storage_detail.layoutManager = manager
            adapter = SendOutDoneDetailAdapter(this, R.layout.item_product_detail_send_out_done, list)
            rv_in_storage_detail.adapter = adapter

            adapter?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
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
                        intent.putExtra("goodsNo", list[position].goodsNo)
                        intent.putStringArrayListExtra("boxCodeList", boxQrCodeList)
                        startActivity(intent)
                    }
                }

                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                    return false
                }
            })
        }

    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(this@SendOutDoneDetailActivity, "保存中")
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
