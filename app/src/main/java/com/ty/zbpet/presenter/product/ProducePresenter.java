package com.ty.zbpet.presenter.product;

import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.product.ProductDetails;
import com.ty.zbpet.bean.product.ProductList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * @author TY on 2018/11/26.
 * <p>
 * 生产入库 presenter
 */
public class ProducePresenter {

    private ProductUiListInterface listInterface;

    private HttpMethods httpMethods;
    private Disposable disposable;

    public ProducePresenter(ProductUiListInterface listInterface) {
        this.listInterface = listInterface;
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
    public void fetchProductTodoList(String sapOrderNo, String startDate, String endDate) {
        httpMethods.getProductTodoList(new SingleObserver<BaseResponse<ProductList>>() {
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
        }, sapOrderNo, startDate, endDate);
    }


    /**
     * 待办详情
     *
     * @param sapOrderNo
     */
    public void fetchProductTodoInfo(String sign, String sapOrderNo) {


        httpMethods.getProduceOrderInfo(new SingleObserver<BaseResponse<ProductDetails>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductDetails> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    ProductDetails data = response.getData();
                    listInterface.showProduct(data.getList());

                } else {
                    listInterface.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                listInterface.showError(e.getMessage());
            }
        }, sign, sapOrderNo);
    }

    /**
     * 代办保存
     */
    public void productTodoSave(RequestBody body) {
        listInterface.showLoading();
        httpMethods.getProduceTodoSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                listInterface.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    listInterface.saveSuccess();
                } else {
                    listInterface.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                listInterface.hideLoading();
                listInterface.showError(e.getMessage());
            }
        }, body);
    }

    /**
     * 已办列表
     */
    public void fetchProductDoneList(String type,String sapOrderNo,String startDate,String endDate) {
        httpMethods.getProduceDoneList(new SingleObserver<BaseResponse<ProductList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    ProductList data = response.getData();
                    listInterface.showProduct(data.getList());

                } else {
                    listInterface.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                listInterface.showError(e.getMessage());
            }
        }, type,sapOrderNo,startDate,endDate);
    }

    /**
     * 已办详情
     */
    public void fetchProductDoneInfo(String orderId) {
        httpMethods.getProduceDoneInfo(new SingleObserver<BaseResponse<ProductDetails>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductDetails> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    ProductDetails data = response.getData();

                    listInterface.showProduct(data.getList());

                } else {
                    listInterface.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                listInterface.showError(e.getMessage());
            }
        }, orderId);
    }

    /**
     * 已办冲销
     *
     * @param body
     */
    public void productDoneSave(RequestBody body) {
        listInterface.showLoading();
        httpMethods.getProduceDoneSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                listInterface.hideLoading();
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    listInterface.saveSuccess();
                } else {
                    listInterface.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                listInterface.hideLoading();
                listInterface.showError(e.getMessage());
            }
        }, body);

    }

}
