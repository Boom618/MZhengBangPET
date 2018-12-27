package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.SparseArray
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.material.MaterialDetailsIn
import com.ty.zbpet.bean.material.MaterialTodoSave
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.material.BackGoodsPresenter
import com.ty.zbpet.presenter.material.MaterialUiObjInterface
import com.ty.zbpet.ui.adapter.material.BackGoodsTodoDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBLog
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_content_row_two.*
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author TY on 2018/11/22.
 * 采购退货 待办详情
 */
class BackGoodsTodoDetailActivity : BaseActivity(), MaterialUiObjInterface<MaterialDetailsIn>, ScanBoxInterface, BackGoodsTodoDetailAdapter.SaveEditListener {


    private var adapter: BackGoodsTodoDetailAdapter? = null

    private var selectTime: String? = null
    private var sapOrderNo: String? = null

    private var list: ArrayList<MaterialDetailsIn.ListBean>? = ArrayList()

    /**
     * 点击库位码输入框
     */
    private var currentFocus: Boolean? = false

    /**
     * list 中 Position
     */
    private var currentPosition = -1

    private val scanner = ScanReader.getScannerInstance()
    private val scanObservable = ScanObservable(this)
    private val presenter = BackGoodsPresenter(this)

    /**
     * 保存用户在输入框中的数据
     */
    private val bulkNumArray: SparseArray<String> = SparseArray(10)
    private val carCodeArray: SparseArray<String> = SparseArray(10)
    private val batchNoArray: SparseArray<String> = SparseArray(10)
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

        presenter.fetchBackTodoListInfo(sapOrderNo)
    }

    override fun initTwoView() {


        initToolBar(R.string.back_goods, View.OnClickListener { view ->
            ZBUiUtils.hideInputWindow(view.context, view)
            pickOutTodoSave(initTodoBody())
        })

        findViewById<View>(R.id.add_ship).visibility = View.GONE

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
    private fun pickOutTodoSave(body: RequestBody?) {

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

        val requestBody = MaterialTodoSave()
        val detail = ArrayList<MaterialTodoSave.DetailsBean>()

        val size = list!!.size
        for (i in 0 until size) {
            val bulkNum = bulkNumArray.get(i)
            val carCode = carCodeArray.get(i)
            val batchNo = batchNoArray.get(i)
            val id = positionId.get(i)

            val supplierId = list!![i].supplierId
            val concentration = list!![i].concentration
            val materialId = list!![i].materialId
            val supplierNo = list!![i].supplierNo
            val zkg = list!![i].ZKG

            val bean = MaterialTodoSave.DetailsBean()
            if (!TextUtils.isEmpty(bulkNum) && !TextUtils.isEmpty(carCode)) {

                bean.ZKG = zkg
                bean.positionId = id
                bean.materialId = materialId
                bean.supplierId = supplierId

                bean.supplierNo = supplierNo
                bean.concentration = concentration
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

        val remark = et_desc!!.text.toString().trim { it <= ' ' }
        val time = tv_time!!.text.toString().trim { it <= ' ' }

        requestBody.details = detail
        requestBody.warehouseId = warehouseId
        requestBody.sapOrderNo = sapOrderNo
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
            if (currentFocus!! && currentPosition != -1) {
                // 扫描
                doDeCode()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun doDeCode() {

        scanner.open(applicationContext)

        scanObservable.scanBox(scanner, currentPosition)

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
            warehouseId = carData.list!![0].warehouseId
            positionId.put(position, carId)

            adapter!!.notifyItemChanged(position)
        } else {
            ZBUiUtils.showToast("请扫正确的库位码")
        }
    }


    override fun detailObjData(details: MaterialDetailsIn) {

        // list.clear();
        // 提交保存 需要用到
        list = details.list

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            rv_in_storage_detail!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            rv_in_storage_detail!!.layoutManager = manager
            adapter = BackGoodsTodoDetailAdapter(this, R.layout.item_material_detail_three_todo, list!!)
            rv_in_storage_detail!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
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
        } else {
            adapter!!.notifyDataSetChanged()
        }

    }


    /**
     * @param etType   输入框标识
     * @param hasFocus 有无焦点
     * @param position 位置
     * @param editText 内容
     */
    override fun saveEditAndGetHasFocusPosition(etType: String, hasFocus: Boolean?, position: Int, editText: EditText) {
        // 用户在 EditText 中输入的数据
        currentPosition = position

        val textContent = editText.text.toString().trim { it <= ' ' }

        when (etType) {
            CodeConstant.ET_BULK_NUM -> bulkNumArray.put(position, textContent)
            // 库位码 需要判断合法性
            CodeConstant.ET_CODE -> {
                currentFocus = hasFocus
                carCodeArray.put(position, textContent)
            }
            CodeConstant.ET_BATCH_NO -> batchNoArray.put(position, textContent)
        }
    }
}
