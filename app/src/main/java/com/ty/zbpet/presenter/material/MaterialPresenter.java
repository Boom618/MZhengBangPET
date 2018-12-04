package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.MaterialDoneDetailsData;
import com.ty.zbpet.bean.material.MaterialDetailsIn;
import com.ty.zbpet.bean.material.MaterialDoneList;
import com.ty.zbpet.bean.material.MaterialTodoList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ZBLog;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @author PVer on 2018/10/28.
 * <p>
 * 采购入库 Presenter
 */
public class MaterialPresenter {

    /**
     * UI 接口
     */
    MaterialUiListInterface materialListUi;

    /**
     * 详情 接口
     */
    MaterialUiObjInterface materialObjUi;

    /**
     * model 数据接口
     */
    MaterialModelInterface materialModel = new MaterialModelInterfaceImpl();

    /**
     * API 网络
     */
    HttpMethods httpMethods;

    Disposable disposable;

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

    public void dispose(){
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * 原辅料 待办 list  业务逻辑
     */
    public void fetchTODOMaterial() {

        materialListUi.showLoading();

        // APi  获取数据
        httpMethods.getMaterialTodoList(new SingleObserver<BaseResponse<MaterialTodoList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialTodoList> infoList) {
                materialListUi.hideLoading();

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {

                    if (infoList != null && infoList.getData().getList().size() != 0) {
                        List<MaterialTodoList.ListBean> list = infoList.getData().getList();

                        // 数据
                        materialListUi.showMaterial(list);


                        // 保存数据(数据库，缓存。。。)
                        //materialModel.saveMaterial();

                    } else {
                        ZBUiUtils.showToast("没有采购入库待办数据");
                    }
                }

            }
        });

    }

    /**
     * 原辅料 待办 list  详情  MaterialPurchaseInData  MaterialDetailsData
     */
    public void fetchTODOMaterialDetails(String sapOrderNo) {

        httpMethods.getMaterialTodoListDetail(new SingleObserver<BaseResponse<MaterialDetailsIn>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDetailsIn> info) {

                if (CodeConstant.SERVICE_SUCCESS.equals(info.getTag())) {

                    MaterialDetailsIn data = info.getData();

                    ZBLog.d(data);

                    materialObjUi.detailObjData(data);

                } else {
                    ZBUiUtils.showToast(info.getMessage());
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
     * 待办 保存
     */
    public void materialTodeInSave() {

    }

    /**
     * 原材料 已办 列表
     */
    public void fetchDoneMaterial(String type) {
        httpMethods.getMaterialDoneList(new SingleObserver<BaseResponse<MaterialDoneList>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDoneList> infoList) {

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {
                    if (null != infoList && infoList.getData() != null) {
                        List<MaterialDoneList.ListBean> list = infoList.getData().getList();
                        if (list != null && list.size() != 0) {
                            materialListUi.showMaterial(list);
                        } else {
                            ZBUiUtils.showToast("没有采购入库已办数据");
                        }
                    }
                } else {
                    ZBUiUtils.showToast(infoList.getMessage());
                }
            }
        },type);
    }

    /**
     * 已办详情
     */
    public void fetchDoneMaterialDetails(String id) {
        httpMethods.getMaterialDoneListDetail(new SingleObserver<BaseResponse<MaterialDoneDetailsData>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(BaseResponse<MaterialDoneDetailsData> infoList) {

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {
                    if (null != infoList && infoList.getData() != null) {
                        List<MaterialDoneDetailsData.ListBean> list = infoList.getData().getList();
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
        }, id);
    }


}
