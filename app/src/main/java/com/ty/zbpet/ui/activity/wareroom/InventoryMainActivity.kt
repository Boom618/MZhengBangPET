package com.ty.zbpet.ui.activity.wareroom

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_inventory_main.*
import kotlinx.android.synthetic.main.activity_move_room_main.*
import kotlinx.android.synthetic.main.activity_person_center_c.view.*
import kotlinx.android.synthetic.main.widget_bottom_dialog.view.*

/**
 * @author TY on 2019/3/14.
 * 盘点主页
 */
class InventoryMainActivity : BaseActivity() {
    override val activityLayout: Int
        get() = R.layout.activity_inventory_main

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
    }

    override fun initTwoView() {
        initToolBar(R.string.label_inventory)

//        materials.setOnClickListener { gotoActivity(MoveRoomMaterialsActivity::class.java) }
//        product.setOnClickListener { gotoActivity(ReversalMaterialsActivity::class.java) }
//        delete_order.setOnClickListener { gotoActivity(ProductMoveActivity::class.java) }

    }
}