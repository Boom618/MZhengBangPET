package com.ty.zbpet.presenter.product;

import java.util.List;

/**
 * @author TY on 2018/11/26.
 *
 * 产品 list
 */
public interface ProductUiListInterface<T> {

    /**
     * Http 数据
     * @param list
     */
    void showProduct(List<T> list);

}
