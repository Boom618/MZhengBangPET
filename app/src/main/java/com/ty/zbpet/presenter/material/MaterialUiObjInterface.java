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
     * @param position
     * @param count
     */
    void showSuccess(int position, int count);
}
