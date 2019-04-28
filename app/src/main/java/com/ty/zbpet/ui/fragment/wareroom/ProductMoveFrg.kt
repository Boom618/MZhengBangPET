package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import com.ty.zbpet.bean.UserInfo
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.util.SimpleCache
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.fragment_product_move.view.*

/**
 * @author TY on 2019/4/24.
 * 成品 移库
 */
class ProductMoveFrg : BaseSupFragment() {

    private var userInfo: UserInfo = SimpleCache.getUserInfo(CodeConstant.USER_DATA)

    private var warehouseNo: String? = null
    private var houseList: MutableList<String> = mutableListOf()
    private var warehouseList: MutableList<UserInfo.WarehouseListBean> = mutableListOf()

    override val fragmentLayout: Int
        get() = R.layout.fragment_product_move

    override fun onBaseCreate(view: View): View {
        warehouseList = userInfo.warehouseList
        val size = warehouseList.size
        for (i in 0 until size) {
            houseList.add(warehouseList[i].warehouseName!!)
        }
        view.tv_target_move.setOnClickListener {
            ZBUiUtils.selectDialog(it.context, CodeConstant.SELECT_HOUSE_BUY_IN, 0, houseList, view.tv_target_move)
        }
        return view
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        initToolBar(R.string.product_move,TipString.moveHouse, View.OnClickListener {
            ZBUiUtils.showSuccess("===")
        })
    }


    companion object {
        fun newInstance(): ProductMoveFrg {
            val fragment = ProductMoveFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}