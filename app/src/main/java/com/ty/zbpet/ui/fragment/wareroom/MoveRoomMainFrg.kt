package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import kotlinx.android.synthetic.main.fragment_move_main.view.*

/**
 * @author TY on 2019/4/24.
 * Frg  移库主页
 */
class MoveRoomMainFrg : BaseSupFragment() {
    override val fragmentLayout: Int
        get() = R.layout.fragment_move_main

    override fun onBaseCreate(view: View): View {
        gotoNextFrg(view)
        return view
    }

    private fun gotoNextFrg(view: View) {

        view.move_room_materials.setOnClickListener { start(MaterialsMoveRoomFrg.newInstance()) }
        view.reversal_materials.setOnClickListener { start(MaterialsReversalFrg.newInstance()) }
        view.move_room_product.setOnClickListener { start(ProductMoveFrg.newInstance()) }
        view.reversal_product.setOnClickListener { start(ProductReversalFrg.newInstance()) }

    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        initToolBar(R.string.label_transfer_storage)
    }


    companion object {
        fun newInstance(type: String): MoveRoomMainFrg {
            val fragment = MoveRoomMainFrg()

            val bundle = Bundle()
            bundle.putString("type", type)
            fragment.arguments = bundle

            return fragment
        }
    }
}