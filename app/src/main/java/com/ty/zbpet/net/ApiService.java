package com.ty.zbpet.net;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.UserInfo;
import com.ty.zbpet.bean.eventbus.system.CheckDoneDetailEvent;
import com.ty.zbpet.bean.material.MaterialDetails;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.bean.product.ProductDetails;
import com.ty.zbpet.bean.product.ProductList;
import com.ty.zbpet.bean.system.BoxCodeUrl;
import com.ty.zbpet.bean.system.ImageData;
import com.ty.zbpet.bean.system.PositionCode;
import com.ty.zbpet.bean.system.ProductInventorList;
import com.ty.zbpet.bean.system.ProductQuery;
import com.ty.zbpet.bean.system.QualityCheckTodoDetails;
import com.ty.zbpet.bean.system.QualityCheckTodoList;
import com.ty.zbpet.bean.system.ReceiptList;
import com.ty.zbpet.constant.ApiNameConstant;
import com.ty.zbpet.ui.base.BaseResponse;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author TY
 */
public interface ApiService {


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
    Single<BaseResponse<UserInfo>> userLogin(@Field("userName") String userName,
                                             @Field("password") String password);

    /**
     * 用户登出
     *
     * @return
     */
    @POST(ApiNameConstant.USER_LOGOUT)
    Single<ResponseInfo> userLogout();


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
    Single<ResponseInfo> userUpdatePass(@Field("oldPassword") String oldPassword,
                                        @Field("newPassword") String newPassword,
                                        @Field("newPasswordAgain") String newPasswordAgain);


    /**
     * 个人中心
     *
     * @return
     */
    @POST(ApiNameConstant.USER_CENTER)
    Single<ResponseInfo> userCenter();

    /**--------------------------------- 到货入库 ----------------------------------------*/

    /**
     * 获取原辅料采购 待办 列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_IN_WAREHOUSE_ORDER_LIST)
    Single<BaseResponse<MaterialList>> getMaterialTodoList(@Field("sapOrderNo") String sapOrderNo,
                                                           @Field("startDate") String startDate,
                                                           @Field("endDate") String endDate);

    /**
     * 获取原辅料采购 待办 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_IN_WAREHOUSE_ORDER_INFO)
    Single<BaseResponse<MaterialDetails>> getMaterialTodoListDetail(@Field("sapFirmNo") String sapFirmNo,
                                                                    @Field("sapOrderNo") String sapOrderNo,
                                                                    @Field("supplierNo") String supplierNo);

    /**
     * 待办 保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.PURCHASE_IN)
    Single<ResponseInfo> materialPurchaseInSave(@Body RequestBody body);


    /**
     * 库位码检验
     *
     * @param positionNo  库位码
     * @param warehouseNo 仓库编号
     * @return Single
     */
    @GET(ApiNameConstant.CHECK_CAR_CODE)
    Single<CarPositionNoData> checkCarCode(@Query("positionNo") String positionNo,
                                           @Query("warehouseNo") String warehouseNo);

    /**
     * url 解析
     *
     * @param url     url
     * @param goodsNo 商品编号
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.URL_ANALYZE)
    Single<BaseResponse<BoxCodeUrl>> urlAnalyze(@Field("url") String url,
                                                @Field("goodsNo") String goodsNo);

    /**
     * 获取原辅料采购 已办列表
     *
     * @param type 服务器接口类型
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_PURCHASE_LIST)
    Single<BaseResponse<MaterialList>> getMaterialDoneList(@Field("type") String type,
                                                           @Field("sapOrderNo") String sapOrderNo,
                                                           @Field("startDate") String startDate,
                                                           @Field("endDate") String endDate);

    /**
     * 获取原辅料采购 已办列表详情
     *
     * @param orderId
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_PURCHASE_LIST_INFO)
    Single<BaseResponse<MaterialDetails>> getMaterialDoneListDetail(@Field("orderId") String orderId);

    /**
     * 原辅料采购冲销入库(已办保存)
     *
     * @param body body
     * @return Single
     */
    @POST(ApiNameConstant.PURCHASE_IN_RECALL_OUT)
    Single<ResponseInfo> purchaseInRecallOut(@Body RequestBody body);

    /**--------------------------------- 领料出库 ----------------------------------------*/

