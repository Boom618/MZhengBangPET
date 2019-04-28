package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import com.ty.zbpet.bean.UserInfo
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
class ProductSelectFrg : BaseSupFragment(), ComplexInterface<String> {


    private var userInfo: UserInfo = SimpleCache.getUserInfo(CodeConstant.USER_DATA)

    private var goodsNo: String? = null
    private var warehouseNo: String? = null
    private var houseList: MutableList<String> = mutableListOf()
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
        view.tv_product.setOnClickListener {
            //ZBUiUtils.selectDialog(it.context, CodeConstant.SELECT_HOUSE_BUY_IN, 0, houseList, view.tv_product)
            //presenter.goodsStock(goodsNo, warehouseNo)
        }

        // 仓库名称
        view.tv_house.setOnClickListener {
            ZBUiUtils.selectDialog(it.context, CodeConstant.SELECT_HOUSE_BUY_IN, 0, houseList, view.tv_house)

//            val which = SharedP.getWarehouseId(it.context)
//            warehouseNo = warehouseList[which].warehouseNo
//
//            presenter.goodsStock(goodsNo, warehouseNo)
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
        val which = context?.let { SharedP.getWarehouseId(it) }
        warehouseNo = which?.let { warehouseList[it].warehouseNo }
        val product = tv_product.text.toString()

        //presenter.goodsStock(product, warehouseNo)
        start(ProductMoveFrg.newInstance())


    }

    override fun showListData(list: MutableList<String>) {

        //start(ProductMoveFrg.newInstance())

    }

    override fun showObjData(obj: String) {
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