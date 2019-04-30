package com.ty.zbpet.ui.activity.wareroom

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.widget.CheckBox
import android.widget.TextView
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.AdapterButtonClick
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.wareroom.ProductMoveAdapter
import com.ty.zbpet.base.BaseActivity
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
 * 成品移库
 */
class ProductMoveActivity : BaseActivity() {

    private var itemId = 0
    private var codeList = ArrayList<String>()
    private var codeArray: SparseArray<ArrayList<String>> = SparseArray(10)

    private var adapter: ProductMoveAdapter? = null

    override val activityLayout: Int
        get() = R.layout.activity_product_move

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
        EventBus.getDefault().register(this)
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
        ZBUiUtils.showSuccess("提交")

    }

    override fun initTwoView() {
        initToolBar(R.string.product_move)

        val list = mutableListOf<String>()
        list.add("1")
        list.add("1")

        adapter = ProductMoveAdapter(this, R.layout.item_move_room_product, list)
        recycler_product.adapter = adapter

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventAdapter(event: AdapterButtonClick) {
        val position = event.position()
        when(event.type()){
            CodeConstant.BUTTON_TYPE ->{
                val list = codeArray[position]
                val intent = Intent(this, ScanBoxCodeActivity::class.java)
                intent.putExtra(CodeConstant.PAGE_STATE, true)
                intent.putExtra("itemId", position)
                intent.putExtra("goodsNo", "")
                intent.putStringArrayListExtra("boxCodeList", list)
                startActivityForResult(intent, CodeConstant.REQUEST_CODE)
            }
            CodeConstant.SELECT_TYPE ->{
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CodeConstant.REQUEST_CODE && resultCode == CodeConstant.RESULT_CODE) {
            itemId = data!!.getIntExtra("itemId", -1)
            codeList = data.getStringArrayListExtra("boxCodeList")
            codeArray.put(itemId, codeList)
            val size = codeList.size
            val view = recycler_product.getChildAt(itemId)
            val textView = view.findViewById<TextView>(R.id.tv_move_number)
            textView.text = "移库数量11：$size"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}