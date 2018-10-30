package com.ty.zbpet.presenter.material;


import java.util.List;

/**
 * @author PVer on 2018/10/28.
 *
 * UI 接口
 */
public interface MaterialUiInterface<T> {

    /**
     * Http 数据
     * @param list
     */
    void showMaterial(List<T> list);

    void showLoading();

    void hideLoading();
}