    /**
     * 领料出库 - 待办 列表
     *
     * @param sign S  生产订单   Y预留单号
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PICK_OUT_TODO_LIST)
    Single<BaseResponse<MaterialList>> pickOutTodoList(@Field("sign") String sign,
                                                       @Field("sapOrderNo") String sapOrderNo,
                                                       @Field("startDate") String startDate,
                                                       @Field("endDate") String endDate);

    /**
     * 领料出库 - 待办 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PICK_OUT_TODO_LIST_INFO)
    Single<BaseResponse<MaterialDetails>> pickOutTodoListDetail(@Field("sign") String sign,
                                                                @Field("sapOrderNo") String sapOrderNo,
                                                                @Field("sapFirmNo") String sapFirmNo,
                                                                @Field("orderTime") String orderTime);

    /**
     * 领料出库 - 待办详情 保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.PICK_OUT_TODO_LIST_SAVE)
    Single<ResponseInfo> pickOutTodoSave(@Body RequestBody body);

    /**
     * 领料出库 - 已办 列表
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PICK_OUT_DONE_LIST)
    Single<BaseResponse<MaterialList>> pickOutDoneList(@Field("type") String type,
                                                       @Field("sapOrderNo") String sapOrderNo,
                                                       @Field("startDate") String startDate,
                                                       @Field("endDate") String endDate);

    /**
     * 领料出库 - 已办详情
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PICK_OUT_DONE_LIST_INFO)
    Single<BaseResponse<MaterialDetails>> pickOutDoneListDetail(@Field("orderId") String orderId);


    /**
     * 领料出库 - 已办详情 保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.PICK_OUT_DONE_LIST_SAVE)
    Single<ResponseInfo> pickOutDoneSave(@Body RequestBody body);

    /**--------------------------------- 采购退货 ----------------------------------------*/

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
    Single<BaseResponse<MaterialList>> getBackTodoList(@Field("sapOrderNo") String sapOrderNo,
                                                       @Field("startDate") String startDate,
                                                       @Field("endDate") String endDate);

    /**
     * 采购退货 待办列表 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_BACK_GOODS_TODO_LIST_INFO)
    Single<BaseResponse<MaterialDetails>> getBackTodoListInfo(@Field("sapOrderNo") String sapOrderNo,
                                                              @Field("sapFirmNo") String sapFirmNo,
                                                              @Field("supplierNo") String supplierNo);

    /**
     * 采购退货 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_BACK_GOODS_TODO_SAVE)
    Single<ResponseInfo> getBackTodoSave(@Body RequestBody body);


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
    Single<BaseResponse<MaterialList>> getBackDoneList(@Field("type") String type,
                                                       @Field("sapOrderNo") String sapOrderNo,
                                                       @Field("startDate") String startDate,
                                                       @Field("endDate") String endDate);

    /**
     * 采购退货 已办详情
     *
     * @param orderId orderId
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_BACK_GOODS_DONE_LIST_INFO)
    Single<BaseResponse<MaterialDetails>> getBackDoneListInfo(@Field("orderId") String orderId);

    /**
     * 采购退货 已办保存
     *
     * @param body body
     * @return Single
     */
    @POST(ApiNameConstant.GET_BACK_GOODS_DONE_SAVE)
    Single<ResponseInfo> getBackDoneSave(@Body RequestBody body);

    /**----------------------------------------------------------------------------------*/
    /**--------------------------------- 成品库存 ----------------------------------------*/
    /**----------------------------------------------------------------------------------*/

    /**--------------------------------- 外采入库 ----------------------------------------*/

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
    Single<BaseResponse<ProductList>> getBuyInOrderList(@Field("sapOrderNo") String sapOrderNo,
                                                        @Field("startDate") String startDate,
                                                        @Field("endDate") String endDate);

    /**
     * 外采入库 待办详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_GOODS_PURCHASE_ORDER_INFO)
    Single<BaseResponse<ProductDetails>> getBuyInOrderInfo(@Field("sapOrderNo") String sapOrderNo,
                                                           @Field("sapFirmNo") String sapFirmNo,
                                                           @Field("supplierNo") String supplierNo);


    /**
     * 外采入库 待办保存
     *
     * @param body body
     * @return Single
     */
    @POST(ApiNameConstant.GET_PURCHASE_TODO_SAVE)
    Single<ResponseInfo> getBuyInTodoSave(@Body RequestBody body);


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
    Single<BaseResponse<ProductList>> getBuyInDoneList(@Field("type") String type,
                                                       @Field("sapOrderNo") String sapOrderNo,
                                                       @Field("startDate") String startDate,
                                                       @Field("endDate") String endDate);

