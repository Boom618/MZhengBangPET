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
import com.ty.zbpet.R
import com.ty.zbpet.bean.UserInfo
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.bean.product.ProductTodoSave
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.product.ProductUiListInterface
import com.ty.zbpet.presenter.product.ReturnPresenter
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.adapter.product.ReturnGoodsTodoDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.*
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.activity_content_row_two.*
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author TY on 2018/11/22.
 * 退货入库 待办详情
 */
class ReturnGoodsTodoDetailActivity : BaseActivity(), ProductUiListInterface<ProductDetails.ListBean> {

    private var adapter: ReturnGoodsTodoDetailAdapter? = null

    private var selectTime: String? = null
    private var sapOrderNo: String? = null
    private var sapFirmNo: String? = null
    private var content: String = ""

    private var oldList: List<ProductDetails.ListBean> = ArrayList()

    private val presenter = ReturnPresenter(this)

    /**
     * 箱码
     */
    private var boxCodeList = ArrayList<String>()

    /**
     * 列表 ID
     */
    private var itemId = -1

    /**
     * 保存用户在输入框中的数据
     */
    private val carCodeArray: SparseArray<ArrayList<String>> = SparseArray(10)
    /**
     * 库位码 ID
     */
    private val positionId: SparseArray<String> = SparseArray(10)

    /**
     * 用户信息
     */
    private var userInfo: UserInfo? = null

    override val activityLayout: Int
        get() = R.layout.activity_content_row_two


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {
        sapOrderNo = intent.getStringExtra("sapOrderNo")
        sapFirmNo = intent.getStringExtra("sapFirmNo")
        content = intent.getStringExtra("content")

        userInfo = DataUtils.getUserInfo()

        presenter.fetchReturnOrderInfo(sapOrderNo)
    }

    override fun initTwoView() {

        initToolBar(R.string.label_return_sell, "保存", View.OnClickListener { view ->
            ZBUiUtils.hideInputWindow(view.context, view)
            returnGoodsTodoSave(initTodoBody())
        })

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time!!.text = selectTime
        in_storage_detail!!.text = "退货明细"

    }

    /**
     * 出库 保存
     */
    private fun returnGoodsTodoSave(body: RequestBody?) {

        if (body == null) {
            return
        }
        presenter.getReturnTodoSave(body)

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
            val number = view.findViewById<EditText>(R.id.et_number).text.toString().trim()
            val bulkNumber = view.findViewById<EditText>(R.id.et_scattered).text.toString().trim()
            val startCode = view.findViewById<EditText>(R.id.et_start_code).text.toString().trim()
            val endCode = view.findViewById<EditText>(R.id.et_end_code).text.toString().trim()
            val sap = view.findViewById<EditText>(R.id.et_sap).text.toString().trim()
            val id = positionId.get(i)

            val goodsId = oldList[i].goodsId
            val goodsNo = oldList[i].goodsNo
            val orderNumber = oldList[i].orderNumber

            val bean = ProductTodoSave.DetailsBean()
            if (!TextUtils.isEmpty(number) && boxQrCode != null) {

                val subContent = oldList[i].content
                val mergeContent = JsonStringMerge().StringMerge(subContent, content)

                bean.content = mergeContent
                bean.positionId = id
                bean.number = number
                bean.goodsId = goodsId
                bean.goodsNo = goodsNo
                bean.unit = oldList[i].unit
                bean.goodsName = oldList[i].goodsName
                bean.orderNumber = orderNumber
                bean.startQrCode = startCode
                bean.endQrCode = endCode
                bean.sapMaterialBatchNo = sap
                bean.bulkNumber = bulkNumber
                bean.boxQrCode = boxQrCode

                detail.add(bean)
            }
        }
        // 没有合法的操作数据,不请求网络
        if (detail.size == 0) {
            ZBUiUtils.showWarning("请完善您要退货的信息")
            return null
        }
        val remark = et_desc!!.text.toString().trim { it <= ' ' }
        val time = tv_time!!.text.toString().trim { it <= ' ' }

        requestBody.list = detail
        requestBody.sapOrderNo = sapOrderNo
        requestBody.moveType = "653"
        requestBody.inTime = time
        requestBody.remark = remark

        val json = DataUtils.toJson(requestBody, 1)
        ZBLog.e("JSON $json")
        return RequestBodyJson.requestBody(json)
    }


    override fun showProduct(list: List<ProductDetails.ListBean>) {
        oldList = list
        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            rv_in_storage_detail.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            rv_in_storage_detail.layoutManager = manager
            adapter = ReturnGoodsTodoDetailAdapter(this, R.layout.item_product_detail_return_todo, list)
            rv_in_storage_detail.adapter = adapter

            adapter?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
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
                    return false
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SCAN_CODE && resultCode == RESULT_SCAN_CODE) {
            itemId = data!!.getIntExtra("itemId", -1)
//            warehouseId = data.getStringExtra("warehouseId")
            boxCodeList = data.getStringArrayListExtra("boxCodeList")
            carCodeArray.put(itemId, boxCodeList)
        }
    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(this@ReturnGoodsTodoDetailActivity, "保存中")
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

    companion object {

        private const val REQUEST_SCAN_CODE = 1
        private const val RESULT_SCAN_CODE = 2
    }

}
