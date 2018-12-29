package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.SparseArray
import android.view.KeyEvent
import android.view.View
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.presenter.material.MaterialUiObjInterface
import com.ty.zbpet.ui.adapter.material.MaterialTodoDetailAdapterR
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBLog
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main_detail_two.*
import kotlinx.android.synthetic.main.item_main_bottom_other.*
import okhttp3.RequestBody
import java.util.*

/**
 * 原辅料——到货入库详情、待办
 *
 *
 * MaterialUiObjInterface<MaterialTodoDetailsData> ：数据接口
 * MaterialTodoDetailAdapter.SaveEditListener      ：输入框接口
 * ScanBoxInterface                                ：扫码接口
</MaterialTodoDetailsData> *
 *
 * 【采用 RecycleView 嵌套 RecycleView 展示详情】
 *
 * @author TY
 */
class ArrivalInTodoDetailActivityR : BaseActivity(), MaterialUiObjInterface<MaterialDetails>, ScanBoxInterface {
    override val activityLayout: Int
        get() = R.layout.activity_main_detail_two

    private var adapter: MaterialTodoDetailAdapterR? = null
    private val list = ArrayList<MaterialDetails.ListBean>()

    private var sapOrderNo: String? = null
    private var supplierId: String? = null // 供应商 ID
    private var warehouseId: String? = null

    /**
     * 点击库位码输入框
     */
    private var currentFocus: Boolean? = false

    /**
     * list 中 Position
     */
    private var currentPosition = -1

    private val scanner = ScanReader.getScannerInstance()
    private val scan = ScanObservable(this)

    /**
     * 保存用户在输入框中的数据
     */
    private val bulkNumArray = DataUtils.getNumber()
    private val zkgArray = DataUtils.getZkg()
    private val carCodeArray = DataUtils.getCode()
    private val sapArray = DataUtils.getSap()

    /**
     * 库位码 ID
     */
    private val positionId: SparseArray<String> = SparseArray(10)

    private val materialPresenter = MaterialPresenter(this)

    override fun onBaseCreate(savedInstanceState: Bundle?) {}

    override fun initOneData() {

        sapOrderNo = intent.getStringExtra("sapOrderNo")
        supplierId = intent.getStringExtra("supplierId")

        materialPresenter.fetchTODOMaterialDetails(sapOrderNo)
    }

    override fun initTwoView() {

        initToolBar(R.string.label_purchase_detail, View.OnClickListener {
            ZBUiUtils.hideInputWindow(it.context, it)
            // 冲销入库
            doPurchaseInRecallOut(initParam()) })

        in_storage_detail.text = "到货明细"
    }


    private fun initParam(): RequestBody? {

        val requestBody = MaterialDetails()
        val detail = ArrayList<MaterialDetails.ListBean>()

        val size = list.size
        for (i in 0 until size) {
            val bulkNum = bulkNumArray.get(i)
            val carCode = carCodeArray.get(i)
            val sap = sapArray.get(i)
            val zkg = zkgArray.get(i)

            val id = positionId.get(i)

            val bean = MaterialDetails.ListBean()
            if (null != bulkNum && null != carCode) {

                bean.number = bulkNum
                bean.positionId = carCode
                bean.sapMaterialBatchNo = sap
                bean.positionId = id
                bean.ZKG = zkg
                bean.supplierId = supplierId
                bean.supplierNo = list[i].supplierNo
                bean.materialId = list[i].materialId
                bean.concentration = list[i].concentration

                detail.add(bean)
            }
        }

        // 没有合法的操作数据,不请求网络
        if (detail.size == 0) {
            ZBUiUtils.showToast("请完善您要入库的信息")
            return null
        }
        val remark = et_desc.text.toString().trim { it <= ' ' }
        val time = tv_time.text.toString().trim { it <= ' ' }

        requestBody.list = detail
        requestBody.warehouseId = warehouseId
        requestBody.inTime = time
        requestBody.sapOrderNo = sapOrderNo
        requestBody.remark = remark

        val json = DataUtils.toJson(requestBody, 1)
        ZBLog.e("JSON $json")
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

        HttpMethods.getInstance().materialTodoInSave(object : SingleObserver<ResponseInfo> {
            override fun onError(e: Throwable) {
                ZBUiUtils.showToast(e.message)
            }

            override fun onSubscribe(d: Disposable) {

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

    override fun detailObjData(obj: MaterialDetails) {

        list.clear()
        list.addAll(obj.list!!)
        val listBean = MaterialDetails.ListBean()
        listBean.tag = "Bottom"
        list.add(listBean)

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext(), LinearLayoutManager.VERTICAL, false)
            recycler_main!!.layoutManager = manager
            adapter = MaterialTodoDetailAdapterR(this, list)
            recycler_main!!.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
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
            val codeFocus = DataUtils.getCodeFocus()

            if (!TextUtils.isEmpty(codeFocus)) {
                val split = codeFocus
                        .split("@".toRegex())
                        .dropLastWhile { it.isEmpty() }
                        .toTypedArray()

                currentPosition = Integer.valueOf(split[0])
                currentFocus = java.lang.Boolean.valueOf(split[1])

                if (currentFocus!! && currentPosition != -1) {
                    // 扫描
                    scanner.open(applicationContext)

                    scan.scanBox(scanner, currentPosition)
                }
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * scan.scanBox 成功回调
     * 【情况 ① 】
     * 有焦点 扫码  http 校验
     *
     * @param positionNo
     */
    override fun ScanSuccess(position: Int, positionNo: String) {

        // http 校验
        materialPresenter.checkCarCode(position, positionNo)

    }

    override fun showCarSuccess(position: Int, carData: CarPositionNoData) {
        if (carData.count > 0) {
            val carId = carData.list!![0].id
            warehouseId = carData.list!![0].warehouseId
            positionId.put(position, carId)

            adapter!!.notifyItemChanged(position)
        } else {
            ZBUiUtils.showToast("请扫正确的库位码")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scanner.close()

        // 清除所有 SA 保存的数据
        DataUtils.clearId()
    }
}