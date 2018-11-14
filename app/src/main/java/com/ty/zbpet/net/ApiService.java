package com.ty.zbpet.net;

import com.ty.zbpet.bean.GoodsPurchaseOrderInfo;
import com.ty.zbpet.bean.GoodsPurchaseOrderList;
import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.bean.MaterialDetailsData;
import com.ty.zbpet.bean.MaterialDoneData;
import com.ty.zbpet.bean.PickOutDetailInfo;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.WarehouseInfo;
import com.ty.zbpet.constant.ApiNameConstant;
import com.ty.zbpet.ui.base.BaseResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author TY
 */
public interface ApiService {


    /**
     * 获取原辅料采购 待办 列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_MATERIAL_INWAREHOUSE_ORDERLIST)
    Observable<BaseResponse<MaterialData>> getMaterialOrderList();

    /**
     * 获取原辅料采购 待办 详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_MATERIAL_INWAREHOUSE_ORDERINFO)
    Observable<BaseResponse<MaterialDetailsData>> getMaterialInWarehouseOrderInfo(@Field("sapOrderNo") String sapOrderNo);

    /**
     * 待办 保存
     *
     * @param body
     * @return
     */
    @POST(ApiNameConstant.PURCHASE_IN)
    Observable<ResponseInfo> materialPurchaseInSave(@Body RequestBody body);


    /**
     * 车库码检验
     *
     * @param materialId
     * @param carCode
     * @return
     */
    @POST(ApiNameConstant.CHECK_CAR_CODE)
    Observable<ResponseInfo> checkCarCode(@Field("materialId") String materialId,
                                          @Field("carCode") String carCode);

    /**
     * 获取原辅料采购 已办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_MATERIAL_PURCHASE_LIST)
    Observable<BaseResponse<MaterialDoneData>> getMaterialInWarehouseOrderList();

    /**
     * 获取原辅料采购 已办列表详情
     *
     * @return
     */
    @POST(ApiNameConstant.GET_MATERIAL_PURCHASE_LIST_INFO)
    Observable<BaseResponse<MaterialData>> getMaterialInWarehouseOrderListInfo(@Field("mInWarehouseOrderId") String id);

    /**
     * 原辅料采购冲销入库
     *
     * @param
     * @return
     */
    @POST(ApiNameConstant.PURCHASE_IN_RECALL_OUT)
    Observable<ResponseInfo> purchaseInRecallOut(@Body RequestBody body);

    /**
     * 原料--- 领料出库详情
     *
     * @return
     */
    @POST(ApiNameConstant.PICK_OUT_DETAIL_INFO)
    Observable<BaseResponse<PickOutDetailInfo>> pickOutDetailInfo();

    /**
     * 获取成品采购待办列表
     *
     * @return
     */
    @POST(ApiNameConstant.GET_GOODS_PURCHASE_ORDERLIST)
    Observable<GoodsPurchaseOrderList> getGoodsPurchaseOrderList();

    /**
     * 获取成品采购待办详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST(ApiNameConstant.GET_GOODS_PURCHASE_ORDERINFO)
    Observable<GoodsPurchaseOrderInfo> getGoodsPurchaseOrderInfo(@Field("sapOrderNo") String sapOrderNo);

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
