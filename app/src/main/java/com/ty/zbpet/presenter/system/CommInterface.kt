package com.ty.zbpet.presenter.system

/**
 * @author TY on 2019/3/19.
 * App 公共接口
 */
interface CommInterface {

    /**
     * url 解析
     */
    fun urlAnalyze(codeNo:String)

    fun showError(msg:String)

    fun showLoading()

    fun hideLoading()
}