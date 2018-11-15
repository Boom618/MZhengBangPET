package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.MaterialTodoData;
import com.ty.zbpet.bean.MaterialTodoDetailsData;
import com.ty.zbpet.bean.MaterialDoneData;
import com.ty.zbpet.bean.ResponseInfo;
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
    MaterialUiObjlInterface materialObjUi;

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
    public MaterialPresenter(MaterialUiObjlInterface materialObjUi) {
        this.materialObjUi = materialObjUi;
        httpMethods = HttpMethods.getInstance();
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
                UIUtils.showToast("原辅料——到货入库——待办 == onError ");
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<MaterialTodoData> infoList) {
                materialListUi.hideLoading();
                UIUtils.showToast("原辅料——到货入库——待办 == success ");

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {

                    if (infoList != null && infoList.getData().getList().size() != 0) {
                        List<MaterialTodoData.ListBean> list = infoList.getData().getList();

                        // 数据
                        materialListUi.showMaterial(list);


                        // 保存数据(数据库，缓存。。。)
                        materialModel.saveMaterial(list);

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
     * 车库码校验
     * @param id
     * @param code
     */
    public void checkCarCode(String id,String code){

        httpMethods.checkCarCode(new BaseSubscriber<ResponseInfo>() {
            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onNext(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 车库码合法


                }else{
                    UIUtils.showToast(responseInfo.getMessage());
                }

            }
        },id, code);
    }

    /**
     * 原材料 已办 列表
     */
    public void fetchDoneMaterial(){
        httpMethods.getMaterialDoneList(new BaseSubscriber<BaseResponse<MaterialDoneData>>() {
            @Override
            public void onError(ApiException e) {
                UIUtils.showToast("原辅料——到货入库——已办 == onError ");
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<MaterialDoneData> infoList) {
                UIUtils.showToast("原辅料——到货入库——已办 == success ");

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {
                    if ( null != infoList && infoList.getData() != null) {
                        List<MaterialDoneData.ListBean> list = infoList.getData().getList();
                        if (list != null && list.size() != 0) {
                            materialListUi.showMaterial(list);
                        } else {
                            UIUtils.showToast("没有信息");
                        }
                    }
                }else {
                    UIUtils.showToast(infoList.getMessage());
                }
            }
        });
    }

    /**
     * 已办详情
     */
    public void fetchDoneMaterialDetails(){

    }


}
