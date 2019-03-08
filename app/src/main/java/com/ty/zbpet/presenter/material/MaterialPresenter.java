package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.material.MaterialDetails;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.ZBLog;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * @author PVer on 2018/10/28.
 * <p>
 * 采购入库 Presenter
 */
public class MaterialPresenter {

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
     * @param materialUiInterface
     */
    public MaterialPresenter(MaterialUiListInterface materialUiInterface) {
        this.materialListUi = materialUiInterface;
        httpMethods = HttpMethods.getInstance();
    }

    /**
     * 接收 object UI 接口
     *
     * @param materialObjUi
     */
    public MaterialPresenter(MaterialUiObjInterface materialObjUi) {
        this.materialObjUi = materialObjUi;
        httpMethods = HttpMethods.getInstance();
    }

    /**
     * 接收 list UI 接口  And  mode 接口
     *
     * @param materialListUi
     * @param materialModel
     */
    public MaterialPresenter(MaterialUiListInterface materialListUi, MaterialModelInterface materialModel) {
        this.materialListUi = materialListUi;
        this.materialModel = materialModel;
    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * 原辅料 待办 list  业务逻辑
     */
    public void fetchTODOMaterial(String sapOrderNo, String startDate, String endDate) {

        materialListUi.showLoading();

        // APi  获取数据
        httpMethods.getMaterialTodoList(new SingleObserver<BaseResponse<MaterialList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> response) {
                materialListUi.hideLoading();

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialList.ListBean> list = response.getData().getList();

                    materialListUi.showMaterial(list);
                }else{
                    materialListUi.showError(response.getMessage());
                }
            }
        },sapOrderNo,startDate,endDate);

    }

    /**
     * 原辅料 待办 list  详情  MaterialPurchaseInData  MaterialDetailsData
     */
    public void fetchTODOMaterialDetails(String sapFirmNo, String sapOrderNo, String supplierNo) {

        httpMethods.getMaterialTodoListDetail(new SingleObserver<BaseResponse<MaterialDetails>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetails> info) {

                if (CodeConstant.SERVICE_SUCCESS.equals(info.getTag())) {

                    MaterialDetails data = info.getData();

                    materialListUi.showMaterial(data.getList());

                } else {
                    materialListUi.showError(info.getMessage());
                }
            }
        }, sapFirmNo, sapOrderNo, supplierNo);
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
                    materialListUi.showCarSuccess(position, responseInfo);
                } else {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                }

            }
        }, positionNo);
    }


    /**
     * 待办 保存
     */
    public void materialTodoInSave(RequestBody body) {

        httpMethods.materialTodoInSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag()) ) {
                    // 入库成功（保存）
                    materialListUi.saveSuccess();
                } else {
                    materialListUi.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }
        }, body);

    }

    /**
     * 原材料 已办 列表
     */
    public void fetchDoneMaterial(String type,String sapOrderNo,String startDate,String endDate) {
        httpMethods.getMaterialDoneList(new SingleObserver<BaseResponse<MaterialList>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    List<MaterialList.ListBean> list = response.getData().getList();
                    materialListUi.showMaterial(list);
                } else {
                    ZBUiUtils.showToast(response.getMessage());
                    materialListUi.showError(response.getMessage());
                }
            }
        }, type,sapOrderNo,startDate,endDate);
    }

    /**
     * 已办详情
     */
    public void fetchDoneMaterialDetails(String orderId) {
        httpMethods.getMaterialDoneListDetail(new SingleObserver<BaseResponse<MaterialDetails>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetails> infoList) {

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {
                    if (null != infoList && infoList.getData() != null) {
                        List<MaterialDetails.ListBean> list = infoList.getData().getList();
                        if (list != null && list.size() != 0) {
                            materialListUi.showMaterial(list);
                        } else {
                            ZBUiUtils.showToast("没有采购入库已办详情数据");
                        }
                    }
                } else {
                    ZBUiUtils.showToast(infoList.getMessage());
                }
            }
        }, orderId);
    }

    /**
     * 已办冲销
     * @param body
     */
    public void materialDoneInSave(RequestBody body){
        httpMethods.materialDoneInSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    materialListUi.saveSuccess();
                } else {
                    materialListUi.showError(responseInfo.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                materialListUi.showError(e.getMessage());
            }
        }, body);
    }

}
