package com.ty.zbpet.constant;

/**
 * @author TY
 */
public class ApiNameConstant {

    /**
     * 徐坤
     */
    public static final String BASE_URL = "http://192.168.21.33:3099/";
//    public static final String BASE_URL = "http://117.40.132.236:3099/";
//    public static final String BASE_URL = "http://jxsh.zhengbang.com:3309/";

    /**
     * 顾炎
     */
    public static final String BASE_URL1 = "http://192.168.11.24:3000/";

    /**
     * 少杰
     */
    public static final String BASE_URL2 = "http://192.168.11.6:3099/";

    /**
     * --------------------------------- 系统登录 ----------------------------------------
     */

    /**
     * 用户登录
     */
    public static final String USER_LOGIN = "login";

    /**
     * 用户登出
     */
    public static final String USER_LOGOUT = "sys/user/logout";

    /**
     * 修改密码
     */
    public static final String USER_UPDATE_PASSWORD = "sys/user/updatePassword";

    /**
     * 个人中心
     */
    public static final String USER_CENTER = "sys/user/userCenter";


    /**--------------------------------- 到货入库 ----------------------------------------*/

    /**
     * 原辅料——采购待办 列表
     */
    public static final String GET_MATERIAL_IN_WAREHOUSE_ORDER_LIST = "sap/zbSap/getMaterialPurchaseOrderList";

    /**
     * 原辅料——采购待办 详情
     */
    public static final String GET_MATERIAL_IN_WAREHOUSE_ORDER_INFO = "sap/zbSap/getMaterialPurchaseOrderInfo";


    /**
     * 原辅料——采购入库（待办 保存）
     */
    public static final String PURCHASE_IN = "stock/materialStock/mPurchaseIn";

    /**
     * 库位码校验
     */
    public static final String CHECK_CAR_CODE = "resources/warePositionInfo/getPositionList";

    /**
     * 扫码解析
     */
    public static final String URL_ANALYZE = "boxQrCode/boxQrCode/getBoxQrCode";

    /**
     * 原辅料 -- 已办列表
     */
    public static final String GET_MATERIAL_PURCHASE_LIST = "stock/materialStock/getInWarehouseOrderList";

    /**
     * 原辅料 已办列表详情
     */
    public static final String GET_MATERIAL_PURCHASE_LIST_INFO = "stock/materialStock/getInWarehouseOrderInfo";


    /**
     * 原辅料——冲销出库（已办 保存）
     */
    public static final String PURCHASE_IN_RECALL_OUT = "stock/materialStock/mPurchaseInRecallOut";

    /**--------------------------------- 领料出库 ----------------------------------------*/

    /**
     * 领料出库 - 待办 列表
     */
    public static final String PICK_OUT_TODO_LIST = "sap/zbSap/getMaterialReceiveOrderList";

    /**
     * 领料出库 - 待办 详情
     */
    public static final String PICK_OUT_TODO_LIST_INFO = "sap/zbSap/getMaterialReceiveOrderInfo";

    /**
     * 领料出库 - 待办详情 保存
     */
    public static final String PICK_OUT_TODO_LIST_SAVE = "stock/materialStock/mReceiveOut";


    /**
     * 领料出库 - 已办列表
     */
    public static final String PICK_OUT_DONE_LIST = "stock/materialStock/getMaterialOutOrderList";

    /**
     * 领料出库 - 已办列表 详情
     */
    public static final String PICK_OUT_DONE_LIST_INFO = "stock/materialStock/getMaterialOutOrderInfo";

    /**
     * 领料出库 - 已办详情 保存
     */
    public static final String PICK_OUT_DONE_LIST_SAVE = "stock/materialStock/mReceiveRecallIn";

    /**--------------------------------- 采购退货 ----------------------------------------*/


    /**
     * 采购退货 待办列表
     */
    public static final String GET_BACK_GOODS_TODO_LIST = "sap/zbSap/getMaterialBackOrderList";

    /**
     * 采购退货 待办详情
     */
    public static final String GET_BACK_GOODS_TODO_LIST_INFO = "sap/zbSap/getMaterialBackOrderInfo";

    /**
     * 采购退货 待办保存
     */
    public static final String GET_BACK_GOODS_TODO_SAVE = "stock/materialStock/mPurchaseReturnOut";


    /**
     * 采购退货 已办列表
     */
    public static final String GET_BACK_GOODS_DONE_LIST = "stock/materialStock/getMaterialOutOrderList";

    /**
     * 采购退货 已办详情
     */
    public static final String GET_BACK_GOODS_DONE_LIST_INFO = "stock/materialStock/getMaterialOutOrderInfo";

    /**
     * 采购退货 已办保存
     */
    public static final String GET_BACK_GOODS_DONE_SAVE = "stock/materialStock/mReturnRecallIn";

    /**----------------------------------------------------------------------------------*/
    /**--------------------------------- 成品库存 ----------------------------------------*/
    /**----------------------------------------------------------------------------------*/

    /**--------------------------------- 外采入库 ----------------------------------------*/

    /**
     * 外采入库 待办列表
     */
    public static final String GET_GOODS_PURCHASE_ORDER_LIST = "sap/zbSap/getGoodsPurchaseOrderList";

    /**
     * 外采入库 待办详情
     */
    public static final String GET_GOODS_PURCHASE_ORDER_INFO = "sap/zbSap/getGoodsPurchaseOrderInfo";


    /**
     * 外采入库 待办保存
     */
    public static final String GET_PURCHASE_TODO_SAVE = "stock/goodsStock/gPurchaseIn";


