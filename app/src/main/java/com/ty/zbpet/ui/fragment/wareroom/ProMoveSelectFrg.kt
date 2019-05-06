package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import kotlinx.android.synthetic.main.pro_move_select_frg.view.*

/**
 * @author TY on 2019/4/24.
 *  产品移库选择
 */
class ProMoveSelectFrg : BaseSupFragment() {
    override val fragmentLayout: Int
        get() = R.layout.pro_move_select_frg

    override fun onBaseCreate(view: View): View {
        view.move_source.setOnClickListener {
            start(ProductSelectFrg.newInstance())
        }
        view.move_target.setOnClickListener {
            start(ProTargetHouseFrg.newInstance("move"))
        }
        return view
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        initToolBar(R.string.product_move)
    }


    companion object {
        fun newInstance(): ProMoveSelectFrg {
            val fragment = ProMoveSelectFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}