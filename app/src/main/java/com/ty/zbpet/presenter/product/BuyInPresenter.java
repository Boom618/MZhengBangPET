package com.ty.zbpet.presenter.product;

import com.ty.zbpet.bean.PickOutDoneDetailsData;
import com.ty.zbpet.bean.product.BuyInTodoDetails;
import com.ty.zbpet.bean.product.ProductDetailsIn;
import com.ty.zbpet.bean.product.ProductDetailsOut;
import com.ty.zbpet.bean.product.ProductDoneList;
import com.ty.zbpet.bean.product.ProductTodoList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @author TY on 2018/11/26.
 * <p>
 * 外采入库 presenter
 */
public class BuyInPresenter {

    private ProductUiListInterface listInterface;
    private ProductUiObjInterface objInterface;

    private HttpMethods httpMethods;

    private Disposable disposable;

    public BuyInPresenter(ProductUiListInterface listInterface) {
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public BuyInPresenter(ProductUiObjInterface objInterface) {
        this.objInterface = objInterface;
        httpMethods = HttpMethods.getInstance();
    }


    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * 待办列表
     */
    public void fetchBuyInTodoList() {
        httpMethods.getPurchaseOrderList(new SingleObserver<BaseResponse<ProductTodoList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductTodoList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<ProductTodoList.ListBean> list = response.getData().getList();
                    listInterface.showProduct(list);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        });
    }


    /**
     * 待办详情
     *
     * @param sapOrderNo
     */
    public void fetchBuyInTodoListDetails(String sapOrderNo) {

        httpMethods.getPurchaseOrderInfo(new SingleObserver<BaseResponse<ProductDetailsIn>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductDetailsIn> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    ProductDetailsIn data = response.getData();
                    objInterface.detailObjData(data);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast("失败 : =" + e.getMessage());
            }
        }, sapOrderNo);
    }

    /**
     * 已办列表
     */
    public void fetchBuyInDoneList(String type) {
        httpMethods.getPurchaseDoneList(new SingleObserver<BaseResponse<ProductDoneList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductDoneList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    ProductDoneList data = response.getData();
                    objInterface.detailObjData(data);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        }, type);
    }

    /**
     * 已办详情
     */
    public void fetchBuyInDoneListDetails(String orderId) {
        httpMethods.getPurchaseDoneListInfo(new SingleObserver<BaseResponse<ProductDetailsOut>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductDetailsOut> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    ProductDetailsOut data = response.getData();

                    objInterface.detailObjData(data);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        }, orderId);
    }


}
