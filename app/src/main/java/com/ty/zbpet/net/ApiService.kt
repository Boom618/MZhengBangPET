package com.ty.zbpet.net

import com.ty.zbpet.base.BaseResponse
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.UserInfo
import com.ty.zbpet.bean.eventbus.system.CheckDoneDetailEvent
import com.ty.zbpet.bean.eventbus.system.PersonCenterEvent
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.bean.material.MaterialList
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.bean.product.ProductList
import com.ty.zbpet.bean.system.*
import com.ty.zbpet.constant.ApiNameConstant
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @author TY
 */
interface ApiService {


    /**
     * --------------------------------- 系统登录 ----------------------------------------
     */

    /**
     * 用户登录
     *
     * @param userName 用户手机
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.USER_LOGIN)
    fun userLogin(@Field("userName") userName: String,
                  @Field("password") password: String): Single<BaseResponse<UserInfo>>

    /**
     * 用户登出
     *
     * @return
     */
    @POST(ApiNameConstant.USER_LOGOUT)
    fun userLogout(): Single<ResponseInfo>


    /**
     * 用户修改密码
     *
     * @param oldPassword      原密码
     * @param newPassword      新密码
     * @param newPasswordAgain 新密码确认
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.USER_UPDATE_PASSWORD)
    fun userUpdatePass(@Field("oldPassword") oldPassword: String,
                       @Field("newPassword") newPassword: String,
                       @Field("newPasswordAgain") newPasswordAgain: String): Single<ResponseInfo>


    /**
     * 个人中心
     *
     * @return
     */
    @POST(ApiNameConstant.USER_CENTER)
    fun userCenter(): Single<BaseResponse<PersonCenterEvent>>

    /**--------------------------------- 到货入库 ---------------------------------------- */

    /**
     * 获取原辅料采购 待办 列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_IN_WAREHOUSE_ORDER_LIST)
    fun getMaterialTodoList(@Field("sapOrderNo") sapOrderNo: String,
                            @Field("startDate") startDate: String,
                            @Field("endDate") endDate: String): Single<BaseResponse<MaterialList>>

    /**
     * 获取原辅料采购 待办 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_IN_WAREHOUSE_ORDER_INFO)
    fun getMaterialTodoListDetail(@Field("sign") sign: String,
                                  @Field("sapFirmNo") sapFirmNo: String,
                                  @Field("sapOrderNo") sapOrderNo: String,
                                  @Field("supplierNo") supplierNo: String): Single<BaseResponse<MaterialDetails>>

    /**
     * 待办 保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.PURCHASE_IN)
    fun materialPurchaseInSave(@Body body: RequestBody): Single<ResponseInfo>


    /**
     * 库位码检验
     *
     * @param positionNo  库位码
     * @param warehouseNo 仓库编号
     * @return Single
     */
    @GET(ApiNameConstant.CHECK_CAR_CODE)
    fun checkCarCode(@Query("positionNo") positionNo: String,
                     @Query("warehouseNo") warehouseNo: String): Single<CarPositionNoData>

    @FormUrlEncoded
    @POST(ApiNameConstant.getStock)
    fun getStock(@Field("positionNo") positionNo: String,
                 @Field("materialNo") materialNo: String): Single<BaseResponse<String>>

    /**
     * url 解析
     *
     * @param url     url
     * @param goodsNo 商品编号
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.URL_ANALYZE)
    fun urlAnalyze(@Field("url") url: String,
                   @Field("goodsNo") goodsNo: String): Single<BaseResponse<BoxCodeUrl>>

    /**
     * 获取原辅料采购 已办列表
     *
     * @param type 服务器接口类型
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_PURCHASE_LIST)
    fun getMaterialDoneList(@Field("type") type: String,
                            @Field("sapOrderNo") sapOrderNo: String,
                            @Field("startDate") startDate: String,
                            @Field("endDate") endDate: String): Single<BaseResponse<MaterialList>>

    /**
     * 获取原辅料采购 已办列表详情
     *
     * @param orderId
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_PURCHASE_LIST_INFO)
    fun getMaterialDoneListDetail(@Field("orderId") orderId: String): Single<BaseResponse<MaterialDetails>>

    /**
     * 原辅料采购冲销入库(已办保存)
     *
     * @param body body
     * @return Single
     */
    @POST(ApiNameConstant.PURCHASE_IN_RECALL_OUT)
    fun purchaseInRecallOut(@Body body: RequestBody): Single<ResponseInfo>

