package com.ty.zbpet.ui.fragment.wareroom

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.base.BaseSupFragment

/**
 * @author TY on 2019/4/24.
 * 目标库位
 */
class TargetLocationFrg:BaseSupFragment() {
    override val fragmentLayout: Int
        get() = R.layout.fragment_move_target

    override fun onBaseCreate(view: View): View {
        return view
    }

    /**
     * 用户可见时 回调
     */
    override fun onSupportVisible() {
        super.onSupportVisible()
        initToolBar(R.string.move_room_target)
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