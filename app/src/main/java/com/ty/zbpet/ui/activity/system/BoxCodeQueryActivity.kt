package com.ty.zbpet.ui.activity.system

import android.os.Bundle
import com.ty.zbpet.R
import com.ty.zbpet.ui.base.BaseActivity
import kotlinx.android.synthetic.main.layout_top_system.*

/**
 * @author TY on 2019/3/11.
 * 箱码查询
 */
class BoxCodeQueryActivity : BaseActivity() {
    override val activityLayout: Int
        get() = R.layout.activity_box_code_query

    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {
    }

    override fun initTwoView() {
        tv_title.text = "箱码查询"
    }
}