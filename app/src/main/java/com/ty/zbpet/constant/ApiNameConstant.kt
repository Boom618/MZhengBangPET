package com.ty.zbpet.constant

/**
 * @author TY
 */
object ApiNameConstant {

    /**
     * 徐坤
     */
    const val BASE_URL = "http://192.168.11.2:3000/"
//    const val BASE_URL = "http://117.40.132.236:3099/"
//    const val BASE_URL = "http://jxsh.zhengbang.com:3000/"

    /*--------------------------------- 系统登录 ---------------------------------------- */

    // 用户登录
    const val USER_LOGIN = "login"

    // 用户登出
    const val USER_LOGOUT = "sys/user/logout"

    // 修改密码
    const val USER_UPDATE_PASSWORD = "sys/user/updatePassword"

    // 个人中心
    const val USER_CENTER = "sys/user/userCenter"

    /*--------------------------------- 到货入库 ----------------------------------------*/

    // 原辅料——采购待办 列表
    const val GET_MATERIAL_IN_WAREHOUSE_ORDER_LIST = "sap/zbSap/getMaterialPurchaseOrderList"

    // 原辅料——采购待办 详情
    const val GET_MATERIAL_IN_WAREHOUSE_ORDER_INFO = "sap/zbSap/getMaterialPurchaseOrderInfo"

    // 原辅料——采购入库（待办 保存）
    const val PURCHASE_IN = "stock/materialStock/mPurchaseIn"

    // 库位码校验
    const val CHECK_CAR_CODE = "resources/warePositionInfo/getPositionList"

    // 扫码解析
    const val URL_ANALYZE = "boxQrCode/boxQrCode/getBoxQrCode"

    // 原辅料 -- 已办列表
    const val GET_MATERIAL_PURCHASE_LIST = "stock/materialStock/getInWarehouseOrderList"

    // 原辅料 已办列表详情
    const val GET_MATERIAL_PURCHASE_LIST_INFO = "stock/materialStock/getInWarehouseOrderInfo"


    // 原辅料——冲销出库（已办 保存）
    const val PURCHASE_IN_RECALL_OUT = "stock/materialStock/mPurchaseInRecallOut"

    /*--------------------------------- 领料出库 ----------------------------------------*/

    // 领料出库 - 待办 列表
    const val PICK_OUT_TODO_LIST = "sap/zbSap/getMaterialReceiveOrderList"

    // 领料出库 - 待办 详情
    const val PICK_OUT_TODO_LIST_INFO = "sap/zbSap/getMaterialReceiveOrderInfo"

    // 领料出库 - 待办详情 保存
    const val PICK_OUT_TODO_LIST_SAVE = "stock/materialStock/mReceiveOut"

    // 领料出库 - 已办列表
    const val PICK_OUT_DONE_LIST = "stock/materialStock/getMaterialOutOrderList"

    // 领料出库 - 已办列表 详情
    const val PICK_OUT_DONE_LIST_INFO = "stock/materialStock/getMaterialOutOrderInfo"

    // 领料出库 - 已办详情 保存
    const val PICK_OUT_DONE_LIST_SAVE = "stock/materialStock/mReceiveRecallIn"

    /*--------------------------------- 采购退货 ----------------------------------------*/

    // 采购退货 待办列表
    const val GET_BACK_GOODS_TODO_LIST = "sap/zbSap/getMaterialBackOrderList"

    // 采购退货 待办详情
    const val GET_BACK_GOODS_TODO_LIST_INFO = "sap/zbSap/getMaterialBackOrderInfo"

    // 采购退货 待办保存
    const val GET_BACK_GOODS_TODO_SAVE = "stock/materialStock/mPurchaseReturnOut"

    // 采购退货 已办列表
    const val GET_BACK_GOODS_DONE_LIST = "stock/materialStock/getMaterialOutOrderList"

    // 采购退货 已办详情
    const val GET_BACK_GOODS_DONE_LIST_INFO = "stock/materialStock/getMaterialOutOrderInfo"

    // 采购退货 已办保存
    const val GET_BACK_GOODS_DONE_SAVE = "stock/materialStock/mReturnRecallIn"

