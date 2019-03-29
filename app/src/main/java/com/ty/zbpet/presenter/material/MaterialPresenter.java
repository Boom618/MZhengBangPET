package com.ty.zbpet.presenter.material;

import android.content.Context;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.eventbus.ErrorMessage;
import com.ty.zbpet.bean.eventbus.SuccessMessage;
import com.ty.zbpet.bean.eventbus.UrlMessage;
import com.ty.zbpet.bean.eventbus.system.CheckDoneDetailEvent;
import com.ty.zbpet.bean.material.MaterialDetails;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.bean.system.BoxCodeUrl;
import com.ty.zbpet.bean.system.ImageData;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ZBUiUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author PVer on 2018/10/28.
 * <p>
 * 采购入库 Presenter
 */
public class MaterialPresenter {

    /**
     * UI 接口
     */
    private MaterialUiListInterface materialListUi;


    /**
     * API 网络
     */
    private HttpMethods httpMethods;

    private Disposable disposable;

    /**
     * 接收 list UI 接口
     *
     * @param materialUiInterface
     */
    public MaterialPresenter(MaterialUiListInterface materialUiInterface) {
        this.materialListUi = materialUiInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public MaterialPresenter() {
        httpMethods = HttpMethods.getInstance();
    }


    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * 原辅料 待办 list  业务逻辑
     */
    public void fetchTODOMaterial(String sapOrderNo, String startDate, String endDate) {

        materialListUi.showLoading();

        // APi  获取数据
        httpMethods.getMaterialTodoList(new SingleObserver<BaseResponse<MaterialList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> response) {
                materialListUi.hideLoading();

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialList.ListBean> list = response.getData().getList();

                    materialListUi.showMaterial(list);
                } else {
                    materialListUi.showError(response.getMessage());
                }
            }
        }, sapOrderNo, startDate, endDate);

    }

    /**
     * 原辅料 待办 list  详情  MaterialPurchaseInData  MaterialDetailsData
     */
    public void fetchTODOMaterialDetails(String sapFirmNo, String sapOrderNo, String supplierNo) {

        httpMethods.getMaterialTodoListDetail(new SingleObserver<BaseResponse<MaterialDetails>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetails> info) {

                if (CodeConstant.SERVICE_SUCCESS.equals(info.getTag())) {

                    MaterialDetails data = info.getData();

                    materialListUi.showMaterial(data.getList());

                } else {
                    materialListUi.showError(info.getMessage());
                }
            }
        }, sapFirmNo, sapOrderNo, supplierNo);
    }

    /**
     * URL 解析
     *
     * @param url url
     */
    public void urlAnalyze(int position, String url) {
        httpMethods.urlAnalyze(new SingleObserver<BaseResponse<BoxCodeUrl>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<BoxCodeUrl> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    String qrCode = response.getData().getBoxQrCode();
                    EventBus.getDefault().post(new UrlMessage(position, qrCode));
                } else {
                    materialListUi.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }
        }, url);

    }

    /**
     * 库位码校验
     *
     * @param positionNo
     */
    public void checkCarCode(final int position, final String positionNo) {

        httpMethods.checkCarCode(new SingleObserver<CarPositionNoData>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }

            @Override
            public void onSuccess(CarPositionNoData responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 库位码合法
                    materialListUi.showCarSuccess(position, responseInfo);
                } else {
                    materialListUi.showError(responseInfo.getMessage());
                }

            }
        }, positionNo);
    }


    /**
     * 待办 保存
     */
    public void materialTodoInSave(RequestBody body) {
        materialListUi.showLoading();

        httpMethods.materialTodoInSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                materialListUi.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 入库成功（保存）
                    materialListUi.saveSuccess();
                } else {
                    materialListUi.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.hideLoading();
                materialListUi.showError(e.getMessage());
            }
        }, body);

    }

    /**
     * 原材料 已办 列表
     */
    public void fetchDoneMaterial(String type, String sapOrderNo, String startDate, String endDate) {
        httpMethods.getMaterialDoneList(new SingleObserver<BaseResponse<MaterialList>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    List<MaterialList.ListBean> list = response.getData().getList();
                    materialListUi.showMaterial(list);
                } else {
                    materialListUi.showError(response.getMessage());
                }
            }
        }, type, sapOrderNo, startDate, endDate);
    }

    /**
     * 已办详情
     */
    public void fetchDoneMaterialDetails(String orderId) {
        httpMethods.getMaterialDoneListDetail(new SingleObserver<BaseResponse<MaterialDetails>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetails> infoList) {

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {
                    if (null != infoList && infoList.getData() != null) {
                        List<MaterialDetails.ListBean> list = infoList.getData().getList();
                        if (list != null && list.size() != 0) {
                            materialListUi.showMaterial(list);
                        } else {
                            ZBUiUtils.showToast("没有采购入库已办详情数据");
                        }
                    }
                } else {
                    ZBUiUtils.showToast(infoList.getMessage());
                }
            }
        }, orderId);
    }

    /**
     * 已办冲销
     *
     * @param body
     */
    public void materialDoneInSave(RequestBody body) {
        materialListUi.showLoading();
        httpMethods.materialDoneInSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                materialListUi.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    materialListUi.saveSuccess();
                } else {
                    materialListUi.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.hideLoading();
                materialListUi.showError(e.getMessage());
            }
        }, body);
    }

    /*
     * ============================质检 =============================
     * */


    /**
     * 上传质检照片
     */
    public void updateImage(final int position, String path) {


        File file = new File(path);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        httpMethods.updateCheckImage(new SingleObserver<ImageData>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ImageData responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {

                    String fileName = responseInfo.getFileName();
                    // TODO 保存图片（目前只支持一张图片）
                    //DataUtils.setImageId(position, fileName);
                    DataUtils.saveImage(fileName);

                    ZBUiUtils.showToast("图片上传成功");
                } else {
                    ZBUiUtils.showToast("图片上传失败");
//                    materialListUi.showError("图片上传失败");
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        }, imageBodyPart);
    }

    /**
     * 质检代办保存
     *
     * @param body body
     */
    public void quaCheckTodoSave(RequestBody body) {
        materialListUi.showLoading();
        httpMethods.getQualityCheckTodoSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                materialListUi.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    materialListUi.saveSuccess();
                } else {
                    materialListUi.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.hideLoading();
                materialListUi.showError(e.getMessage());
            }
        }, body);
    }

    /**
     * 质检 已办 详情
     *
     * @param @id id值
     */
    public void fetchQualityCheckDoneInfo(String id) {

        httpMethods.getQualityCheckDoneInfo(new SingleObserver<BaseResponse<CheckDoneDetailEvent>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<CheckDoneDetailEvent> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    CheckDoneDetailEvent data = response.getData();

                    EventBus.getDefault().post(data);
                } else {
                    EventBus.getDefault().post(new ErrorMessage(response.getMessage()));
                }
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
            }
        }, id);
    }

    /**
     * 质检已办保存
     *
     * @param body body
     */
    public void quaCheckDoneSave(RequestBody body) {
        httpMethods.getQualityCheckDoneSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    EventBus.getDefault().post(new SuccessMessage("保存成功"));
                } else {
                    EventBus.getDefault().post(new ErrorMessage(responseInfo.getMessage()));
                }
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
            }
        }, body);
    }
}
