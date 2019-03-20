package com.ty.zbpet.ui.activity.system

import android.os.Bundle
import android.view.KeyEvent
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.data.SharedP
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_top_system.*

/**
 * @author TY on 2019/3/11.
 * 箱码查询
 */
class BoxCodeQueryActivity : BaseActivity(), ScanBoxInterface {

    private lateinit var disposable: Disposable
    private val scanner = ScanReader.getScannerInstance()
    private val scan = ScanObservable(this)

    override val activityLayout: Int
        get() = R.layout.activity_box_code_query

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
    }

    override fun initTwoView() {
        initToolBar(R.string.box_code_query)
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

    /**
     * 扫码解析成功
     */
    override fun ScanSuccess(position: Int, msg: String?) {

    }

    override fun onDestroy() {
        super.onDestroy()

        scanner?.close()
    }
}