package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.material.MaterialDetails;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

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
        httpMethods.pickOutTodoList(new SingleObserver<BaseResponse<MaterialList>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialList.ListBean> list = response.getData().getList();
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
    public void fetchPickOutTodoListDetails(String sapOrderNo, String sapFirmNo) {


        httpMethods.pickOutTodoListDetails(new SingleObserver<BaseResponse<MaterialDetails>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast("失败 : =" + e.getMessage());

            }


            @Override
            public void onSuccess(BaseResponse<MaterialDetails> pickOutDetailInfo) {

                if (CodeConstant.SERVICE_SUCCESS.equals(pickOutDetailInfo.getTag())) {

                    MaterialDetails data = pickOutDetailInfo.getData();
                    objInterface.detailObjData(data);

                } else {
                    ZBUiUtils.showToast("失败 : =" + pickOutDetailInfo.getMessage());
                }
            }
        }, sapOrderNo, sapFirmNo);
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
                listInterface.showError(e.getMessage());
            }

            @Override
            public void onSuccess(CarPositionNoData responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 库位码合法
                    listInterface.showCarSuccess(position, responseInfo);
                } else {
                    listInterface.showError(responseInfo.getMessage());
                }

            }
        }, positionNo);
    }

    /**
     * 待办保存
     *
     * @param body
     */
    public void pickOutTodoSave(RequestBody body) {
        httpMethods.pickOutTodoSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    listInterface.saveSuccess();
                } else {
                    listInterface.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                listInterface.showError(e.getMessage());
            }
        }, body);

    }

    /**
     * 已办列表
     */
    public void fetchPickOutDoneList(String type) {
        httpMethods.pickOutDoneList(new SingleObserver<BaseResponse<MaterialList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialList.ListBean> list = response.getData().getList();
                    listInterface.showMaterial(list);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }
        }, type);
    }

    /**
     * 已办详情
     */
    public void fetchPickOutDoneListDetails(String sapOrderNo) {
        httpMethods.pickOutDoneListDetails(new SingleObserver<BaseResponse<MaterialDetails>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetails> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    List<MaterialDetails.ListBean> list = response.getData().getList();

                    listInterface.showMaterial(list);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }
        }, sapOrderNo);
    }

    /**
     * 出库保存
     *
     * @param body
     */
    public void pickOutDoneSave(RequestBody body) {
        httpMethods.pickOutTodoSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    listInterface.saveSuccess();
                } else {
                    listInterface.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                listInterface.showError(e.getMessage());

            }
        }, body);
    }
}
