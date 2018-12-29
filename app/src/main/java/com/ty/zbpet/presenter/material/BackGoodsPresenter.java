package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.material.MaterialDetails;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @author TY on 2018/11/25.
 * <p>
 * 采购退货 Presenter
 */
public class BackGoodsPresenter {

    /**
     * UI 接口
     */
    private MaterialUiListInterface materialListUi;

    /**
     * 详情 接口
     */
    private MaterialUiObjInterface materialObjUi;

    /**
     * model 数据接口
     */
    private MaterialModelInterface materialModel = new MaterialModelInterfaceImpl();

    /**
     * API 网络
     */
    private HttpMethods httpMethods;

    private Disposable disposable;

    /**
     * 接收 list UI 接口
     *
     * @param materialUiInterface 构造方法
     */
    public BackGoodsPresenter(MaterialUiListInterface materialUiInterface) {
        this.materialListUi = materialUiInterface;
        httpMethods = HttpMethods.getInstance();
    }

    /**
     * 接收 object UI 接口
     *
     * @param materialObjUi 构造方法
     */
    public BackGoodsPresenter(MaterialUiObjInterface materialObjUi) {
        this.materialObjUi = materialObjUi;
        httpMethods = HttpMethods.getInstance();
    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * 获取待办 列表
     */
    public void fetchBackTodoList() {

        httpMethods.getBackTodoList(new SingleObserver<BaseResponse<MaterialList>>() {
            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialList.ListBean> list = response.getData().getList();

                    materialListUi.showMaterial(list);
                } else {
                    ZBUiUtils.showToast(response.getMessage());
                }

            }
        });
    }


    /**
     * 获取待办 详情
     */
    public void fetchBackTodoListInfo(String sapOrderNo) {

        httpMethods.getBackTodoListInfo(new SingleObserver<BaseResponse<MaterialDetails>>() {
            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());

            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetails> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    materialObjUi.detailObjData(response.getData());

                } else {
                    ZBUiUtils.showToast(response.getMessage());
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

        httpMethods.checkCarCode(new SingleObserver<CarPositionNoData>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(CarPositionNoData responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 库位码合法
                    materialObjUi.showCarSuccess(position, responseInfo);
                } else {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                }

            }
        }, positionNo);
    }

    /**
     * 已办 列表
     */
    public void fetchBackDoneList(String type) {
        httpMethods.getBackDoneList(new SingleObserver<BaseResponse<MaterialList>>() {
            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> infoList) {

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {
                    if (null != infoList && infoList.getData() != null) {
                        List<MaterialList.ListBean> list = infoList.getData().getList();
                        materialListUi.showMaterial(list);
                    }
                } else {
                    ZBUiUtils.showToast(infoList.getMessage());
                }
            }
        }, type);
    }

    /**
     * 已办 详情
     */
    public void fetchBackDoneListInfo(String orderId) {
        httpMethods.getBackDoneListInfo(new SingleObserver<BaseResponse<MaterialDetails>>() {
            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetails> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    materialObjUi.detailObjData(response.getData());

                } else {
                    ZBUiUtils.showToast(response.getMessage());
                }
            }
        }, orderId);

    }

}
