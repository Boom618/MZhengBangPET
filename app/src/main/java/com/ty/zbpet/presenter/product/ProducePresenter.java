package com.ty.zbpet.presenter.product;

import com.ty.zbpet.net.HttpMethods;

/**
 * @author TY on 2018/11/26.
 *
 * 生产入库 presenter
 */
public class ProducePresenter {

    private ProductUiListInterface listInterface;
    private ProductUiObjInterface objInterface;

    private HttpMethods httpMethods;

    public ProducePresenter(ProductUiListInterface listInterface){
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public ProducePresenter(ProductUiObjInterface objInterface){
        this.objInterface = objInterface;
        httpMethods = HttpMethods.getInstance();
    }


}
