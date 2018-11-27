package com.ty.zbpet.presenter.product;

import com.ty.zbpet.net.HttpMethods;

/**
 * @author TY on 2018/11/26.
 *
 * 发货出库 presenter
 */
public class ShipPresenter {

    private ProductUiListInterface listInterface;
    private ProductUiObjInterface objInterface;

    private HttpMethods httpMethods;

    public ShipPresenter(ProductUiListInterface listInterface){
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public ShipPresenter(ProductUiObjInterface objInterface){
        this.objInterface = objInterface;
        httpMethods = HttpMethods.getInstance();
    }


}
