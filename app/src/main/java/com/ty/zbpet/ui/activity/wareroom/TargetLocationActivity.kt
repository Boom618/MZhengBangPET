package com.ty.zbpet.ui.activity.wareroom

import android.os.Bundle
import android.view.KeyEvent
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.wareroom.TargetAdapter
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_move_room_target.*

/**
 * @author TY on 2019/3/14.
 * 目标库位
 *
 */
class TargetLocationActivity : BaseActivity(), ScanBoxInterface {

    private var adapter: TargetAdapter? = null

    private lateinit var disposable: Disposable
    private val scanner = ScanReader.getScannerInstance()
    private val scan = ScanObservable(this)

    override val activityLayout: Int
        get() = R.layout.activity_move_room_target

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
    }

    override fun initTwoView() {
        initToolBar(R.string.move_room_target)
        bt_confirm.setOnClickListener {
            ZBUiUtils.showSuccess("确定")
        }
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
        tv_target.text = "目标库位码：$msg"

        val list = mutableListOf<String>()
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")

        LayoutInit.initLayoutManager(this, recycler)
        adapter = TargetAdapter(this, R.layout.item_move_room_target, list)
        recycler.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        recycler.adapter = adapter

    }
}