    /*----------------------------------------------------------------------------------*/
    /*--------------------------------- 成品库存 ----------------------------------------*/
    /*----------------------------------------------------------------------------------*/

    /*--------------------------------- 外采入库 ----------------------------------------*/

    // 外采入库 待办列表
    const val GET_GOODS_PURCHASE_ORDER_LIST = "sap/zbSap/getGoodsPurchaseOrderList"

    // 外采入库 待办详情
    const val GET_GOODS_PURCHASE_ORDER_INFO = "sap/zbSap/getGoodsPurchaseOrderInfo"


    // 外采入库 待办保存
    const val GET_PURCHASE_TODO_SAVE = "stock/goodsStock/gPurchaseIn"


    // 外采入库 已办列表
    const val GET_PURCHASE_DONE_LIST = "stock/goodsStock/getGoodsInWarehouseOrderList"

    // 外采入库 已办详情
    const val GET_PURCHASE_DONE_LIST_INFO = "stock/goodsStock/getGoodsInWarehouseOrderInfo"

    // 外采入库 已办保存
    const val GET_PURCHASE_DONE_SAVE = "stock/goodsStock/gPurchaseRecallOut"

    /*--------------------------------- 生产入库 ----------------------------------------*/

    // 生产入库 待办列表
    const val GET_PRODUCE_ORDER_LIST = "sap/zbSap/getGoodsProductionOrderList"

    // 生产入库 待办详情
    const val GET_PRODUCE_ORDER_INFO = "sap/zbSap/getGoodsProductionOrderInfo"


    // 生产入库 待办保存
    const val GET_PRODUCE_TODO_SAVE = "stock/goodsStock/gProductionIn"

    // 生产入库 已办列表
    const val GET_PRODUCE_DONE_LIST = "stock/goodsStock/getGoodsInWarehouseOrderList"

    // 生产入库 已办详情
    const val GET_PRODUCE_DONE_LIST_INFO = "stock/goodsStock/getGoodsInWarehouseOrderInfo"

    // 生产入库 已办保存
    const val GET_PRODUCE_DONE_SAVE = "stock/goodsStock/gProductionRecallOut"

    /*--------------------------------- 发货出库 ----------------------------------------*/

    // 发货出库 待办列表
    const val GET_SHIP_ORDER_LIST = "sap/zbSap/getGoodsSendOrderList"

    // 发货出库 待办详情
    const val GET_SHIP_ORDER_INFO = "sap/zbSap/getGoodsSendOrderInfo"

    // 发货出库 待办保存
    const val GET_SHIP_TODO_SAVE = "stock/goodsStock/gSendOut"

    // 发货出库 已办列表
    const val GET_SHIP_DONE_LIST = "stock/goodsStock/getGoodsOutWarehouseOrderList"

    // 发货出库 已办详情
    const val GET_SHIP_DONE_LIST_INFO = "stock/goodsStock/getGoodsOutWarehouseOrderInfo"

    // 发货出库 已办保存
    const val GET_SHIP_DONE_SAVE = "stock/goodsStock/gSendRecallIn"

    /*--------------------------------- 退货入库 ----------------------------------------*/

    // 退货入库 待办列表
    const val GET_RETURN_ORDER_LIST = "sap/zbSap/getGoodsBackOrderList"

    // 退货入库 待办详情
    const val GET_RETURN_ORDER_INFO = "sap/zbSap/getGoodsBackOrderInfo"

    // 退货入库 待办保存
    const val GET_RETURN_TODO_SAVE = "stock/goodsStock/gSaleReturnIn"

    // 退货入库 已办列表
    const val GET_RETURN_DONE_LIST = "stock/goodsStock/getGoodsInWarehouseOrderList"

    // 退货入库 已办详情
    const val GET_RETURN_DONE_LIST_INFO = "stock/goodsStock/getGoodsInWarehouseOrderInfo"

    // 退货入库 已办保存
    const val GET_RETURN_DONE_SAVE = "stock/goodsStock/gSaleReturnRecallOut"

