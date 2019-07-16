package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import com.ty.zbpet.bean.eventbus.system.FragmentScanEvent
import com.ty.zbpet.bean.system.PositionCode
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.presenter.move.ComplexInterface
import com.ty.zbpet.presenter.move.MovePresenter
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.wareroom.TargetAdapter
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.fragment_move_target.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author TY on 2019/4/24.
 * 目标库位
 */
class TargetLocationFrg : BaseSupFragment(), ComplexInterface<PositionCode> {

    private var adapter: TargetAdapter? = null
    private var listSource = mutableListOf<PositionCode.StockListBean>()
    private val presenter: MovePresenter = MovePresenter(this)
    private var warehouseNo = ""
    // 查的 列表 还是 目标库位
    private var isList = true
    private var selectPosition = -1

    override val fragmentLayout: Int
        get() = R.layout.fragment_move_target

    override fun onBaseCreate(view: View): View {
        EventBus.getDefault().register(this)
        return view
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()

        presenter.moveList()
        isList = true
    }

    override fun onStart() {
        super.onStart()
        initToolBar(R.string.move_room_target, TipString.define, View.OnClickListener {
            initReqBody()
        })
    }

    private fun initReqBody() {
        val positionNo = tv_target.text.toString()
        if (positionNo.isEmpty()) {
            ZBUiUtils.showWarning(TipString.scanLocationTarget)
            return
        }
        when (selectPosition == -1) {
            true -> ZBUiUtils.showWarning(TipString.moveSelect)
            else -> {
                val id = listSource[selectPosition].id
                presenter.moveMaterial(id, positionNo, warehouseNo, "")
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun FragmentMsg(event: FragmentScanEvent) {
        val type = event.getType()
        val positionNo = event.getPositionNo()
        if (type.equals("target")) {
            tv_target.text = positionNo
            // TODO 根据库位码 查询 warehouseNo
            presenter.positionStock(positionNo)
            isList = false
        }
    }

    override fun showListData(list: MutableList<PositionCode>) {


    }

    override fun showObjData(obj: PositionCode) {
        when (isList) {
            true -> {
                listSource = obj.list!!
                context?.let { LayoutInit.initLayoutManager(it, recycler_target) }
                recycler_target.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
                adapter = context?.let { TargetAdapter(it, R.layout.item_move_target_frg, listSource) }
                recycler_target.adapter = adapter

                adapter?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                        return true
                    }

                    override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                        adapter?.setSelection(position)
                        selectPosition = position
                    }
                })
            }
            false -> warehouseNo = obj.warehouseNo
        }

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

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    companion object {
        fun newInstance(): TargetLocationFrg {
            val fragment = TargetLocationFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}