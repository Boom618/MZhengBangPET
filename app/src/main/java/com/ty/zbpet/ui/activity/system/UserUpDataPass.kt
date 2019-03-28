package com.ty.zbpet.ui.activity.system

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.data.Md5
import com.ty.zbpet.presenter.user.UserInterface
import com.ty.zbpet.presenter.user.UserPresenter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.activity_system_modify_pass.*


/**
 * @author PVer on 2018/12/15.
 * 修改密码
 */
class UserUpDataPass : BaseActivity(), UserInterface {


    private val presenter = UserPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_system_modify_pass

    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

    }

    override fun initTwoView() {

        initToolBar(R.string.label_modify_pwd)

        pass_confirm.setOnClickListener(View.OnClickListener {
            val oldWord = old_pass.text.toString().trim { it <= ' ' }
            val newWord = new_pass.text.toString().trim { it <= ' ' }
            val newWordAgain = new_pass_again.text.toString().trim { it <= ' ' }

            if (oldWord.isEmpty() || newWord.isEmpty() || newWordAgain.isEmpty()) {
                ZBUiUtils.showToast("密码不能为空")
                return@OnClickListener
            }
            if (newWord != newWordAgain) {
                ZBUiUtils.showToast("新密码不一致")
                return@OnClickListener
            }

            val md5OldWord = Md5.encryptMD5ToString(oldWord)
            val md5NewWord = Md5.encryptMD5ToString(newWord)
            val md5NewWordAgain = Md5.encryptMD5ToString(newWordAgain)

            presenter.userUpdatePass(md5OldWord, md5NewWord, md5NewWordAgain)
        })

    }


    override fun onSuccess() {
        ZBUiUtils.showToast("密码修改成功")
        finish()
    }

    override fun onError(e: String) {
        ZBUiUtils.showToast(e)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }
}
