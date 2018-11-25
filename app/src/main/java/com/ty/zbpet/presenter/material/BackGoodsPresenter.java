package com.ty.zbpet.presenter.material;

import com.ty.zbpet.net.HttpMethods;

/**
 * @author TY on 2018/11/25.
 *
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

    /**
     * 接收 list UI 接口
     *
     * @param materialUiInterface
     */
    public BackGoodsPresenter(MaterialUiListInterface materialUiInterface) {
        this.materialListUi = materialUiInterface;
        httpMethods = HttpMethods.getInstance();
    }

    /**
     * 接收 object UI 接口
     *
     * @param materialObjUi
     */
    public BackGoodsPresenter(MaterialUiObjInterface materialObjUi) {
        this.materialObjUi = materialObjUi;
        httpMethods = HttpMethods.getInstance();
    }


}
