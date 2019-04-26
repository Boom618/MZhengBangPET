//package com.ty.zbpet.ui.activity.wareroom
//
//import android.os.Bundle
//import android.view.KeyEvent
//import com.pda.scanner.ScanReader
//import com.ty.zbpet.R
//import com.ty.zbpet.constant.CodeConstant
//import com.ty.zbpet.base.BaseActivity
//import com.ty.zbpet.util.ZBUiUtils
//import com.ty.zbpet.util.scan.ScanBoxInterface
//import com.ty.zbpet.util.scan.ScanObservable
//import io.reactivex.disposables.Disposable
//import kotlinx.android.synthetic.main.activity_move_room_source.*
//
///**
// * @author TY on 2019/3/14.
// * 源库位
// *
// */
//class SourceLocationActivity : BaseActivity(), ScanBoxInterface {
//
//    private lateinit var disposable: Disposable
//    private val scanner = ScanReader.getScannerInstance()
//    private val scan = ScanObservable(this)
//    override val activityLayout: Int
//        get() = R.layout.activity_move_room_source
//
//    override fun onBaseCreate(savedInstanceState: Bundle?) {
//    }
//
//    override fun initOneData() {
//    }
//
//    override fun initTwoView() {
//        initToolBar(R.string.move_room_source)
//
//        bt_commit.setOnClickListener { commit() }
//    }
//
//    /**
//     * 原库位提交
//     */
//    private fun commit() {
//        val number = edit_number.text.toString().trim { it >= ' ' }
//        ZBUiUtils.showSuccess(number)
//
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == CodeConstant.KEY_CODE_131
//                || keyCode == CodeConstant.KEY_CODE_135
//                || keyCode == CodeConstant.KEY_CODE_139) {
//
//            scanner.open(applicationContext)
//            disposable = scan.scanBox(scanner, 0)
//
//            return true
//        }
//
//        return super.onKeyDown(keyCode, event)
//    }
//
//    override fun ScanSuccess(position: Int, msg: String?) {
//        tv_inventory_sap.text = "库存批次号："
//        tv_material_name.text = "原辅料名称："
//        tv_supplier_name.text = "供应商名称："
//        tv_horse.text = "当前仓库："
//        tv_number.text = "库存数量："
//        tv_content.text = "含量："
//        tv_sap.text = "SAP 批次号："
//        tv_move.text = "移库数量："
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//
//        scanner?.close()
//    }
//}