package com.ty.zbpet.presenter.product;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.PickOutDoneData;
import com.ty.zbpet.bean.PickOutDoneDetailsData;
import com.ty.zbpet.bean.PickOutTodoDetailsData;
import com.ty.zbpet.bean.material.MaterialDoneList;
import com.ty.zbpet.bean.material.MaterialTodoList;
import com.ty.zbpet.bean.product.ProductDetailsIn;
import com.ty.zbpet.bean.product.ProductTodoSave;
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


    public void dispose(){
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * 待办列表
     */
    public void fetchBuyInTodoList() {
        httpMethods.getPurchaseOrderList(new SingleObserver<BaseResponse<MaterialTodoList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<MaterialTodoList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialTodoList.ListBean> list = response.getData().getList();
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


        httpMethods.getPurchaseOrderInfo(new BaseSubscriber<BaseResponse<ProductDetailsIn>>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast("失败 : =" + e.getMessage());

            }


            @Override
            public void onNext(BaseResponse<ProductDetailsIn> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    ProductDetailsIn data = response.getData();
                    objInterface.detailObjData(data);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }
        }, sapOrderNo);
    }


    /**
     * 库位码校验
     *
     * @param positionNo
     */
    public void checkCarCode(final int position, final String positionNo) {

        httpMethods.checkCarCode(new BaseSubscriber<CarPositionNoData>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(CarPositionNoData responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 库位码合法

                } else {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                }

            }
        }, positionNo);
    }

    /**
     * 已办列表
     */
    public void fetchBuyInDoneList() {
        httpMethods.getPurchaseDoneList(new BaseSubscriber<BaseResponse<MaterialDoneList>>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<MaterialDoneList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialDoneList.ListBean> list = response.getData().getList();
                    listInterface.showProduct(list);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }
        });
    }

    /**
     * 已办详情
     */
    public void fetchBuyInDoneListDetails(String sapOrderNo) {
        httpMethods.getPurchaseDoneListInfo(new BaseSubscriber<BaseResponse<PickOutDoneDetailsData>>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<PickOutDoneDetailsData> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    List<PickOutDoneDetailsData.ListBean> list = response.getData().getList();

                    listInterface.showProduct(list);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }
        }, sapOrderNo);
    }


}
