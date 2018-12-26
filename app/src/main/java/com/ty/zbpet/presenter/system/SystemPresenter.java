package com.ty.zbpet.presenter.system;

import android.content.Context;

import com.ty.zbpet.bean.system.ImageData;
import com.ty.zbpet.bean.system.QualityCheckTodoDetails;
import com.ty.zbpet.bean.system.QualityCheckTodoList;
import com.ty.zbpet.constant.ApiNameConstant;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ZBUiUtils;

import java.io.File;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author TY on 2018/12/12.
 */
public class SystemPresenter {

    SystemUiListInterface listInterface;


    /**
     * API 网络
     */
    HttpMethods httpMethods;

    Disposable disposable;

    public SystemPresenter(SystemUiListInterface listInterface) {
        this.listInterface = listInterface;
        httpMethods = new HttpMethods(ApiNameConstant.BASE_URL2);

    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }


    /**
     * 获取质检 待办列表
     */
    public void fetchQualityCheckTodoList() {
        httpMethods.getQualityCheckTodoList(new SingleObserver<BaseResponse<QualityCheckTodoList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<QualityCheckTodoList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<QualityCheckTodoList.DataBean> list = response.getData().getList();

                    listInterface.showSystem(list);
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        });
    }


    /**
     * 待办 详情
     * @param arrivalOrderNo
     */
    public void fetchQualityCheckTodoInfo(String arrivalOrderNo){

        httpMethods.getQualityCheckTodoInfo(new SingleObserver<BaseResponse<QualityCheckTodoDetails>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<QualityCheckTodoDetails> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<QualityCheckTodoDetails.DataBean> list = response.getData().getList();

                    listInterface.showSystem(list);
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        },arrivalOrderNo);
    }

    /**
     * 获取质检 已办列表
     */
    public void fetchQualityCheckDoneList() {
        httpMethods.getQualityCheckDoneList(new SingleObserver<BaseResponse<QualityCheckTodoList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<QualityCheckTodoList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<QualityCheckTodoList.DataBean> list = response.getData().getList();

                    listInterface.showSystem(list);
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        });
    }


    /**
     * 已办 详情
     * @param arrivalOrderNo
     */
    public void fetchQualityCheckDoneInfo(String arrivalOrderNo){

        httpMethods.getQualityCheckDoneInfo(new SingleObserver<BaseResponse<QualityCheckTodoList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<QualityCheckTodoList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<QualityCheckTodoList.DataBean> list = response.getData().getList();

                    listInterface.showSystem(list);
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        },arrivalOrderNo);
    }

    /**
     * 上传质检照片
     */
    public void updateImage(Context context, final int position, String path) {


        File file = new File(path);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        httpMethods.updateCheckImage(new SingleObserver<ImageData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ImageData responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {

                    String fileName = responseInfo.getFileName();
                    // TODO 保存图片（目前只支持一张图片）
                    DataUtils.setImageId(position, fileName);
                    ZBUiUtils.showToast("图片上传：" + responseInfo.getTag());
                }

            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        }, imageBodyPart);
    }


}
