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
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.UserInfo
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.bean.product.ProductTodoSave
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.product.ProducePresenter
import com.ty.zbpet.presenter.product.ProductUiObjInterface
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.adapter.product.ProductTodoDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBLog
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_content_row_three.*
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

/**
 * 生产入库 待办详情
 *
 * @author TY
 */
class ProductTodoDetailActivity : BaseActivity(), ProductUiObjInterface<ProductDetails>, ProductTodoDetailAdapter.SaveEditListener {

    private var adapter: ProductTodoDetailAdapter? = null

    private var selectTime: String? = null
    private var sapOrderNo: String? = null

    private var oldList: List<ProductDetails.ListBean>? = ArrayList()
//    private val houseList = ArrayList<ProductTodoDetails.WarehouseListBean>()

    private val presenter = ProducePresenter(this)

    /**
     * 用户信息
     */
    private var userInfo: UserInfo? = null

    /**
     * 保存用户在输入框中的数据
     */
    private val numberArray:SparseArray<String> = SparseArray(10)
    private val contentArray:SparseArray<String> = SparseArray(10)
    private val startCodeArray:SparseArray<String> = SparseArray(10)
    private val endCodeArray:SparseArray<String> = SparseArray(10)
    private val sapArray:SparseArray<String> = SparseArray(10)
    private val carCodeArray:SparseArray<ArrayList<String>> = SparseArray(10)
    /**
     * 库位码 ID
     */
    private val positionId:SparseArray<String> = SparseArray(10)


    /**
     * 列表 ID
     */
    private var itemId = -1

    /**
     * 箱码
     */
    private var boxCodeList = ArrayList<String>()


    /**
     * 仓库 name
     */
    private val houseName = ArrayList<String>()

    override val activityLayout: Int
        get() = R.layout.activity_content_row_three

    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {
        sapOrderNo = intent.getStringExtra("sapOrderNo")

        // TODO　仓库默认值设置
        DataUtils.setHouseId(0, 0)

        userInfo = DataUtils.getUserInfo()
        val warehouseList = userInfo!!.warehouseList

        val size = warehouseList!!.size
        for (i in 0 until size) {
            houseName.add(warehouseList[i].warehouseName.toString())
        }
        tv_house!!.text = houseName[0]

        presenter.fetchProductTodoInfo(sapOrderNo)
    }

    override fun initTwoView() {

        initToolBar(R.string.label_produce_in_storage, View.OnClickListener { productTodoSave(initTodoBody()) })

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time!!.text = selectTime
        in_storage_detail!!.text = "入库明细"


        tv_time!!.setOnClickListener { v ->
            ZBUiUtils.showPickDate(v.context) { date, _ ->
                selectTime = ZBUiUtils.getTime(date)
                tv_time!!.text = selectTime

                ZBUiUtils.showToast(selectTime)
            }
        }

        // 用户选择仓库信息
        tv_house!!.setOnClickListener { v -> ZBUiUtils.selectDialog(v.context, CodeConstant.SELECT_HOUSE_BUY_IN, 0, houseName, tv_house) }

    }

    /**
     * 出库 保存
     *
     * @param body 参数
     */
    private fun productTodoSave(body: RequestBody?) {

        if (body == null) {
            return
        }

        HttpMethods.getInstance().getProduceTodoSave(object : SingleObserver<ResponseInfo> {
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

        // TODO 获取用户选择的仓库信息
        val houseId = DataUtils.getHouseId()

        val warehouseList = userInfo!!.warehouseList

        // 仓库信息
        val warehouseId: String?
        val warehouseNo: String?
        val warehouseName: String?
        if (houseId == null) {
            warehouseId = warehouseList!![0].warehouseId
            warehouseNo = warehouseList[0].warehouseNo
            warehouseName = warehouseList[0].warehouseName
        } else {
            val which = houseId.get(0)
            warehouseId = warehouseList!![which!!].warehouseId
            warehouseNo = warehouseList[which].warehouseNo
            warehouseName = warehouseList[which].warehouseName
        }

        val size = oldList!!.size
        for (i in 0 until size) {
            val boxQrCode = carCodeArray.get(i)
            val bulkNum = numberArray.get(i)
            val content = contentArray.get(i)
            val startCode = startCodeArray.get(i)
            val endCode = endCodeArray.get(i)
            val sap = sapArray.get(i)

            val id = positionId.get(i)

            val bean = ProductTodoSave.DetailsBean()
            if (!TextUtils.isEmpty(bulkNum) && boxQrCode != null) {

                val goodsId = oldList!![i].goodsId

                bean.positionId = id
                bean.number = bulkNum
                bean.content = content

                bean.sapMaterialBatchNo = sap
                bean.startQrCode = startCode
                bean.endQrCode = endCode

                bean.goodsId = goodsId
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

        requestBody.details = detail
        requestBody.warehouseId = warehouseId
        requestBody.warehouseNo = warehouseNo
        requestBody.warehouseName = warehouseName
        requestBody.sapOrderNo = sapOrderNo
        requestBody.inTime = time
        requestBody.remark = remark

        val json = DataUtils.toJson(requestBody, 1)
        ZBLog.e("JSON $json")
        return RequestBodyJson.requestBody(json)
    }


    override fun detailObjData(details: ProductDetails) {

        oldList = details.list

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            rv_in_storage_detail!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            rv_in_storage_detail!!.layoutManager = manager
            adapter = ProductTodoDetailAdapter(this, R.layout.item_produce_detail_todo, oldList)
            rv_in_storage_detail!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {

                    val rlDetail = holder.itemView.findViewById<View>(R.id.rl_detail)
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
        } else {
            adapter!!.notifyDataSetChanged()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SCAN_CODE && resultCode == RESULT_SCAN_CODE) {
            itemId = data!!.getIntExtra("itemId", -1)
            boxCodeList = data.getStringArrayListExtra("boxCodeList")
            carCodeArray.put(itemId, boxCodeList)
        }
    }

    override fun saveEditAndGetHasFocusPosition(etType: String, hasFocus: Boolean?, position: Int, editText: EditText) {

        val textContent = editText.text.toString().trim { it <= ' ' }

        when (etType) {
            CodeConstant.ET_NUMBER -> numberArray.put(position, textContent)
            CodeConstant.ET_CONTENT -> contentArray.put(position, textContent)
            CodeConstant.ET_BATCH_NO -> sapArray.put(position, textContent)
            CodeConstant.ET_START_CODE -> startCodeArray.put(position, textContent)
            CodeConstant.ET_END_CODE -> endCodeArray.put(position, textContent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        // TODO 清除仓库数据
        DataUtils.clearId()
    }

    companion object {

        private val REQUEST_SCAN_CODE = 1
        private val RESULT_SCAN_CODE = 2
    }
}
