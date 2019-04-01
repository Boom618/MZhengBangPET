package com.ty.zbpet.ui.activity.wareroom

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.ErrorMessage
import com.ty.zbpet.bean.system.ProductInventorList
import com.ty.zbpet.presenter.system.SystemPresenter
import com.ty.zbpet.ui.ActivitiesHelper
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.wareroom.ProductDeleteAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.activity_product_inventory_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author TY on 2019/4/1.
 * 成品单据删除列表
 */
class ProductDeleteListActivity : BaseActivity() {


    private val presenter = SystemPresenter()

    override val activityLayout: Int
        get() = R.layout.activity_product_inventory_list

    override fun onBaseCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
    }

    override fun initOneData() {
        initToolBar(R.string.product_delete)
        LayoutInit.initLayoutManager(this, recycler_inventory)
        recycler_inventory.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        presenter.getGoodsList(6, "")
    }

    override fun initTwoView() {


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getGoodsList(list: MutableList<ProductInventorList.ListBean>) {
        //val list = event
        val activity = ActivitiesHelper.get().lastActivity
        if (activity is ProductDeleteListActivity) {
            val adapter = ProductDeleteAdapter(this, R.layout.item_delete_product_list, list)
            recycler_inventory.adapter = adapter

            adapter.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                    return true
                }

                override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder, position: Int) {
                    val intent = Intent(this@ProductDeleteListActivity, ProductInventoryActivity::class.java)
                    intent.putExtra("goodsNo", list[position].goodsNo)
//                intent.putExtra("goodsNo", "90000947")
                    startActivity(intent)
                }
            })
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ErrorEvnet(event: ErrorMessage) {
        ZBUiUtils.showError(event.error())
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}