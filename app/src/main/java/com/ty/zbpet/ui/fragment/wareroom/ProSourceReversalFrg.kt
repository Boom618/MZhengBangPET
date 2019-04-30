package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import com.ty.zbpet.bean.system.PositionCode
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.move.ComplexInterface
import com.ty.zbpet.presenter.move.MovePresenter
import com.ty.zbpet.util.ZBUiUtils
import okhttp3.RequestBody

/**
 * @author TY on 2019/4/24.
 * 冲销到原仓库 [ 移入到目标仓库 ]
 */
class ProSourceReversalFrg : BaseSupFragment(), ComplexInterface<PositionCode> {

    private val presenter: MovePresenter = MovePresenter(this)

    override val fragmentLayout: Int
        get() = R.layout.fragment_move_source

    override fun onBaseCreate(view: View): View {

        return view
    }

    override fun onStart() {
        super.onStart()
        initToolBar(R.string.move_house_source, TipString.define, View.OnClickListener {
            createOrder(initReqBody())
        })
        presenter.moveProductList()
    }

    private fun initReqBody(): RequestBody? {


        val json = Gson().toJson("")
        return RequestBodyJson.requestBody(json)

    }

    private fun createOrder(body: RequestBody?) {

        if (body == null) {
            return
        }
        presenter.materialMoveOrder(body)
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()

    }



    override fun onStop() {
        super.onStop()
        presenter.dispose()
    }

    override fun showListData(list: MutableList<PositionCode>) {


    }

    override fun showObjData(obj: PositionCode) {
        // 接收查询的库位码信息

    }

    override fun responseSuccess() {
        pop()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }


    override fun showError(msg: String) {
        ZBUiUtils.showError(msg)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        fun newInstance(): ProSourceReversalFrg {
            val fragment = ProSourceReversalFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}