    /**--------------------------------- 领料出库 ---------------------------------------- */

    /**
     * 领料出库 - 待办 列表
     *
     * @param sign S  生产订单   Y预留单号
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PICK_OUT_TODO_LIST)
    fun pickOutTodoList(@Field("sign") sign: String,
                        @Field("sapOrderNo") sapOrderNo: String,
                        @Field("startDate") startDate: String,
                        @Field("endDate") endDate: String): Single<BaseResponse<MaterialList>>

    /**
     * 领料出库 - 待办 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PICK_OUT_TODO_LIST_INFO)
    fun pickOutTodoListDetail(@Field("sign") sign: String,
                              @Field("sapOrderNo") sapOrderNo: String,
                              @Field("sapFirmNo") sapFirmNo: String,
                              @Field("orderTime") orderTime: String): Single<BaseResponse<MaterialDetails>>

    /**
     * 领料出库 - 待办详情 保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.PICK_OUT_TODO_LIST_SAVE)
    fun pickOutTodoSave(@Body body: RequestBody): Single<ResponseInfo>

    /**
     * 领料出库 - 已办 列表
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PICK_OUT_DONE_LIST)
    fun pickOutDoneList(@Field("type") type: String,
                        @Field("sapOrderNo") sapOrderNo: String,
                        @Field("startDate") startDate: String,
                        @Field("endDate") endDate: String): Single<BaseResponse<MaterialList>>

    /**
     * 领料出库 - 已办详情
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PICK_OUT_DONE_LIST_INFO)
    fun pickOutDoneListDetail(@Field("orderId") orderId: String): Single<BaseResponse<MaterialDetails>>


    /**
     * 领料出库 - 已办详情 保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.PICK_OUT_DONE_LIST_SAVE)
    fun pickOutDoneSave(@Body body: RequestBody): Single<ResponseInfo>

    /**--------------------------------- 采购退货 ---------------------------------------- */

    /**
     * 采购退货 待办列表
     *
     * @param sapOrderNo 搜索单号
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_BACK_GOODS_TODO_LIST)
    fun getBackTodoList(@Field("sapOrderNo") sapOrderNo: String,
                        @Field("startDate") startDate: String,
                        @Field("endDate") endDate: String): Single<BaseResponse<MaterialList>>

    /**
     * 采购退货 待办列表 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_BACK_GOODS_TODO_LIST_INFO)
    fun getBackTodoListInfo(@Field("sapOrderNo") sapOrderNo: String,
                            @Field("sapFirmNo") sapFirmNo: String,
                            @Field("supplierNo") supplierNo: String): Single<BaseResponse<MaterialDetails>>

    /**
     * 采购退货 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_BACK_GOODS_TODO_SAVE)
    fun getBackTodoSave(@Body body: RequestBody): Single<ResponseInfo>


    /**
     * 采购退货 已办列表
     *
     * @param type       type
     * @param sapOrderNo 搜索单号
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_BACK_GOODS_DONE_LIST)
    fun getBackDoneList(@Field("type") type: String,
                        @Field("sapOrderNo") sapOrderNo: String,
                        @Field("startDate") startDate: String,
                        @Field("endDate") endDate: String): Single<BaseResponse<MaterialList>>

    /**
     * 采购退货 已办详情
     *
     * @param orderId orderId
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_BACK_GOODS_DONE_LIST_INFO)
    fun getBackDoneListInfo(@Field("orderId") orderId: String): Single<BaseResponse<MaterialDetails>>

    /**
     * 采购退货 已办保存
     *
     * @param body body
     * @return Single
     */
    @POST(ApiNameConstant.GET_BACK_GOODS_DONE_SAVE)
    fun getBackDoneSave(@Body body: RequestBody): Single<ResponseInfo>

