package com.ty.zbpet.presenter.move;

import com.ty.zbpet.base.BaseResponse;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.system.PositionCode;
import com.ty.zbpet.bean.system.PositionQuery;
import com.ty.zbpet.bean.system.ProMoveList;
import com.ty.zbpet.bean.system.ProductInventorList;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.constant.TipString;
import com.ty.zbpet.net.HttpMethods;
import java.net.SocketTimeoutException;
import java.util.List;
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
        httpMethods.positionStock(new SingleObserver<BaseResponse<PositionQuery>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<PositionQuery> response) {
                compInterface.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    List<PositionQuery> list = response.getList();
                    compInterface.showListData(list);
                } else {
                    compInterface.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                compInterface.hideLoading();
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
                    compInterface.showObjData(responseInfo.getData());
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

    /**
     * 冲销列表
     */
    public void reversalList() {
        compInterface.showLoading();
        httpMethods.reversalList(new SingleObserver<BaseResponse<PositionCode>>() {
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
     * 原辅料移库冲销
     *
     * @param body body
     */
    public void reversalMove(RequestBody body) {
        compInterface.showLoading();
        httpMethods.reversalMove(new SingleObserver<ResponseInfo>() {
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
                if (e instanceof SocketTimeoutException) {
                    compInterface.showError(TipString.loginRetry);
                } else {
                    compInterface.showError(e.getMessage());
                }
            }
        }, body);
    }

    /**
     * 选择仓库 产品
     */
    public void goodsStock(String goodsNo, String warehouseNo) {
        compInterface.showLoading();
        httpMethods.goodsStock(new SingleObserver<BaseResponse<ProductInventorList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductInventorList> responseInfo) {
                compInterface.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    compInterface.showListData(responseInfo.getData().getList());
                } else {
                    compInterface.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    compInterface.showError(TipString.loginRetry);
                } else {
                    compInterface.showError(e.getMessage());
                }

            }
        }, goodsNo, warehouseNo);
    }

    /**
     * 获取产品列表
     */
    public void getGoodsList(int pageSize, String goodsNo) {
        httpMethods.getGoodsList(new SingleObserver<BaseResponse<ProductInventorList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductInventorList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    List<ProductInventorList.ListBean> list = response.getData().getList();
                    compInterface.showListData(list);
                } else {
                    compInterface.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                compInterface.showError(e.getMessage());
            }
        }, pageSize, goodsNo);
    }


    /**
     * 成品移出原仓库
     *
     * @param body body
     */
    public void goodsMoveOrder(RequestBody body) {
        compInterface.showLoading();
        httpMethods.goodsMoveOrder(new SingleObserver<ResponseInfo>() {
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
        }, body);
    }

    /**
     * 获取成品移库单 314待冲销列表
     */
    public void moveProductList() {
        compInterface.showLoading();
        httpMethods.moveProductList(new SingleObserver<BaseResponse<ProMoveList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProMoveList> responseInfo) {
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
     * 成品移入目标仓库
     */
    public void goodsMoveToTarget(String id, String goodsNo, String goodsName) {
        compInterface.showLoading();
        httpMethods.goodsMoveToTarget(new SingleObserver<ResponseInfo>() {
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
        }, id, goodsNo, goodsName);
    }

    /**
     * 成品移出源库位冲销
     *
     * @param id id
     */
    public void goodsSourceRecall(String id) {
        compInterface.showLoading();
        httpMethods.goodsSourceRecall(new SingleObserver<ResponseInfo>() {
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
        }, id);
    }

    /**
     * 成品待冲销列表
     */
    public void goodsRecallList() {
        compInterface.showLoading();
        httpMethods.goodsRecallList(new SingleObserver<BaseResponse<ProMoveList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProMoveList> responseInfo) {
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
     * 成品冲销（316冲销/目标仓库冲销）
     *
     * @param id id
     */
    public void goodsMoveRecall(String id) {
        compInterface.showLoading();
        httpMethods.goodsMoveRecall(new SingleObserver<ResponseInfo>() {
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
        }, id);
    }
}
