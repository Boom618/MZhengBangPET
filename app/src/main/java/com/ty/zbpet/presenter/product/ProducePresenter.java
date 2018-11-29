package com.ty.zbpet.presenter.product;

import com.ty.zbpet.bean.product.ProductDetailsIn;
import com.ty.zbpet.bean.product.ProductDetailsOut;
import com.ty.zbpet.bean.product.ProductDoneList;
import com.ty.zbpet.bean.product.ProductTodoList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @author TY on 2018/11/26.
 * <p>
 * 生产入库 presenter
 */
public class ProducePresenter {

    private ProductUiListInterface listInterface;
    private ProductUiObjInterface objInterface;

    private HttpMethods httpMethods;
    private Disposable disposable;

    public ProducePresenter(ProductUiListInterface listInterface) {
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public ProducePresenter(ProductUiObjInterface objInterface) {
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
    public void fetchProductTodoList() {
        httpMethods.getProductTodoList(new SingleObserver<BaseResponse<ProductTodoList>>() {
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
    public void fetchProductTodoInfo(String sapOrderNo) {


        httpMethods.getProduceOrderInfo(new SingleObserver<BaseResponse<ProductDetailsIn>>() {
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
    public void fetchProductDoneList(String type) {
        httpMethods.getProduceDoneList(new SingleObserver<BaseResponse<ProductDoneList>>() {
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

            }
        });
    }

    /**
     * 已办详情
     */
    public void fetchProductDoneInfo(String orderId) {
        httpMethods.getProduceDoneInfo(new SingleObserver<BaseResponse<ProductDetailsOut>>() {

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
