package com.ty.zbpet.ui.activity.system

import android.content.Intent
import android.os.Bundle
import com.ty.zbpet.R
import com.ty.zbpet.presenter.user.UserInterface
import com.ty.zbpet.presenter.user.UserPresenter
import com.ty.zbpet.ui.ActivitiesHelper
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.util.SimpleCache
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.activity_person_center_c.*

/**
 * 质检个人中心
 *
 * @author TY
 */
class PersonCenterActivity : BaseActivity(), UserInterface {

    private var isExit = false

    private val presenter = UserPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_person_center_c

    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

    }

    override fun initTwoView() {


        iv_back.setOnClickListener { finish() }

        ll_track.setOnClickListener { gotoActivity(StockTrackActivity::class.java) }
//        image_to_query.setOnClickListener {gotoActivity(BoxCodeQueryActivity::class.java)}
        ll_position.setOnClickListener { gotoActivity(PositionQueryActivity::class.java) }
        btn_modify_pwd.setOnClickListener { gotoActivity(UserUpDataPass::class.java) }
        btn_cancel!!.setOnClickListener { exitApp() }

    }


    /**
     * 退出登录
     */
    private fun exitApp() {
        isExit = true
        presenter.userLogOut()
    }


    override fun onStart() {
        super.onStart()

        presenter.userCenter()
    }


    override fun onSuccess() {
        if (isExit) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)

            SimpleCache.clearAll()
            ActivitiesHelper.get().finishAll()
            finish()
        } else {
            // 个人中心数据

        }

    }

    override fun onError(e: String) {
        ZBUiUtils.showError(e)

    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }
}
