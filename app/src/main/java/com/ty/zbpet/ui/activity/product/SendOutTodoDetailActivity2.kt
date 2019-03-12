package com.ty.zbpet.ui.activity.product

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.SparseArray
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.bean.product.ProductTodoSave
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.presenter.product.ProductUiListInterface
import com.ty.zbpet.presenter.product.SendOutPresenter
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.adapter.product.SendOutTodoDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.util.*
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
 * 发货出库 待办详情（ 直接显示列表数据  删除添加按钮）
 *
 * @author TY
 */
class SendOutTodoDetailActivity2 : BaseActivity(), ProductUiListInterface<ProductDetails.ListBean>{

    private var adapter: SendOutTodoDetailAdapter? = null

    private var selectTime: String? = null
    private var sapOrderNo: String? = null
    private var sapFirmNo: String? = null
    private var content: String = ""

    /**
     * 生产、客户、成品 信息
     */
    private var productInfo: String? = null
    private var customerInfo: String? = null
    private var goodsInfo: String? = null

    /**
     * 商品种类 原数据
     */
    private val rawData = ArrayList<ProductDetails.ListBean>()

    private val presenter = SendOutPresenter(this)

    /**
     * 箱码
     */
    private var boxCodeList = ArrayList<String>()

    /**
     * 列表 ID
     */
    private var itemId = -1

    private val carCodeArray: SparseArray<ArrayList<String>> = SparseArray(10)
    /**
     * 仓库 ID
     */
    private var warehouseId: String? = null

    override val activityLayout: Int
        get() = R.layout.activity_content_row_two


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {
        sapOrderNo = intent.getStringExtra("sapOrderNo")
        sapFirmNo = intent.getStringExtra("sapFirmNo")
        content = intent.getStringExtra("content")

        productInfo = intent.getStringExtra("productInfo")
        customerInfo = intent.getStringExtra("customerInfo")
        goodsInfo = intent.getStringExtra("goodsInfo")

        presenter.fetchSendOutTodoInfo(sapOrderNo)
    }

    override fun initTwoView() {

        initToolBar(R.string.label_send_out_storage, View.OnClickListener { view ->
            ZBUiUtils.hideInputWindow(view.context, view)
            sendOutTodoSave(initTodoBody())
        })

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time!!.text = selectTime
        in_storage_detail!!.text = "发货明细"

        tv_time!!.setOnClickListener { v ->
            ZBUiUtils.showPickDate(v.context) { date, _ ->
                selectTime = ZBUiUtils.getTime(date)
                tv_time!!.text = selectTime

                ZBUiUtils.showToast(selectTime)
            }
        }
    }

