package com.ty.zbpet.ui.activity.wareroom

import android.os.Bundle
import com.ty.zbpet.R
import com.ty.zbpet.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_inventory_main.*

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

        materials.setOnClickListener { gotoActivity(MaterialsStockActivity::class.java) }
        product.setOnClickListener { gotoActivity(ProductInventoryListActivity::class.java) }
        delete_order.setOnClickListener { gotoActivity(ProductDeleteListActivity::class.java) }

    }
}