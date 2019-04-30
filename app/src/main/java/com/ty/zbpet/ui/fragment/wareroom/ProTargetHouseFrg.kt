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
 * 移入目标仓库
 */
class ProTargetHouseFrg : BaseSupFragment(), ComplexInterface<PositionCode> {

    private val presenter: MovePresenter = MovePresenter(this)

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
        initToolBar(R.string.move_house_target, TipString.define, View.OnClickListener {
            initReqBody()
        })
        //
        presenter.moveProductList()
    }

    private fun initReqBody() {

    }



    override fun showListData(list: MutableList<PositionCode>) {


    }

    override fun showObjData(obj: PositionCode) {

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
        fun newInstance(): ProTargetHouseFrg {
            val fragment = ProTargetHouseFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}