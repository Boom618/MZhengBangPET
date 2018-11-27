package com.ty.zbpet.net;

import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.GoodsPurchaseOrderInfo;
import com.ty.zbpet.bean.GoodsPurchaseOrderList;
import com.ty.zbpet.bean.MaterialDoneDetailsData;
import com.ty.zbpet.bean.MaterialTodoData;
import com.ty.zbpet.bean.MaterialTodoDetailsData;
import com.ty.zbpet.bean.PickOutDoneData;
import com.ty.zbpet.bean.PickOutDoneDetailsData;
import com.ty.zbpet.bean.PickOutTodoDetailsData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.WarehouseInfo;
import com.ty.zbpet.bean.material.MaterialDetailsIn;
import com.ty.zbpet.bean.material.MaterialDetailsOut;
import com.ty.zbpet.bean.material.MaterialDoneList;
import com.ty.zbpet.bean.material.MaterialTodoList;
import com.ty.zbpet.constant.ApiNameConstant;
import com.ty.zbpet.ui.base.BaseResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author TY
 */
public interface ApiService {


    /**--------------------------------- 到货入库 ----------------------------------------*/

    /**
     * 获取原辅料采购 待办 列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_MATERIAL_IN_WAREHOUSE_ORDER_LIST)
    Observable<BaseResponse<MaterialTodoData>> getMaterialTodoList();

    /**
     * 获取原辅料采购 待办 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_IN_WAREHOUSE_ORDER_INFO)
    Observable<BaseResponse<MaterialTodoDetailsData>> getMaterialTodoListDetail(@Field("sapOrderNo") String sapOrderNo);

    /**
     * 待办 保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.PURCHASE_IN)
    Observable<ResponseInfo> materialPurchaseInSave(@Body RequestBody body);


    /**
     * 库位码检验
     *
     * @param positionNo
     * @return
     */
    @GET(ApiNameConstant.CHECK_CAR_CODE)
    Observable<CarPositionNoData> checkCarCode(@Query("positionNo") String positionNo);

    /**
     * 获取原辅料采购 已办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_MATERIAL_PURCHASE_LIST)
    Observable<BaseResponse<MaterialDoneList>> getMaterialDoneList();

    /**
     * 获取原辅料采购 已办列表详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_PURCHASE_LIST_INFO)
    Observable<BaseResponse<MaterialDoneDetailsData>> getMaterialDoneListDetail(@Field("mInWarehouseOrderId") String id);

    /**
     * 原辅料采购冲销入库(已办保存)
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.PURCHASE_IN_RECALL_OUT)
    Observable<ResponseInfo> purchaseInRecallOut(@Body RequestBody body);

    /**--------------------------------- 领料出库 ----------------------------------------*/

    /**
     * 领料出库 - 待办 列表
     *
     * @return
     */
    @POST(ApiNameConstant.PICK_OUT_TODO_LIST)
    Observable<BaseResponse<MaterialTodoList>> pickOutTodoList();

    /**
     * 领料出库 - 待办 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PICK_OUT_TODO_LIST_INFO)
    Observable<BaseResponse<PickOutTodoDetailsData>> pickOutTodoListDetail(@Field("sapOrderNo") String sapOrderNo);

    /**
     * 领料出库 - 待办详情 保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.PICK_OUT_TODO_LIST_SAVE)
    Observable<ResponseInfo> pickOutTodoSave(@Body RequestBody body);

    /**
     * 领料出库 - 已办 列表
     *
     * @return
     */
    @POST(ApiNameConstant.PICK_OUT_DONE_LIST)
    Observable<BaseResponse<PickOutDoneData>> pickOutDoneList();

    /**
     * 领料出库 - 已办列表 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.PICK_OUT_DONE_LIST_INFO)
    Observable<BaseResponse<PickOutDoneDetailsData>> pickOutDoneListDetail(@Field("sapOrderNo") String sapOrderNo);


    /**
     * 领料出库 - 已办详情 保存
     *
     * @return
     */
    @POST(ApiNameConstant.PICK_OUT_DONE_LIST_SAVE)
    Observable<ResponseInfo> pickOutDoneSave();

    /**--------------------------------- 采购退货 ----------------------------------------*/

    /**
     * 采购退货 待办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_BACK_GOODS_TODO_LIST)
    Observable<BaseResponse<MaterialTodoList>> getBackTodoList();

    /**
     * 采购退货 待办列表 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_BACK_GOODS_TODO_LIST_INFO)
    Observable<BaseResponse<MaterialDetailsIn>> getBackTodoListInfo(@Field("sapOrderNo") String sapOrderNo);

    /**
     * 采购退货 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_BACK_GOODS_TODO_SAVE)
    Observable<ResponseInfo> getBackTodoSave(@Body RequestBody body);


    /**
     * 采购退货 已办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_BACK_GOODS_DONE_LIST)
    Observable<BaseResponse<MaterialDoneList>> getBackDoneList();

    /**
     * 采购退货 已办详情
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_BACK_GOODS_DONE_LIST_INFO)
    Observable<BaseResponse<MaterialDetailsOut>> getBackDoneListInfo(@Field("mOutWarehouseOrderId") String orderId);

    /**
     * 采购退货 已办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_BACK_GOODS_DONE_SAVE)
    Observable<ResponseInfo> getBackDoneSave(@Body RequestBody body);

    /**----------------------------------------------------------------------------------*/
    /**--------------------------------- 成品库存 ----------------------------------------*/
    /**----------------------------------------------------------------------------------*/

