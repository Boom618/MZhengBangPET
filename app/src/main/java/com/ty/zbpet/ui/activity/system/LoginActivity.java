package com.ty.zbpet.ui.activity.system;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ty.zbpet.R;
import com.ty.zbpet.presenter.user.UserInterface;
import com.ty.zbpet.presenter.user.UserPresenter;
import com.ty.zbpet.ui.activity.MainActivity;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.util.Md5;
import com.ty.zbpet.util.ZBUiUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author TY
 */
public class LoginActivity extends BaseActivity implements UserInterface {

    @BindView(R.id.et_company_no)
    EditText etCompanyNo;
    @BindView(R.id.et_user_no)
    EditText etUserNo;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.rl_company_login)
    RelativeLayout rlCompanyLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_phone_pwd)
    EditText etPhonePwd;
    @BindView(R.id.rl_phone_login)
    RelativeLayout rlPhoneLogin;

    private UserPresenter presenter = new UserPresenter(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {

    }

    /**
     * 默认账号：test
     * 密码：MD5（123）
     *
     * @param view
     */

    @OnClick({R.id.btn_login, R.id.tv_switch_way})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                String userName = etPhone.getText().toString().trim();
                String pass = etPhonePwd.getText().toString().trim();
                String md5Pass = Md5.encryptMD5ToString(pass);

                presenter.userLogin(userName, md5Pass);
                break;
            case R.id.tv_switch_way:

                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess() {

        gotoActivity(MainActivity.class, true);

    }

    @Override
    public void onError(Throwable e) {

        ZBUiUtils.showToast(e.getMessage());

    }
}
