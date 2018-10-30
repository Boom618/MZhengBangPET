package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.UIUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

/**
 * @author PVer on 2018/10/28.
 *
 * 采购入库 Presenter
 */
public class MaterialPresenter {

    /**
     * UI 接口
     */
    MaterialUiInterface materialUi;

    /**
     * model 数据接口
     */
    MaterialModelInterface materialModel = new MaterialModelInterfaceImpl();

    // API 网络

    public MaterialPresenter(MaterialUiInterface materialUiInterface) {
        this.materialUi = materialUiInterface;
    }

    /**
     * 拉取数据  业务逻辑
     */
    public void fetchMaterial() {

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
        });


    }

}