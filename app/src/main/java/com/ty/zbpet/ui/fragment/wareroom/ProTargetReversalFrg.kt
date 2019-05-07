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
import com.ty.zbpet.ui.adapter.wareroom.ProReversalTargetAdapter
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.fragment_move_source.*

/**
 * @author TY on 2019/4/24.
 * 目标仓库 --> 冲销目标仓库
 */
class ProTargetReversalFrg : BaseSupFragment(), ComplexInterface<ProMoveList.ListBean> {

    private val presenter: MovePresenter = MovePresenter(this)
    private var reversalList: MutableList<ProMoveList.ListBean> = mutableListOf()
    private var adapter: ProReversalTargetAdapter? = null
    private var selectPost = -1

    override val fragmentLayout: Int
        get() = R.layout.fragment_move_source

    override fun onBaseCreate(view: View): View {
        return view
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        initToolBar(R.string.move_house_target, TipString.reversal, View.OnClickListener {
            initReqBody()
        })
        presenter.goodsRecallList()
    }

    private fun initReqBody() {
        when (selectPost == -1) {
            true -> ZBUiUtils.showWarning(TipString.reversalSelect)
            else -> {
                val id = reversalList[selectPost].id
                presenter.goodsMoveRecall(id)
            }
        }

    }


    override fun showListData(list: MutableList<ProMoveList.ListBean>) {
        reversalList = list

        context?.let { LayoutInit.initLayoutManager(it, recycler_source) }
        recycler_source.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))

        adapter = context?.let { ProReversalTargetAdapter(it, R.layout.item_reverse_target_frg, list) }
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
        ZBUiUtils.showSuccess(TipString.reversalSuccess)
    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(_mActivity, TipString.reversalIng)
    }

    override fun hideLoading() {
        dialog?.close()
    }

    override fun showError(msg: String) {
        ZBUiUtils.showError(msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }


    companion object {
        fun newInstance(): ProTargetReversalFrg {
            val fragment = ProTargetReversalFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}