    /*--------------------------------- end ----------------------------------------*/

    /*----------------------------------------------------------------------------------*/
    /*--------------------------------- 系统 --------------------------------------------*/
    /*----------------------------------------------------------------------------------*/

    /*--------------------------------- 溯源 --------------------------------------------*/

    // 成品溯源
    const val PRODUCT_QUERY = "source/qrCodeSource/pdaQrCodeSourceInfo"

    // 库位码查询
    const val POSITION_QUERY = "source/qrCodeSource/pdaPositionSourceInfo"

    /*--------------------------------- 质检 --------------------------------------------*/

    // 质检 待办列表
    const val GET_CHECK_TODO_LIST = "check/materialCheckReport/getMaterialPurchaseList"

    // 质检 待办详情
    const val GET_CHECK_TODO_INFO = "check/materialCheckReport/getMaterialPurchaseInfo"

    // 质检 待办保存
    const val GET_CHECK_TODO_SAVE = "check/materialCheckReport/addMaterialCheckReport"

    // 质检 已办列表
    const val GET_CHECK_DONE_LIST = "check/materialCheckReport/getMaterialCheckReportList"

    // 质检 已办详情
    const val GET_CHECK_DONE_INFO = "check/materialCheckReport/getMaterialCheckReportInfo"

    // 质检 已办保存(修改)
    const val GET_CHECK_DONE_SAVE = "check/materialCheckReport/updateMaterialCheckReport"

    /*--------------------------------- 质检 end ------------------------------------------*/


    /*--------------------------------- 盘点 start ------------------------------------------*/

    // 原辅料盘点
    const val MATERIAL_POSITION_STOCK = "stock/checkStock/getMaterialPositionStock"

    // 盘点提交
    const val MATERIAL_POSITION_STOCK_SAVE = "stock/checkStock/materialInventory"

    // 盘点 成品列表(也是移库产品列表)
    const val GET_GOODS_LIST = "resources/goodsInfo/getGoodsList"

    // 成品盘点扫箱码
    const val GET_GOODS_STOCK = "stock/checkStock/getGoodsStock"

    // 成品盘点 提交
    const val GOODS_INVENTORY = "stock/checkStock/goodsInventory"

    // 盘点列表
    const val GET_CHECK_LIST = "stock/checkStock/getCheckList"

    // 删除盘点记录
    const val DELETE_CHECK = "stock/checkStock/deleteCheck"

    /*--------------------------------- 移库 --------------------------------------------*/

    // 源库位 移出
    const val MATERIAL_MOVE_ORDER = "stock/moveStock/createMaterialMoveOrder"

    // 移库列表
    const val MOVE_LIST = "stock/moveStock/getMaterialMoveList"

    // 移入目标库位
    const val MATERIAL_MOVE_TARGET = "stock/moveStock/moveMaterial"

    // 原辅料移库待冲销列表
    const val REVERSAL_LIST = "stock/moveStock/materialMoveList"

    // 原辅料移库冲销
    const val MOVE_RECALL = "stock/moveStock/materialMoveRecall"

    // 查询 成品移库的数据
    const val GoodsStock = "stock/moveStock/getGoodsStock"

    // 成品移出源库位
    const val goodsMoveOrder = "stock/moveStock/createGoodsMoveOrder"

    // 获取成品移库单
    const val moveProductList = "stock/moveStock/getGoodsMoveList"

    // 成品移入目标库位
    const val goodsMoveToTarget = "stock/moveStock/goodsMove"

    // 成品待冲销列表
    const val goodsRecallList = "stock/moveStock/goodsMoveList"

    // 成品冲销
    const val goodsMoveRecall = "stock/moveStock/goodsMoveRecall"

    // 成品移出源库位冲销(314冲销)
    const val goodsSourceRecall = "stock/moveStock/sourceWarehouseRecall"

    /*--------------------------------- 图片 --------------------------------------------*/
    // 质检 待办 上传图片
    const val POST_USER_QUA_CHECK_IMAGE = "check/materialCheckReport/uploadMaterialCheckPicture"

}
