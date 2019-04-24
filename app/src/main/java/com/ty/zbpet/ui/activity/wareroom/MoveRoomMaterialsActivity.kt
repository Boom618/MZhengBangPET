package com.ty.zbpet.ui.activity.wareroom

import android.os.Bundle
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseActivity
import kotlinx.android.synthetic.main.activity_materials_main.*

/**
 * @author TY on 2019/3/14.
 * 原辅料移库
 */
class MoveRoomMaterialsActivity : BaseActivity() {
    override val activityLayout: Int
        get() = R.layout.activity_materials_main

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
    }

    override fun initTwoView() {
        initToolBar(R.string.materials_move)

        source_location.setOnClickListener { gotoActivity(SourceLocationActivity::class.java) }
        target_location.setOnClickListener { gotoActivity(TargetLocationActivity::class.java) }
    }
}