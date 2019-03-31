package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.eventbus.UrlMessage;
import com.ty.zbpet.bean.material.MaterialDetails;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.bean.system.BoxCodeUrl;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.constant.CodeConstant;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * @author TY on 2018/11/25.
 * <p>
 * 采购退货 Presenter
 */
public class BackGoodsPresenter {

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
     * @param materialUiInterface 构造方法
     */
    public BackGoodsPresenter(MaterialUiListInterface materialUiInterface) {
        this.materialListUi = materialUiInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * 获取待办 列表
     */
    public void fetchBackTodoList(String sapOrderNo, String startDate, String endDate) {

        httpMethods.getBackTodoList(new SingleObserver<BaseResponse<MaterialList>>() {
            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
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
        }, sapOrderNo, startDate, endDate);
    }


    /**
     * 获取待办 详情
     */
    public void fetchBackTodoListInfo(String sapOrderNo, String sapFirmNo, String supplierNo) {

        httpMethods.getBackTodoListInfo(new SingleObserver<BaseResponse<MaterialDetails>>() {
            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetails> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    materialListUi.showMaterial(response.getData().getList());

                } else {
                    materialListUi.showError(response.getMessage());
                }

            }
        }, sapOrderNo, sapFirmNo, supplierNo);
    }
    /**
     * URL 解析
     *
     * @param url
     */
    public void urlAnalyze(int position,String url,String goodsNo) {
        httpMethods.urlAnalyze(new SingleObserver<BaseResponse<BoxCodeUrl>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<BoxCodeUrl> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    String qrCode = response.getData().getBoxQrCode();
                    EventBus.getDefault().post(new UrlMessage(position,qrCode));
                } else {
                    materialListUi.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }
        }, url,goodsNo);

    }

    /**
     * 库位码校验
     *
     * @param positionNo 库位码 KWM234542
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
     * 出库保存
     * @param body body
     */
    public void backTodoSave(RequestBody body){
        materialListUi.showLoading();
        httpMethods.getBackTodoSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                materialListUi.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    materialListUi.saveSuccess();
                }else{
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
     * 已办 列表
     */
    public void fetchBackDoneList(String type, String sapOrderNo, String startDate, String endDate) {
        httpMethods.getBackDoneList(new SingleObserver<BaseResponse<MaterialList>>() {
            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    if (response.getData() != null) {
                        List<MaterialList.ListBean> list = response.getData().getList();
                        materialListUi.showMaterial(list);
                    }
                } else {
                    materialListUi.showError(response.getMessage());
                }
            }
        }, type, sapOrderNo, startDate, endDate);
    }

    /**
     * 已办 详情
     */
    public void fetchBackDoneListInfo(String orderId) {
        httpMethods.getBackDoneListInfo(new SingleObserver<BaseResponse<MaterialDetails>>() {
            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetails> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    materialListUi.showMaterial(response.getData().getList());

                } else {
                    materialListUi.showError(response.getMessage());
                }
            }
        }, orderId);

    }

    /**
     * 冲销保存
     * @param body body
     */
    public void backDoneSave(RequestBody body){
        materialListUi.showLoading();
        httpMethods.getBackDoneSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                materialListUi.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    materialListUi.saveSuccess();
                }else{
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

}
