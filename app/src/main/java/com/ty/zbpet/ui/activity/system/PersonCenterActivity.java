package com.ty.zbpet.ui.activity.system;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ty.zbpet.R;
import com.ty.zbpet.presenter.user.UserInterface;
import com.ty.zbpet.presenter.user.UserPresenter;
import com.ty.zbpet.ui.ActivitiesHelper;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.util.SimpleCache;

/**
 * 质检个人中心
 *
 * @author TY
 */
public class PersonCenterActivity extends BaseActivity implements UserInterface {

    private Button btnUpDataPass;
    private Button btnExit;
    private ImageView userImage;
    private ImageView backImage;

    private boolean isExit = false;

    private UserPresenter presenter = new UserPresenter(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_person_center_c;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {

        btnUpDataPass = findViewById(R.id.btn_modify_pwd);
        btnExit = findViewById(R.id.btn_cancel);
        userImage = findViewById(R.id.user_select_image);
        userImage.setVisibility(View.GONE);
        backImage = findViewById(R.id.iv_back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnUpDataPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(UserUpDataPass.class);

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exitApp();
            }
        });

    }


    /**
     * 退出登录
     */
    private void exitApp() {
        isExit = true;
        presenter.userLogOut();
    }


    @Override
    protected void onStart() {
        super.onStart();

        presenter.userCenter();
    }


    @Override
    public void onSuccess() {
        if (isExit) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

            SimpleCache.clearAll();
            ActivitiesHelper.get().finishAll();
            finish();
        } else {
            // 个人中心数据

        }

    }

    @Override
    public void onError(String e) {


    }
}
