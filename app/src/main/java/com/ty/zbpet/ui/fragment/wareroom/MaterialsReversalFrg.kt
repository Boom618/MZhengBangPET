package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import com.google.gson.Gson
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import com.ty.zbpet.bean.system.PositionCode
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.move.ComplexInterface
import com.ty.zbpet.presenter.move.MovePresenter
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.wareroom.MaterialReversalAdapter
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.fragment_move_source_reversal.*
import okhttp3.RequestBody

/**
 * @author TY on 2019/4/24.
 * 原辅料 冲销
 */
class MaterialsReversalFrg : BaseSupFragment(), ComplexInterface<PositionCode.StockListBean> {

    private var adapter: MaterialReversalAdapter? = null
    private var selectPosition = -1
    private var reversalList: MutableList<PositionCode.StockListBean> = mutableListOf()
    private val presenter: MovePresenter = MovePresenter(this)

    override val fragmentLayout: Int
        get() = R.layout.fragment_move_source_reversal

    override fun onBaseCreate(view: View): View {
        return view
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        initToolBar(R.string.materials_move_reversal, TipString.reversal, View.OnClickListener {
            reqFun(initBody())
        })
        presenter.reversalList()
    }

    private fun initBody(): RequestBody? {

        val size = recycler_mater.childCount
        // TODO   view.findViewById 取值问题
        var stockId = 0
        for (i in 0 until size) {
            val view = recycler_mater.getChildAt(i)
            val checkBox = view.findViewById<CheckBox>(R.id.check)
            if (checkBox.isChecked) {
                stockId = i
            }
        }
        when (selectPosition == -1) {
            true -> ZBUiUtils.showWarning(TipString.reversalSelect)
            else -> {
                val json = Gson().toJson(reversalList[selectPosition])
                return RequestBodyJson.requestBody(json)
            }
        }
        return null
    }

    private fun reqFun(body: RequestBody?) {
        if (body == null) {
            return
        }
        presenter.reversalMove(body)
    }

    override fun showListData(list: MutableList<PositionCode.StockListBean>) {
        reversalList = list
        context?.let { LayoutInit.initLayoutManager(it, recycler_mater) }
        recycler_mater.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
        adapter = context?.let { MaterialReversalAdapter(it, R.layout.item_reversal_source_frg, list) }
        recycler_mater.adapter = adapter

        adapter?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemLongClick(view: View?, holder: androidx.recyclerview.widget.RecyclerView.ViewHolder?, position: Int): Boolean {
                return true
            }

            override fun onItemClick(view: View?, holder: androidx.recyclerview.widget.RecyclerView.ViewHolder?, position: Int) {
                adapter?.setSelection(position)
                selectPosition = position
            }
        })

    }

    override fun showObjData(obj: PositionCode.StockListBean) {
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

    companion object {
        fun newInstance(): MaterialsReversalFrg {
            val fragment = MaterialsReversalFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}