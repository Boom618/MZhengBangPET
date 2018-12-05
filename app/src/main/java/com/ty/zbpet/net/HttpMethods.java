package com.ty.zbpet.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.GoodsPurchaseOrderInfo;
import com.ty.zbpet.bean.GoodsPurchaseOrderList;
import com.ty.zbpet.bean.MaterialDoneDetailsData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.WarehouseInfo;
import com.ty.zbpet.bean.material.MaterialDetailsIn;
import com.ty.zbpet.bean.material.MaterialDetailsOut;
import com.ty.zbpet.bean.material.MaterialDoneList;
import com.ty.zbpet.bean.material.MaterialTodoList;
import com.ty.zbpet.bean.product.ProductDetailsIn;
import com.ty.zbpet.bean.product.ProductDetailsOut;
import com.ty.zbpet.bean.product.ProductDoneList;
import com.ty.zbpet.bean.product.ProductTodoDetails;
import com.ty.zbpet.bean.product.ProductTodoList;
import com.ty.zbpet.constant.ApiNameConstant;
import com.ty.zbpet.net.gson.DoubleDefault0Adapter;
import com.ty.zbpet.net.gson.IntegerDefault0Adapter;
import com.ty.zbpet.net.gson.LongDefault0Adapter;
import com.ty.zbpet.net.gson.StringDefault0Adapter;
import com.ty.zbpet.ui.base.BaseResponse;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * description:
 * author: XingZheng
 * date: 2016/11/22.
 *
 * @author TY
 */
public class HttpMethods {

    /**
     * 默认超时时间
     */
    private static final int DEFAULT_TIMEOUT = 20;
    private Retrofit mRetrofit;
    private ApiService mService;
    private static Gson gson;
    private String baseUrl = ApiNameConstant.BASE_URL;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private HttpMethods() {
        init(baseUrl);
    }

    public HttpMethods(String url) {
        init(url);
    }

    private void init(String url) {
        //创建OKHttpClient
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                // 日志拦截器
                .addInterceptor(new LogInterceptor());

        mRetrofit = new Retrofit.Builder()
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(url)
                .build();
        mService = mRetrofit.create(ApiService.class);
    }

    //构建单例
    public static class SingletonHolder {
        static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**--------------------------------- 到货入库 ----------------------------------------*/

