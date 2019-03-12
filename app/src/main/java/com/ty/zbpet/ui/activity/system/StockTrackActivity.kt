package com.ty.zbpet.ui.activity.system

import android.os.Bundle
import android.view.KeyEvent
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.system.QueryProductAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import kotlinx.android.synthetic.main.activity_stock_track.*
import kotlinx.android.synthetic.main.layout_top_system.*

/**
 * @author TY on 2019/3/5.
 * 库存追踪
 */
class StockTrackActivity : BaseActivity(), ScanBoxInterface {


    private val scanner = ScanReader.getScannerInstance()
    private val scanObservable = ScanObservable(this)

    override val activityLayout: Int
        get() = R.layout.activity_stock_track

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
    }

    override fun initTwoView() {
//        iv_back tv_title

        iv_back.setOnClickListener { finish() }
        tv_title.text = "成品追踪"
        product_no.text = "产品二维码编号："
        query_time.text = "查询时间："
        query_number.text = "查询次数："
        product_name.text = "产品名称："
        product_order.text = "生产订单："
        product_batch.text = "生产批次："
        product_time.text = "生产日期："
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == CodeConstant.KEY_CODE_131
                || keyCode == CodeConstant.KEY_CODE_135
                || keyCode == CodeConstant.KEY_CODE_139) {

            scanner.open(applicationContext)
            scanObservable.scanBox(scanner, 100)

            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun ScanSuccess(position: Int, msg: String?) {
        ZBUiUtils.showToast(msg)
        val list = mutableListOf<String>()
        list.add("123")
        list.add("123")

        LayoutInit.initLayoutManager(this, recycler)
        recycler.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        val adapter = QueryProductAdapter(this, R.layout.item_sys_query_pro, list)
        recycler.adapter = adapter


    }
}