    /**--------------------------------- 外采入库 ----------------------------------------*/

    /**
     * 待办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_GOODS_PURCHASE_ORDER_LIST)
    Observable<GoodsPurchaseOrderList> getGoodsPurchaseOrderList();

    /**
     * 待办详情
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_GOODS_PURCHASE_ORDER_INFO)
    Observable<GoodsPurchaseOrderInfo> getGoodsPurchaseOrderInfo(@Field("sapOrderNo") String sapOrderNo);


    /**
     * 采购退货 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_PURCHASE_TODO_SAVE)
    Observable<ResponseInfo> getPurchaseTodoSave(@Body RequestBody body);


    /**
     * 采购退货 已办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_PURCHASE_DONE_LIST)
    Observable<BaseResponse<MaterialDoneList>> getPurchaseDoneList();

    /**
     * 采购退货 已办列表 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PURCHASE_DONE_LIST_INFO)
    Observable<BaseResponse<PickOutDoneDetailsData>> getPurchaseDoneListInfo(@Field("sapOrderNo") String sapOrderNo);

    /**
     * 采购退货 已办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_PURCHASE_DONE_SAVE)
    Observable<ResponseInfo> getPurchaseDoneSave(@Body RequestBody body);


    /**--------------------------------- 生产入库 ----------------------------------------*/

    /**
     * 待办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_PRODUCE_ORDER_LIST)
    Observable<GoodsPurchaseOrderList> getProduceOrderList();

    /**
     * 待办详情
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PRODUCE_ORDER_INFO)
    Observable<GoodsPurchaseOrderInfo> getProduceOrderInfo(@Field("sapOrderNo") String sapOrderNo);


    /**
     * 采购退货 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_PRODUCE_TODO_SAVE)
    Observable<ResponseInfo> getProduceTodoSave(@Body RequestBody body);


    /**
     * 采购退货 已办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_PRODUCE_DONE_LIST)
    Observable<BaseResponse<MaterialDoneList>> getProduceDoneList();

    /**
     * 采购退货 已办列表 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_PRODUCE_DONE_LIST_INFO)
    Observable<BaseResponse<PickOutDoneDetailsData>> getProduceDoneListInfo(@Field("sapOrderNo") String sapOrderNo);

    /**
     * 采购退货 已办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_PRODUCE_DONE_SAVE)
    Observable<ResponseInfo> getProduceDoneSave(@Body RequestBody body);


    /**--------------------------------- 发货出库 ----------------------------------------*/

    /**
     * 待办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_SHIP_ORDER_LIST)
    Observable<GoodsPurchaseOrderList> getShipOrderList();

    /**
     * 待办详情
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_SHIP_ORDER_INFO)
    Observable<GoodsPurchaseOrderInfo> getShipOrderInfo(@Field("sapOrderNo") String sapOrderNo);


    /**
     * 采购退货 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_SHIP_TODO_SAVE)
    Observable<ResponseInfo> getShipTodoSave(@Body RequestBody body);


    /**
     * 采购退货 已办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_SHIP_DONE_LIST)
    Observable<BaseResponse<MaterialDoneList>> getShipDoneList();

    /**
     * 采购退货 已办列表 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_SHIP_DONE_LIST_INFO)
    Observable<BaseResponse<PickOutDoneDetailsData>> getShipDoneListInfo(@Field("sapOrderNo") String sapOrderNo);

    /**
     * 采购退货 已办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_SHIP_DONE_SAVE)
    Observable<ResponseInfo> getShipDoneSave(@Body RequestBody body);


    /**--------------------------------- 退货入库 ----------------------------------------*/
    /**
     * 待办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_RETURN_ORDER_LIST)
    Observable<GoodsPurchaseOrderList> getReturnOrderList();

    /**
     * 待办详情
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_RETURN_ORDER_INFO)
    Observable<GoodsPurchaseOrderInfo> getReturnOrderInfo(@Field("sapOrderNo") String sapOrderNo);


    /**
     * 采购退货 待办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_RETURN_TODO_SAVE)
    Observable<ResponseInfo> getReturnTodoSave(@Body RequestBody body);


    /**
     * 采购退货 已办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_RETURN_DONE_LIST)
    Observable<BaseResponse<MaterialDoneList>> getReturnDoneList();

    /**
     * 采购退货 已办列表 详情
     *
     * @param sapOrderNo
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_RETURN_DONE_LIST_INFO)
    Observable<BaseResponse<PickOutDoneDetailsData>> getReturnDoneListInfo(@Field("sapOrderNo") String sapOrderNo);

    /**
     * 采购退货 已办保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.GET_RETURN_DONE_SAVE)
    Observable<ResponseInfo> getReturnDoneSave(@Body RequestBody body);




    /**--------------------------------- End ----------------------------------------*/

    /**
     * 获取仓库信息
     *
     * @return
     */
    @GET(ApiNameConstant.GET_WAREHOUSE_INFO)
    Observable<WarehouseInfo> getWarehouseList();

    /**
     * 成品采购入库——待办
     *
     * @param
     * @return
     */
    @POST(ApiNameConstant.GOODS_PURCHASE_INSTORAGE)
    Observable<ResponseInfo> doGoodsPurchaseInStorage(@Body RequestBody body);

}
