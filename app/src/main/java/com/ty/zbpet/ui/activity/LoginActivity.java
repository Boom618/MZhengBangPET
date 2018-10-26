package com.ty.zbpet.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author TY
 */
public class LoginActivity extends BaseActivity {

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

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.btn_login, R.id.tv_switch_way})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                break;

            case R.id.tv_switch_way:

                break;
            default:
                break;
        }
    }
}