    /**----------------------------销售出库 ------------------------------------------- */

    @FormUrlEncoded
    @POST(ApiNameConstant.SaleOrderList)
    fun getSaleOrderList(@Field("type") type: String,
                         @Field("sapOrderNo") sapOrderNo: String,
                         @Field("startDate") startDate: String,
                         @Field("endDate") endDate: String): Single<BaseResponse<MaterialList>>

    @FormUrlEncoded
    @POST(ApiNameConstant.SaleOrderInfo)
    fun saleOrderInfo(@Field("sign") sing: String,
                      @Field("sapOrderNo") sapOrderNo: String,
                      @Field("sapFirmNo") sapFirmNo: String,
                      @Field("supplierNo") supplierNo: String): Single<BaseResponse<MaterialDetails>>

    @POST(ApiNameConstant.SaleOut)
    fun saleOut(@Body body: RequestBody): Single<ResponseInfo>

    @POST(ApiNameConstant.SaleInList)
    fun saleInList(@Body body: RequestBody): Single<ResponseInfo>


    /**---------------------------------------------------------------------------------- */
    /**--------------------------------- 成品库存 ---------------------------------------- */
    /**---------------------------------------------------------------------------------- */

    /**--------------------------------- 外采入库 ---------------------------------------- */

    /**
     * 外采入库 待办列表
     *
     * @param sapOrderNo 搜索单号
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_GOODS_PURCHASE_ORDER_LIST)
    fun getBuyInOrderList(@Field("sapOrderNo") sapOrderNo: String,
                          @Field("startDate") startDate: String,
                          @Field("endDate") endDate: String): Single<BaseResponse<ProductList>>

    /**
     * 外采入库 待办详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_GOODS_PURCHASE_ORDER_INFO)
    fun getBuyInOrderInfo(@Field("sign") sign: String,
                          @Field("sapOrderNo") sapOrderNo: String,
                          @Field("sapFirmNo") sapFirmNo: String,
                          @Field("supplierNo") supplierNo: String): Single<BaseResponse<ProductDetails>>


    /**
     * 外采入库 待办保存
     *
     * @param body body
     * @return Single
     */
    @POST(ApiNameConstant.GET_PURCHASE_TODO_SAVE)
    fun getBuyInTodoSave(@Body body: RequestBody): Single<ResponseInfo>


    /**
     * 外采入库 已办列表
     *
     * @param type       type
     * @param sapOrderNo 搜索单号
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PURCHASE_DONE_LIST)
    fun getBuyInDoneList(@Field("type") type: String,
                         @Field("sapOrderNo") sapOrderNo: String,
                         @Field("startDate") startDate: String,
                         @Field("endDate") endDate: String): Single<BaseResponse<ProductList>>

    /**
     * 外采入库 已办列表 详情
     *
     * @param orderId orderId
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PURCHASE_DONE_LIST_INFO)
    fun getBuyInDoneListInfo(@Field("orderId") orderId: String): Single<BaseResponse<ProductDetails>>

    /**
     * 采购退货 已办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_PURCHASE_DONE_SAVE)
    fun getBuyInDoneSave(@Body body: RequestBody): Single<ResponseInfo>


    /**--------------------------------- 生产入库 ---------------------------------------- */

