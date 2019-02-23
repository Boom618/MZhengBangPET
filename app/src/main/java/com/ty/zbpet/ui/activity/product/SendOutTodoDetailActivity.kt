//package com.ty.zbpet.ui.activity.product
//
//import android.content.Intent
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.text.TextUtils
//import android.util.SparseArray
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.TextView
//
//import com.ty.zbpet.R
//import com.ty.zbpet.bean.ResponseInfo
//import com.ty.zbpet.bean.product.ProductDetails
//import com.ty.zbpet.bean.product.ProductTodoSave
//import com.ty.zbpet.net.HttpMethods
//import com.ty.zbpet.presenter.product.ProductUiListInterface
//import com.ty.zbpet.presenter.product.SendOutPresenter
//import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
//import com.ty.zbpet.ui.adapter.product.SendOutTodoDetailAdapter
//import com.ty.zbpet.ui.base.BaseActivity
//import com.ty.zbpet.ui.widght.SpaceItemDecoration
//import com.ty.zbpet.constant.CodeConstant
//import com.ty.zbpet.net.RequestBodyJson
//import com.ty.zbpet.util.DataUtils
//import com.ty.zbpet.util.ResourceUtil
//import com.ty.zbpet.util.ZBLog
//import com.ty.zbpet.util.ZBUiUtils
//import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
//
//import java.text.SimpleDateFormat
//import java.util.ArrayList
//import java.util.Date
//import java.util.Locale
//
//import io.reactivex.SingleObserver
//import io.reactivex.disposables.Disposable
//import kotlinx.android.synthetic.main.activity_content_row_two.*
//import okhttp3.RequestBody
//
///**
// * 发货出库 待办详情
// *
// * @author TY
// */
//class SendOutTodoDetailActivity : BaseActivity(), ProductUiListInterface<ProductDetails.ListBean> {
//
//    private var adapter: SendOutTodoDetailAdapter? = null
//
//    private var selectTime: String? = null
//    private var sapOrderNo: String? = null
//    private var sapFirmNo: String? = null
//
//    /**
//     * 生产、客户、成品 信息
//     */
//    private var productInfo: String? = null
//    private var customerInfo: String? = null
//    private var goodsInfo: String? = null
//
//    /**
//     * 商品种类 原数据
//     */
//    private val rawData = ArrayList<ProductDetails.ListBean>()
//
//    private var oldList: MutableList<ProductDetails.ListBean> = ArrayList()
//    private val newList = ArrayList<ProductDetails.ListBean>()
//
//    private val presenter = SendOutPresenter(this)
//
//    /**
//     * 箱码
//     */
//    private var boxCodeList = ArrayList<String>()
//
//    /**
//     * 列表 ID
//     */
//    private var itemId = -1
//
//    /**
//     * 保存用户在输入框中的数据
//     */
//    private val numberArray: SparseArray<String> = SparseArray(10)
//    private val startCodeArray: SparseArray<String> = SparseArray(10)
//    private val endCodeArray: SparseArray<String> = SparseArray(10)
//    private val sapArray: SparseArray<String> = SparseArray(10)
//    private val carCodeArray: SparseArray<ArrayList<String>> = SparseArray(10)
//    /**
//     * 库位码 ID
//     */
//    private val positionId: SparseArray<String> = SparseArray(10)
//
//    /**
//     * 仓库 ID
//     */
//    private var warehouseId: String? = null
//
//    /**
//     * 商品种类 用户 下拉选择
//     */
//    private var goodsName: MutableList<String>? = null
//
//    override val activityLayout: Int
//        get() = R.layout.activity_content_row_two
//
//
//    override fun onBaseCreate(savedInstanceState: Bundle?) {
//
//    }
//
//    override fun initOneData() {
//        sapOrderNo = intent.getStringExtra("sapOrderNo")
//        sapFirmNo = intent.getStringExtra("sapFirmNo")
//
//        productInfo = intent.getStringExtra("productInfo")
//        customerInfo = intent.getStringExtra("customerInfo")
//        goodsInfo = intent.getStringExtra("goodsInfo")
//
//        presenter.fetchSendOutTodoInfo(sapOrderNo)
//    }
//
//    override fun initTwoView() {
//
//        initToolBar(R.string.label_send_out_storage, View.OnClickListener { view ->
//            ZBUiUtils.hideInputWindow(view.context, view)
//            sendOutTodoSave(initTodoBody())
//        })
//
//        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
//        selectTime = format.format(Date())
//
//        tv_time!!.text = selectTime
//        in_storage_detail!!.text = "发货明细"
//
//        tv_time!!.setOnClickListener { v ->
//            ZBUiUtils.showPickDate(v.context) { date, _ ->
//                selectTime = ZBUiUtils.getTime(date)
//                tv_time!!.text = selectTime
//
//                ZBUiUtils.showToast(selectTime)
//            }
//        }
//
//        add_ship.visibility = View.VISIBLE
//
//        add_ship!!.setOnClickListener {
//            val rawSize = rawData.size
//            val oldSize = oldList.size
//            if (oldSize < rawSize) {
////                adapter.notifyItemRangeChanged()
//                // 有列表删除操作 ，保证 newList 只有 oldList 中的数据 + 添加的一个数据
//                newList.clear()
//                newList.addAll(oldList)
//                val bean = ProductDetails.ListBean()
//                val info = rawData[0]
//
//                bean.sapOrderNo = info.sapOrderNo
//                bean.goodsName = info.goodsName
//                bean.goodsId = info.goodsId
//                bean.goodsNo = info.goodsNo
//                bean.unit = info.unit
//                bean.orderNumber = info.orderNumber
//                newList.add(bean)
//                //adapter!!.notifyItemRangeChanged(oldSize-1,oldSize - 1)
//
//                // 插入在最后 不需要 ：notifyItemRangeChanged
//                adapter!!.notifyItemInserted(newList.size - 1)
//                adapter!!.notifyItemRangeChanged(newList.size - 1, newList.size)
////                val diffResult = DiffUtil.calculateDiff(SendOutDiffUtil(oldList, newList))
////                diffResult.dispatchUpdatesTo(adapter!!)
//
//                // TODO　清除老数据,更新原数据,清除临时保存数据
//                oldList.clear()
//                oldList.addAll(newList)
//                //newList.clear();
//
//                ZBUiUtils.showToast("添加发货出库")
//            } else {
//                ZBUiUtils.showToast("谢谢")
//            }
//        }
//
//
//    }
//
//    /**
//     * 出库 保存
//     */
//    private fun sendOutTodoSave(body: RequestBody?) {
//
//        if (body == null) {
//            return
//        }
//
//        HttpMethods.getInstance().getShipTodoSave(object : SingleObserver<ResponseInfo> {
//
//            override fun onSubscribe(d: Disposable) {
//
//            }
//
//            override fun onError(e: Throwable) {
//                ZBUiUtils.showToast(e.message)
//            }
//
//            override fun onSuccess(responseInfo: ResponseInfo) {
//                if (CodeConstant.SERVICE_SUCCESS == responseInfo.tag) {
//                    // 入库成功（保存）
//                    ZBUiUtils.showToast(responseInfo.message)
//                    runOnUiThread { finish() }
//                } else {
//                    ZBUiUtils.showToast(responseInfo.message)
//                }
//            }
//        }, body)
//    }
//
//    /**
//     * 构建 保存 的 Body
//     *
//     * @return
//     */
//    private fun initTodoBody(): RequestBody? {
//
//        val requestBody = ProductTodoSave()
//
//        val detail = ArrayList<ProductTodoSave.DetailsBean>()
//
//        // 获取 用户选择商品的信息: 【那一列中的第几个】
//        val goodsArray = DataUtils.getGoodsId()
//
//        // TODO( for 遍历 View 获取控件的值)
//        //        View child = reView.getChildAt(0);
//        //        child.findViewById()
//
//        // int size = rawData.size();
//        val size = oldList.size
//        for (i in 0 until size) {
//            val boxQrCode = carCodeArray.get(i)
//            val number = numberArray.get(i)
//            val startCode = startCodeArray.get(i)
//            val endCode = endCodeArray.get(i)
//            val sap = sapArray.get(i)
//            val id = positionId.get(i)
//
//            // 商品信息
//            val goodsId: String?
//            val goodsNo: String?
//            val goodsName: String?
//
//            val bean = ProductTodoSave.DetailsBean()
//            if (!TextUtils.isEmpty(number) && boxQrCode != null) {
//
//                // 默认只有第一个
//                var which = 0
//                try {
//                    which = goodsArray.get(i)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//
//                // 从原数据中的列表中获取
//                goodsId = rawData[which].goodsId
//                goodsNo = rawData[which].goodsNo
//                goodsName = rawData[which].goodsName
//
//                bean.positionId = id
//                bean.startQrCode = startCode
//                bean.endQrCode = endCode
//                bean.number = number
//                bean.sapMaterialBatchNo = sap
//
//                bean.goodsId = goodsId
//                bean.goodsNo = goodsNo
//
//                bean.boxQrCode = boxQrCode
//
//                detail.add(bean)
//            }
//        }
//        // 没有合法的操作数据,不请求网络
//        if (detail.size == 0) {
//            ZBUiUtils.showToast("请完善您要出库的信息")
//            return null
//        }
//
//        val remark = et_desc!!.text.toString().trim { it <= ' ' }
//        val time = tv_time!!.text.toString().trim { it <= ' ' }
//
//        requestBody.productInfo = productInfo
//        requestBody.customerInfo = customerInfo
//        requestBody.goodsInfo = goodsInfo
//
//        requestBody.list = detail
//        requestBody.warehouseId = warehouseId
//        requestBody.sapOrderNo = sapOrderNo
//        requestBody.inTime = time
//        requestBody.remark = remark
//
//
//        val json = DataUtils.toJson(requestBody, 1)
//        ZBLog.e("JSON $json")
//        return RequestBodyJson.requestBody(json)
//    }
//
//
//    override fun showProduct(list: MutableList<ProductDetails.ListBean>) {
//
//        // 保存原数据
//        rawData.addAll(list)
//
//        goodsName = ArrayList()
//        val size = rawData.size
//        for (i in 0 until size) {
//            goodsName!!.add(rawData[i].goodsName.toString())
//        }
//
//        oldList = list
//        // 列表最开始不显示数据，所以 用 rawData 保存数据后，清除 oldList
//        oldList.clear()
//
//        if (adapter == null) {
//            val manager = LinearLayoutManager(ResourceUtil.getContext())
//            rv_in_storage_detail!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
//            rv_in_storage_detail!!.layoutManager = manager
//            adapter = SendOutTodoDetailAdapter(this, R.layout.item_product_detail_send_out_todo, newList)
//            rv_in_storage_detail!!.adapter = adapter
//
//            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
//                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
//
//                    val rlDetail = holder.itemView.findViewById<View>(R.id.gone_view)
//                    val ivArrow = holder.itemView.findViewById<ImageView>(R.id.iv_arrow)
//                    val tvName = holder.itemView.findViewById<TextView>(R.id.tv_name)
//                    val bindingCode = holder.itemView.findViewById<Button>(R.id.btn_binding_code)
//                    bindingCode.text = "箱码出库"
//
//                    // 选择商品
//                    val selectGoods = holder.itemView.findViewById<TextView>(R.id.tv_select_ware)
//                    selectGoods.setOnClickListener { ZBUiUtils.selectDialog(view.context, CodeConstant.SELECT_GOODS, position, goodsName, selectGoods) }
//                    //SparseArray<Integer> goodsId = DataUtils.getGoodsId();
//                    //int which = goodsId.get(position);
//                    if (rlDetail.visibility == View.VISIBLE) {
//                        rlDetail.visibility = View.GONE
//                        ivArrow.setImageResource(R.mipmap.ic_collapse)
//                        //tvName.setText(list.get(which).getGoodsName());
//                    } else {
//                        rlDetail.visibility = View.VISIBLE
//                        ivArrow.setImageResource(R.mipmap.ic_expand)
//                    }
//
//                    bindingCode.setOnClickListener {
//                        itemId = position
//                        val intent = Intent(it.context, ScanBoxCodeActivity::class.java)
//                        intent.putExtra("itemId", itemId)
//                        intent.putExtra(CodeConstant.PAGE_STATE, true)
//                        intent.putStringArrayListExtra("boxCodeList", carCodeArray.get(itemId))
//                        startActivityForResult(intent, REQUEST_SCAN_CODE)
//                    }
//
//                    ZBUiUtils.hideInputWindow(view.context, view)
//
//                }
//
//                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
//
//                    val tvGoods = holder.itemView.findViewById<TextView>(R.id.tv_select_ware)
//                    val goodsName = tvGoods.text.toString().trim { it <= ' ' }
//
//                    ZBUiUtils.deleteItemDialog(view.context, goodsName) { dialog ->
//                        dialog.dismiss()
//                        newList.clear()
//                        // TODO 犯错： 不能 new ,导致 DiffUtil 更新出错
//                        //newList = new ArrayList<>(oldList);
//                        newList.addAll(oldList)
//                        newList.removeAt(position)
//                        // itemCount: 已经发生变化的 item 的个数(包括自己,即正在点击这个)
//                        adapter!!.notifyItemRemoved(position)
//                        adapter!!.notifyItemRangeChanged(position, newList.size - position)
//
////                        val diffResult = DiffUtil.calculateDiff(SendOutDiffUtil(oldList, newList), true)
////                        diffResult.dispatchUpdatesTo(adapter!!)
////                        // oldList 也应该删除一列数据
//                        oldList.removeAt(position)
//                    }
//
//                    return true
//                }
//            })
//        } else {
//            adapter!!.notifyDataSetChanged()
//        }
//
//    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_SCAN_CODE && resultCode == RESULT_SCAN_CODE) {
//            itemId = data!!.getIntExtra("itemId", -1)
//            warehouseId = data.getStringExtra("warehouseId")
//            boxCodeList = data.getStringArrayListExtra("boxCodeList")
//            carCodeArray.put(itemId, boxCodeList)
//        }
//    }
//
//
//    companion object {
//
//        private val REQUEST_SCAN_CODE = 1
//        private val RESULT_SCAN_CODE = 2
//    }
//}