    /**
     * 出库 保存
     */
    private fun sendOutTodoSave(body: RequestBody?) {

        if (body == null) {
            return
        }

        HttpMethods.getInstance().getShipTodoSave(object : SingleObserver<ResponseInfo> {

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                ZBUiUtils.showToast(e.message)
            }

            override fun onSuccess(responseInfo: ResponseInfo) {
                if (CodeConstant.SERVICE_SUCCESS == responseInfo.tag) {
                    // 入库成功（保存）
                    ZBUiUtils.showToast(responseInfo.message)
                    finish()
                } else {
                    ZBUiUtils.showToast(responseInfo.message)
                }
            }
        }, body)
    }

    /**
     * 构建 保存 的 Body
     *
     * @return
     */
    private fun initTodoBody(): RequestBody? {

        val requestBody = ProductTodoSave()

        val detail = ArrayList<ProductTodoSave.DetailsBean>()

        val size = rawData.size
        for (i in 0 until size) {
            val view = rv_in_storage_detail.getChildAt(i)

            val boxQrCode = carCodeArray.get(i)
            val number = view.findViewById<EditText>(R.id.et_number).text.toString().trim { it <= ' ' }
            val startCode = view.findViewById<EditText>(R.id.et_start_code).text.toString().trim { it <= ' ' }
            val endCode = view.findViewById<EditText>(R.id.et_end_code).text.toString().trim { it <= ' ' }
            val sap = view.findViewById<EditText>(R.id.et_sap).text.toString().trim { it <= ' ' }

            val bean = ProductTodoSave.DetailsBean()
            if (!TextUtils.isEmpty(number) && boxQrCode != null) {

                val subContent = rawData[i].content
                val mergeContent = JsonStringMerge().StringMerge(subContent, content)

                bean.startQrCode = startCode
                bean.endQrCode = endCode
                bean.number = number
                bean.sapMaterialBatchNo = sap
                bean.content = mergeContent
                bean.boxQrCode = boxQrCode

                bean.unit = rawData[i].unit
                bean.goodsNo = rawData[i].goodsNo
                bean.goodsName = rawData[i].goodsName
                bean.warehouseNo = rawData[i].warehouseNo

                detail.add(bean)
            }
        }
        // 没有合法的操作数据,不请求网络
        if (detail.size == 0) {
            ZBUiUtils.showToast("请完善您要出库的信息")
            return null
        }

        val remark = et_desc!!.text.toString().trim { it <= ' ' }
        val time = tv_time!!.text.toString().trim { it <= ' ' }

        requestBody.productInfo = productInfo
        requestBody.customerInfo = customerInfo
        requestBody.goodsInfo = goodsInfo

        requestBody.list = detail
        requestBody.moveType = "601"
        requestBody.sapOrderNo = sapOrderNo
        requestBody.inTime = time
        requestBody.remark = remark


        val json = DataUtils.toJson(requestBody, 1)
        ZBLog.e("JSON $json")
        return RequestBodyJson.requestBody(json)
    }


    override fun showProduct(list: MutableList<ProductDetails.ListBean>) {

        // 保存原数据
        rawData.addAll(list)

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            rv_in_storage_detail!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            rv_in_storage_detail!!.layoutManager = manager
            adapter = SendOutTodoDetailAdapter(this, R.layout.item_product_detail_send_out_todo, rawData)
            rv_in_storage_detail!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {

                    val rlDetail = holder.itemView.findViewById<View>(R.id.gone_view)
                    val ivArrow = holder.itemView.findViewById<ImageView>(R.id.iv_arrow)
                    val bindingCode = holder.itemView.findViewById<Button>(R.id.btn_binding_code)

                    if (rlDetail.visibility == View.VISIBLE) {
                        rlDetail.visibility = View.GONE
                        ivArrow.setImageResource(R.mipmap.ic_collapse)
                    } else {
                        rlDetail.visibility = View.VISIBLE
                        ivArrow.setImageResource(R.mipmap.ic_expand)
                    }

                    bindingCode.setOnClickListener {
                        itemId = position
                        val intent = Intent(it.context, ScanBoxCodeActivity::class.java)
                        intent.putExtra("itemId", itemId)
                        intent.putExtra(CodeConstant.PAGE_STATE, true)
                        intent.putStringArrayListExtra("boxCodeList", carCodeArray.get(itemId))
                        startActivityForResult(intent, REQUEST_SCAN_CODE)
                    }

                    ZBUiUtils.hideInputWindow(view.context, view)

                }

                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {

                    return true
                }
            })
        } else {
            adapter!!.notifyDataSetChanged()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SCAN_CODE && resultCode == RESULT_SCAN_CODE) {
            itemId = data!!.getIntExtra("itemId", -1)
            warehouseId = data.getStringExtra("warehouseId")
            boxCodeList = data.getStringArrayListExtra("boxCodeList")
            carCodeArray.put(itemId, boxCodeList)
        }
    }
    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun saveSuccess() {
    }

    override fun showError(msg: String?) {
    }

    companion object {

        private const val REQUEST_SCAN_CODE = 1
        private const val RESULT_SCAN_CODE = 2
    }
}
