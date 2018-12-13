package com.ty.zbpet.ui.activity.system;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker_extension.MimeType;
import com.qingmei2.rximagepicker_extension_zhihu.ZhihuConfigurationBuilder;
import com.ty.zbpet.R;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.util.ZBUiUtils;
import com.ty.zbpet.util.image.ZhiHuImagePicker;

import io.reactivex.functions.Consumer;

/**
 * 质检个人中心
 *
 * @author TY
 */
public class PersonCenterActivity extends BaseActivity {

    private Button btnPass;
    private Button btnExit;
    private ImageView userImage;
    private ImageView backImage;

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

        btnPass = findViewById(R.id.btn_modify_pwd);
        btnExit = findViewById(R.id.btn_cancel);
        userImage = findViewById(R.id.user_select_image);
        backImage = findViewById(R.id.iv_back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });


    }

    /**
     * 选择图片
     */
    private void selectImage() {

        ZBUiUtils.showToast("selectImage");


        //systemGallery();

        //zhiHuGallery();


    }

    /**
     * 知乎相册选择
     */
    @SuppressLint("CheckResult")
    private void zhiHuGallery() {
        RxImagePicker.INSTANCE.create(ZhiHuImagePicker.class)
                .openGallery(this, new ZhihuConfigurationBuilder(MimeType.INSTANCE.ofImage(), false)
                        .maxSelectable(3)
                        .countable(true)
                        .spanCount(4)
                        .theme(R.style.Zhihu_Normal)
                        .build())
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        Uri uri = result.getUri();
                        Glide.with(PersonCenterActivity.this)
                                .load(uri)
                                .into(userImage);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ZBUiUtils.showToast(throwable.getMessage());
                    }
                });
    }

    /**
     * 打开系统相册
     */
    @SuppressLint("CheckResult")
    private void systemGallery() {
        RxImagePicker.INSTANCE
                .create()
                .openGallery(this)
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {

                        Uri uri = result.getUri();

                        ZBUiUtils.showToast(uri.toString());
                        Glide.with(PersonCenterActivity.this)
                                .load(uri)
                                .into(userImage);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        ZBUiUtils.showToast(throwable.getMessage());

                    }
                });
    }
}
