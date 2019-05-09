package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import com.ty.zbpet.bean.UserInfo
import com.ty.zbpet.bean.system.ProductInventorList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.data.SharedP
import com.ty.zbpet.presenter.move.ComplexInterface
import com.ty.zbpet.presenter.move.MovePresenter
import com.ty.zbpet.util.SimpleCache
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.fragment_product_select.*
import kotlinx.android.synthetic.main.fragment_product_select.view.*

/**
 * @author TY on 2019/4/24.
 * 成品 产品 仓库选择
 */
class ProductSelectFrg : BaseSupFragment(), ComplexInterface<ProductInventorList.ListBean> {


    private var userInfo: UserInfo = SimpleCache.getUserInfo(CodeConstant.USER_DATA)

    // 查询结果
    private var isResult = false
    private var warehouseNo: String? = null
    private var houseList: MutableList<String> = mutableListOf()
//    private var goodNoList: MutableList<String> = mutableListOf()
    private var goodsNo: String = ""
    private var goodsNameList: MutableList<String> = mutableListOf()
    private var warehouseList: MutableList<UserInfo.WarehouseListBean> = mutableListOf()

    private val presenter: MovePresenter = MovePresenter(this)

    override val fragmentLayout: Int
        get() = R.layout.fragment_product_select

    override fun onBaseCreate(view: View): View {

        warehouseList = userInfo.warehouseList
        val size = warehouseList.size
        for (i in 0 until size) {
            houseList.add(warehouseList[i].warehouseName!!)
        }

        // 产品名称
//        view.tv_product.setOnClickListener {
//            ZBUiUtils.selectDialog(it.context, CodeConstant.TYPE_GOODS, 0, goodsNameList, view.tv_product)
//        }
        // 产品搜索
        view.edit_search.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchString = v.text.toString().trim { it <= ' ' }
                presenter.getGoodsList(5, searchString)
                isResult = false
            }
            ZBUiUtils.hideInputWindow(v.context, v)
            true
        }

        // 默认值
        try {
            view.tv_house.text = houseList[0]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        SharedP.putGoodsOrHouseId(view.context, 0, CodeConstant.TYPE_HOUSE)
        // 仓库名称
        view.tv_house.setOnClickListener {
            ZBUiUtils.selectDialog(it.context, CodeConstant.TYPE_HOUSE, 0, houseList, view.tv_house)
        }
        return view
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        initToolBar(R.string.product_select, TipString.define, View.OnClickListener {
            gotoMove()
        })

    }

    private fun gotoMove() {
        val houseWhich = context?.let { SharedP.getGoodsOrHouseId(it, CodeConstant.TYPE_HOUSE) }
        val goodsWhich = context?.let { SharedP.getGoodsOrHouseId(it, CodeConstant.TYPE_GOODS) }
        warehouseNo = houseWhich?.let { warehouseList[it].warehouseNo }

        if (goodsNo.isNotBlank()) {
            presenter.goodsStock(goodsNo, warehouseNo)
            isResult = true
        }else{
            ZBUiUtils.showSuccess(TipString.searchResultNot)
        }


    }

    override fun showListData(list: MutableList<ProductInventorList.ListBean>) {

        if (isResult) {
            if (list.size == 0) {
                ZBUiUtils.showSuccess(TipString.searchResultNot)
                return
            }
            start(ProductMoveFrg.newInstance(list))
        } else {
            when (list.size) {
                0 -> {
                    ZBUiUtils.showSuccess(TipString.searchResultNot)
                    search_result.text = "产品名称："
                    goodsNo = ""
                }
                1 -> {
                    goodsNo = list[0].goodsNo!!
                    search_result.text = "产品名称：${list[0].goodsName}"
                }
                else -> search_result.text = "产品名称："
            }
        }

    }

    override fun showObjData(obj: ProductInventorList.ListBean) {
    }

    override fun responseSuccess() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(msg: String) {
        ZBUiUtils.showError(msg)
    }


    companion object {
        fun newInstance(): ProductSelectFrg {
            val fragment = ProductSelectFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}