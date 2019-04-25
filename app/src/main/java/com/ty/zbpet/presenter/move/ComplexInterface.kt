package com.ty.zbpet.presenter.move

/**
 * @author PVer on 2018/10/28.
 *
 *
 * UI 接口
 */
interface ComplexInterface<T> {

    /**
     * Http 数据
     *
     * @param list list
     */
    fun showListData(list: MutableList<T>)

    fun showObjData(obj: T)

    /**
     * 相应成功
     */
    fun responseSuccess()

    /**
     * 保存加载 dialog
     */
    fun showLoading()

    /**
     * 隐藏 dialog
     */
    fun hideLoading()

    /**
     * 失败
     *
     * @param msg msg
     */
    fun showError(msg: String)
}
