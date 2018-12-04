package com.ty.zbpet.util;

/**
 * @author TY on 2018/11/6.
 * <p>
 * 代码常量
 */
public final class CodeConstant {

    public static final String DATE_SIMPLE_H_M = "yyyy-MM-dd HH:mm";
    public static final String DATE_SIMPLE_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_START_TIME = "2010-01-01 00:00";
    public static final String DATE_END_TIME = "2999-01-01 00:00";

    // 服务器返回成功标识
    public static final String SERVICE_SUCCESS = "success";

    /**
     * 待办详情中的输入信息
     * ZKG 单位
     * 库位码
     * sap 物料批次号
     */

    public static final String ET_ZKG = "et_zkg";
    public static final String ET_CODE = "et_code";
    public static final String ET_BULK_NUM = "et_bulk_num";
    public static final String ET_BATCH_NO = "et_batch_no";

    /**
     * 按键码 131 or 135 : K3A手柄键   139 : G3A F1键
     */
    public static final int KEY_CODE_131 = 131;
    public static final int KEY_CODE_135 = 135;
    public static final int KEY_CODE_139 = 139;

    /**
     * 扫码保存的信息
     */
    public static final String SCAN_BOX_KEY = "scan";

    /**
     * 扫码字符集
     */
    public static final String UNICODE_STRING = "\ufffd";
    public static final String CHARSET_UTF8 = "utf8";
    public static final String CHARSET_GBK = "gbk";

    /**
     * 区分箱码绑定页面
     */
    public static final String PAGE_STATE = "state";

    /**
     * 原材料的入库 已办列表
     */
    public static final String BUY_IN_TYPE = "1";
    public static final String PICK_OUT_TYPE = "20";
    public static final String BACK_GOODS_TYPE = "21";

}
