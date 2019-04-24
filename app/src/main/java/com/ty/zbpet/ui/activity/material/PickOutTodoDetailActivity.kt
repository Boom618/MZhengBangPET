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
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.data.DeepCopyData
import com.ty.zbpet.data.SharedP
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.presenter.material.PickOutPresenter
import com.ty.zbpet.ui.adapter.diffadapter.TodoCarCodeDiffUtil
import com.ty.zbpet.ui.adapter.material.PickingTodoDetailAdapter
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.JsonStringMerge
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.activity_content_row_two.*
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author TY on 2018/11/22.
 * 领料出库 待办详情
 */
class PickOutTodoDetailActivity : BaseActivity()
        , MaterialUiListInterface<MaterialDetails.ListBean>
        , ScanBoxInterface {


    private lateinit var adapter: PickingTodoDetailAdapter

    private lateinit var sign: String
    private lateinit var orderTime: String
    private lateinit var selectTime: String
    private lateinit var sapOrderNo: String
    private lateinit var sapFirmNo: String
    private lateinit var content: String

    private lateinit var warehouseId: String
    private lateinit var warehouseNo: String
    private var list: MutableList<MaterialDetails.ListBean> = mutableListOf()

    private val scanner = ScanReader.getScannerInstance()
    private val scanObservable = ScanObservable(this)
    private val presenter = PickOutPresenter(this)

    /**
     * 库位码 ID
     */
    private val positionId: SparseArray<String> = SparseArray(10)
    private val houseNo: SparseArray<String> = SparseArray(10)

    override val activityLayout: Int
        get() = R.layout.activity_content_row_two

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
        sapOrderNo = intent.getStringExtra("sapOrderNo")
        sapFirmNo = intent.getStringExtra("sapFirmNo")
        orderTime = intent.getStringExtra("orderTime")
        content = intent.getStringExtra("content")
        sign = intent.getStringExtra("sign")

        presenter.fetchPickOutTodoListDetails(sign, sapOrderNo, sapFirmNo, orderTime)
    }

    override fun initTwoView() {

        initToolBar(R.string.material_pick_out_details, "保存", View.OnClickListener { view ->
            ZBUiUtils.hideInputWindow(view.context, view)
            pickOutTodoSave(initTodoBody())
        })

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time.text = selectTime

        tv_time.setOnClickListener { v ->
            ZBUiUtils.showPickDate(v.context) { date, _ ->
                selectTime = ZBUiUtils.getTime(date)
                tv_time.text = selectTime

                ZBUiUtils.showSuccess(selectTime)
            }
        }

    }

    /**
     * 出库 保存
     */
    private fun pickOutTodoSave(body: RequestBody?) {

        if (body == null) {
            return
        }
        presenter.pickOutTodoSave(body)
    }

    private fun initTodoBody(): RequestBody? {

        val requestBody = MaterialDetails()
        val detail = ArrayList<MaterialDetails.ListBean>()

        val size = list.size
        for (i in 0 until size) {
            val view = rv_in_storage_detail.getChildAt(i)

            //val concentration = view.findViewById<EditText>(R.id.bulk_num).text.toString().trim { it <= ' ' }
            val carCode = view.findViewById<EditText>(R.id.et_code).text.toString().trim { it <= ' ' }
            val bulkNum = view.findViewById<EditText>(R.id.et_number).text.toString().trim { it <= ' ' }
            //val batchNo = view.findViewById<EditText>(R.id.et_batch_no).text.toString().trim { it <= ' ' }
            val materialId = list[i].materialId
            val supplierNo = list[i].supplierNo

            val id = positionId.get(i)
            val no = houseNo.get(i)

            val bean = MaterialDetails.ListBean()
            if (bulkNum.isNotEmpty() && carCode.isNotEmpty()) {

                val subContent = list[i].content!!
                val mergeContent = JsonStringMerge().StringMerge(subContent, content)

                bean.positionId = id
                bean.unit = list[i].unit
                bean.supplierNo = supplierNo
                bean.materialId = materialId
                bean.content = mergeContent
                bean.warehouseNo = no
                bean.materialNo = list[i].materialNo
                bean.materialName = list[i].materialName
                //bean.concentration = concentration
                // 用户输入数据
                bean.positionNo = carCode
                bean.number = bulkNum
                //bean.sapMaterialBatchNo = batchNo

                detail.add(bean)
            }
        }
        // 没有合法的操作数据,不请求网络
        if (detail.size == 0) {
            ZBUiUtils.showWarning("请完善您要出库的信息")
            return null
        }

        val remark = et_desc!!.text.toString().trim { it <= ' ' }
        val time = tv_time!!.text.toString().trim { it <= ' ' }

        requestBody.list = detail
//        requestBody.warehouseId = warehouseId
//        requestBody.warehouseNo = warehouseNo
        requestBody.outTime = time
        requestBody.moveType = "261"
        requestBody.sapOrderNo = sapOrderNo
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

        //  服务器校验 库位码
//        presenter.urlAnalyze(position, positionNo)
        val warehouseNo = list[position].warehouseNo
        presenter.checkCarCode(position, positionNo,warehouseNo)
    }


    override fun showCarSuccess(position: Int, carData: CarPositionNoData) {
        if (carData.count > 0) {
            val carId = carData.list!![0].id
            val positionNo = carData.list!![0].positionNo
            warehouseId = carData.list!![0].warehouseId!!
            warehouseNo = carData.list!![0].warehouseNo!!
            val rawHouseNo = list[position].warehouseNo
            if (warehouseNo != rawHouseNo) {
                ZBUiUtils.showWarning("该库位是 $rawHouseNo 与扫码库位 $warehouseNo 不一致")
            }
            positionId.put(position, carId)
            houseNo.put(position, warehouseNo)

            val deepCopyList = DeepCopyData.deepCopyList(list)

            deepCopyList[position].positionNo = positionNo

            val diffUtil = DiffUtil.calculateDiff(TodoCarCodeDiffUtil(list, deepCopyList))
            diffUtil.dispatchUpdatesTo(adapter)
        } else {
            ZBUiUtils.showWarning("请扫正确的库位码")
        }
    }


    override fun showMaterial(lists: MutableList<MaterialDetails.ListBean>) {

        list = lists
        val manager = LinearLayoutManager(ResourceUtil.getContext())
        rv_in_storage_detail.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
        rv_in_storage_detail.layoutManager = manager
        adapter = PickingTodoDetailAdapter(this, R.layout.item_material_detail_three_todo, list)
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

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(this@PickOutTodoDetailActivity, "保存中")
    }

    override fun hideLoading() {
        dialog?.close()
    }

    override fun saveSuccess() {
        ZBUiUtils.showSuccess("成功")
        finish()
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showError(msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedP.clearFocusAndPosition(this)
    }
}