    /**
     * 生产入库 待办列表
     *
     * @param sapOrderNo 搜索单号
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PRODUCE_ORDER_LIST)
    fun getProduceOrderList(@Field("sapOrderNo") sapOrderNo: String,
                            @Field("startDate") startDate: String,
                            @Field("endDate") endDate: String): Single<BaseResponse<ProductList>>

    /**
     * 生产入库 待办详情
     *
     * @param sign       生产/预留 单
     * @param sapOrderNo sapNo
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PRODUCE_ORDER_INFO)
    fun getProduceOrderInfo(@Field("sign") sign: String,
                            @Field("sapOrderNo") sapOrderNo: String): Single<BaseResponse<ProductDetails>>


    /**
     * 生产入库 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_PRODUCE_TODO_SAVE)
    fun getProduceTodoSave(@Body body: RequestBody): Single<ResponseInfo>


    /**
     * 生产入库 已办列表
     *
     * @param type       type
     * @param sapOrderNo 搜索单号
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PRODUCE_DONE_LIST)
    fun getProduceDoneList(@Field("type") type: String,
                           @Field("sapOrderNo") sapOrderNo: String,
                           @Field("startDate") startDate: String,
                           @Field("endDate") endDate: String): Single<BaseResponse<ProductList>>

    /**
     * 生产入库 已办列表 详情
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PRODUCE_DONE_LIST_INFO)
    fun getProduceDoneInfo(@Field("orderId") orderId: String): Single<BaseResponse<ProductDetails>>

    /**
     * 生产入库 已办保存
     *
     * @param body body
     * @return Single
     */
    @POST(ApiNameConstant.GET_PRODUCE_DONE_SAVE)
    fun getProduceDoneSave(@Body body: RequestBody): Single<ResponseInfo>


    /**--------------------------------- 发货出库 ---------------------------------------- */

    /**
     * 发货出库 待办列表
     *
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_SHIP_ORDER_LIST)
    fun getShipOrderList(@Field("sapOrderNo") sapOrderNo: String,
                         @Field("startDate") startDate: String,
                         @Field("endDate") endDate: String): Single<BaseResponse<ProductList>>

    /**
     * 发货出库 待办详情
     *
     * @param sapOrderNo  sign，sapFirmNo，supplierNo
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_SHIP_ORDER_INFO)
    fun getShipOrderInfo(@Field("sign") sign: String,
                         @Field("sapOrderNo") sapOrderNo: String,
                         @Field("supplierNo") supplierNo: String,
                         @Field("customerNo") customerNo: String,
                         @Field("sapFirmNo") sapFirmNo: String): Single<BaseResponse<ProductDetails>>


    /**
     * 发货出库 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_SHIP_TODO_SAVE)
    fun getShipTodoSave(@Body body: RequestBody): Single<ResponseInfo>


    /**
     * 发货出库 已办列表
     *
     * @param type type
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_SHIP_DONE_LIST)
    fun getShipDoneList(@Field("type") type: String,
                        @Field("sapOrderNo") sapOrderNo: String,
                        @Field("startDate") startDate: String,
                        @Field("endDate") endDate: String): Single<BaseResponse<ProductList>>

    /**
     * 发货出库 已办列表 详情
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_SHIP_DONE_LIST_INFO)
    fun getShipDoneListInfo(@Field("orderId") orderId: String): Single<BaseResponse<ProductDetails>>

    /**
     * 发货出库 已办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_SHIP_DONE_SAVE)
    fun getShipDoneSave(@Body body: RequestBody): Single<ResponseInfo>


    /**--------------------------------- 退货入库 ---------------------------------------- */
    /**
     * 退货入库 待办列表
     *
     * @param sapOrderNo 搜索单号
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_RETURN_ORDER_LIST)
    fun getReturnOrderList(@Field("sapOrderNo") sapOrderNo: String,
                           @Field("startDate") startDate: String,
                           @Field("endDate") endDate: String): Single<BaseResponse<ProductList>>

    /**
     * 退货入库 待办详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_RETURN_ORDER_INFO)
    fun getReturnOrderInfo(@Field("sapOrderNo") sapOrderNo: String): Single<BaseResponse<ProductDetails>>


    /**
     * 退货入库 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_RETURN_TODO_SAVE)
    fun getReturnTodoSave(@Body body: RequestBody): Single<ResponseInfo>


    /**
     * 退货入库 已办列表
     *
     * @param type       type
     * @param sapOrderNo 搜索单号
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_RETURN_DONE_LIST)
    fun getReturnDoneList(@Field("type") type: String,
                          @Field("sapOrderNo") sapOrderNo: String,
                          @Field("startDate") startDate: String,
                          @Field("endDate") endDate: String): Single<BaseResponse<ProductList>>

    /**
     * 退货入库 已办列表 详情
     *
     * @param orderId orderId
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_RETURN_DONE_LIST_INFO)
    fun getReturnDoneListInfo(@Field("orderId") orderId: String): Single<BaseResponse<ProductDetails>>

    /**
     * 退货入库 已办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_RETURN_DONE_SAVE)
    fun getReturnDoneSave(@Body body: RequestBody): Single<ResponseInfo>


    /**--------------------------------- End ---------------------------------------- */

