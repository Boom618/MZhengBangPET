package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import com.ty.zbpet.bean.system.ProMoveList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.presenter.move.ComplexInterface
import com.ty.zbpet.presenter.move.MovePresenter
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.wareroom.ProMoveReverAdapter
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.fragment_move_source.*
import okhttp3.RequestBody

/**
 * @author TY on 2019/4/24.
 * 移入目标仓库 [ 冲销到原仓库 ]
 */
class ProTargetHouseFrg : BaseSupFragment(), ComplexInterface<ProMoveList.ListBean> {

    private var adapter: ProMoveReverAdapter? = null
    private var type: String? = null
    private var selectPost = -1
    private var moveList: MutableList<ProMoveList.ListBean> = mutableListOf()
    private val presenter: MovePresenter = MovePresenter(this)

    override val fragmentLayout: Int
        get() = R.layout.fragment_move_source

    override fun onBaseCreate(view: View): View {

        type = arguments?.getString("type")
        return view
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()

        when (type) {
            "move" -> {
                initToolBar(R.string.move_house_target, TipString.define, View.OnClickListener {
                    initReqBody()
                })
            }
            else -> {
                // 冲销
                initToolBar(R.string.move_house_source, TipString.reversal, View.OnClickListener {
                    // TODO　＝＝　createOrder(initReqBody())
                })
            }
        }
        presenter.moveProductList()
    }

    private fun initReqBody() {

        val orderId = moveList[selectPost].orderId
        val goodsNo = moveList[selectPost].goodsNo
        val goodsName = moveList[selectPost].goodsName

        presenter.goodsMoveToTarget(orderId, goodsNo, goodsName)

    }

    // 源库位 移出 冲销
    private fun materialMove(body: RequestBody) {

        presenter.materialMoveOrder(body)
    }


    override fun showListData(list: MutableList<ProMoveList.ListBean>) {
        moveList = list
        context?.let { LayoutInit.initLayoutManager(it, recycler_source) }
        recycler_source.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))

        adapter = context?.let { ProMoveReverAdapter(it, R.layout.item_move_target_two_frg, list) }
        recycler_source.adapter = adapter

        adapter?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return true
            }

            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                adapter?.setSelection(position)
                selectPost = position
            }
        })
    }

    override fun showObjData(obj: ProMoveList.ListBean) {

    }

    override fun responseSuccess() {
        pop()
        ZBUiUtils.showSuccess("移库成功")
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
        presenter.dispose()
    }


    companion object {
        fun newInstance(type: String): ProTargetHouseFrg {
            val fragment = ProTargetHouseFrg()

            val bundle = Bundle()
            bundle.putString("type", type)
            fragment.arguments = bundle

            return fragment
        }
    }
}