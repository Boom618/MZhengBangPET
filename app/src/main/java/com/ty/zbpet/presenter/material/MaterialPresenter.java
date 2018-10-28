package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.MaterialInWarehouseOrderList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.util.UIUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

/**
 * @author PVer on 2018/10/28.
 *
 *
 */
public class MaterialPresenter {

    // UI 接口
    MaterialUiInterface materialUi;

    // model 数据
    MaterialModelInterface materialModel = new MaterialModelInterfaceImpl();

    // API 网络

    public MaterialPresenter(MaterialUiInterface materialUiInterface) {
        this.materialUi = materialUiInterface;
    }

    /**
     * 拉取数据  业务逻辑
     */
    public void fetchMaterial(){
        materialUi.showLoading();
        // APi  获取数据
        HttpMethods.getInstance().getMaterialInWarehouseOrderList(new BaseSubscriber<MaterialInWarehouseOrderList>() {
            @Override
            public void onError(ApiException e) {
                UIUtils.showToast("原辅料——到货入库——已办 == onError ");
                UIUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(MaterialInWarehouseOrderList infoList) {
                UIUtils.showToast("原辅料——到货入库——已办 == success ");

                if (infoList.getData() != null) {
                    List<MaterialInWarehouseOrderList.DataBean.ListBean> list = infoList.getData().getList();
                    if (list != null && list.size() != 0) {

                        // 数据
                        materialUi.showMaterial(null);

                        materialUi.hideLoading();

                        // 保存数据(数据库，缓存。。。)
                        materialModel.saveMaterial(null);

                    } else {
                        UIUtils.showToast("没有信息");
                    }
                } else {
                    UIUtils.showToast(infoList.getMessage());
                }
            }
        });


    }

}