    /**
     * 外采入库 已办列表 详情
     *
     * @param orderId orderId
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PURCHASE_DONE_LIST_INFO)
    Single<BaseResponse<ProductDetails>> getBuyInDoneListInfo(@Field("orderId") String orderId);

    /**
     * 采购退货 已办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_PURCHASE_DONE_SAVE)
    Single<ResponseInfo> getBuyInDoneSave(@Body RequestBody body);


    /**--------------------------------- 生产入库 ----------------------------------------*/

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
    Single<BaseResponse<ProductList>> getProduceOrderList(@Field("sapOrderNo") String sapOrderNo,
                                                          @Field("startDate") String startDate,
                                                          @Field("endDate") String endDate);

    /**
     * 生产入库 待办详情
     *
     * @param sign       生产/预留 单
     * @param sapOrderNo sapNo
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PRODUCE_ORDER_INFO)
    Single<BaseResponse<ProductDetails>> getProduceOrderInfo(@Field("sign") String sign,
                                                             @Field("sapOrderNo") String sapOrderNo);


    /**
     * 生产入库 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_PRODUCE_TODO_SAVE)
    Single<ResponseInfo> getProduceTodoSave(@Body RequestBody body);


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
    Single<BaseResponse<ProductList>> getProduceDoneList(@Field("type") String type,
                                                         @Field("sapOrderNo") String sapOrderNo,
                                                         @Field("startDate") String startDate,
                                                         @Field("endDate") String endDate);

    /**
     * 生产入库 已办列表 详情
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PRODUCE_DONE_LIST_INFO)
    Single<BaseResponse<ProductDetails>> getProduceDoneInfo(@Field("orderId") String orderId);

    /**
     * 生产入库 已办保存
     *
     * @param body body
     * @return Single
     */
    @POST(ApiNameConstant.GET_PRODUCE_DONE_SAVE)
    Single<ResponseInfo> getProduceDoneSave(@Body RequestBody body);


    /**--------------------------------- 发货出库 ----------------------------------------*/

    /**
     * 发货出库 待办列表
     *
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_SHIP_ORDER_LIST)
    Single<BaseResponse<ProductList>> getShipOrderList(@Field("sapOrderNo") String sapOrderNo,
                                                       @Field("startDate") String startDate,
                                                       @Field("endDate") String endDate);

    /**
     * 发货出库 待办详情
     *
     * @param sapOrderNo
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_SHIP_ORDER_INFO)
    Single<BaseResponse<ProductDetails>> getShipOrderInfo(@Field("sapOrderNo") String sapOrderNo);


    /**
     * 发货出库 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_SHIP_TODO_SAVE)
    Single<ResponseInfo> getShipTodoSave(@Body RequestBody body);


    /**
     * 发货出库 已办列表
     *
     * @param type type
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_SHIP_DONE_LIST)
    Single<BaseResponse<ProductList>> getShipDoneList(@Field("type") String type,
                                                      @Field("sapOrderNo") String sapOrderNo,
                                                      @Field("startDate") String startDate,
                                                      @Field("endDate") String endDate);

    /**
     * 发货出库 已办列表 详情
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_SHIP_DONE_LIST_INFO)
    Single<BaseResponse<ProductDetails>> getShipDoneListInfo(@Field("orderId") String orderId);

    /**
     * 发货出库 已办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_SHIP_DONE_SAVE)
    Single<ResponseInfo> getShipDoneSave(@Body RequestBody body);


    /**--------------------------------- 退货入库 ----------------------------------------*/
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
    Single<BaseResponse<ProductList>> getReturnOrderList(@Field("sapOrderNo") String sapOrderNo,
                                                         @Field("startDate") String startDate,
                                                         @Field("endDate") String endDate);

    /**
     * 退货入库 待办详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_RETURN_ORDER_INFO)
    Single<BaseResponse<ProductDetails>> getReturnOrderInfo(@Field("sapOrderNo") String sapOrderNo);


    /**
     * 退货入库 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_RETURN_TODO_SAVE)
    Single<ResponseInfo> getReturnTodoSave(@Body RequestBody body);


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
    Single<BaseResponse<ProductList>> getReturnDoneList(@Field("type") String type,
                                                        @Field("sapOrderNo") String sapOrderNo,
                                                        @Field("startDate") String startDate,
                                                        @Field("endDate") String endDate);

    /**
     * 退货入库 已办列表 详情
     *
     * @param orderId orderId
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_RETURN_DONE_LIST_INFO)
    Single<BaseResponse<ProductDetails>> getReturnDoneListInfo(@Field("orderId") String orderId);

    /**
     * 退货入库 已办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_RETURN_DONE_SAVE)
    Single<ResponseInfo> getReturnDoneSave(@Body RequestBody body);


    /**--------------------------------- End ----------------------------------------*/

