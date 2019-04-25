package com.ty.zbpet.presenter.system;

import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.eventbus.ErrorMessage;
import com.ty.zbpet.bean.eventbus.SuccessMessage;
import com.ty.zbpet.bean.eventbus.system.EndLoadingMessage;
import com.ty.zbpet.bean.eventbus.system.StartLoadingMessage;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.bean.system.PositionCode;
import com.ty.zbpet.bean.system.ProductInventorList;
import com.ty.zbpet.bean.system.ReceiptList;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.base.BaseResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
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

    public SystemPresenter() {
        httpMethods = HttpMethods.getInstance();
    }

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
    public void fetchQualityCheckTodoList(String sapOrderNo, String startDate, String endDate) {
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
        }, sapOrderNo, startDate, endDate);

    }


    /**
     * 获取质检 已办列表
     */
    public void fetchQualityCheckDoneList(String sapOrderNo, String startDate, String endDate) {

        httpMethods.getQualityCheckDoneList(new SingleObserver<BaseResponse<MaterialList>>() {

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
        }, sapOrderNo, startDate, endDate);
    }

    /*---------------------------盘点-------------------------------------*/

    /**
     * 原辅料盘点
     */
    public void positionStock(String positionNo) {
        httpMethods.positionStock(new SingleObserver<BaseResponse<PositionCode>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<PositionCode> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    PositionCode data = response.getData();

                    EventBus.getDefault().post(data);
                } else {
                    EventBus.getDefault().post(new ErrorMessage(response.getMessage()));
                }
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ErrorMessage(e.getMessage()));

            }
        }, positionNo);
    }

    /**
     * 盘点保存
     */
    public void positionStockSave(RequestBody body) {
        httpMethods.positionStockSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    EventBus.getDefault().post(new SuccessMessage("盘点成功"));
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

    /* ------------------------------------- 成品盘点 ------------------------------------- */

    /**
     * 成品盘点列表
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
                    EventBus.getDefault().post(list);
                } else {
                    EventBus.getDefault().post(new ErrorMessage(response.getMessage()));
                }
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
            }
        }, pageSize, goodsNo);
    }

    /**
     * 成品盘点 提交
     */
    public void goodsInventory(RequestBody body) {
        EventBus.getDefault().post(new StartLoadingMessage("保存中"));
        httpMethods.goodsInventory(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                EventBus.getDefault().post(new EndLoadingMessage(""));
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    EventBus.getDefault().post(new SuccessMessage(responseInfo.getMessage()));
                } else {
                    EventBus.getDefault().post(new ErrorMessage(responseInfo.getMessage()));
                }
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new EndLoadingMessage(""));
                EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
            }
        }, body);
    }

    /**
     * 盘点单据号列表
     */
    public void getCheckList(String type) {
        httpMethods.getCheckList(new SingleObserver<BaseResponse<ReceiptList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ReceiptList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    List<ReceiptList.ListBean> list = response.getData().getList();
                    EventBus.getDefault().post(list);
                } else {
                    EventBus.getDefault().post(new ErrorMessage(response.getMessage()));
                }
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
            }
        }, type);

    }

    /**
     * 删除盘点列表
     *
     * @param id         id
     * @param sapCheckNo sapCheckNo
     */
    public void deleteCheck(String id, String sapCheckNo) {
        httpMethods.deleteCheck(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    EventBus.getDefault().post(new SuccessMessage(response.getMessage()));
                } else {
                    EventBus.getDefault().post(new ErrorMessage(response.getMessage()));
                }
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
            }
        }, id, sapCheckNo);
    }

    /*--------------------------------- 移库 --------------------------------------------*/

}
