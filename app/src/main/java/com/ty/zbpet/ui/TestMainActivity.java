package com.ty.zbpet.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ty.zbpet.R;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.util.ZBUiUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;

/**
 * @author TY on 2018/12/25.
 * <p>
 * 测试页面
 */
public class TestMainActivity extends BaseActivity {

    ImageView imageView;

    RxPermissions rxPermissions;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

        imageView = findViewById(R.id.user_image);

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main_test;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {

        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);

    }

    public void open(View view) {

        rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.name.equals(Manifest.permission.CAMERA)) {
                            openCamera();
                        }
                    }
                });

        rxPermissions.ensureEach(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
                .apply(new Observable<Object>() {
                    @Override
                    protected void subscribeActual(Observer<? super Object> observer) {

                    }
                });

    }

    private void openCamera(){
        ZBUiUtils.showToast("允许 = 了权限!");
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, 100);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            /*缩略图信息是储存在返回的intent中的Bundle中的，
             * 对应Bundle中的键为data，因此从Intent中取出
             * Bundle再根据data取出来Bitmap即可*/
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            imageView.setImageBitmap(bitmap);
        }
    }

}
