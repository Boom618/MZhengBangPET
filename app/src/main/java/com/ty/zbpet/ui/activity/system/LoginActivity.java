package com.ty.zbpet.ui.activity.system;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.presenter.user.UserInterface;
import com.ty.zbpet.presenter.user.UserPresenter;
import com.ty.zbpet.ui.activity.MainActivity;
import com.ty.zbpet.ui.activity.MainCompanyActivity;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.constant.CodeConstant;
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


    /**
     * 登入入口
     */
    private boolean isCompany = false;

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

                if (isCompany) {
                    String companyNo = etCompanyNo.getText().toString().trim();
                    String userNo = etUserNo.getText().toString().trim();
                    String pass = etPwd.getText().toString().trim();
                    gotoActivity(MainCompanyActivity.class, true);
                } else {
                    String userName = etPhone.getText().toString().trim();
                    String pass = etPhonePwd.getText().toString().trim();
                    String md5Pass = Md5.encryptMD5ToString(pass);
                    presenter.userLogin(userName, md5Pass);
                }

                break;
            case R.id.tv_switch_way:
                // 切换组织
                TextView changeUser = findViewById(R.id.tv_switch_way);
                String userRole = changeUser.getText().toString();
                if (CodeConstant.CHANGE_ROLE_COMPANY.equals(userRole)) {
                    isCompany = true;
                    changeUser.setText(CodeConstant.CHANGE_ROLE_PHONE);
                    rlPhoneLogin.setVisibility(View.GONE);
                    rlCompanyLogin.setVisibility(View.VISIBLE);
                } else {
                    isCompany = false;
                    changeUser.setText(CodeConstant.CHANGE_ROLE_COMPANY);
                    rlPhoneLogin.setVisibility(View.VISIBLE);
                    rlCompanyLogin.setVisibility(View.GONE);
                }


                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess() {
        if (isCompany) {
            gotoActivity(MainCompanyActivity.class, true);
        } else {
            gotoActivity(MainActivity.class, true);
        }


    }

    @Override
    public void onError(String e) {

        ZBUiUtils.showToast(e);

    }
}
