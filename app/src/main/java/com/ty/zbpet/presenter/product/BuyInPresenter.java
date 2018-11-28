package com.ty.zbpet.presenter.product;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.PickOutDoneData;
import com.ty.zbpet.bean.PickOutDoneDetailsData;
import com.ty.zbpet.bean.PickOutTodoDetailsData;
import com.ty.zbpet.bean.material.MaterialTodoList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

/**
 * @author TY on 2018/11/26.
 * <p>
 * 外采入库 presenter
 */
public class BuyInPresenter {

    private ProductUiListInterface listInterface;
    private ProductUiObjInterface objInterface;

    private HttpMethods httpMethods;

    public BuyInPresenter(ProductUiListInterface listInterface) {
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public BuyInPresenter(ProductUiObjInterface objInterface) {
        this.objInterface = objInterface;
        httpMethods = HttpMethods.getInstance();
    }

    /**
     * 待办列表
     */
    public void fetchBuyInTodoList() {
        httpMethods.getPurchaseOrderList(new BaseSubscriber<BaseResponse<MaterialTodoList>>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<MaterialTodoList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialTodoList.ListBean> list = response.getData().getList();
                    listInterface.showProduct(list);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }
        });
    }


    /**
     * 待办详情
     *
     * @param sapOrderNo
     */
    public void fetchBuyInTodoListDetails(String sapOrderNo) {


        httpMethods.pickOutTodoListDetails(new BaseSubscriber<BaseResponse<PickOutTodoDetailsData>>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast("失败 : =" + e.getMessage());

            }


            @Override
            public void onNext(BaseResponse<PickOutTodoDetailsData> pickOutDetailInfo) {

                if (CodeConstant.SERVICE_SUCCESS.equals(pickOutDetailInfo.getTag())) {

                    PickOutTodoDetailsData data = pickOutDetailInfo.getData();
                    objInterface.detailObjData(data);

                } else {
                    ZBUiUtils.showToast("失败 : =" + pickOutDetailInfo.getMessage());
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
        httpMethods.pickOutDoneList(new BaseSubscriber<BaseResponse<PickOutDoneData>>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<PickOutDoneData> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<PickOutDoneData.ListBean> list = response.getData().getList();
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
        httpMethods.pickOutDoneListDetails(new BaseSubscriber<BaseResponse<PickOutDoneDetailsData>>() {
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
