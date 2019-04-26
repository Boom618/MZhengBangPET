package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
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
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
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
        initToolBar(R.string.move_room_target, TipString.define, View.OnClickListener {
            initReqBody()
        })
        presenter.moveList()
    }

    private fun initReqBody() {
        val positionNo = tv_target.text.toString()
        if (positionNo.isEmpty()) {
            ZBUiUtils.showWarning(TipString.scanLocationTarget)
            return
        }
        var id = ""
        val count = listSource.size
        for (i in 0 until count) {
            val view = recycler_target.getChildAt(i)
            val checkBox = view.findViewById<CheckBox>(R.id.check)
            // TODO 单选 待完善
            if (checkBox.isChecked) {
                id = listSource[i].id!!
            }
        }
        // id,positionNo,warehouseNo,time
        presenter.moveMaterial(id, positionNo, warehouseNo, "2019-04-27")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun FragmentMsg(event: FragmentScanEvent) {
        val type = event.getType()
        val positionNo = event.getPositionNo()
        if (type.equals("target")) {
            tv_target.text = positionNo
        }
        // TODO 根据库位码 查询 warehouseNo
        presenter.positionStock(positionNo)
    }

    override fun showListData(list: MutableList<PositionCode>) {
        listSource = list[0].list!!
        context?.let { LayoutInit.initLayoutManager(it, recycler_target) }
        recycler_target.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
        adapter = context?.let { TargetAdapter(it, R.layout.item_move_target_frg, listSource) }
        recycler_target.adapter = adapter

    }

    override fun showObjData(obj: PositionCode) {
        warehouseNo = obj.warehouseNo
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