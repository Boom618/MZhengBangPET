package com.ty.zbpet.presenter.material;


import com.ty.zbpet.bean.CarPositionNoData;

import java.util.List;

/**
 * @author PVer on 2018/10/28.
 *
 * UI 接口
 */
public interface MaterialUiListInterface<T> {

    /**
     * Http 数据
     * @param list
     */
    void showMaterial(List<T> list);

    /**
     * 扫库位码
     * @param position
     * @param carData
     */
    void showCarSuccess(int position, CarPositionNoData carData);

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
    void showSuccess();

    /**
     * 失败
     * @param msg
     */
    void showError(String msg);
}