    /**---------------------------------------------------------------------------------- */
    /**--------------------------------- 系统 -------------------------------------------- */
    /**---------------------------------------------------------------------------------- */

    /**
     * --------------------------------- 溯源 --------------------------------------------
     */

    /**
     * 成品溯源
     *
     * @param url URL
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PRODUCT_QUERY)
    fun getProductQuery(@Field("url") url: String): Single<BaseResponse<ProductQuery>>

    /**
     * 库位码查询
     *
     * @param positionNo positionNo
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.POSITION_QUERY)
    fun getPositionQuery(@Field("positionNo") positionNo: String): Single<BaseResponse<PositionCode>>


    /**--------------------------------- 质检 -------------------------------------------- */

    /**
     * 质检待办 列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_CHECK_TODO_LIST)
    fun getCheckTodoList(@Field("state") state: String): Single<BaseResponse<QualityCheckTodoList>>

    /**
     * 质检待办 详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_CHECK_TODO_INFO)
    fun getCheckTodoInfo(@Field("arrivalOrderNo") arrivalOrderNo: String): Single<BaseResponse<QualityCheckTodoDetails>>

    /**
     * 质检待办 保存
     *
     * @return
     */
    @POST(ApiNameConstant.GET_CHECK_TODO_SAVE)
    fun getCheckTodoSave(@Body body: RequestBody): Single<ResponseInfo>

    /**
     * 质检已办 列表
     *
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_CHECK_DONE_LIST)
    fun getCheckDoneList(@Field("state") state: String,
                         @Field("sapOrderNo") sapOrderNo: String,
                         @Field("startDate") startDate: String,
                         @Field("endDate") endDate: String): Single<BaseResponse<MaterialList>>
    //Single<BaseResponse<QualityCheckTodoList>> getCheckDoneList(@Field("state") String state);

    /**
     * 质检已办 详情
     *
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_CHECK_DONE_INFO)
    fun getCheckDoneInfo(@Field("id") id: String): Single<BaseResponse<CheckDoneDetailEvent>>

    /**
     * 质检已办 保存
     * @return
     */
    @POST(ApiNameConstant.GET_CHECK_DONE_SAVE)
    fun getCheckDoneSave(@Body body: RequestBody): Single<ResponseInfo>

    /*--------------------------------- 盘点 --------------------------------------------*/

    /**
     * 原辅料盘点
     *
     * @param positionNo 库位码
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.MATERIAL_POSITION_STOCK)
    fun positionStock(@Field("positionNo") positionNo: String): Single<BaseResponse<PositionCode>>

    /**
     * 原辅料盘点提交
     *
     * @param body body
     * @return Single
     */
    @POST(ApiNameConstant.MATERIAL_POSITION_STOCK_SAVE)
    fun positionStockSave(@Body body: RequestBody): Single<ResponseInfo>

    /**
     * 成品盘点 列表
     *
     * @return Single
     */
    @GET(ApiNameConstant.GET_GOODS_LIST)
    fun getGoodsList(@Query("pageSize") pageSize: Int,
                     @Query("goodsNo") goodsNo: String,
                     @Query("pageNumber") pageNumber: Int): Single<BaseResponse<ProductInventorList>>

    /**
     * 盘点列表
     *
     * @param type 1产品盘点单 2原辅料盘点单
     * @return Single
     */
    @POST(ApiNameConstant.GET_CHECK_LIST)
    fun getCheckList(): Single<BaseResponse<ReceiptList>>

