package com.ty.zbpet.ui.activity.system;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ty.zbpet.R;
import com.ty.zbpet.presenter.user.UserInterface;
import com.ty.zbpet.presenter.user.UserPresenter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.data.Md5;
import com.ty.zbpet.util.ZBUiUtils;


/**
 * @author PVer on 2018/12/15.
 * 修改密码
 */
public class UserUpDataPass extends BaseActivity implements UserInterface {


    private EditText oldPass;
    private EditText newPass;
    private EditText newPassAgain;
    private Button btnConfirm;

    private UserPresenter presenter = new UserPresenter(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_system_modify_pass;
    }

    @Override
    protected void initOneData() {

        oldPass = findViewById(R.id.old_pass);
        newPass = findViewById(R.id.new_pass);
        newPassAgain = findViewById(R.id.new_pass_again);
        btnConfirm = findViewById(R.id.pass_confirm);

    }

    @Override
    protected void initTwoView() {

        initToolBar(R.string.label_modify_pwd);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldWord = oldPass.getText().toString().trim();
                String newWord = newPass.getText().toString().trim();
                String newWordAgain = newPassAgain.getText().toString().trim();

                if (TextUtils.isEmpty(oldWord) || TextUtils.isEmpty(newWord) || TextUtils.isEmpty(newWordAgain)) {
                    ZBUiUtils.showToast("密码不能为空");
                    return;
                }
                if (!newWord.equals(newWordAgain)) {
                    ZBUiUtils.showToast("新密码不一致");
                    return;
                }

                String md5OldWord = Md5.encryptMD5ToString(oldWord);
                String md5NewWord = Md5.encryptMD5ToString(newWord);
                String md5NewWordAgain = Md5.encryptMD5ToString(newWordAgain);

                presenter.userUpdatePass(md5OldWord, md5NewWord, md5NewWordAgain);
            }
        });

    }


    @Override
    public void onSuccess() {
        ZBUiUtils.showToast("密码修改成功");
        finish();
    }

    @Override
    public void onError(String e) {
        ZBUiUtils.showToast(e);
    }
}
