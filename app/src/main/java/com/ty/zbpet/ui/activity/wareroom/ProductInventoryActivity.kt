package com.ty.zbpet.ui.activity.wareroom

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.system.EndLoadingMessage
import com.ty.zbpet.bean.eventbus.system.StartLoadingMessage
import com.ty.zbpet.bean.system.ProductInventorList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.system.SystemPresenter
import com.ty.zbpet.ui.ActivitiesHelper
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import kotlinx.android.synthetic.main.item_inventory_product.*
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * @author TY on 2019/3/18.
 * 成品盘点
 */
class ProductInventoryActivity : BaseActivity() {

    private var itemId = 0
    private var codeList = ArrayList<String>()
    private var codeArray: SparseArray<ArrayList<String>> = SparseArray(10)

    private lateinit var goodsNo: String

    private val presenter = SystemPresenter()

    override val activityLayout: Int
        get() = R.layout.item_inventory_product

    override fun onBaseCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        goodsNo = intent.getStringExtra("goodsNo")
        presenter.getGoodsList(0, goodsNo)
    }

    override fun initOneData() {

    }


    override fun onStart() {
        super.onStart()
        button_scan.setOnClickListener {
            val list = codeArray[0]
            val intent = Intent(this, ScanBoxCodeActivity::class.java)
            intent.putExtra(CodeConstant.PAGE_STATE, true)
            intent.putExtra("itemId", 0)
            intent.putExtra("goodsNo", goodsNo)
            intent.putStringArrayListExtra("boxCodeList", list)
            startActivityForResult(intent, REQUEST_SCAN_CODE)
        }
    }

    override fun initTwoView() {
        initToolBar(R.string.product_inventor, "提交", View.OnClickListener {
            confirm(initBody())
        })
    }

    private fun confirm(body: RequestBody?) {
        ZBUiUtils.showSuccess("提交")
        if (body == null) {
            return
        }
        presenter.goodsInventory(body)

    }

    private fun initBody(): RequestBody? {

        val req = ProductInventorList()
        val list = mutableListOf<ProductInventorList.ListBean>()
        val checkNumber = codeList.size
        if (checkNumber > 0) {
            val bean = ProductInventorList.ListBean()

            bean.goodsNo = checkNumber.toString()

            list.add(bean)
        }

        if (list.size == 0) {
            ZBUiUtils.showWarning("请输入盘点数量")
            return null
        }

//        req.positionNo =  ""
//        req.warehouseNo =  "warehouseNo"
        req.list = list

        val json = DataUtils.toJson(req, 1)
        return RequestBodyJson.requestBody(json)
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getGoodsList(list: MutableList<ProductInventorList.ListBean>) {
        val activity = ActivitiesHelper.get().lastActivity
        if (activity is ProductInventoryActivity) {
            val data = list[0]
            sap_no.text = "库存批次号：${data.companyNo}"
            tv_material_name.text = "原辅料名称：${data.goodsName}"
            tv_supplier_name.text = "供应商名称：${data.goodsName}"
            tv_number.text = "库存数量：${data.goodsName}"
            tv_content.text = "含量：${data.goodsName}"
            tv_sap.text = "SAP 批次号：${data.goodsName}"
            tv_actual_number.text = "实际数量："
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SCAN_CODE && resultCode == RESULT_SCAN_CODE) {
            itemId = data!!.getIntExtra("itemId", -1)
            codeList = data.getStringArrayListExtra("boxCodeList")
            codeArray.put(itemId, codeList)
            val size = codeList.size
//            val view = recycler_product.getChildAt(itemId)
//            val textView = view.findViewById<TextView>(R.id.tv_actual_number)
            tv_actual_number.text = "实际数量22：$size"
        }
    }

    private var dialog: LoadingDialog? = null
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ErrorEvnet(event: StartLoadingMessage) {
        dialog = ShowDialog.showFullDialog(this, event.message())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ErrorEvnet(event: EndLoadingMessage) {
        dialog?.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    companion object {
        private const val REQUEST_SCAN_CODE = 1
        private const val RESULT_SCAN_CODE = 2
    }

}