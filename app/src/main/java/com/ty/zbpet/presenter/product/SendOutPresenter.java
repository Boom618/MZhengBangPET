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
 *
 * 发货出库 presenter
 */
public class SendOutPresenter {

    private ProductUiListInterface listInterface;
    private ProductUiObjInterface objInterface;

    private HttpMethods httpMethods;
    private Disposable disposable;

    public SendOutPresenter(ProductUiListInterface listInterface){
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public SendOutPresenter(ProductUiObjInterface objInterface){
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
    public void fetchSendOutTodoList() {
        httpMethods.getShipOrderList(new SingleObserver<BaseResponse<ProductList>>() {
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
     * @param sapOrderNo BuyInTodoDetails
     */
    public void fetchSendOutTodoInfo(String sapOrderNo) {


        httpMethods.getShipOrderInfo(new SingleObserver<BaseResponse<ProductDetails>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductDetails> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    ProductDetails data = response.getData();
                    //objInterface.detailObjData(data);
                    listInterface.showProduct(data.getList());

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
    public void fetchSendOutDoneList(String type) {
        httpMethods.getShipDoneList(new SingleObserver<BaseResponse<ProductList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    ProductList data = response.getData();
                    if (data.getCount() == 0) {
                        ZBUiUtils.showToast("没有数据");
                    }else {
                        objInterface.detailObjData(data);
                    }

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        },type);
    }

    /**
     * 已办详情
     */
    public void fetchSendOutDoneInfo(String orderId) {
        httpMethods.getShipDoneListInfo(new SingleObserver<BaseResponse<ProductDetails>>() {

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
