package com.ty.zbpet.presenter.product;

import com.ty.zbpet.net.HttpMethods;

/**
 * @author TY on 2018/11/26.
 *
 * 退货入库 presenter
 */
public class ReturnPresenter {

    private ProductUiListInterface listInterface;
    private ProductUiObjInterface objInterface;

    private HttpMethods httpMethods;

    public ReturnPresenter(ProductUiListInterface listInterface){
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public ReturnPresenter(ProductUiObjInterface objInterface){
        this.objInterface = objInterface;
        httpMethods = HttpMethods.getInstance();
    }


}
