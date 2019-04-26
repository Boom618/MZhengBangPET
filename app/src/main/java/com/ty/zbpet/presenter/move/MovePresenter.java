package com.ty.zbpet.presenter.move;

import com.ty.zbpet.base.BaseResponse;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.system.PositionCode;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.net.HttpMethods;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * @author TY on 2019/4/24.
 * <p>
 * 移库 P 层
 */
public class MovePresenter {


    private ComplexInterface compInterface;
    private HttpMethods httpMethods;
    private Disposable disposable;

    public MovePresenter(ComplexInterface compInterface) {
        this.compInterface = compInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * 库位码查询
     *
     * @param positionNo 库位码
     */
    public void positionStock(String positionNo) {
        compInterface.showLoading();
        httpMethods.positionStock(new SingleObserver<BaseResponse<PositionCode>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<PositionCode> response) {
                compInterface.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    PositionCode data = response.getData();
                    compInterface.showObjData(data);
                    //EventBus.getDefault().post("");
                } else {
                    compInterface.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                compInterface.showError(e.getMessage());
            }
        }, positionNo);
    }

    /**
     * 源库位 移出
     */
    public void materialMoveOrder(RequestBody body) {
        compInterface.showLoading();
        httpMethods.materialMoveOrder(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                compInterface.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    compInterface.responseSuccess();
                } else {
                    compInterface.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                compInterface.hideLoading();
                compInterface.showError("移出源库位失败");
            }
        }, body);
    }

    /**
     * 移库列表
     */
    public void moveList() {
        compInterface.showLoading();
        httpMethods.moveList(new SingleObserver<BaseResponse<PositionCode>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<PositionCode> responseInfo) {
                compInterface.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    compInterface.showListData(responseInfo.getData().getList());
                } else {
                    compInterface.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                compInterface.hideLoading();
                compInterface.showError(e.getMessage());
            }
        });
    }

    /**
     * 移入目标库位
     */
    public void moveMaterial(String id, String positionNo, String warehouseNo, String time) {
        compInterface.showLoading();
        httpMethods.moveMaterial(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                compInterface.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    compInterface.responseSuccess();
                } else {
                    compInterface.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                compInterface.hideLoading();
                compInterface.showError(e.getMessage());
            }
        }, id, positionNo, warehouseNo, time);

    }
}
