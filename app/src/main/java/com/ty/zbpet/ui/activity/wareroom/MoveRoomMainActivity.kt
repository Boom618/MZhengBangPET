package com.ty.zbpet.ui.activity.wareroom

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_move_room_main.*
import kotlinx.android.synthetic.main.activity_person_center_c.view.*
import kotlinx.android.synthetic.main.widget_bottom_dialog.view.*

/**
 * @author TY on 2019/3/14.
 * 移库主页
 */
class MoveRoomMainActivity : BaseActivity() {
    override val activityLayout: Int
        get() = R.layout.activity_move_room_main

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
    }

    override fun initTwoView() {
        initToolBar(R.string.label_transfer_storage)

        move_room_materials.setOnClickListener { gotoActivity(MoveRoomMaterialsActivity::class.java) }
        reversal_materials.setOnClickListener { gotoActivity(ReversalMaterialsActivity::class.java) }
        move_room_product.setOnClickListener { gotoActivity(MoveRoomMaterialsActivity::class.java) }
        reversal_product.setOnClickListener { gotoActivity(MoveRoomMaterialsActivity::class.java) }

    }
}