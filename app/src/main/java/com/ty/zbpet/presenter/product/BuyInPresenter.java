package com.ty.zbpet.presenter.product;

import com.ty.zbpet.net.HttpMethods;

/**
 * @author TY on 2018/11/26.
 *
 * 外采入库 presenter
 */
public class BuyInPresenter {

    private ProductUiListInterface listInterface;
    private ProductUiObjInterface objInterface;

    private HttpMethods httpMethods;

    public BuyInPresenter(ProductUiListInterface listInterface){
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public BuyInPresenter(ProductUiObjInterface objInterface){
        this.objInterface = objInterface;
        httpMethods = HttpMethods.getInstance();
    }


}
