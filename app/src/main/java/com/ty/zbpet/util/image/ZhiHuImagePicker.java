package com.ty.zbpet.util.image;

import android.content.Context;

import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker.entity.sources.Camera;
import com.qingmei2.rximagepicker.entity.sources.Gallery;
import com.qingmei2.rximagepicker_extension.entity.SelectionSpec;
import com.qingmei2.rximagepicker_extension_zhihu.ui.ZhihuImagePickerActivity;

import io.reactivex.Observable;

/**
 * @author TY on 2018/12/12.
 * 知乎 相册选择
 *
 */
public interface ZhiHuImagePicker {

    @Gallery(componentClazz = ZhihuImagePickerActivity.class,openAsFragment = false)
    Observable<Result> openGallery(Context context, SelectionSpec config);

    @Camera
    Observable<Result> openCamera(Context context);
}