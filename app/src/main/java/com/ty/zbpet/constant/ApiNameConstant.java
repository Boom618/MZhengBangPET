package com.ty.zbpet.constant;

/**
 * @author TY
 */
public class ApiNameConstant {

    public static final String BASE_URL = "http://192.168.11.2:3099/";//徐坤

    public static final String BASE_URL1 = "http://192.168.11.24:3000/";//顾炎

    /**--------------------------------- 到货入库 ----------------------------------------*/

    /**
     * 原辅料——采购待办 列表
     */
    public static final String GET_MATERIAL_IN_WAREHOUSE_ORDER_LIST = "sap/zbSap/getMaterialInWarehouseOrderList";

    /**
     * 原辅料——采购待办 详情
     */
    public static final String GET_MATERIAL_IN_WAREHOUSE_ORDER_INFO = "sap/zbSap/getMaterialInWarehouseOrderInfo";


    /**
     * 原辅料——采购入库（待办 保存）
     */
    public static final String PURCHASE_IN = "stock/materialStock/mPurchaseIn";

    /**
     * 库位码校验
     */
    public static final String CHECK_CAR_CODE = "resources/warePositionInfo/getPositionList";

    /**
     * 原辅料 -- 已办列表
     */
    //public static final String GET_MATERIAL_PURCHASE_LIST = "stock/materialStock/getMaterialPurchaseList";
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
    public static final String PICK_OUT_DONE_LIST = "stock/materialStock/getReceiveOutList";

    /**
     * 领料出库 - 已办列表 详情
     */
    public static final String PICK_OUT_DONE_LIST_INFO = "sap";

    /**
     * 领料出库 - 已办详情 保存
     */
    public static final String PICK_OUT_DONE_LIST_SAVE = "stock/materialStock/mReceiveRecallIn";

    /**--------------------------------- 采购退货 ----------------------------------------*/


    /**
     * 采购退货 待办列表
     */
    public static final String GET_BACK_GOODS_TODO_LIST = "stock";

    /**
     * 采购退货 待办列表
     */
    public static final String GET_BACK_GOODS_TODO_LIST_INFO = "stock";

    /**
     * 采购退货 待办保存
     */
    public static final String GET_BACK_GOODS_TODO_SAVE = "stock/materialStock/mPurchaseReturnOut";


    /**
     * 采购退货 已办列表
     */
    public static final String GET_BACK_GOODS_DONE_LIST = "stock";

    /**
     * 采购退货 已办列表
     */
    public static final String GET_BACK_GOODS_DONE_LIST_INFO = "stock";

    /**
     * 采购退货 已办保存
     */
    public static final String GET_BACK_GOODS_DONE_SAVE = "stock/materialStock/mReturnRecallIn";

    /**----------------------------------------------------------------------------------*/
    /**--------------------------------- 成品库存 ----------------------------------------*/
    /**----------------------------------------------------------------------------------*/

    /**--------------------------------- 外采入库 ----------------------------------------*/

    /**
     * 成品——外采入库待办列表
     */
    public static final String GET_GOODS_PURCHASE_ORDERLIST = "sap/zbSap/getGoodsPurchaseOrderList";

    /**
     * 成品——外采入库待办详情
     */
    public static final String GET_GOODS_PURCHASE_ORDERINFO = "sap/zbSap/getGoodsPurchaseOrderInfo";

    /**
     * 获取仓库信息
     */
    public static final String GET_WAREHOUSE_INFO = "baseInfo/warehouseInfo/getWarehouseList";


    /**
     * 成品采购入库——待办
     */
    public static final String GOODS_PURCHASE_INSTORAGE = " /stock/goodsStock/gPurchaseIn";
}
