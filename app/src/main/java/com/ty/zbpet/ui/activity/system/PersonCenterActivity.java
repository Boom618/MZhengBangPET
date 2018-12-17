package com.ty.zbpet.ui.activity.system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker_extension.MimeType;
import com.qingmei2.rximagepicker_extension_zhihu.ZhihuConfigurationBuilder;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.system.ImageData;
import com.ty.zbpet.constant.ApiNameConstant;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.user.UserInterface;
import com.ty.zbpet.presenter.user.UserPresenter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.SimpleCache;
import com.ty.zbpet.util.ZBUiUtils;
import com.ty.zbpet.util.image.ZhiHuImagePicker;

import java.io.File;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.os.Environment.DIRECTORY_DCIM;

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

    private Uri uri;

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

    /**
     * 选择图片
     */
    private void selectImage() {

        ZBUiUtils.showToast("selectImage");


        systemGallery();

        //zhiHuGallery();


    }

    /**
     * 上传图片
     */
    private void updateImage() {

        //获取图片的路径：

        // /storage/emulated/0/DCIM/Camera/IMG_20181025_150858.jpg
        //  /data
        String systemPath = Environment.getDataDirectory().getPath();
        // /storage/emulated/0
        String filepath = Environment.getExternalStorageDirectory().getPath();

        // /storage/emulated/0/DCIM
        File directory = Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM);


        String path = ResourceUtil.getRealPathFromUri(this, uri);

        File file = new File(path);


//        =========

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

//=====================

        HttpMethods methods = new HttpMethods(ApiNameConstant.BASE_URL2);

        methods.updateCheckImage(new SingleObserver<ImageData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ImageData responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {

                    String fileName = responseInfo.getFileName();
                    ZBUiUtils.showToast(responseInfo.getTag());
                }

            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        }, imageBodyPart);
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

                        uri = result.getUri();

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

    @Override
    public void onSuccess() {
        if (isExit) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

            SimpleCache.clearAll();
            finish();
        } else {
            // 个人中心数据

        }

    }

    @Override
    public void onError(Throwable e) {


    }
}
