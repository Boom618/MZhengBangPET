package com.ty.zbpet.ui.activity.wareroom

import android.os.Bundle
import com.ty.zbpet.R
import com.ty.zbpet.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_move_room_source_reversal.*

/**
 * @author TY on 2019/3/14.
 * 原辅料移库冲销
 *
 */
class ReversalMaterialsActivity : BaseActivity() {
    override val activityLayout: Int
        get() = R.layout.activity_move_room_source_reversal

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
    }

    override fun initTwoView() {
        initToolBar(R.string.materials_move_reversal)

        tv_inventory_sap.text = "库存批次号："
        tv_material_name.text = "原辅料名称："
        tv_supplier_name.text = "供应商名称："
        tv_horse.text = "当前仓库："
        tv_number.text = "库存数量："
        tv_content.text = "含量："
        tv_sap.text = "SAP 批次号："
    }
}