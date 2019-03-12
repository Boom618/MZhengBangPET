package com.ty.zbpet.presenter.product;

import com.ty.zbpet.bean.product.ProductDetails;
import com.ty.zbpet.bean.product.ProductList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;

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
    public void fetchBuyInTodoList(String sapOrderNo,String startDate,String endDate) {
        httpMethods.getBuyInOrderList(new SingleObserver<BaseResponse<ProductList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<ProductList.ListBean> list = response.getData().getList();
                    listInterface.showProduct(list);

                } else {
                    listInterface.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                listInterface.showError(e.getMessage());
            }
        },sapOrderNo,startDate,endDate);
    }


    /**
     * 待办详情
     *
     * @param sapOrderNo
     */
    public void fetchBuyInTodoListDetails(String sapOrderNo,String sapFirmNo,String supplierNo) {

        httpMethods.getBuyInOrderInfo(new SingleObserver<BaseResponse<ProductDetails>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductDetails> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    ProductDetails data = response.getData();
                    List<ProductDetails.ListBean> list = data.getList();
                    listInterface.showProduct(list);

                } else {
                    listInterface.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                listInterface.showError(e.getMessage());
            }
        }, sapOrderNo,sapFirmNo,supplierNo);
    }

    /**
     * 已办列表
     */
    public void fetchBuyInDoneList(String type,String sapOrderNo,String startDate,String endDate) {
        httpMethods.getBuyInDoneList(new SingleObserver<BaseResponse<ProductList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    ProductList data = response.getData();
                    objInterface.detailObjData(data);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        }, type,sapOrderNo,startDate,endDate);
    }

    /**
     * 已办详情
     */
    public void fetchBuyInDoneListDetails(String orderId) {
        httpMethods.getBuyInDoneListInfo(new SingleObserver<BaseResponse<ProductDetails>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductDetails> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    ProductDetails data = response.getData();

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
