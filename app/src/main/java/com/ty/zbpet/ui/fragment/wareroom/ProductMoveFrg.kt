package com.ty.zbpet.ui.fragment.wareroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import com.ty.zbpet.bean.UserInfo
import com.ty.zbpet.bean.system.ProductMove
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.data.SharedP
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.move.ComplexInterface
import com.ty.zbpet.presenter.move.MovePresenter
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.util.SimpleCache
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.fragment_product_move.*
import kotlinx.android.synthetic.main.fragment_product_move.view.*

/**
 * @author TY on 2019/4/24.
 * 成品 移库
 */
class ProductMoveFrg : BaseSupFragment(), ComplexInterface<String> {

    private var userInfo: UserInfo = SimpleCache.getUserInfo(CodeConstant.USER_DATA)

    //    private var warehouseNo: String? = null
    private val presenter: MovePresenter = MovePresenter(this)
    // 仓库列表
    private var houseList: MutableList<String> = mutableListOf()
    // 箱码列表
    private var boxCodeList: ArrayList<String> = ArrayList()
    // outWarehouseNo(移出仓库/源仓库),inWarehouseNo（移入仓库/目标仓库）
    private var outWarehouseNo = ""
    private var inWarehouseNo = ""
    private var warehouseList: MutableList<UserInfo.WarehouseListBean> = mutableListOf()

    override val fragmentLayout: Int
        get() = R.layout.fragment_product_move

    override fun onBaseCreate(view: View): View {
        warehouseList = userInfo.warehouseList
        val size = warehouseList.size
        for (i in 0 until size) {
            houseList.add(warehouseList[i].warehouseName!!)
        }
        // 默认值
        view.tv_target_move.text = houseList[0]
        SharedP.putWarehouseId(view.context, 0)
        view.tv_target_move.setOnClickListener {
            ZBUiUtils.selectDialog(it.context, CodeConstant.SELECT_HOUSE_BUY_IN, 0, houseList, view.tv_target_move)
        }
        view.bt_scan_pro.setOnClickListener {
            val intent = Intent(it.context, ScanBoxCodeActivity::class.java)
            intent.putExtra("goodsNo", "")
            intent.putExtra(CodeConstant.PAGE_STATE, true)
            intent.putStringArrayListExtra("boxCodeList", boxCodeList)
            startActivityForResult(intent, CodeConstant.REQUEST_CODE)
        }
        return view
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        initToolBar(R.string.product_move, TipString.moveHouse, View.OnClickListener {
            val which = SharedP.getWarehouseId(it.context)
            warehouseList[which].warehouseNo?.let { houseNo ->
                inWarehouseNo = houseNo
            }
            pop()
        })
    }
    // goodsNo,goodsName,list(箱码列表),outWarehouseNo(移出仓库/源仓库),inWarehouseNo（移入仓库/目标仓库）

    private fun initBody() {
        val bean = ProductMove()
        bean.goodsNo = ""
        bean.goodsName = ""
        bean.outWarehouseNo = "源仓库"
        bean.inWarehouseNo = inWarehouseNo
        bean.list = boxCodeList

        val json = Gson().toJson(bean)
        val body = RequestBodyJson.requestBody(json)

        presenter.goodsMoveOrder(body)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CodeConstant.REQUEST_CODE && resultCode == CodeConstant.RESULT_CODE) {
            ZBUiUtils.showSuccess("回传 data")
            boxCodeList = data!!.getStringArrayListExtra("boxCodeList")
            tv_move_number.text = "移库数量：${boxCodeList.size}"
        }
    }

    override fun showListData(list: MutableList<String>) {

    }

    override fun showObjData(obj: String) {
    }

    override fun responseSuccess() {
        ZBUiUtils.showSuccess(TipString.moveSuccess)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(msg: String) {
        ZBUiUtils.showError(msg)
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