    /**
     * 成品盘点 提交
     *
     * @param body body
     * @return Single
     */
    @POST(ApiNameConstant.GOODS_INVENTORY)
    fun goodsInventory(@Body body: RequestBody): Single<ResponseInfo>

    /**
     * 删除盘点记录
     *
     * @param id         id
     * @param sapCheckNo sapCheckNo
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.DELETE_CHECK)
    fun deleteCheck(@Field("id") id: String, @Field("sapCheckNo") sapCheckNo: String): Single<ResponseInfo>

    /*--------------------------------- 移库 --------------------------------------------*/

    // 源库位 移出
    @POST(ApiNameConstant.MATERIAL_MOVE_ORDER)
    fun materialMoveOrder(@Body body: RequestBody): Single<ResponseInfo>

    // 移库列表
    @POST(ApiNameConstant.MOVE_LIST)
    fun moveList(): Single<BaseResponse<PositionCode>>

    // 目标库位
    @FormUrlEncoded
    @POST(ApiNameConstant.MATERIAL_MOVE_TARGET)
    fun moveMaterial(@Field("id") id: String,
                     @Field("positionNo") positionNo: String,
                     @Field("warehouseNo") warehouseNo: String,
                     @Field("time") time: String): Single<ResponseInfo>

    // 原辅料移库待冲销列表
    @POST(ApiNameConstant.REVERSAL_LIST)
    fun reversalList(): Single<BaseResponse<PositionCode>>

    // 原辅料移库冲销
    @POST(ApiNameConstant.MOVE_RECALL)
    fun reversalMove(@Body body: RequestBody): Single<ResponseInfo>

    // 查询 成品移库的数据
    @FormUrlEncoded
    @POST(ApiNameConstant.GoodsStock)
    fun goodsStock(@Field("goodsNo") goodsNo: String,
                   @Field("warehouseNo") warehouseNo: String): Single<BaseResponse<ProductInventorList>>

    // 成品移出原仓库
    @POST(ApiNameConstant.goodsMoveOrder)
    fun goodsMoveOrder(@Body body: RequestBody): Single<ResponseInfo>

    // 获取成品移库单
    @POST(ApiNameConstant.moveProductList)
    fun moveProductList(): Single<BaseResponse<ProMoveList>>

    // 成品移入目标仓库
    @FormUrlEncoded
    @POST(ApiNameConstant.goodsMoveToTarget)
    fun goodsMoveToTarget(@Field("id") id: String,
                          @Field("goodsNo") goodsNo: String,
                          @Field("goodsName") goodsName: String,
                          @Field("time") time: String): Single<ResponseInfo>

    // 成品移出源库位冲销(314冲销)
    @FormUrlEncoded
    @POST(ApiNameConstant.goodsSourceRecall)
    fun goodsSourceRecall(@Field("id") id: String): Single<ResponseInfo>

    // 成品待冲销列表
    @POST(ApiNameConstant.goodsRecallList)
    fun goodsRecallList(): Single<BaseResponse<ProMoveList>>

    // 成品冲销（316冲销/目标仓库冲销）
    @FormUrlEncoded
    @POST(ApiNameConstant.goodsMoveRecall)
    fun goodsMoveRecall(@Field("id") id: String): Single<ResponseInfo>


    /*--------------------------------- 图片 --------------------------------------------*/

    /**
     * 质检 待办 上传图片
     *
     * @param description
     * @param part
     * @return Single
     */
    @Multipart
    @POST(ApiNameConstant.POST_USER_QUA_CHECK_IMAGE)
    fun updateCheckImage1(@Part("file") description: RequestBody,
                          @Part part: MultipartBody.Part): Single<ImageData>

    /**
     * @param part MultipartBody.Part
     * @return Single
     */
    @Multipart
    @POST(ApiNameConstant.POST_USER_QUA_CHECK_IMAGE)
    fun updateCheckImage(@Part part: MultipartBody.Part): Single<ImageData>

}
