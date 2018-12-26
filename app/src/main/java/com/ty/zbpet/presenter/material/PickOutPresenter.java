package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.material.MaterialDetailsIn;
import com.ty.zbpet.bean.material.MaterialDetailsOut;
import com.ty.zbpet.bean.material.MaterialDoneList;
import com.ty.zbpet.bean.material.MaterialTodoList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @author TY on 2018/10/29.
 * <p>
 * 领料出库 Presenter
 */
public class PickOutPresenter {

    private MaterialUiListInterface listInterface;
    private MaterialUiObjInterface objInterface;

    private HttpMethods httpMethods;
    private Disposable disposable;

    public PickOutPresenter(MaterialUiObjInterface objInterface) {
        this.objInterface = objInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public PickOutPresenter(MaterialUiListInterface listInterface) {
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
    public void fetchPickOutTodoList() {
        httpMethods.pickOutTodoList(new SingleObserver<BaseResponse<MaterialTodoList>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialTodoList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialTodoList.ListBean> list = response.getData().getList();
                    listInterface.showMaterial(list);

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
    public void fetchPickOutTodoListDetails(String sapOrderNo) {


        httpMethods.pickOutTodoListDetails(new SingleObserver<BaseResponse<MaterialDetailsIn>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast("失败 : =" + e.getMessage());

            }


            @Override
            public void onSuccess(BaseResponse<MaterialDetailsIn> pickOutDetailInfo) {

                if (CodeConstant.SERVICE_SUCCESS.equals(pickOutDetailInfo.getTag())) {

                    MaterialDetailsIn data = pickOutDetailInfo.getData();
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
                    objInterface.showCarSuccess(position, responseInfo);
                } else {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                }

            }
        }, positionNo);
    }

    /**
     * 已办列表
     */
    public void fetchPickOutDoneList(String type) {
        httpMethods.pickOutDoneList(new SingleObserver<BaseResponse<MaterialDoneList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDoneList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialDoneList.ListBean> list = response.getData().getList();
                    listInterface.showMaterial(list);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }
        },type);
    }

    /**
     * 已办详情
     */
    public void fetchPickOutDoneListDetails(String sapOrderNo) {
        httpMethods.pickOutDoneListDetails(new SingleObserver<BaseResponse<MaterialDetailsOut>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetailsOut> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    List<MaterialDetailsOut.ListBean> list = response.getData().getList();

                    listInterface.showMaterial(list);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }
        }, sapOrderNo);
    }
}
