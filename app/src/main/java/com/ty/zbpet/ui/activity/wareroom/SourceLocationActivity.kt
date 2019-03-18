package com.ty.zbpet.ui.activity.wareroom

import android.os.Bundle
import android.view.KeyEvent
import com.pda.scanner.ScanReader
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.data.SharedP
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import io.reactivex.disposables.Disposable

/**
 * @author TY on 2019/3/14.
 * 源库位
 *
 */
class SourceLocationActivity : BaseActivity(), ScanBoxInterface {

    private lateinit var disposable: Disposable
    private val scanner = ScanReader.getScannerInstance()
    private val scan = ScanObservable(this)
    override val activityLayout: Int
        get() = 0

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
    }

    override fun initTwoView() {
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == CodeConstant.KEY_CODE_131
                || keyCode == CodeConstant.KEY_CODE_135
                || keyCode == CodeConstant.KEY_CODE_139) {

            scanner.open(applicationContext)
            disposable = scan.scanBox(scanner, SharedP.getPosition(this))

            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun ScanSuccess(position: Int, msg: String?) {

    }

    override fun onDestroy() {
        super.onDestroy()

        scanner?.close()
    }
}