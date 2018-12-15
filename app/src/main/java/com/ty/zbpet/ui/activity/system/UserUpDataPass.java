package com.ty.zbpet.ui.activity.system;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ty.zbpet.presenter.user.UserInterface;
import com.ty.zbpet.presenter.user.UserPresenter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.util.Md5;
import com.ty.zbpet.util.ZBUiUtils;


/**
 * @author PVer on 2018/12/15.
 * 修改密码
 */
public class UserUpDataPass extends BaseActivity implements UserInterface {


    private Button btnChagePass;
    private EditText oldPass;
    private EditText newPass;
    private EditText newPassAgain;

    private UserPresenter presenter = new UserPresenter(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return 0;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {


        btnChagePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldWord = oldPass.getText().toString().trim();
                String newWord = newPass.getText().toString().trim();
                String newWordAgain = newPassAgain.getText().toString().trim();

                if (TextUtils.isEmpty(oldWord) || TextUtils.isEmpty(newWord) || TextUtils.isEmpty(newWordAgain)) {
                    ZBUiUtils.showToast("密码不能为空");
                    return;
                }
                if (newWord.equals(newWordAgain)) {
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
    }

    @Override
    public void onError(Throwable e) {

    }
}
