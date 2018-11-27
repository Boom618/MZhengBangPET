package com.ty.zbpet.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import com.ty.zbpet.net.gson.DoubleDefault0Adapter;
import com.ty.zbpet.net.gson.IntegerDefault0Adapter;
import com.ty.zbpet.net.gson.LongDefault0Adapter;
import com.ty.zbpet.net.gson.StringDefault0Adapter;
import com.ty.zbpet.ui.base.BaseResponse;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.concurrent.TimeUnit;

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
    public void getMaterialTodoList(BaseSubscriber<BaseResponse<MaterialTodoData>> subscriber) {
        mService.getMaterialTodoList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取原辅料采购 待办 详情
     *
     * @param subscriber
     */
    public void getMaterialTodoListDetail(BaseSubscriber<BaseResponse<MaterialTodoDetailsData>> subscriber, String sapOrderNo) {
        mService.getMaterialTodoListDetail(sapOrderNo)
                .subscribeOn(Schedulers.io())
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 库位码校验
     */
    public void checkCarCode(BaseSubscriber<CarPositionNoData> subscriber, String positionNo) {
        mService.checkCarCode(positionNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 已办列表
     *
     * @param subscriber
     */
    public void getMaterialDoneList(BaseSubscriber<BaseResponse<MaterialDoneList>> subscriber) {
        mService.getMaterialDoneList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 已办详情
     *
     * @param subscriber
     * @param id
     */
    public void getMaterialDoneListDetail(BaseSubscriber<BaseResponse<MaterialDoneDetailsData>> subscriber, String id) {
        mService.getMaterialDoneListDetail(id)
                .subscribeOn(Schedulers.io())
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**--------------------------------- 领料出库 ----------------------------------------*/

    /**
     * 领料出库 待办列表
     *
     * @param subscriber
     */
    public void pickOutTodoList(BaseSubscriber<BaseResponse<MaterialTodoList>> subscriber) {
        mService.pickOutTodoList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 领料出库 待办详情
     *
     * @param subscriber
     * @param sapOrderNo
     */
    public void pickOutTodoListDetails(BaseSubscriber<BaseResponse<PickOutTodoDetailsData>> subscriber, String sapOrderNo) {
        mService.pickOutTodoListDetail(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 领料出库 待办详情 保存
     *
     * @param subscriber
     */
    public void pickOutTodoSave(BaseSubscriber<ResponseInfo> subscriber, RequestBody body) {
        mService.pickOutTodoSave(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 领料出库 已办列表
     *
     * @param subscriber
     */
    public void pickOutDoneList(BaseSubscriber<BaseResponse<PickOutDoneData>> subscriber) {
        mService.pickOutDoneList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 领料出库 已办详情
     *
     * @param subscriber
     * @param sapOrderNo
     */
    public void pickOutDoneListDetails(BaseSubscriber<BaseResponse<PickOutDoneDetailsData>> subscriber, String sapOrderNo) {
        mService.pickOutDoneListDetail(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 领料出库 已办详情 保存
     *
     * @param subscriber
     */
    public void pickOutDoneSave(BaseSubscriber<ResponseInfo> subscriber) {
        mService.pickOutDoneSave()
                .subscribeOn(Schedulers.io())
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
                .subscribeOn(Schedulers.io())
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 待办保存
     *
     * @param subscriber
     */
    public void getBackTodoSave(BaseSubscriber<ResponseInfo> subscriber,RequestBody body) {
        mService.getBackTodoSave(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 已办列表
     *
     * @param subscriber
     */
    public void getBackDoneList(BaseSubscriber<BaseResponse<MaterialDoneList>> subscriber) {
        mService.getBackDoneList()
                .subscribeOn(Schedulers.io())
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 已办保存
     *
     * @param subscriber
     */
    public void getBackDoneSave(BaseSubscriber<ResponseInfo> subscriber,RequestBody body) {
        mService.getBackDoneSave(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**----------------------------------------------------------------------------------*/
    /**--------------------------------- 成品库存 ----------------------------------------*/
    /**----------------------------------------------------------------------------------*/

    /**--------------------------------- 外采入库 ----------------------------------------*/
    /**
     * 已办列表
     *
     * @param subscriber
     */
    public void getPurchaseOrderList(BaseSubscriber<GoodsPurchaseOrderList> subscriber) {
        mService.getGoodsPurchaseOrderList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 待办详情
     *
     * @param subscriber
     */
    public void getPurchaseOrderInfo(BaseSubscriber<GoodsPurchaseOrderInfo> subscriber, String sapOrderNo) {
        mService.getGoodsPurchaseOrderInfo(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 待办保存
     *
     * @param subscriber
     */
    public void getPurchaseTodoSave(BaseSubscriber<ResponseInfo> subscriber,RequestBody body) {
        mService.getPurchaseTodoSave(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 已办列表
     *
     * @param subscriber
     */
    public void getPurchaseDoneList(BaseSubscriber<BaseResponse<MaterialDoneList>> subscriber) {
        mService.getPurchaseDoneList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     *  已办列表 详情
     *
     * @param subscriber
     */
    public void getPurchaseDoneListInfo(BaseSubscriber<BaseResponse<PickOutDoneDetailsData>> subscriber,String sapOrderNo) {
        mService.getPurchaseDoneListInfo(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 已办保存
     *
     * @param subscriber
     */
    public void getPurchaseDoneSave(BaseSubscriber<ResponseInfo> subscriber,RequestBody body) {
        mService.getPurchaseDoneSave(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**--------------------------------- 生产入库 ----------------------------------------*/

    /**
     * 已办列表
     *
     * @param subscriber
     */
    public void getProduceOrderList(BaseSubscriber<GoodsPurchaseOrderList> subscriber) {
        mService.getProduceOrderList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 待办详情
     *
     * @param subscriber
     */
    public void getProduceOrderInfo(BaseSubscriber<GoodsPurchaseOrderInfo> subscriber, String sapOrderNo) {
        mService.getProduceOrderInfo(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 待办保存
     *
     * @param subscriber
     */
    public void getProduceTodoSave(BaseSubscriber<ResponseInfo> subscriber,RequestBody body) {
        mService.getProduceTodoSave(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 已办列表
     *
     * @param subscriber
     */
    public void getProduceDoneList(BaseSubscriber<BaseResponse<MaterialDoneList>> subscriber) {
        mService.getProduceDoneList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     *  已办列表 详情
     *
     * @param subscriber
     */
    public void getProduceDoneListInfo(BaseSubscriber<BaseResponse<PickOutDoneDetailsData>> subscriber,String sapOrderNo) {
        mService.getProduceDoneListInfo(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 已办保存
     *
     * @param subscriber
     */
    public void getProduceDoneSave(BaseSubscriber<ResponseInfo> subscriber,RequestBody body) {
        mService.getProduceDoneSave(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**--------------------------------- 发货出库 ----------------------------------------*/

    /**
     * 已办列表
     *
     * @param subscriber
     */
    public void getShipOrderList(BaseSubscriber<GoodsPurchaseOrderList> subscriber) {
        mService.getShipOrderList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 待办详情
     *
     * @param subscriber
     */
    public void getShipOrderInfo(BaseSubscriber<GoodsPurchaseOrderInfo> subscriber, String sapOrderNo) {
        mService.getShipOrderInfo(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 待办保存
     *
     * @param subscriber
     */
    public void getShipTodoSave(BaseSubscriber<ResponseInfo> subscriber,RequestBody body) {
        mService.getShipTodoSave(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 已办列表
     *
     * @param subscriber
     */
    public void getShipDoneList(BaseSubscriber<BaseResponse<MaterialDoneList>> subscriber) {
        mService.getShipDoneList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     *  已办列表 详情
     *
     * @param subscriber
     */
    public void getShipDoneListInfo(BaseSubscriber<BaseResponse<PickOutDoneDetailsData>> subscriber,String sapOrderNo) {
        mService.getShipDoneListInfo(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 已办保存
     *
     * @param subscriber
     */
    public void getShipDoneSave(BaseSubscriber<ResponseInfo> subscriber,RequestBody body) {
        mService.getShipDoneSave(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**--------------------------------- 退货入库 ----------------------------------------*/

    /**
     * 已办列表
     *
     * @param subscriber
     */
    public void getReturnOrderList(BaseSubscriber<GoodsPurchaseOrderList> subscriber) {
        mService.getReturnOrderList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 待办详情
     *
     * @param subscriber
     */
    public void getReturnOrderInfo(BaseSubscriber<GoodsPurchaseOrderInfo> subscriber, String sapOrderNo) {
        mService.getReturnOrderInfo(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 待办保存
     *
     * @param subscriber
     */
    public void getReturnTodoSave(BaseSubscriber<ResponseInfo> subscriber,RequestBody body) {
        mService.getReturnTodoSave(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 已办列表
     *
     * @param subscriber
     */
    public void getReturnDoneList(BaseSubscriber<BaseResponse<MaterialDoneList>> subscriber) {
        mService.getReturnDoneList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     *  已办列表 详情
     *
     * @param subscriber
     */
    public void getReturnDoneListInfo(BaseSubscriber<BaseResponse<PickOutDoneDetailsData>> subscriber,String sapOrderNo) {
        mService.getReturnDoneListInfo(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 已办保存
     *
     * @param subscriber
     */
    public void getReturnDoneSave(BaseSubscriber<ResponseInfo> subscriber,RequestBody body) {
        mService.getReturnDoneSave(body)
                .subscribeOn(Schedulers.io())
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
                .subscribeOn(Schedulers.io())
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
                .subscribeOn(Schedulers.io())
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
