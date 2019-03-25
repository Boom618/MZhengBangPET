package com.ty.zbpet.presenter.system;

import android.content.Context;

import com.ty.zbpet.bean.eventbus.ErrorMessage;
import com.ty.zbpet.bean.material.MaterialDetails;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.bean.system.ImageData;
import com.ty.zbpet.bean.system.QualityCheckTodoDetails;
import com.ty.zbpet.bean.system.QualityCheckTodoList;
import com.ty.zbpet.constant.ApiNameConstant;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ZBUiUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
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

    private SystemUiListInterface listInterface;


    /**
     * API 网络
     */
    private HttpMethods httpMethods;

    private Disposable disposable;

    public SystemPresenter(SystemUiListInterface listInterface) {
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();

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
        httpMethods.getMaterialTodoList(new SingleObserver<BaseResponse<MaterialList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialList.ListBean> list = response.getData().getList();

                    listInterface.showSystem(list);
                } else {
                    EventBus.getDefault().post(new ErrorMessage(response.getMessage()));
                }
            }
        }, "", "", "");

    }


    /**
     * 待办 详情
     *
     * @param sapFirmNo sapFirmNo
     */
    public void fetchQualityCheckTodoInfo(String sapFirmNo,String sapOrderNo,String supplierNo) {

        httpMethods.getMaterialTodoListDetail(new SingleObserver<BaseResponse<MaterialDetails>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetails> info) {

                if (CodeConstant.SERVICE_SUCCESS.equals(info.getTag())) {

                    ArrayList<MaterialDetails.ListBean> list = info.getData().getList();

                    EventBus.getDefault().post(list);

                } else {
                    EventBus.getDefault().post(new ErrorMessage(info.getMessage()));
                }
            }
        }, sapFirmNo, sapOrderNo, supplierNo);

    }

    /**
     * 获取质检 已办列表
     */
    public void fetchQualityCheckDoneList(String type, String sapOrderNo, String startDate, String endDate) {

        httpMethods.getMaterialDoneList(new SingleObserver<BaseResponse<MaterialList>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    List<MaterialList.ListBean> list = response.getData().getList();
                    listInterface.showSystem(list);
                } else {
                    EventBus.getDefault().post(new ErrorMessage(response.getMessage()));
                }
            }
        }, type, sapOrderNo, startDate, endDate);
    }


    /**
     * 已办 详情
     *
     * @param arrivalOrderNo
     */
    public void fetchQualityCheckDoneInfo(String arrivalOrderNo) {

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
                }else{
                    EventBus.getDefault().post(new ErrorMessage(response.getMessage()));
                }
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
            }
        }, arrivalOrderNo);
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
