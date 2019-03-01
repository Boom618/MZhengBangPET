package com.ty.zbpet.ui.activity.system

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.ty.zbpet.R
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.user.UserInterface
import com.ty.zbpet.presenter.user.UserPresenter
import com.ty.zbpet.ui.activity.MainActivity
import com.ty.zbpet.ui.activity.MainCompanyActivity
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.data.Md5
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @author TY
 */
class LoginActivity : BaseActivity(), UserInterface {

    override val activityLayout: Int
        get() = R.layout.activity_login

    /**
     * 登入入口
     */
    private var isCompany = false

    private val presenter = UserPresenter(this)

    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

    }

    override fun initTwoView() {

        // 点击事件不响应
        btn_login.setOnClickListener { _ ->
            if (isCompany) {
                val companyNo = et_company_no!!.text.toString().trim { it <= ' ' }
                val userNo = et_user_no!!.text.toString().trim { it <= ' ' }
                val pass = et_pwd!!.text.toString().trim { it <= ' ' }
                gotoActivity(MainCompanyActivity::class.java, true)
            } else {
                val userName = et_phone!!.text.toString().trim { it <= ' ' }
                val pass = et_phone_pwd!!.text.toString().trim { it <= ' ' }
                val md5Pass = Md5.encryptMD5ToString(pass)
//                presenter.userLogin(userName, md5Pass)
                presenter.userLogin(userName, md5Pass)
            }
        }

        tv_switch_way.setOnClickListener {
            // 切换组织
            val changeUser = findViewById<TextView>(R.id.tv_switch_way)
            val userRole = changeUser.text.toString()
            if (CodeConstant.CHANGE_ROLE_COMPANY == userRole) {
                isCompany = true
                changeUser.text = CodeConstant.CHANGE_ROLE_PHONE
                rl_phone_login!!.visibility = View.GONE
                rl_company_login!!.visibility = View.VISIBLE
            } else {
                isCompany = false
                changeUser.text = CodeConstant.CHANGE_ROLE_COMPANY
                rl_phone_login!!.visibility = View.VISIBLE
                rl_company_login!!.visibility = View.GONE
            }
        }
    }

    /**
     * 默认账号：test
     * 密码：MD5（123）
     *
     * @param view
     */


    override fun onSuccess() {
        if (isCompany) {
            gotoActivity(MainCompanyActivity::class.java, true)
        } else {
            gotoActivity(MainActivity::class.java, true)
        }
    }

    override fun onError(e: String) {

        ZBUiUtils.showToast(e)
    }
}
