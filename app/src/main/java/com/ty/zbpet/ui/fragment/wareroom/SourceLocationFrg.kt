package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
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
import com.ty.zbpet.ui.adapter.wareroom.SourceAdapter
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.fragment_move_source.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author TY on 2019/4/24.
 * 源库位
 */
class SourceLocationFrg : BaseSupFragment(), ComplexInterface<PositionCode> {

    private var adapter: SourceAdapter? = null
    private val presenter: MovePresenter = MovePresenter(this)

    override val fragmentLayout: Int
        get() = R.layout.fragment_move_source

    override fun onBaseCreate(view: View): View {
        EventBus.getDefault().register(this)

        return view
    }

    override fun onStart() {
        super.onStart()
        initToolBar(R.string.move_room_source, TipString.submit, View.OnClickListener {
            ZBUiUtils.showSuccess("submit")
            // TODO　创建源库位单
        })
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        ZBUiUtils.showSuccess(TipString.scanLocationSource)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun FragmentMsg(event: FragmentScanEvent) {
        val type = event.getType()
        val positionNo = event.getPositionNo()

        presenter.positionQuery(positionNo)
    }

    override fun showListData(list: MutableList<PositionCode>) {


    }

    override fun showObjData(obj: PositionCode) {
        // 接收查询的库位码信息
        val stockList = obj.stockList
        context?.let { LayoutInit.initLayoutManager(it, recycler_source) }
        recycler_source.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))

        adapter = context?.let {
            stockList?.let { it1 -> SourceAdapter(it, R.layout.item_move_source_frg, it1) }
        }
        recycler_source.adapter = adapter
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

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    companion object {
        fun newInstance(): SourceLocationFrg {
            val fragment = SourceLocationFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}