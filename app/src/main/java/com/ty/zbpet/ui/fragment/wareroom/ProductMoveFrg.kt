package com.ty.zbpet.ui.fragment.wareroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import com.ty.zbpet.bean.UserInfo
import com.ty.zbpet.bean.system.ProductInventorList
import com.ty.zbpet.bean.system.ProductMove
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.data.SharedP
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.move.ComplexInterface
import com.ty.zbpet.presenter.move.MovePresenter
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.util.SimpleCache
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
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
    private var sum: String? = null
    private var goodsNo: String? = null
    private var goodsName: String? = null
    private var warehouseNo: String? = null
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
        try {
            view.tv_target_move.text = houseList[0]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        sum = arguments?.getString("sum")
        goodsNo = arguments?.getString("goodsNo")
        goodsName = arguments?.getString("goodsName")
        warehouseNo = arguments?.getString("warehouseNo")

        view.tv_number.text = "库存数量：$sum"
        view.tv_product_code.text = "成品代码：$goodsNo"
        view.tv_product_name.text = "成品名称：$goodsName"
        view.tv_old_house.text = "原仓库：$warehouseNo"

        SharedP.putGoodsOrHouseId(view.context, 0, CodeConstant.TYPE_HOUSE)
        view.tv_target_move.setOnClickListener {
            ZBUiUtils.selectDialog(it.context, CodeConstant.TYPE_HOUSE, 0, houseList, view.tv_target_move)
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
            val which = SharedP.getGoodsOrHouseId(it.context, CodeConstant.TYPE_HOUSE)
            warehouseList[which].warehouseNo?.let { houseNo ->
                inWarehouseNo = houseNo
            }
            initBody()
        })
    }

    private fun initBody() {
        val bean = ProductMove()
        bean.goodsNo = goodsNo
        bean.goodsName = goodsName
        bean.outWarehouseNo = warehouseNo
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
        pop()
        ZBUiUtils.showSuccess(TipString.moveSuccess)
    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(_mActivity, TipString.moveHouseIng)
    }

    override fun hideLoading() {
        dialog?.close()
    }

    override fun showError(msg: String) {
        ZBUiUtils.showError(msg)
    }

    companion object {
        fun newInstance(list: MutableList<ProductInventorList.ListBean>): ProductMoveFrg {
            val fragment = ProductMoveFrg()

            val bundle = Bundle()
            bundle.putString("goodsNo", list[0].goodsNo)
            bundle.putString("goodsName", list[0].goodsName)
            bundle.putString("warehouseNo", list[0].warehouseNo)
            bundle.putString("sum", list[0].sum)
            fragment.arguments = bundle

            return fragment
        }
    }
}