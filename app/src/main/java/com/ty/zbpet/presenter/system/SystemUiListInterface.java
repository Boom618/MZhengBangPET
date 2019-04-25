package com.ty.zbpet.presenter.system;

import java.util.List;

/**
 * @author TY on 2018/12/12.
 */
public interface SystemUiListInterface<T> {

    /**
     * Http 数据
     * @param list list
     */
    void showSystem(List<T> list);
}
