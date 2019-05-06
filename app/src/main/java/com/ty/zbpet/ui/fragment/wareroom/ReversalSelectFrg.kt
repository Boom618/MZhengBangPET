package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import kotlinx.android.synthetic.main.reversal_select_frg.view.*

/**
 * @author TY on 2019/4/24.
 *  产品冲销选择
 */
class ReversalSelectFrg : BaseSupFragment() {
    override val fragmentLayout: Int
        get() = R.layout.reversal_select_frg

    override fun onBaseCreate(view: View): View {
        view.reversal_source.setOnClickListener {
            // start(ProSourceReversalFrg.newInstance())
            start(ProTargetHouseFrg.newInstance("reversal"))
        }
        view.reversal_target.setOnClickListener {
            start(ProTargetReversalFrg.newInstance())
        }
        return view
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        initToolBar(R.string.reversal_select)
    }


    companion object {
        fun newInstance(): ReversalSelectFrg {
            val fragment = ReversalSelectFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}