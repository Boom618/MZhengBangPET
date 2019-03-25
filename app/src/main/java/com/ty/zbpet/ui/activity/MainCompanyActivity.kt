package com.ty.zbpet.ui.activity

import android.os.Bundle
import com.ty.zbpet.R
import com.ty.zbpet.ui.activity.system.PersonCenterActivity
import com.ty.zbpet.ui.activity.system.QualityCheckTabActivity
import com.ty.zbpet.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main2.*

/**
 * @author TY on 2018/12/20.
 *
 *
 * 组织代码 主页
 */
class MainCompanyActivity : BaseActivity() {
    override val activityLayout: Int
        get() = R.layout.activity_main2

    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {
    }

    override fun initTwoView() {

        tv_inventory.setOnClickListener {
            //                gotoActivity(QualityCheckActivity.class);
            gotoActivity(QualityCheckTabActivity::class.java)
        }

        tv_person_center.setOnClickListener { gotoActivity(PersonCenterActivity::class.java) }
    }
}
