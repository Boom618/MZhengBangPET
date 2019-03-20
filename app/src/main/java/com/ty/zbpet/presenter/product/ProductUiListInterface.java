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
     * @param list list
     */
    void showProduct(List<T> list);

    /**
     * 保存加载 dialog
     */
    void showLoading();

    /**
     * 隐藏 dialog
     */
    void hideLoading();

    /**
     * 保存成功
     */
    void saveSuccess();

    /**
     * 失败
     * @param msg
     */
    void showError(String msg);

}