    /**
     * 外采入库 已办列表
     */
    public static final String GET_PURCHASE_DONE_LIST = "stock/goodsStock/getGoodsInWarehouseOrderList";

    /**
     * 外采入库 已办详情
     */
    public static final String GET_PURCHASE_DONE_LIST_INFO = "stock/goodsStock/getGoodsInWarehouseOrderInfo";

    /**
     * 外采入库 已办保存
     */
    public static final String GET_PURCHASE_DONE_SAVE = "stock/goodsStock/gPurchaseRecallOut";

    /**--------------------------------- 生产入库 ----------------------------------------*/

    /**
     * 生产入库 待办列表
     */
    public static final String GET_PRODUCE_ORDER_LIST = "sap/zbSap/getGoodsProductionOrderList";

    /**
     * 生产入库 待办详情
     */
    public static final String GET_PRODUCE_ORDER_INFO = "sap/zbSap/getGoodsProductionOrderInfo";


    /**
     * 生产入库 待办保存
     */
    public static final String GET_PRODUCE_TODO_SAVE = "stock/goodsStock/gProductionIn";


    /**
     * 生产入库 已办列表
     */
    public static final String GET_PRODUCE_DONE_LIST = "stock/goodsStock/getGoodsInWarehouseOrderList";

    /**
     * 生产入库 已办详情
     */
    public static final String GET_PRODUCE_DONE_LIST_INFO = "stock/goodsStock/getGoodsInWarehouseOrderInfo";

    /**
     * 生产入库 已办保存
     */
    public static final String GET_PRODUCE_DONE_SAVE = "stock/goodsStock/gProductionRecallOut";


    /**--------------------------------- 发货出库 ----------------------------------------*/

    /**
     * 发货出库 待办列表
     */
    public static final String GET_SHIP_ORDER_LIST = "sap/zbSap/getGoodsSendOrderList";

    /**
     * 发货出库 待办详情
     */
    public static final String GET_SHIP_ORDER_INFO = "sap/zbSap/getGoodsSendOrderInfo";


    /**
     * 发货出库 待办保存
     */
    public static final String GET_SHIP_TODO_SAVE = "stock/goodsStock/gSendOut";


    /**
     * 发货出库 已办列表
     */
    public static final String GET_SHIP_DONE_LIST = "stock/goodsStock/getGoodsOutWarehouseOrderList";

    /**
     * 发货出库 已办详情
     */
    public static final String GET_SHIP_DONE_LIST_INFO = "stock/goodsStock/getGoodsOutWarehouseOrderInfo";

    /**
     * 发货出库 已办保存
     */
    public static final String GET_SHIP_DONE_SAVE = "stock/goodsStock/gSendRecallIn";


    /**--------------------------------- 退货入库 ----------------------------------------*/

    /**
     * 退货入库 待办列表
     */
    public static final String GET_RETURN_ORDER_LIST = "sap/zbSap/getGoodsBackOrderList";

    /**
     * 退货入库 待办详情
     */
    public static final String GET_RETURN_ORDER_INFO = "sap/zbSap/getGoodsBackOrderInfo";


    /**
     * 退货入库 待办保存
     */
    public static final String GET_RETURN_TODO_SAVE = "stock/goodsStock/gSaleReturnIn";


    /**
     * 退货入库 已办列表
     */
    public static final String GET_RETURN_DONE_LIST = "stock/goodsStock/getGoodsInWarehouseOrderList";

    /**
     * 退货入库 已办详情
     */
    public static final String GET_RETURN_DONE_LIST_INFO = "stock/goodsStock/getGoodsInWarehouseOrderInfo";

    /**
     * 退货入库 已办保存
     */
    public static final String GET_RETURN_DONE_SAVE = "stock/goodsStock/gSaleReturnRecallOut";


    /**--------------------------------- end ----------------------------------------*/


    /**----------------------------------------------------------------------------------*/
    /**--------------------------------- 系统 --------------------------------------------*/
    /**----------------------------------------------------------------------------------*/

    /**--------------------------------- 溯源 --------------------------------------------*/

    /**
     * 成品溯源
     */
    public static final String PRODUCT_QUERY = "source/qrCodeSource/pdaQrCodeSourceInfo";

    /**
     * 库位码查询
     */
    public static final String POSITION_QUERY = "source/qrCodeSource/pdaPositionSourceInfo";


    /**--------------------------------- 质检 --------------------------------------------*/

    /**
     * 质检 待办列表
     */
    public static final String GET_CHECK_TODO_LIST = "check/materialCheckReport/getMaterialPurchaseList";

    /**
     * 质检 待办详情
     */
    public static final String GET_CHECK_TODO_INFO = "check/materialCheckReport/getMaterialPurchaseInfo";

    /**
     * 质检 待办保存
     */
    public static final String GET_CHECK_TODO_SAVE = "check/materialCheckReport/addMaterialCheckReport";


    /**
     * 质检 已办列表
     */
    public static final String GET_CHECK_DONE_LIST = "check/materialCheckReport/getMaterialCheckReportList";

    /**
     * 质检 已办详情
     */
    public static final String GET_CHECK_DONE_INFO = "check/materialCheckReport/getMaterialCheckReportInfo";

    /**
     * 质检 已办保存(修改)
     */
    public static final String GET_CHECK_DONE_SAVE = "check/materialCheckReport/updateMaterialCheckReport";

    /**--------------------------------- 质检 end ------------------------------------------*/


    /**--------------------------------- 图片 --------------------------------------------*/
    /**
     * 质检 待办 上传图片
     */
    public static final String POST_USER_QUA_CHECK_IMAGE = "check/materialCheckReport/uploadMaterialCheckPicture";

}
