package com.ty.zbpet.ui.activity.wareroom

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.ErrorMessage
import com.ty.zbpet.bean.eventbus.SuccessMessage
import com.ty.zbpet.bean.system.PositionCode
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.system.QueryPresenter
import com.ty.zbpet.presenter.system.SystemPresenter
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.system.InventorySourceAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_inventory_source.*
import kotlinx.android.synthetic.main.item_inventory_source.*
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author TY on 2019/3/31.
 * 原辅料盘点
 */
class MaterialsStockActivity : BaseActivity(), ScanBoxInterface {

    private val scanner = ScanReader.getScannerInstance()
    private val scan = ScanObservable(this)
    private lateinit var disposable: Disposable
    private lateinit var rawData: PositionCode

    private val presenter: SystemPresenter = SystemPresenter()

    override val activityLayout: Int
        get() = R.layout.activity_inventory_source

    override fun onBaseCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
    }

    override fun initOneData() {
        initToolBar(R.string.materials_inventor)
    }

    override fun initTwoView() {
        bt_commit.isEnabled = false
        bt_commit.setOnClickListener {
            commitInventor(initDoneBody())
        }
    }

    private fun commitInventor(body: RequestBody?) {
        if (body == null) {
            return
        }
        presenter.positionStockSave(body)

    }

    private fun initDoneBody(): RequestBody? {

        val req = PositionCode()
        val list = mutableListOf<PositionCode.StockListBean>()
        val size = rawData.list!!.size
        for (i in 0 until size) {
//            var type = "盘盈"
            val view = recycler.getChildAt(i)
//            val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
//            val radioButton = radioGroup.getChildAt(0) as RadioButton
//            type = when (radioButton.isChecked) {
//                true -> "盘盈"
//                false -> "盘亏"
//            }
            val checkNumber = view.findViewById<EditText>(R.id.edit_number).text.toString().trim()
            if (!TextUtils.isEmpty(checkNumber)) {
                val stockListBean = rawData.list!!
                val bean = PositionCode.StockListBean()
                bean.stockId = stockListBean[i].stockId
                bean.positionNo = stockListBean[i].positionNo
                bean.sapOrderNo = stockListBean[i].sapOrderNo
                bean.materialName = stockListBean[i].materialName
                bean.materialNo = stockListBean[i].materialNo
                bean.supplierNo = stockListBean[i].supplierNo
                bean.sapBatchNo = stockListBean[i].sapBatchNo
                bean.supplierName = stockListBean[i].supplierName
                bean.concentration = stockListBean[i].concentration
                bean.number = stockListBean[i].number
                bean.unit = stockListBean[i].unit

                bean.checkNumber = checkNumber

                list.add(bean)
            }
        }

        if (list.size == 0) {
            ZBUiUtils.showWarning("请输入盘点数量")
            return null
        }

        req.positionNo = rawData.positionNo
        req.warehouseNo = rawData.warehouseNo
        req.list = list

        val json = DataUtils.toJson(req, 1)
        return RequestBodyJson.requestBody(json)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == CodeConstant.KEY_CODE_131
                || keyCode == CodeConstant.KEY_CODE_135
                || keyCode == CodeConstant.KEY_CODE_139) {

            scanner.open(applicationContext)
            disposable = scan.scanBox(scanner, 0)

            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun ScanSuccess(position: Int, msg: String) {
        presenter.positionStock(msg)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun queryEvent(event: PositionCode) {
        bt_commit.isEnabled = true
        rawData = event

        val list = event.list!!

        LayoutInit.initLayoutManager(this, recycler)
        recycler.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        val adapter = InventorySourceAdapter(this, R.layout.item_inventory_source, list)
        recycler.adapter = adapter
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun successEvent(event: SuccessMessage) {
        ZBUiUtils.showSuccess(event.success())
        finish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ErrorEvnet(event: ErrorMessage) {
        ZBUiUtils.showError(event.error())
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        presenter.dispose()
    }
}