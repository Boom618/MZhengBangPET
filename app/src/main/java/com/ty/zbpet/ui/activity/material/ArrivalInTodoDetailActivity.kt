package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.presenter.material.MaterialUiObjInterface
import com.ty.zbpet.ui.adapter.material.MaterialTodoDetailAdapter
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
 * 原辅料——到货入库详情、待办
 *
 *
 * MaterialUiObjInterface<MaterialTodoDetailsData> ：数据接口
 * MaterialTodoDetailAdapter.SaveEditListener      ：输入框接口
 * ScanBoxInterface                                ：扫码接口
 *
 * @author TY
</MaterialTodoDetailsData> */
class ArrivalInTodoDetailActivity : BaseActivity(), MaterialUiObjInterface<MaterialDetailsIn>, MaterialTodoDetailAdapter.SaveEditListener, ScanBoxInterface {
    override val activityLayout: Int
        get() = R.layout.activity_content_row_two

    private var adapter: MaterialTodoDetailAdapter? = null
    private val list = ArrayList<MaterialDetailsIn.ListBean>()

    private var sapOrderNo: String? = null
    private var supplierId: String? = null // 供应商 ID
    private var warehouseId: String? = null

    /**
     * 时间选择
     */
    private var selectTime: String? = null

    /**
     * 点击库位码输入框
     */
    private var currentFocus: Boolean? = false

    /**
     * list 中 Position
     */
    private var currentPosition = -1

    private var disposable: Disposable? = null
    private val scanner = ScanReader.getScannerInstance()
    private val scan = ScanObservable(this)

    /**
     * 保存用户在输入框中的数据
     */
    private val bulkNumArray: SparseArray<String> = SparseArray(10)
    private val zkgArray: SparseArray<String> = SparseArray(10)
    private val carCodeArray: SparseArray<String> = SparseArray(10)
    private val batchNoArray: SparseArray<String> = SparseArray(10)

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

        sapOrderNo = intent.getStringExtra("sapOrderNo")
        supplierId = intent.getStringExtra("supplierId")

        materialPresenter.fetchTODOMaterialDetails(sapOrderNo)
    }

    override fun initTwoView() {

        initToolBar(R.string.label_purchase_detail, View.OnClickListener {
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
                ZBUiUtils.showToast(selectTime)
            }
        }
    }


    private fun initParam(): RequestBody? {

        val requestBody = MaterialTodoSave()
        val detail = ArrayList<MaterialTodoSave.DetailsBean>()

        val size = list.size
        for (i in 0 until size) {
            val bulkNum = bulkNumArray.get(i)
            val carCode = carCodeArray.get(i)
            val batchNo = batchNoArray.get(i)
            val zkg = zkgArray.get(i)

            val id = positionId.get(i)

            val bean = MaterialTodoSave.DetailsBean()
            if (null != bulkNum && null != carCode) {

                bean.number = bulkNum
                bean.positionId = carCode
                bean.sapMaterialBatchNo = batchNo
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
        val remark = et_desc!!.text.toString().trim { it <= ' ' }
        val time = tv_time!!.text.toString().trim { it <= ' ' }

        requestBody.details = detail
        requestBody.warehouseId = warehouseId
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
                    runOnUiThread { finish() }
                } else {
                    ZBUiUtils.showToast(responseInfo.message)
                }
            }
        }, body)
    }

    override fun detailObjData(obj: MaterialDetailsIn) {

        list.clear()
        list.addAll(obj.list!!)


        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            rv_in_storage_detail!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            rv_in_storage_detail!!.layoutManager = manager
            adapter = MaterialTodoDetailAdapter(this, R.layout.item_matterial_todo_detail, list)
            rv_in_storage_detail!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
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
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }


    override fun saveEditAndGetHasFocusPosition(etType: String, hasFocus: Boolean?, position: Int, editText: EditText) {
        // 用户在 EditText 中输入的数据
        currentPosition = position
        val textContent = editText.text.toString().trim { it <= ' ' }

        if (CodeConstant.ET_ZKG == etType) {
            zkgArray.put(position, textContent)
        } else if (CodeConstant.ET_BULK_NUM == etType) {
            bulkNumArray.put(position, textContent)

            // 库位码 需要判断合法性
        } else if (CodeConstant.ET_CODE == etType) {
            currentFocus = hasFocus

            carCodeArray.put(position, textContent)
        } else if (CodeConstant.ET_BATCH_NO == etType) {
            batchNoArray.put(position, textContent)

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

        disposable = scan.scanBox(scanner, currentPosition)

    }

    /**
     * scan.scanBox 成功回调
     * 【情况 ① 】
     * 有焦点 扫码  http 校验
     *
     * @param positionNo
     */
    override fun ScanSuccess(position: Int, positionNo: String) {
        ZBUiUtils.showToast("库位码 ：$positionNo")

        //  服务器校验 库位码
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

    }
}
