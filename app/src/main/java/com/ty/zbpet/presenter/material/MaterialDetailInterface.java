package com.ty.zbpet.presenter.material;

/**
 * @author TY on 2018/11/7.
 *
 * 列表详情 接受 Object
 */
public interface MaterialDetailInterface<T> {


    /**
     * 接收一个 对象
     * @param obj
     */
    void detailObjData(T obj);
}
