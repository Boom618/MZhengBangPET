package com.ty.zbpet.constant

/**
 * @author TY on 2018/11/6.
 *
 *
 * 代码常量
 */
object CodeConstant {

    /**
     *  Java 非静态类中 调 Kotlin 常量字段, const val ,需要 添加 【 const 】 修饰
     */
    const val DATE_SIMPLE_Y_M_D = "yyyy-MM-dd"
    const val DATE_SIMPLE_H_M = "yyyy-MM-dd HH:mm"
    const val DATE_SIMPLE_H_M_S = "yyyy-MM-dd HH:mm:ss"
    const val DATE_START_TIME = "2010-01-01 00:00"
    const val DATE_END_TIME = "2999-01-01 00:00"

    /**
     * 服务器返回成功标识
     */
    const val SERVICE_SUCCESS = "success"

    /**
     * ET_PERCENT_INT       :60 含量
     * ET_CONTENT_INT       :61 文本
     * ET_ZKG_INT           :62 zkg
     * ET_NUMBER_INT        :63 数量
     * ET_CODE_INT          :64 库位码
     * ET_START_CODE_INT    :65 开始码
     * ET_END_CODE_INT      :66 结束码
     * ET_SAP_INT           :67 SAP 物料
     * ET_CODE_FOCUS_INT    :68 库位码是否有焦点
     */
    const val ET_PERCENT_INT = 60
    val ET_NUMBER_INT = 63
    val ET_CODE_FOCUS_INT = 68
    /**
     * 按键码 131 or 135 : K3A手柄键   139 : G3A F1键
     */
    const val KEY_CODE_131 = 131
    const val KEY_CODE_135 = 135
    const val KEY_CODE_139 = 139

    /**
     * 跳转 Activity 类型
     */
    const val ACTIVITY_TYPE = "acType"
    const val FRAGMENT_TYPE = "fgType"
    const val FRAGMENT_TODO = "todoFg"
    const val FRAGMENT_DONE = "doneFg"

    /**
     * S  生产订单   Y 预留单号
     */
    const val SIGN_S = "S"
    const val SIGN_Y = "Y"

    /**
     * 扫码字符集
     */
    const val UNICODE_STRING = "\ufffd"
    const val CHARSET_UTF8 = "utf8"
    const val CHARSET_GBK = "gbk"

    /**
     * 区分箱码绑定页面
     */
    val PAGE_STATE = "state"

    /**
     * 原材料的入库 已办列表 （ 服务器定的规则 ）
     */
    const val BUY_IN_TYPE = "1"
    const val PICK_OUT_TYPE = "20"
    const val BACK_GOODS_TYPE = "21"

    /**
     * 成品  已办列表 （ 服务器定的规则 ）
     */
    const val PRODUCT_TYPE = "2"
    const val RETURN_TYPE = "3"

    /**
     * 成品
     *
     *
     * SELECT_HOUSE_BUY_IN : 选择的是哪个仓库
     * SELECT_GOODS:        选择的是哪个商品
     * SELECT_IMAGE:        质检上传的图片
     */
    val SELECT_HOUSE_BUY_IN = 100
    val SELECT_GOODS = 300
    val SELECT_IMAGE = 700


    /**
     * 腾讯 bug ID
     */
    const val BUG_LY_APP_ID = "1bb4b12139"


    /**
     * 用户 sessionId 全局
     */

    val SYSTEM_KEY = "system"
    val SYSTEM_VALUE = "android"
    const val SESSION_ID_KEY = "sessionid"
    val CHANGE_ROLE_PHONE = "切换手机登录"
    val CHANGE_ROLE_COMPANY = "切换组织代码登录"

    /**
     * 用户数据
     */
    const val USER_DATA = "userData"

    /**
     * 质检列表 state
     */
    val CHECK_STATE_TODO = "01"
    val CHECK_STATE_DONE = "02"
}
