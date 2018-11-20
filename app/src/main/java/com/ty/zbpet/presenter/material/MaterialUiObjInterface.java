package com.ty.zbpet.presenter.material;

/**
 * @author TY on 2018/11/7.
 * <p>
 * 列表详情 接受 Object
 */
public interface MaterialUiObjInterface<T> {


    /**
     * 接收一个 对象
     *
     * @param obj
     */
    void detailObjData(T obj);

    /**
     * 成功
     *
     * @param position  库位码 的 position
     * @param count      数量
     */
    void showSuccess(int position, int count);
}
