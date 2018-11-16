package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.MaterialDoneDetailsData;
import com.ty.zbpet.bean.MaterialTodoData;
import com.ty.zbpet.bean.MaterialTodoDetailsData;
import com.ty.zbpet.bean.MaterialDoneData;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.TLog;
import com.ty.zbpet.util.UIUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

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

    /**
     * 原辅料 待办 list  业务逻辑
     */
    public void fetchTODOMaterial() {

        materialListUi.showLoading();

        // APi  获取数据
        httpMethods.getMaterialTodoList(new BaseSubscriber<BaseResponse<MaterialTodoData>>() {

            @Override
            public void onError(ApiException e) {
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<MaterialTodoData> infoList) {
                materialListUi.hideLoading();

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {

                    if (infoList != null && infoList.getData().getList().size() != 0) {
                        List<MaterialTodoData.ListBean> list = infoList.getData().getList();

                        // 数据
                        materialListUi.showMaterial(list);


                        // 保存数据(数据库，缓存。。。)
                        //materialModel.saveMaterial();

                    } else {
                        UIUtils.showToast("没有信息");
                    }
                }

            }
        });

    }

    /**
     * 原辅料 待办 list  详情  MaterialPurchaseInData  MaterialDetailsData
     */
    public void fetchTODOMaterialDetails(String sapOrderNo) {

        httpMethods.getMaterialTodoListDetail(new BaseSubscriber<BaseResponse<MaterialTodoDetailsData>>() {
            @Override
            public void onError(ApiException e) {
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<MaterialTodoDetailsData> info) {

                if (CodeConstant.SERVICE_SUCCESS.equals(info.getTag())) {

                    MaterialTodoDetailsData data = info.getData();

                    TLog.d(data);

                    materialObjUi.detailObjData(data);

                } else {
                    UIUtils.showToast(info.getMessage());
                }
            }
        }, sapOrderNo);
    }

    /**
     * 库位码校验
     *
     * @param positionNo
     */
    public void checkCarCode(final int position, String positionNo) {

        httpMethods.checkCarCode(new BaseSubscriber<CarPositionNoData>() {
            @Override
            public void onError(ApiException e) {
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(CarPositionNoData responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 库位码合法
                    materialObjUi.showSuccess(position, responseInfo.getCount());
                } else {
                    UIUtils.showToast(responseInfo.getMessage());
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
    public void fetchDoneMaterial() {
        httpMethods.getMaterialDoneList(new BaseSubscriber<BaseResponse<MaterialDoneData>>() {
            @Override
            public void onError(ApiException e) {
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<MaterialDoneData> infoList) {

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {
                    if (null != infoList && infoList.getData() != null) {
                        List<MaterialDoneData.ListBean> list = infoList.getData().getList();
                        if (list != null && list.size() != 0) {
                            materialListUi.showMaterial(list);
                        } else {
                            UIUtils.showToast("没有信息");
                        }
                    }
                } else {
                    UIUtils.showToast(infoList.getMessage());
                }
            }
        });
    }

    /**
     * 已办详情
     */
    public void fetchDoneMaterialDetails(String id) {
        httpMethods.getMaterialDoneListDetail(new BaseSubscriber<BaseResponse<MaterialDoneDetailsData>>() {
            @Override
            public void onError(ApiException e) {
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<MaterialDoneDetailsData> infoList) {

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {
                    if (null != infoList && infoList.getData() != null) {
                        List<MaterialDoneDetailsData.ListBean> list = infoList.getData().getList();
                        if (list != null && list.size() != 0) {
                            materialListUi.showMaterial(list);
                        } else {
                            UIUtils.showToast("没有信息");
                        }
                    }
                } else {
                    UIUtils.showToast(infoList.getMessage());
                }
            }
        }, id);
    }


    /**
     *  已办 保存
     * @param body
     */
//    public void materialDoneInSave(RequestBody body) {
//        httpMethods.materialDoneInSave(new BaseSubscriber<ResponseInfo>() {
//            @Override
//            public void onError(ApiException e) {
//
//            }
//
//            @Override
//            public void onNext(ResponseInfo responseInfo) {
//
//                materialModel.saveMaterial(responseInfo);
//                UIUtils.showToast("onNext :" + responseInfo.getMessage());
//
//            }
//
//        }, body);
//
//    }
}
