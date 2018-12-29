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
import com.ty.zbpet.bean.UserInfo
import com.ty.zbpet.bean.product.ProductDetailsIn
import com.ty.zbpet.bean.product.ProductTodoSave
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.presenter.product.BuyInPresenter
import com.ty.zbpet.presenter.product.ProductUiListInterface
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.adapter.product.BuyInTodoDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.SimpleCache
import com.ty.zbpet.util.ZBLog
import com.ty.zbpet.util.ZBUiUtils
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
 * @author TY on 2018/11/22.
 * 外采入库 待办详情
 */
class BuyInTodoDetailActivity : BaseActivity(), ProductUiListInterface<ProductDetailsIn.ListBean>, BuyInTodoDetailAdapter.SaveEditListener {

    private var adapter: BuyInTodoDetailAdapter? = null

    private var selectTime: String? = null
    private var sapOrderNo: String? = null

    private var oldList: List<ProductDetailsIn.ListBean> = ArrayList()

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
     * 保存用户在输入框中的数据
     */
    private val bulkNumArray: SparseArray<String> = SparseArray(10)
    private val batchNoArray: SparseArray<String> = SparseArray(10)
    private val startCodeArray: SparseArray<String> = SparseArray(10)
    private val endCodeArray: SparseArray<String> = SparseArray(10)
    private val carCodeArray: SparseArray<ArrayList<String>> = SparseArray(10)
    /**
     * 库位码 ID
     */
    private val positionId: SparseArray<String> = SparseArray(10)

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
        supplierId = intent.getStringExtra("supplierId")

        userInfo = SimpleCache.getUserInfo(CodeConstant.USER_DATA)
        // TODO　仓库默认值设置　
        DataUtils.setHouseId(0, 0)

        presenter.fetchBuyInTodoListDetails(sapOrderNo)
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

        val houseId = DataUtils.getHouseId()
        val warehouseList = userInfo!!.warehouseList

        val size = oldList.size
        for (i in 0 until size) {
            val boxQrCode = carCodeArray.get(i)
            val bulkNum = bulkNumArray.get(i)
            val batchNo = batchNoArray.get(i)
            val id = positionId.get(i)

            // 仓库信息
            val warehouseId: String?
            val warehouseNo: String?
            val warehouseName: String?

            // houseId == null ： 是判断用户全部没有选择仓库信息,默认都是第一个，
            // houseId.get(i) == null : 是判断用户部分没选择仓库信息默认第一个
            if (houseId == null || houseId.get(i) == null) {
                warehouseId = warehouseList!![0].warehouseId
                warehouseNo = warehouseList[0].warehouseNo
                warehouseName = warehouseList[0].warehouseName
            } else {
                val which = houseId.get(i)
                warehouseId = warehouseList!![which!!].warehouseId
                warehouseNo = warehouseList[which].warehouseNo
                warehouseName = warehouseList[which].warehouseName
            }

            val bean = ProductTodoSave.DetailsBean()
            if (!TextUtils.isEmpty(bulkNum) && boxQrCode != null) {
                val goodsId = oldList[i].goodsId
                val goodsNo = oldList[i].goodsNo
                val orderNumber = oldList[i].orderNumber

                val startCode = startCodeArray.get(i)
                val endCode = endCodeArray.get(i)

                // 库位 ID
                bean.positionId = id
                // 入库数量
                bean.number = bulkNum
                bean.sapMaterialBatchNo = batchNo

                bean.goodsId = goodsId
                bean.goodsNo = goodsNo

                bean.startQrCode = startCode
                bean.endQrCode = endCode
                bean.orderNumber = orderNumber

                bean.warehouseId = warehouseId
                bean.warehouseNo = warehouseNo
                bean.warehouseName = warehouseName

                bean.boxQrCode = boxQrCode

                detail.add(bean)
            } else {
                // 跳出当前一列、不处理
                continue
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
        requestBody.inTime = time
        requestBody.sapOrderNo = sapOrderNo
        requestBody.remark = remark

        val json = DataUtils.toJson(requestBody, 1)
        ZBLog.e("JSON $json")
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json)
    }


    override fun showProduct(list: List<ProductDetailsIn.ListBean>) {

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
                    val tvName = holder.itemView.findViewById<TextView>(R.id.tv_name)
                    val selectHouse = holder.itemView.findViewById<TextView>(R.id.tv_select_ware)

                    val houses = userInfo!!.warehouseList
                    //                    List<ProductDetailsIn.ListBean.WarehouseListBean> houses = list.get(position).getWarehouseList();
                    val houseName = ArrayList<String>()

                    val size = houses!!.size
                    for (i in 0 until size) {
                        houseName.add(houses[i].warehouseName.toString())
                    }

                    selectHouse.setOnClickListener { ZBUiUtils.selectDialog(view.context, CodeConstant.SELECT_HOUSE_BUY_IN, position, houseName, selectHouse) }

                    //SparseArray<Integer> houseId = DataUtils.getHouseId();
                    // 获取当前 item 中，用户选择的是哪个 仓库位置 ID（先不显示）
                    //                    Integer which = houseId.get(position);
                    //                    String warehouseName = warehouseList.get(which).getWarehouseName();
                    if (rlDetail.visibility == View.VISIBLE) {
                        rlDetail.visibility = View.GONE
                        ivArrow.setImageResource(R.mipmap.ic_collapse)
                        //                        tvName.setText(warehouseName);
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
                        intent.putStringArrayListExtra("boxCodeList",boxCodeList)

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

    /**
     * 保存用户在列表中输入的信息
     *
     * @param etType   输入框标识
     * @param hasFocus 有无焦点
     * @param position 位置
     * @param editText 控件
     */
    override fun saveEditAndGetHasFocusPosition(etType: String, hasFocus: Boolean?, position: Int, editText: EditText) {

        val textContent = editText.text.toString().trim { it <= ' ' }

        if (CodeConstant.ET_BULK_NUM == etType) {
            bulkNumArray.put(position, textContent)
        } else if (CodeConstant.ET_BATCH_NO == etType) {
            batchNoArray.put(position, textContent)
        } else if (CodeConstant.ET_START_CODE == etType) {
            startCodeArray.put(position, textContent)
        } else if (CodeConstant.ET_END_CODE == etType) {
            endCodeArray.put(position, textContent)
        }

    }


    override fun onDestroy() {
        super.onDestroy()

        // TODO  清除仓库数据
        DataUtils.clearId()
    }

    companion object {

        private val REQUEST_SCAN_CODE = 1
        private val RESULT_SCAN_CODE = 2
    }
}
