package com.ty.zbpet.constant;

/**
 * @author TY
 */
public class ApiNameConstant {

    public static final String BASE_URL = "http://192.168.11.2:3099/";//徐坤

    public static final String BASE_URL1 = "http://192.168.11.24:3000/";//顾炎

    /**
     * 原辅料——采购已办列表
     */
    public static final String GET_MATERIAL_INWAREHOUSE_ORDERLIST = "sap/zbSap/getMaterialInWarehouseOrderList";

    /**
     * 原辅料——采购已办详情
     */
    public static final String GET_MATERIAL_INWAREHOUSE_ORDERINFO = "sap/zbSap/getMaterialInWarehouseOrderInfo";


    /**
     * 原辅料——采购入库冲销出库
     */
    public static final String PURCHASE_IN_RECALL_OUT = "stock/materialStock/mPurchaseInRecallOut";


    /**
     * 原料--- 领料出库详情 列表
     */
    public static final String PICK_OUT_DETAIL_INFO = "stock/materialStock/mReceiveOut";

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
