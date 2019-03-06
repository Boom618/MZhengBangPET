package com.ty.zbpet.ui.activity.system

import android.os.Bundle
import android.view.KeyEvent
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import kotlinx.android.synthetic.main.activity_box_code_query.*
import kotlinx.android.synthetic.main.layout_top_system.*

/**
 * @author TY on 2019/3/5.
 * 箱码查询
 */
class BoxCodeQueryActivity : BaseActivity(), ScanBoxInterface {


    private val scanner = ScanReader.getScannerInstance()
    private val scanObservable = ScanObservable(this)


    override val activityLayout: Int
        get() = R.layout.activity_box_code_query

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
    }

    override fun initTwoView() {
//        iv_back tv_title

        iv_back.setOnClickListener { finish() }
        tv_title.text = "成品追踪"
        product_no.text = "产品二维码编号："
        query_time.text = ""


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



    }
}