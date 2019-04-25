package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment
import kotlinx.android.synthetic.main.fragment_materials_move.view.*

/**
 * @author TY on 2019/4/24.
 * 原辅料 移库
 */
class MaterialsMoveRoomFrg : BaseSupFragment() {
    override val fragmentLayout: Int
        get() = R.layout.fragment_materials_move

    override fun onBaseCreate(view: View): View {
        gotoMove(view)
        return view
    }

    private fun gotoMove(view: View) {
        view.source_location.setOnClickListener { start(SourceLocationFrg.newInstance()) }
        view.target_location.setOnClickListener { start(TargetLocationFrg.newInstance()) }
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        initToolBar(R.string.materials_move)
    }


    companion object {
        fun newInstance(): MaterialsMoveRoomFrg {
            val fragment = MaterialsMoveRoomFrg()

            val bundle = Bundle()
            bundle.putString("type", "type")
            fragment.arguments = bundle

            return fragment
        }
    }
}