    /**----------------------------------------------------------------------------------*/
    /**--------------------------------- 系统 --------------------------------------------*/
    /**----------------------------------------------------------------------------------*/

    /**
     * --------------------------------- 溯源 --------------------------------------------
     */

    /**
     * 成品查询
     *
     * @param url URL
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PRODUCT_QUERY)
    Single<BaseResponse<ProductQuery>> getProductQuery(@Field("url") String url);

    /**
     * 库位码查询
     *
     * @param positionNo positionNo
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.POSITION_QUERY)
    Single<BaseResponse<PositionCode>> getPositionQuery(@Field("positionNo") String positionNo);


    /**--------------------------------- 质检 --------------------------------------------*/

    /**
     * 质检待办 列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_CHECK_TODO_LIST)
    Single<BaseResponse<QualityCheckTodoList>> getCheckTodoList(@Field("state") String state);

    /**
     * 质检待办 详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_CHECK_TODO_INFO)
    Single<BaseResponse<QualityCheckTodoDetails>> getCheckTodoInfo(@Field("arrivalOrderNo") String arrivalOrderNo);

    /**
     * 质检待办 保存
     *
     * @return
     */
    @POST(ApiNameConstant.GET_CHECK_TODO_SAVE)
    Single<ResponseInfo> getCheckTodoSave(@Body RequestBody body);

    /**
     * 质检已办 列表
     *
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_CHECK_DONE_LIST)
    Single<BaseResponse<MaterialList>> getCheckDoneList(@Field("state") String state,
                                                        @Field("sapOrderNo") String sapOrderNo,
                                                        @Field("startDate") String startDate,
                                                        @Field("endDate") String endDate);
    //Single<BaseResponse<QualityCheckTodoList>> getCheckDoneList(@Field("state") String state);

    /**
     * 质检已办 详情
     *
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_CHECK_DONE_INFO)
    Single<BaseResponse<CheckDoneDetailEvent>> getCheckDoneInfo(@Field("id") String id);

    /**
     * 质检已办 保存
     *
     * @return
     */
    @POST(ApiNameConstant.GET_CHECK_DONE_SAVE)
    Single<ResponseInfo> getCheckDoneSave(@Body RequestBody body);

    /*--------------------------------- 盘点 --------------------------------------------*/

    /**
     * 原辅料盘点
     *
     * @param positionNo 库位码
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.MATERIAL_POSITION_STOCK)
    Single<BaseResponse<PositionCode>> positionStock(@Field("positionNo") String positionNo);

    /**
     * 原辅料盘点提交
     *
     * @param body body
     * @return Single
     */
    @POST(ApiNameConstant.MATERIAL_POSITION_STOCK_SAVE)
    Single<ResponseInfo> positionStockSave(@Body RequestBody body);

    /**
     * 成品盘点 列表
     *
     * @return Single
     */
    @GET(ApiNameConstant.GET_GOODS_LIST)
    Single<BaseResponse<ProductInventorList>> getGoodsList(@Query("pageSize") int pageSize,
                                                                    @Query("goodsNo") String goodsNo,
                                                                    @Query("pageNumber") int pageNumber);

    /**
     * 成品盘点 goodsNo
     *
     * @return Single
     */
    @GET(ApiNameConstant.GET_GOODS_LIST)
    Single<String> getGoodsNo(@Query("goodsNo") String goodsNo);


    /**
     * 盘点列表
     *
     * @param type 1产品盘点单 2原辅料盘点单
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_CHECK_LIST)
    Single<BaseResponse<ReceiptList>> getCheckList(@Field("type") String type);

    /**
     * 成品盘点 提交
     * @param body body
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GOODS_INVENTORY)
    Single<ResponseInfo> goodsInventory(@Body RequestBody body);

    /**
     * 删除盘点记录
     *
     * @param id         id
     * @param sapCheckNo sapCheckNo
     * @return Single
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.DELETE_CHECK)
    Single<ResponseInfo> deleteCheck(@Field("id") String id, @Field("sapCheckNo") String sapCheckNo);

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
    Single<ImageData> updateCheckImage1(@Part("file") RequestBody description,
                                        @Part MultipartBody.Part part);

    /**
     * @param part MultipartBody.Part
     * @return Single
     */
    @Multipart
    @POST(ApiNameConstant.POST_USER_QUA_CHECK_IMAGE)
    Single<ImageData> updateCheckImage(@Part MultipartBody.Part part);

}
