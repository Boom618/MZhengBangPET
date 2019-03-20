package com.ty.zbpet.ui.activity.wareroom

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.widget.CheckBox
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.AdapterButtonClick
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.wareroom.ProductReversalAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.activity_product_move.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * @author TY on 2019/3/18.
 * 成品冲销
 */
class ProductReversalActivity : BaseActivity() {

    private var itemId = 0
    private var codeList = ArrayList<String>()
    private var codeArray: SparseArray<ArrayList<String>> = SparseArray(10)

    private var adapter: ProductReversalAdapter? = null

    override val activityLayout: Int
        get() = R.layout.activity_product_move

    override fun onBaseCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
    }

    override fun initOneData() {
        LayoutInit.initLayoutManager(this, recycler_product)
        recycler_product.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))

        bt_confirm.setOnClickListener {
            confirm()
        }
    }

    private fun confirm() {

        val view = recycler_product.getChildAt(0)
        val checkBox = view.findViewById<CheckBox>(R.id.check)
        if (checkBox.isChecked) {

        }
        ZBUiUtils.showToast("提交")

    }

    override fun initTwoView() {
        initToolBar(R.string.product_reversal)

        val list = mutableListOf<String>()
        list.add("1")
        list.add("1")

        adapter = ProductReversalAdapter(this, R.layout.item_move_room_product, list)
        recycler_product.adapter = adapter

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventAdapter(event: AdapterButtonClick) {
        val position = event.position()
        val type = event.type()
        when(type){
            // 查看 库位列表
            CodeConstant.BUTTON_TYPE ->{
                val list = codeArray[position]
                val intent = Intent(this, ScanBoxCodeActivity::class.java)
                intent.putExtra(CodeConstant.PAGE_STATE, false)
                intent.putExtra("itemId", position)
                intent.putStringArrayListExtra("boxCodeList", list)
                startActivity(intent)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}