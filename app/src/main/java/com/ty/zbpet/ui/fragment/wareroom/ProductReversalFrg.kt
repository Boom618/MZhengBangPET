package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment

/**
 * @author TY on 2019/4/24.
 * 成品 冲销
 */
class ProductReversalFrg : BaseSupFragment() {
    override val fragmentLayout: Int
        get() = R.layout.fragment_product_move

    override fun onBaseCreate(view: View): View {
        return view
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        initToolBar(R.string.materials_move_reversal)
    }


    companion object {
        fun newInstance(): ProductReversalFrg {
            val fragment = ProductReversalFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}