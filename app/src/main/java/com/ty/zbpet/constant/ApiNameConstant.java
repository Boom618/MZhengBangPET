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

    // 原
    public static final String GOODS_PURCHASE_INSTORAGE = "/stock/goodsStock/gPurchaseIn";


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
    public static final String GET_PURCHASE_DONE_SAVE = "stock/materialStock/mReturnRecallIn";

    /**--------------------------------- 生产入库 ----------------------------------------*/

    /**
     * 生产入库 待办列表
     */
    public static final String GET_PRODUCE_ORDER_LIST = "sap/zbSap/getGoodsPurchaseOrderList";

    /**
     * 生产入库 待办详情
     */
    public static final String GET_PRODUCE_ORDER_INFO = "sap/zbSap/getGoodsProductionOrderInfo";


    /**
     * 生产入库 待办保存
     */
    public static final String GET_PRODUCE_TODO_SAVE = "stock/materialStock/mPurchaseReturnOut";


    /**
     * 生产入库 已办列表
     */
    public static final String GET_PRODUCE_DONE_LIST = "stock";

    /**
     * 生产入库 已办详情
     */
    public static final String GET_PRODUCE_DONE_LIST_INFO = "stock";

    /**
     * 生产入库 已办保存
     */
    public static final String GET_PRODUCE_DONE_SAVE = "stock/materialStock/mReturnRecallIn";



    /**--------------------------------- 发货出库 ----------------------------------------*/

    /**
     * 发货出库 待办列表
     */
    public static final String GET_SHIP_ORDER_LIST = "sap/zbSap/getBuyInOrderList";

    /**
     * 发货出库 待办详情
     */
    public static final String GET_SHIP_ORDER_INFO = "sap/zbSap/getBuyInOrderInfo";


    /**
     * 发货出库 待办保存
     */
    public static final String GET_SHIP_TODO_SAVE = "stock/materialStock/mPurchaseReturnOut";


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
    public static final String GET_SHIP_DONE_SAVE = "stock/materialStock/mReturnRecallIn";



    /**--------------------------------- 退货入库 ----------------------------------------*/

    /**
     * 退货入库 待办列表
     */
    public static final String GET_RETURN_ORDER_LIST = "sap/zbSap/getBuyInOrderList";

    /**
     * 退货入库 待办详情
     */
    public static final String GET_RETURN_ORDER_INFO = "sap/zbSap/getBuyInOrderInfo";


    /**
     * 退货入库 待办保存
     */
    public static final String GET_RETURN_TODO_SAVE = "stock/materialStock/mPurchaseReturnOut";


    /**
     * 退货入库 已办列表
     */
    public static final String GET_RETURN_DONE_LIST = "stock";

    /**
     * 退货入库 已办详情
     */
    public static final String GET_RETURN_DONE_LIST_INFO = "stock";

    /**
     * 退货入库 已办保存
     */
    public static final String GET_RETURN_DONE_SAVE = "stock/materialStock/mReturnRecallIn";


    /**--------------------------------- end ----------------------------------------*/

    /**
     * 获取仓库信息
     */
    public static final String GET_WAREHOUSE_INFO = "baseInfo/warehouseInfo/getWarehouseList";



}
