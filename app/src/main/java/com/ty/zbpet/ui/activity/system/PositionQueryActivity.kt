package com.ty.zbpet.ui.activity.system

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.jaeger.library.StatusBarUtil
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.ErrorMessage
import com.ty.zbpet.bean.system.PositionCode
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.system.QueryPresenter
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.system.QueryPositionAdapter
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import kotlinx.android.synthetic.main.activity_stock_track.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author TY on 2019/3/5.
 * 库位码查询（单批，混批）
 */
class PositionQueryActivity : BaseActivity(), ScanBoxInterface {


    private val scanner = ScanReader.getScannerInstance()
    private val scanObservable = ScanObservable(this)

    private val presenter: QueryPresenter = QueryPresenter()

    override val activityLayout: Int
        get() = R.layout.activity_stock_track

    override fun onBaseCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
    }

    override fun initOneData() {

    }

    override fun initTwoView() {
        StatusBarUtil.setColor(this, resources.getColor(R.color.red))
        initToolBar(R.string.position_query)

        bt_title.text = "库存信息"
        product_no.visibility = View.GONE
        query_time.visibility = View.GONE
        query_number.text = "库位码："

        product_name.text = "库位类型："
        product_order.text = "查询时间："
        product_batch.visibility = View.GONE
        product_time.text = "库存总量："
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

    override fun ScanSuccess(position: Int, msg: String) {
        presenter.positionQuery(msg)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun queryEvent(event: PositionCode) {
        val formatter = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M_S)
        val curDate = Date(System.currentTimeMillis())
        val time = formatter.format(curDate)
        product_no.visibility = View.GONE
        query_time.visibility = View.GONE
        query_number.text = "库位码：${event.positionNo}"

        product_name.text = "库位类型：${event.type}"
        product_order.text = "查询时间：$time"
        product_batch.visibility = View.GONE
        product_time.text = "库存总量：${event.sum}"


        val list = event.list!!

        LayoutInit.initLayoutManager(this, recycler)
        recycler.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        val adapter = QueryPositionAdapter(this, R.layout.item_sys_query_position, list)
        recycler.adapter = adapter

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ErrorEvnet(event: ErrorMessage) {
        ZBUiUtils.showError(event.error())
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}