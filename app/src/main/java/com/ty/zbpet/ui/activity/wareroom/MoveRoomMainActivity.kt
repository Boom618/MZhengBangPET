package com.ty.zbpet.ui.activity.wareroom

import android.os.Bundle
import android.view.KeyEvent
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupActivity
import com.ty.zbpet.bean.eventbus.system.FragmentScanEvent
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.ui.fragment.wareroom.MoveRoomMainFrg
import com.ty.zbpet.ui.fragment.wareroom.SourceLocationFrg
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

/**
 * @author TY on 2019/3/14.
 * 移库主页
 */
class MoveRoomMainActivity : BaseSupActivity(), ScanBoxInterface {

    private val scanner = ScanReader.getScannerInstance()
    private val scan = ScanObservable(this)
    private lateinit var disposable: Disposable

    override val activityLayout: Int
        get() = R.layout.activity_move_room_main

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun onCreateData() {
        val fragment = findFragment(MoveRoomMainFrg::class.java)
        if (fragment == null) {
            loadRootFragment(R.id.frame_content, MoveRoomMainFrg.newInstance("type"))
        }
    }

    override fun onStartView() {
        initToolBar(R.string.label_transfer_storage)

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (topFragment is SourceLocationFrg) {
            if (keyCode == CodeConstant.KEY_CODE_131
                    || keyCode == CodeConstant.KEY_CODE_135
                    || keyCode == CodeConstant.KEY_CODE_139) {

                scanner.open(applicationContext)
                disposable = scan.scanBox(scanner, 0)

                return true
            }
        }
        return super.onKeyDown(keyCode, event)

    }

    override fun ScanSuccess(position: Int, msg: String?) {
        EventBus.getDefault().post(FragmentScanEvent("source", msg))
        //ZBUiUtils.showSuccess("position =  $position 库位码 = $msg")
    }

    /**
     * 监听 back 键
     */
    override fun onBackPressedSupport() {

        when (supportFragmentManager.backStackEntryCount) {
            1 -> finish()
            else -> pop()
        }
    }
}