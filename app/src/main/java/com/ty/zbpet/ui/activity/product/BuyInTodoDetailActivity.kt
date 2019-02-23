package com.ty.zbpet.ui.activity.product

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.ty.zbpet.R
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.UserInfo
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.bean.product.ProductTodoSave
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.product.BuyInPresenter
import com.ty.zbpet.presenter.product.ProductUiListInterface
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.adapter.product.BuyInTodoDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.*
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_content_row_two.*
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author TY on 2018/11/22.
 * 外采入库 待办详情
 */
class BuyInTodoDetailActivity : BaseActivity(), ProductUiListInterface<ProductDetails.ListBean> {

    private var adapter: BuyInTodoDetailAdapter? = null

    private var selectTime: String? = null
    private var sapOrderNo: String? = null
    private var sapFirmNo: String? = null
    private var supplierNo: String? = null
    private var content: String = ""

    private var oldList: List<ProductDetails.ListBean> = ArrayList()

    private val presenter = BuyInPresenter(this)

    /**
     * 箱码
     */
    private var boxCodeList = ArrayList<String>()

    /**
     * 列表 ID
     */
    private var itemId = -1

    /**
     * 保存用户箱码的数据
     */
    private val carCodeArray: SparseArray<ArrayList<String>> = SparseArray(10)

    private var supplierId: String? = null

    /**
     * 用户信息: DataUtils.getUserInfo()
     */
    private var userInfo: UserInfo? = null

    override val activityLayout: Int
        get() = R.layout.activity_content_row_two

    override fun onBaseCreate(savedInstanceState: Bundle?) {}

    override fun initOneData() {
        sapOrderNo = intent.getStringExtra("sapOrderNo")
        sapFirmNo = intent.getStringExtra("sapFirmNo")
        supplierNo = intent.getStringExtra("supplierNo")
        content = intent.getStringExtra("content")
        supplierId = intent.getStringExtra("supplierId")

        userInfo = SimpleCache.getUserInfo(CodeConstant.USER_DATA)
        // 仓库默认值设置　
        DataUtils.setHouseId(0, 0)

        presenter.fetchBuyInTodoListDetails(sapOrderNo,sapFirmNo,supplierNo)
    }

    /**
     * super 在 onStart 回调中
     */
    override fun initTwoView() {

        initToolBar(R.string.label_purchase_in_storage, View.OnClickListener { view ->
            ZBUiUtils.hideInputWindow(view.context, view)
            buyInTodoSave(initTodoBody())
        })

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time!!.text = selectTime
        in_storage_detail!!.text = "到货明细"


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
    private fun buyInTodoSave(body: RequestBody?) {

        if (body == null) {
            return
        }

        HttpMethods.getInstance().getBuyInTodoSave(object : SingleObserver<ResponseInfo> {
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

    /**
     * 构建 保存 的 Body
     *
     * @return
     */
    private fun initTodoBody(): RequestBody? {

        val requestBody = ProductTodoSave()
        val detail = ArrayList<ProductTodoSave.DetailsBean>()

        val size = oldList.size
        for (i in 0 until size) {
            val view = rv_in_storage_detail.getChildAt(i)
            val boxQrCode = carCodeArray.get(i)
            val bulkNum = view.findViewById<EditText>(R.id.et_number).text.toString().trim { it <= ' ' }
            val batchNo = view.findViewById<EditText>(R.id.et_sap).text.toString().trim { it <= ' ' }

            val bean = ProductTodoSave.DetailsBean()
            if (bulkNum.isNotEmpty() && !boxQrCode.isNullOrEmpty()) {
                val goodsId = oldList[i].goodsId
                val goodsNo = oldList[i].goodsNo
                val orderNumber = oldList[i].orderNumber

                val startCode = view.findViewById<EditText>(R.id.et_start_code).text.toString().trim { it <= ' ' }
                val endCode = view.findViewById<EditText>(R.id.et_end_code).text.toString().trim { it <= ' ' }

                val subContent = oldList[i].content
                val mergeContent = JsonStringMerge().StringMerge(subContent, content)
                // 入库数量
                bean.number = bulkNum
                bean.sapMaterialBatchNo = batchNo

                bean.goodsId = goodsId
                bean.goodsNo = goodsNo

                bean.startQrCode = startCode
                bean.endQrCode = endCode
                bean.orderNumber = orderNumber
                bean.content = mergeContent
                bean.unit = oldList[i].unit
                bean.goodsName = oldList[i].goodsName

                bean.warehouseId = oldList[i].warehouseId
                bean.warehouseNo = oldList[i].warehouseNo

                bean.boxQrCode = boxQrCode

                detail.add(bean)
            }
        }
        // 没有合法的操作数据,不请求网络
        if (detail.size == 0) {
            ZBUiUtils.showToast("请完善您要入库的信息")
            return null
        }

        val remark = et_desc!!.text.toString().trim { it <= ' ' }
        val time = tv_time!!.text.toString().trim { it <= ' ' }

        requestBody.list = detail
        requestBody.inTime = time
        requestBody.sapOrderNo = sapOrderNo
        requestBody.supplierNo = supplierNo
        requestBody.moveType = "101"
        requestBody.remark = remark

        val json = DataUtils.toJson(requestBody, 1)
        return RequestBodyJson.requestBody(json)
    }


    override fun showProduct(list: List<ProductDetails.ListBean>) {

        // BuyInTodoDetails  含仓库信息 bean
        // ProductDetailsIn  不含仓库信息 bean
        oldList = list

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            rv_in_storage_detail!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            rv_in_storage_detail!!.layoutManager = manager
            adapter = BuyInTodoDetailAdapter(this, R.layout.item_product_detail_two_todo, list)
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
                        val boxCodeList = carCodeArray.get(itemId)

                        val intent = Intent(it.context, ScanBoxCodeActivity::class.java)
                        intent.putExtra("itemId", itemId)
                        intent.putExtra(CodeConstant.PAGE_STATE, true)
                        intent.putStringArrayListExtra("boxCodeList", boxCodeList)

                        startActivityForResult(intent, REQUEST_SCAN_CODE)
                    }


                    ZBUiUtils.hideInputWindow(view.context, view)

                }

                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                    return false
                }
            })
        } else {
            adapter!!.notifyDataSetChanged()
        }

    }

    /**
     * 扫码 成功的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SCAN_CODE && resultCode == RESULT_SCAN_CODE) {
            itemId = data!!.getIntExtra("itemId", -1)
            boxCodeList = data.getStringArrayListExtra("boxCodeList")
            carCodeArray.put(itemId, boxCodeList)
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        // TODO  清除仓库数据
        DataUtils.clearId()
    }

    companion object {

        private const val REQUEST_SCAN_CODE = 1
        private const val RESULT_SCAN_CODE = 2
    }
}