    /**
     * 获取原辅料采购待办列表
     *
     * @param subscriber
     */
    public void getMaterialTodoList(SingleObserver<BaseResponse<MaterialTodoList>> subscriber) {
        mService.getMaterialTodoList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取原辅料采购 待办 详情
     *
     * @param subscriber
     */
    public void getMaterialTodoListDetail(SingleObserver<BaseResponse<MaterialDetailsIn>> subscriber, String sapOrderNo) {
        mService.getMaterialTodoListDetail(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 待办 保存
     *
     * @param subscriber
     * @param body
     */
    public void materialTodoInSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.materialPurchaseInSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 库位码校验
     */
    public void checkCarCode(SingleObserver<CarPositionNoData> subscriber, String positionNo) {
        mService.checkCarCode(positionNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 已办列表
     *
     * @param subscriber
     */
    public void getMaterialDoneList(SingleObserver<BaseResponse<MaterialDoneList>> subscriber, String type) {
        mService.getMaterialDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 已办详情
     *
     * @param subscriber
     * @param id
     */
    public void getMaterialDoneListDetail(SingleObserver<BaseResponse<MaterialDoneDetailsData>> subscriber, String id) {
        mService.getMaterialDoneListDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 原辅料到货冲销入库 已办 保存
     *
     * @param subscriber
     * @param body
     */
    public void materialDoneInSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.purchaseInRecallOut(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**--------------------------------- 领料出库 ----------------------------------------*/

    /**
     * 领料出库 待办列表
     *
     * @param subscriber
     */
    public void pickOutTodoList(SingleObserver<BaseResponse<MaterialTodoList>> subscriber) {
        mService.pickOutTodoList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 领料出库 待办详情
     *
     * @param subscriber
     * @param sapOrderNo
     */
    public void pickOutTodoListDetails(SingleObserver<BaseResponse<MaterialDetailsIn>> subscriber, String sapOrderNo) {
        mService.pickOutTodoListDetail(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 领料出库 待办详情 保存
     *
     * @param subscriber
     */
    public void pickOutTodoSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.pickOutTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 领料出库 已办列表
     *
     * @param subscriber
     */
    public void pickOutDoneList(SingleObserver<BaseResponse<MaterialDoneList>> subscriber, String type) {
        mService.pickOutDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 领料出库 已办详情
     *
     * @param subscriber
     * @param sapOrderNo
     */
    public void pickOutDoneListDetails(SingleObserver<BaseResponse<MaterialDetailsOut>> subscriber, String sapOrderNo) {
        mService.pickOutDoneListDetail(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 领料出库 已办详情 保存
     *
     * @param subscriber
     */
    public void pickOutDoneSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.pickOutDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**--------------------------------- 采购退货 ----------------------------------------*/


    /**
     * 采购退货 待办列表
     *
     * @param subscriber
     */
    public void getBackTodoList(BaseSubscriber<BaseResponse<MaterialTodoList>> subscriber) {
        mService.getBackTodoList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 采购退货 待办列表 详情
     *
     * @param subscriber
     */
    public void getBackTodoListInfo(BaseSubscriber<BaseResponse<MaterialDetailsIn>> subscriber, String sapOrderNo) {
        mService.getBackTodoListInfo(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 待办保存
     *
     * @param subscriber
     */
    public void getBackTodoSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.getBackTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 已办列表
     *
     * @param subscriber
     */
    public void getBackDoneList(BaseSubscriber<BaseResponse<MaterialDoneList>> subscriber, String type) {
        mService.getBackDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 采购退货 已办列表 详情
     *
     * @param subscriber
     */
    public void getBackDoneListInfo(BaseSubscriber<BaseResponse<MaterialDetailsOut>> subscriber, String sapOrderNo) {
        mService.getBackDoneListInfo(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 已办保存
     *
     * @param subscriber
     */
    public void getBackDoneSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.getBackDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**----------------------------------------------------------------------------------*/
    /**--------------------------------- 成品库存 ----------------------------------------*/
    /**----------------------------------------------------------------------------------*/

    /**--------------------------------- 外采入库 ----------------------------------------*/
    /**
     * 外采入库 待办列表
     *
     * @param subscriber
     */
    public void getBuyInOrderList(SingleObserver<BaseResponse<ProductTodoList>> subscriber) {
        mService.getBuyInOrderList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 待办详情
     *
     * @param subscriber
     */
    public void getBuyInOrderInfo(SingleObserver<BaseResponse<ProductDetailsIn>> subscriber, String sapOrderNo) {
        mService.getBuyInOrderInfo(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 待办保存
     *
     * @param subscriber
     */
    public void getBuyInTodoSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.getBuyInTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 已办列表
     *
     * @param subscriber
     */
    public void getBuyInDoneList(SingleObserver<BaseResponse<ProductDoneList>> subscriber, String type) {
        mService.getBuyInDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 外采入库 已办详情
     *
     * @param subscriber
     */
    public void getBuyInDoneListInfo(SingleObserver<BaseResponse<ProductDetailsOut>> subscriber, String orderId) {
        mService.getBuyInDoneListInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 已办保存
     *
     * @param subscriber
     */
    public void getBuyInDoneSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.getBuyInDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**--------------------------------- 生产入库 ----------------------------------------*/

    /**
     * 生产入库 待办列表
     *
     * @param subscriber
     */
    public void getProductTodoList(SingleObserver<BaseResponse<ProductTodoList>> subscriber) {
        mService.getProduceOrderList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 待办详情
     *
     * @param subscriber
     */
    public void getProduceOrderInfo(SingleObserver<BaseResponse<ProductTodoDetails>> subscriber, String sapOrderNo) {
        mService.getProduceOrderInfo(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 待办保存
     *
     * @param subscriber
     */
    public void getProduceTodoSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.getProduceTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 已办列表
     *
     * @param subscriber
     */
    public void getProduceDoneList(SingleObserver<BaseResponse<ProductDoneList>> subscriber, String type) {
        mService.getProduceDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 生产入库 已办详情
     *
     * @param subscriber
     */
    public void getProduceDoneInfo(SingleObserver<BaseResponse<ProductDetailsOut>> subscriber, String orderId) {
        mService.getProduceDoneInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 已办保存
     *
     * @param subscriber
     */
    public void getProduceDoneSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.getProduceDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**--------------------------------- 发货出库 ----------------------------------------*/

    /**
     * 发货出库 已办列表
     *
     * @param subscriber
     */
    public void getShipOrderList(SingleObserver<BaseResponse<ProductTodoList>> subscriber) {
        mService.getShipOrderList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发货出库 待办详情
     *
     * @param subscriber
     */
    public void getShipOrderInfo(SingleObserver<BaseResponse<ProductTodoDetails>> subscriber, String sapOrderNo) {
        mService.getShipOrderInfo(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发货出库 待办保存
     *
     * @param subscriber
     */
    public void getShipTodoSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getShipTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发货出库 已办列表
     *
     * @param subscriber
     */
    public void getShipDoneList(SingleObserver<BaseResponse<ProductDoneList>> subscriber, String type) {
        mService.getShipDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 发货出库 已办详情
     *
     * @param subscriber
     */
    public void getShipDoneListInfo(SingleObserver<BaseResponse<ProductDetailsOut>> subscriber, String sapOrderNo) {
        mService.getShipDoneListInfo(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发货出库 已办保存
     *
     * @param subscriber
     */
    public void getShipDoneSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.getShipDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**--------------------------------- 退货入库 ----------------------------------------*/

    /**
     * 退货入库 已办列表
     *
     * @param subscriber
     */
    public void getReturnOrderList(BaseSubscriber<GoodsPurchaseOrderList> subscriber) {
        mService.getReturnOrderList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 待办详情
     *
     * @param subscriber
     */
    public void getReturnOrderInfo(BaseSubscriber<GoodsPurchaseOrderInfo> subscriber, String sapOrderNo) {
        mService.getReturnOrderInfo(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 待办保存
     *
     * @param subscriber
     */
    public void getReturnTodoSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.getReturnTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 已办列表
     *
     * @param subscriber
     */
    public void getReturnDoneList(SingleObserver<BaseResponse<MaterialDoneList>> subscriber, String type) {
        mService.getReturnDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 退货入库 已办详情
     *
     * @param subscriber
     */
    public void getReturnDoneListInfo(BaseSubscriber<BaseResponse<MaterialDoneDetailsData>> subscriber, String orderId) {
        mService.getReturnDoneListInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 已办保存
     *
     * @param subscriber
     */
    public void getReturnDoneSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.getReturnDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**--------------------------------- End ----------------------------------------*/

    /**
     * 获取仓库信息
     *
     * @param subscriber
     */
    public void getWarehouseList(BaseSubscriber<WarehouseInfo> subscriber) {
        mService.getWarehouseList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 成品采购入库——待办
     *
     * @param
     */
    public void doGoodsPurchaseInStorage(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.doGoodsPurchaseInStorage(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(String.class, new StringDefault0Adapter())
                    .create();
        }
        return gson;
    }

}
