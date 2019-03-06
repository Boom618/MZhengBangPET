package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.data.DeepCopyData
import com.ty.zbpet.data.SharedP
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.material.BackGoodsPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.adapter.diffadapter.TodoCarCodeDiffUtil
import com.ty.zbpet.ui.adapter.material.BackGoodsTodoDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.*
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
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
 * 采购退货 待办详情
 */
class BackGoodsTodoDetailActivity : BaseActivity()
        , MaterialUiListInterface<MaterialDetails.ListBean>
        , ScanBoxInterface {


    lateinit var adapter: BackGoodsTodoDetailAdapter

    private lateinit var selectTime: String
    private lateinit var sapOrderNo: String
    private lateinit var sapFirmNo: String
    private lateinit var supplierNo: String
    private lateinit var creatorNo: String
    private lateinit var content: String

    private var list: MutableList<MaterialDetails.ListBean> = ArrayList()

    private val scanner = ScanReader.getScannerInstance()
    private val scanObservable = ScanObservable(this)
    private val presenter = BackGoodsPresenter(this)

    /**
     * 库位码 ID
     */
    private val positionId: SparseArray<String> = SparseArray(10)

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
        supplierNo = intent.getStringExtra("supplierNo")
        creatorNo = intent.getStringExtra("creatorNo")
        content = intent.getStringExtra("content")

        presenter.fetchBackTodoListInfo(sapOrderNo,sapFirmNo,supplierNo)
    }

    override fun initTwoView() {

        initToolBar(R.string.back_goods, View.OnClickListener { view ->
            ZBUiUtils.hideInputWindow(view.context, view)
            backTodoSave(initTodoBody())
        })


        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time!!.text = selectTime
        in_storage_detail!!.text = "退货明细"


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
    private fun backTodoSave(body: RequestBody?) {

        if (body == null) {
            return
        }

        HttpMethods.getInstance().getBackTodoSave(object : SingleObserver<ResponseInfo> {
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
     * 构建保存 的 Body
     *
     * @return
     */
    private fun initTodoBody(): RequestBody? {

        val requestBody = MaterialDetails()
        val detail = ArrayList<MaterialDetails.ListBean>()

        val size = list.size
        for (i in 0 until size) {
            val view = rv_in_storage_detail.getChildAt(i)

            val concentration = view.findViewById<EditText>(R.id.bulk_num).text.toString().trim { it <= ' ' }
            val carCode = view.findViewById<EditText>(R.id.et_code).text.toString().trim { it <= ' ' }
            val bulkNum = view.findViewById<EditText>(R.id.et_number).text.toString().trim { it <= ' ' }
            val batchNo = view.findViewById<EditText>(R.id.et_batch_no).text.toString().trim { it <= ' ' }

            val id = positionId.get(i)

            val supplierId = list[i].supplierId
            val materialId = list[i].materialId
            //val supplierNo = list[i].supplierNo
            val zkg = list[i].ZKG

            val bean = MaterialDetails.ListBean()
            if (bulkNum.isNotEmpty() && carCode.isNotEmpty()) {

                bean.ZKG = zkg
                bean.positionId = id
                bean.materialId = materialId
                bean.supplierId = supplierId
                bean.unit = list[i].unit
                bean.materialNo = list[i].materialNo
                bean.materialName = list[i].materialName

                //bean.supplierNo = supplierNo
                bean.concentration = concentration
                // 新加 包含 sap 所有字段
                bean.content = list[i].content
                // 用户输入数据
                bean.positionNo = carCode
                bean.number = bulkNum
                bean.sapMaterialBatchNo = batchNo

                detail.add(bean)
            }
        }
        // 没有合法的操作数据,不请求网络
        if (detail.size == 0) {
            ZBUiUtils.showToast("请完善您要退货的信息")
            return null
        }

        val remark = et_desc.text.toString().trim { it <= ' ' }
        val time = tv_time.text.toString().trim { it <= ' ' }

        requestBody.list = detail
        requestBody.warehouseId = warehouseId
        requestBody.sapOrderNo = sapOrderNo
        requestBody.supplierNo = supplierNo
        requestBody.creatorNo = creatorNo
        requestBody.moveType = "101"
        requestBody.outTime = time
        requestBody.remark = remark


        val json = DataUtils.toJson(requestBody, 1)
        return RequestBodyJson.requestBody(json)
    }

    /**
     * 扫描
     *
     * @param keyCode
     * @param event
     * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == CodeConstant.KEY_CODE_131
                || keyCode == CodeConstant.KEY_CODE_135
                || keyCode == CodeConstant.KEY_CODE_139) {
            if (SharedP.getFocus(this) && SharedP.getPosition(this) != -1) {
                // 扫描
                doDeCode()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun doDeCode() {

        scanner.open(applicationContext)

        scanObservable.scanBox(scanner, SharedP.getPosition(this))

    }

    override fun ScanSuccess(position: Int, positionNo: String) {
        ZBUiUtils.showToast("库位码 ：$positionNo")

        //  服务器校验 库位码
        presenter.checkCarCode(position, positionNo)
    }


    override fun showCarSuccess(position: Int, carData: CarPositionNoData) {
        val count = carData.count
        if (count > 0) {
            val carId = carData.list!![0].id
            val positionNo = carData.list!![0].positionNo
            warehouseId = carData.list!![0].warehouseId
            positionId.put(position, carId)

            val deepCopyList = DeepCopyData.deepCopyList(list)

            deepCopyList[position].positionNo = positionNo

            val diffUtil = DiffUtil.calculateDiff(TodoCarCodeDiffUtil(list, deepCopyList))
            diffUtil.dispatchUpdatesTo(adapter)
        } else {
            ZBUiUtils.showToast("请扫正确的库位码")
        }
    }


    override fun showMaterial(lists: MutableList<MaterialDetails.ListBean>?) {

        list = lists!!

        val manager = LinearLayoutManager(ResourceUtil.getContext())
        rv_in_storage_detail.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        rv_in_storage_detail.layoutManager = manager
        adapter = BackGoodsTodoDetailAdapter(this, R.layout.item_material_detail_three_todo, list)
        rv_in_storage_detail.adapter = adapter

        adapter.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {

                val rlDetail = holder.itemView.findViewById<View>(R.id.gone_view)
                val ivArrow = holder.itemView.findViewById<ImageView>(R.id.iv_arrow)

                if (rlDetail.visibility == View.VISIBLE) {
                    rlDetail.visibility = View.GONE
                    ivArrow.setImageResource(R.mipmap.ic_collapse)
                } else {
                    rlDetail.visibility = View.VISIBLE
                    ivArrow.setImageResource(R.mipmap.ic_expand)
                }

                ZBUiUtils.hideInputWindow(view.context, view)
            }

            override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                return false
            }
        })
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun saveSuccess() {
        ZBUiUtils.showToast("成功")
        finish()
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showToast(msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedP.clearFocusAndPosition(this)
    }
}
