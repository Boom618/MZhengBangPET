package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.data.DeepCopyData
import com.ty.zbpet.data.SharedP
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.adapter.diffadapter.TodoCarCodeDiffUtil
import com.ty.zbpet.ui.adapter.material.MaterialTodoDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
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
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_content_row_two.*
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

/**
 * 原辅料——到货入库详情、待办
 * @author TY
 */
class ArrivalInTodoDetailActivity : BaseActivity()
        , MaterialUiListInterface<MaterialDetails.ListBean>
        , ScanBoxInterface {

    override val activityLayout: Int
        get() = R.layout.activity_content_row_two

    private lateinit var adapter: MaterialTodoDetailAdapter
    private var list = mutableListOf<MaterialDetails.ListBean>()

    private lateinit var sapOrderNo: String
    private lateinit var supplierNo: String
    private lateinit var supplierName: String
    private lateinit var creatorNo: String
    private lateinit var sapFirmNo: String
    private lateinit var content: String
    private lateinit var warehouseId: String
    private lateinit var warehouseNo: String
    /**
     * 时间选择
     */
    private lateinit var selectTime: String

    private lateinit var disposable: Disposable
    private val scanner = ScanReader.getScannerInstance()
    private val scan = ScanObservable(this)

    /**
     * 库位码 ID
     */
    private val positionId: SparseArray<String> = SparseArray(10)

    private val materialPresenter = MaterialPresenter(this)

    override fun onBaseCreate(savedInstanceState: Bundle?) {
        val sdf = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = sdf.format(Date())
        tv_time!!.text = selectTime

    }

    override fun initOneData() {

        sapFirmNo = intent.getStringExtra("sapFirmNo")
        sapOrderNo = intent.getStringExtra("sapOrderNo")
        supplierNo = intent.getStringExtra("supplierNo")
        supplierName = intent.getStringExtra("supplierName")
        creatorNo = intent.getStringExtra("creatorNo")
        content = intent.getStringExtra("content")

        materialPresenter.fetchTODOMaterialDetails(sapFirmNo, sapOrderNo, supplierNo)
    }

    override fun initTwoView() {

        initToolBar(R.string.label_purchase_detail, "保存", View.OnClickListener {
            ZBUiUtils.hideInputWindow(it.context, it)
            doPurchaseInRecallOut(initParam())
        })

        val titleName = findViewById<TextView>(R.id.in_storage_detail)
        titleName.text = "到货明细"

        tv_time!!.setOnClickListener {
            ZBUiUtils.showPickDate(it.context) { date, _ ->
                //选中事件回调
                selectTime = ZBUiUtils.getTime(date)
                tv_time!!.text = selectTime
                ZBUiUtils.showSuccess(selectTime)
            }
        }
    }


    private fun initParam(): RequestBody? {

        val requestBody = MaterialDetails()
        val detail = ArrayList<MaterialDetails.ListBean>()

        val size = list.size
        for (i in 0 until size) {

            val view = rv_in_storage_detail.getChildAt(i)

            var viewUnit = "KG"
            val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
            for (position in 0 until 3) {
                val radioButton = radioGroup.getChildAt(position) as RadioButton
                if (radioButton.isChecked) {
                    viewUnit = radioButton.text.toString()
                }
            }

            // 含量手输
            val concentration = view.findViewById<EditText>(R.id.tv_box_num).text.toString().trim { it <= ' ' }
            val viewCode = view.findViewById<EditText>(R.id.et_code).text.toString().trim { it <= ' ' }
            val viewNumber = view.findViewById<EditText>(R.id.et_bulk_num).text.toString().trim { it <= ' ' }
            val viewSap = view.findViewById<EditText>(R.id.et_batch_no).text.toString().trim { it <= ' ' }

            val id = positionId.get(i)

            val bean = MaterialDetails.ListBean()
            if (viewNumber.isNotEmpty() && viewCode.isNotEmpty()) {

                val subContent = list[i].content!!
                val mergeContent = JsonStringMerge().StringMerge(subContent, content)

                bean.concentration = concentration
                bean.number = viewNumber
                bean.positionNo = viewCode
                bean.sapMaterialBatchNo = viewSap
                bean.positionId = id
                bean.warehouseNo = list[i].warehouseNo
                bean.supplierNo = list[i].supplierNo
                bean.materialId = list[i].materialId

                bean.content = mergeContent
                bean.sapFirmNo = list[i].sapFirmNo
                bean.inNumber = list[i].inNumber
                bean.materialName = list[i].materialName
                bean.materialNo = list[i].materialNo
                bean.unit = viewUnit

                detail.add(bean)
            }
        }

        // 没有合法的操作数据,不请求网络
        if (detail.size == 0) {
            ZBUiUtils.showWarning("请完善您要入库的信息")
            return null
        }
        val remark = et_desc!!.text.toString().trim { it <= ' ' }
        val time = tv_time!!.text.toString().trim { it <= ' ' }

        requestBody.list = detail
//        requestBody.warehouseId = warehouseId
//        requestBody.warehouseNo = warehouseNo
        requestBody.supplierNo = supplierNo
        requestBody.supplierName = supplierName
        requestBody.creatorNo = creatorNo
        requestBody.moveType = "105"
        requestBody.inTime = time
        requestBody.sapOrderNo = sapOrderNo
        requestBody.remark = remark


        val json = DataUtils.toJson(requestBody, 1)
        return RequestBodyJson.requestBody(json)
    }

    /**
     * 入库保存
     *
     * @param body
     */
    private fun doPurchaseInRecallOut(body: RequestBody?) {

        if (body == null) {
            return
        }
        materialPresenter.materialTodoInSave(body)
    }

    override fun showMaterial(lists: MutableList<MaterialDetails.ListBean>?) {

        list = lists!!

        val manager = LinearLayoutManager(ResourceUtil.getContext())
        rv_in_storage_detail.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
        rv_in_storage_detail.layoutManager = manager
        adapter = MaterialTodoDetailAdapter(this, R.layout.item_matterial_todo_detail, list)
        rv_in_storage_detail.adapter = adapter

        adapter.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {

                val rlDetail = holder.itemView.findViewById<View>(R.id.rl_detail)
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

        scanner?.open(applicationContext)

        disposable = scan.scanBox(scanner, SharedP.getPosition(this))

    }

    /**
     * scan.scanBox 成功回调
     * 【情况 ① 】
     * 有焦点 扫码  http 校验
     *
     * @param positionNo
     */
    override fun ScanSuccess(position: Int, positionNo: String) {

        //  服务器校验 库位码
        val warehouseNo = list[position].warehouseNo
        materialPresenter.checkCarCode(position, positionNo, warehouseNo)

    }

    override fun showCarSuccess(position: Int, carData: CarPositionNoData) {
        if (carData.count > 0) {
            val carId = carData.list!![0].id
            val positionNo = carData.list!![0].positionNo
            warehouseId = carData.list!![0].warehouseId!!
            // 扫库位码的 仓库编号
            warehouseNo = carData.list!![0].warehouseNo!!
            // 入库仓库编号
            val rawHouseNo = list[position].warehouseNo
            if (warehouseNo != rawHouseNo) {
                ZBUiUtils.showWarning("该库位是 $warehouseNo 请扫 $rawHouseNo 的库位码")
                return
            }
            positionId.put(position, carId)

            val deepCopyList = DeepCopyData.deepCopyList(list)

            deepCopyList[position].positionNo = positionNo

            val diffUtil = DiffUtil.calculateDiff(TodoCarCodeDiffUtil(list, deepCopyList))
            diffUtil.dispatchUpdatesTo(adapter)

        } else {
            ZBUiUtils.showWarning("请扫正确的库位码")
        }
    }

    override fun saveSuccess() {
        finish()
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showError(msg)
    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(this@ArrivalInTodoDetailActivity, "保存中")
    }

    override fun hideLoading() {
        dialog?.close()
    }


    override fun onDestroy() {
        super.onDestroy()
        scanner?.close()
        SharedP.clearFocusAndPosition(this)

    }
}
