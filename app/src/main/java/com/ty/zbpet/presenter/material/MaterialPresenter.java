package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.bean.MaterialDetailsData;
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
    MaterialUiListInterface materialUi;

    /**
     * 详情 接口
     */
    MaterialUiObjlInterface detailUi;

    /**
     * model 数据接口
     */
    MaterialModelInterface materialModel = new MaterialModelInterfaceImpl();

    // API 网络

    /**
     * 接收 list UI 接口
     *
     * @param materialUiInterface
     */
    public MaterialPresenter(MaterialUiListInterface materialUiInterface) {
        this.materialUi = materialUiInterface;
    }

    /**
     * 接收 object UI 接口
     *
     * @param detailUi
     */
    public MaterialPresenter(MaterialUiObjlInterface detailUi) {
        this.detailUi = detailUi;
    }

    /**
     * 原辅料 待办 list  业务逻辑
     */
    public void fetchTODOMaterial() {

        materialUi.showLoading();

        // APi  获取数据
        HttpMethods.getInstance().getMaterialOrderListTodo(new BaseSubscriber<BaseResponse<MaterialData>>() {

            @Override
            public void onError(ApiException e) {
                UIUtils.showToast("原辅料——到货入库——待办 == onError ");
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<MaterialData> infoList) {
                materialUi.hideLoading();
                UIUtils.showToast("原辅料——到货入库——待办 == success ");

                if (CodeConstant.SERVICE_SUCCESS.equals(infoList.getTag())) {

                    if (infoList != null && infoList.getData().getList().size() != 0) {
                        List<MaterialData.ListBean> list = infoList.getData().getList();

                        // 数据
                        materialUi.showMaterial(list);


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

        HttpMethods.getInstance().getMaterialInWarehouseOrderInfo(new BaseSubscriber<BaseResponse<MaterialDetailsData>>() {
            @Override
            public void onError(ApiException e) {
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<MaterialDetailsData> info) {

                if (CodeConstant.SERVICE_SUCCESS.equals(info.getTag())) {

                    MaterialDetailsData data = info.getData();

                    TLog.d(data);

                    detailUi.detailObjData(data);

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

        HttpMethods.getInstance().checkCarCode(new BaseSubscriber<ResponseInfo>() {
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


}
