package com.ty.zbpet.presenter.product;

import com.ty.zbpet.bean.material.MaterialDoneList;
import com.ty.zbpet.bean.product.ProductDetailsOut;
import com.ty.zbpet.bean.product.ProductDoneList;
import com.ty.zbpet.bean.product.ProductTodoDetails;
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
 * 退货入库 presenter
 */
public class ReturnPresenter {

    private ProductUiListInterface listInterface;
    private ProductUiObjInterface objInterface;

    private HttpMethods httpMethods;
    private Disposable disposable;

    public ReturnPresenter(ProductUiListInterface listInterface) {
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public ReturnPresenter(ProductUiObjInterface objInterface) {
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
    public void fetchReturnGoodsTodoList() {
        httpMethods.getReturnOrderList(new SingleObserver<BaseResponse<ProductTodoList>>() {
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
     * 获取 待办详情
     *
     * @param sapOrderNo
     */
    public void fetchReturnOrderInfo(String sapOrderNo) {
        httpMethods.getReturnOrderInfo(new SingleObserver<BaseResponse<ProductTodoDetails>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductTodoDetails> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<ProductTodoDetails.ListBean> list = response.getData().getList();

                    listInterface.showProduct(list);

                }

            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        }, sapOrderNo);
    }

    /**
     * 已办列表
     */
    public void fetchReturnGoodsDoneList(String type) {
        httpMethods.getReturnDoneList(new SingleObserver<BaseResponse<ProductDoneList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductDoneList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    ProductDoneList data = response.getData();
                    if (data.getCount() == 0) {
                        ZBUiUtils.showToast("没有数据");
                    } else {
                        objInterface.detailObjData(data);
                    }

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, type);
    }


    /**
     * 获取 已办详情
     *
     * @param sapOrderNo
     */
    public void fetchReturnOrderDoneInfo(String sapOrderNo) {
        httpMethods.getReturnDoneListInfo(new SingleObserver<BaseResponse<ProductDetailsOut>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<ProductDetailsOut> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<ProductDetailsOut.ListBean> list = response.getData().getList();
                    listInterface.showProduct(list);
                }

            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        }, sapOrderNo);
    }


}
