package com.ty.zbpet.net;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.UserInfo;
import com.ty.zbpet.bean.material.MaterialDetails;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.bean.product.ProductDetails;
import com.ty.zbpet.bean.product.ProductList;
import com.ty.zbpet.bean.system.ImageData;
import com.ty.zbpet.bean.system.QualityCheckTodoDetails;
import com.ty.zbpet.bean.system.QualityCheckTodoList;
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
    @POST(ApiNameConstant.GET_MATERIAL_IN_WAREHOUSE_ORDER_LIST)
    Single<BaseResponse<MaterialList>> getMaterialTodoList();

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
     * @param positionNo
     * @return
     */
    @GET(ApiNameConstant.CHECK_CAR_CODE)
    Single<CarPositionNoData> checkCarCode(@Query("positionNo") String positionNo);

    /**
     * 获取原辅料采购 已办列表
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_PURCHASE_LIST)
    Single<BaseResponse<MaterialList>> getMaterialDoneList(@Field("type") String type);

    /**
     * 获取原辅料采购 已办列表详情
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_PURCHASE_LIST_INFO)
    Single<BaseResponse<MaterialDetails>> getMaterialDoneListDetail(@Field("orderId") String orderId);

    /**
     * 原辅料采购冲销入库(已办保存)
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.PURCHASE_IN_RECALL_OUT)
    Single<ResponseInfo> purchaseInRecallOut(@Body RequestBody body);

    /**--------------------------------- 领料出库 ----------------------------------------*/

    /**
     * 领料出库 - 待办 列表
     *
     * @return
     */
    @POST(ApiNameConstant.PICK_OUT_TODO_LIST)
    Single<BaseResponse<MaterialList>> pickOutTodoList();

    /**
     * 领料出库 - 待办 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PICK_OUT_TODO_LIST_INFO)
    Single<BaseResponse<MaterialDetails>> pickOutTodoListDetail(@Field("sapOrderNo") String sapOrderNo,
                                                                @Field("sapFirmNo") String sapFirmNo);

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
    Single<BaseResponse<MaterialList>> pickOutDoneList(@Field("type") String type);

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
     * @return
     */
    @POST(ApiNameConstant.GET_BACK_GOODS_TODO_LIST)
    Single<BaseResponse<MaterialList>> getBackTodoList();

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
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_BACK_GOODS_DONE_LIST)
    Single<BaseResponse<MaterialList>> getBackDoneList(@Field("type") String type);

    /**
     * 采购退货 已办详情
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_BACK_GOODS_DONE_LIST_INFO)
    Single<BaseResponse<MaterialDetails>> getBackDoneListInfo(@Field("orderId") String orderId);

    /**
     * 采购退货 已办保存
     *
     * @param body
     * @return
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
     * @return
     */
    @POST(ApiNameConstant.GET_GOODS_PURCHASE_ORDER_LIST)
    Single<BaseResponse<ProductList>> getBuyInOrderList();

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
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_PURCHASE_TODO_SAVE)
    Single<ResponseInfo> getBuyInTodoSave(@Body RequestBody body);


    /**
     * 外采入库 已办列表
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PURCHASE_DONE_LIST)
    Single<BaseResponse<ProductList>> getBuyInDoneList(@Field("type") String type);

    /**
     * 外采入库 已办列表 详情
     *
     * @param orderId
     * @return
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
     * @return
     */
    @POST(ApiNameConstant.GET_PRODUCE_ORDER_LIST)
    Single<BaseResponse<ProductList>> getProduceOrderList();

    /**
     * 生产入库 待办详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PRODUCE_ORDER_INFO)
    Single<BaseResponse<ProductDetails>> getProduceOrderInfo(@Field("sapOrderNo") String sapOrderNo);


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
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PRODUCE_DONE_LIST)
    Single<BaseResponse<ProductList>> getProduceDoneList(@Field("type") String type);

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
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_PRODUCE_DONE_SAVE)
    Single<ResponseInfo> getProduceDoneSave(@Body RequestBody body);


    /**--------------------------------- 发货出库 ----------------------------------------*/

    /**
     * 发货出库 待办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_SHIP_ORDER_LIST)
    Single<BaseResponse<ProductList>> getShipOrderList();

    /**
     * 发货出库 待办详情
     *
     * @param sapOrderNo
     * @return
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
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_SHIP_DONE_LIST)
    Single<BaseResponse<ProductList>> getShipDoneList(@Field("type") String type);

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
     * @return
     */
    @POST(ApiNameConstant.GET_RETURN_ORDER_LIST)
    Single<BaseResponse<ProductList>> getReturnOrderList();

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
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_RETURN_DONE_LIST)
    Single<BaseResponse<ProductList>> getReturnDoneList(@Field("type") String type);

    /**
     * 退货入库 已办列表 详情
     *
     * @param orderId
     * @return
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
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_CHECK_DONE_LIST)
    Single<BaseResponse<QualityCheckTodoList>> getCheckDoneList(@Field("state") String state);

    /**
     * 质检已办 详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_CHECK_DONE_INFO)
    Single<BaseResponse<QualityCheckTodoList>> getCheckDoneInfo(@Field("arrivalOrderNo") String arrivalOrderNo);

    /**
     * 质检已办 保存
     *
     * @return
     */
    @POST(ApiNameConstant.GET_CHECK_DONE_SAVE)
    Single<ResponseInfo> getCheckDoneSave(@Body RequestBody body);

    /**--------------------------------- 图片 --------------------------------------------*/

    /**
     * 质检 待办 上传图片
     *
     * @param description
     * @param part
     * @return
     */
    @Multipart
    @POST(ApiNameConstant.POST_USER_QUA_CHECK_IMAGE)
    Single<ImageData> updateCheckImage1(@Part("file") RequestBody description,
                                        @Part MultipartBody.Part part);

    @Multipart
    @POST(ApiNameConstant.POST_USER_QUA_CHECK_IMAGE)
    Single<ImageData> updateCheckImage(@Part MultipartBody.Part part);

}
