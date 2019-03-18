package com.ty.zbpet.ui.activity.wareroom

import android.os.Bundle
import com.ty.zbpet.R
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.wareroom.ProductAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import kotlinx.android.synthetic.main.activity_product_move.*

/**
 * @author TY on 2019/3/18.
 * 成品移库
 */
class ProductMoveActivity : BaseActivity() {

    private var adapter: ProductAdapter? = null
    override val activityLayout: Int
        get() = R.layout.activity_product_move

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
    }

    override fun initTwoView() {
        initToolBar(R.string.product_move)

        val list = mutableListOf<String>()
        list.add("1")
        list.add("1")
        LayoutInit.initLayoutManager(this, recycler_product)
        recycler_product.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        adapter = ProductAdapter(this, R.layout.item_move_room_product, list)
        recycler_product.adapter = adapter

    }

}