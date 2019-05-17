package com.ty.zbpet.presenter.material;

import com.ty.zbpet.base.BaseResponse;
import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.material.MaterialDetails;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.constant.TipString;
import com.ty.zbpet.net.HttpMethods;

import java.net.SocketTimeoutException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * @author TY
 * @Date: 2019/5/15 16:39
 * @Description: 销售出库 P 层
 */
public class SaleOrderPresenter {

    private MaterialUiListInterface view;

    private HttpMethods httpMethods;
    private Disposable disposable;


    public SaleOrderPresenter(MaterialUiListInterface listInterface) {
        this.view = listInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * 销售出库列表
     *
     * @param sapOrderNo sapOrderNo
     * @param startDate  startDate
     * @param endDate    endDate
     */
    public void getSaleOrderList(String type, String sapOrderNo, String startDate, String endDate) {
        view.showLoading();
        httpMethods.getSaleOrderList(new SingleObserver<BaseResponse<MaterialList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> response) {
                view.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    view.showMaterial(response.getData().getList());
                } else {
                    view.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                if (e instanceof SocketTimeoutException) {
                    view.showError(TipString.loginRetry);
                } else {
                    view.showError(e.getMessage());
                }
            }
        }, type, sapOrderNo, startDate, endDate);
    }

    /**
     * 销售出库详情
     *
     * @param sapOrderNo sapOrderNo
     */
    public void saleOrderInfo(String sing, String sapOrderNo, String sapFirmNo, String supplierNo) {
        view.showLoading();
        httpMethods.saleOrderInfo(new SingleObserver<BaseResponse<MaterialDetails>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetails> response) {
                view.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    view.showMaterial(response.getData().getList());
                } else {
                    view.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                if (e instanceof SocketTimeoutException) {
                    view.showError(TipString.loginRetry);
                } else {
                    view.showError(e.getMessage());
                }
            }
        }, sing, sapOrderNo, sapFirmNo, supplierNo);
    }
    /**
     * 库位码校验
     *
     * @param positionNo
     */
    public void checkCarCode(final int position, final String positionNo,String warehouseNo) {

        httpMethods.checkCarCode(new SingleObserver<CarPositionNoData>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.getMessage());
            }

            @Override
            public void onSuccess(CarPositionNoData responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 库位码合法
                    view.showCarSuccess(position, responseInfo);
                } else {
                    view.showError(responseInfo.getMessage());
                }

            }
        }, positionNo,warehouseNo);
    }


    /**
     * 销售出库保存
     * @param body
     */
    public void saleOut(RequestBody body) {
        view.showLoading();
        httpMethods.saleOut(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                view.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    view.saveSuccess();
                } else {
                    view.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                if (e instanceof SocketTimeoutException) {
                    view.showError(TipString.loginRetry);
                } else {
                    view.showError(e.getMessage());
                }
            }
        }, body);
    }
}
