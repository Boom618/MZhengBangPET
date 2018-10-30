package com.ty.zbpet.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ty.zbpet.bean.GoodsPurchaseOrderInfo;
import com.ty.zbpet.bean.GoodsPurchaseOrderList;
import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.bean.MaterialInWarehouseOrderInfo;
import com.ty.zbpet.bean.PickOutDetailInfo;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.WarehouseInfo;
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
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * description:
 * author: XingZheng
 * date: 2016/11/22.
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
    private String baseUrl= ApiNameConstant.BASE_URL;

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
                .addInterceptor(new HttpLoggingInterceptor());

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

    /**
     * 获取原辅料采购已办列表
     * @param subscriber
     */
//    public void getMaterialInWarehouseOrderList(BaseSubscriber<MaterialInWarehouseOrderList> subscriber){
//        mService.getMaterialInWarehouseOrderList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

    /**
     * 已办列表 新方式
     * @param subscriber
     */
    public void getMaterialOrderListDone(BaseSubscriber<BaseResponse<MaterialData>> subscriber){
        mService.getMaterialInWarehouseOrderList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取原辅料采购待办列表  -- 新方式
     * @param subscriber
     */
    public void getMaterialOrderListTodo(BaseSubscriber<BaseResponse<MaterialData>> subscriber){
        mService.getMaterialOrderList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取原辅料采购已办详情
     * @param subscriber
     */
    public void getMaterialInWarehouseOrderInfo(BaseSubscriber<MaterialInWarehouseOrderInfo> subscriber,String sapOrderNo){
        mService.getMaterialInWarehouseOrderInfo(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 原料--领料出库详情 列表
     * @param subscriber
     */
    public void pickOutDetail(BaseSubscriber<PickOutDetailInfo> subscriber){
        mService.pickOutDetailInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 原辅料到货冲销入库
     * @param subscriber
     * @param body
     */
    public void purchaseInRecallOut(BaseSubscriber<ResponseInfo> subscriber, RequestBody body){
        mService.purchaseInRecallOut(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取成品采购已办列表
     * @param subscriber
     */
    public void getGoodsPurchaseOrderList(BaseSubscriber<GoodsPurchaseOrderList> subscriber){
        mService.getGoodsPurchaseOrderList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取成品采购待办详情
     * @param subscriber
     */
    public void getGoodsPurchaseOrderInfo(BaseSubscriber<GoodsPurchaseOrderInfo> subscriber, String sapOrderNo){
        mService.getGoodsPurchaseOrderInfo(sapOrderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取仓库信息
     * @param subscriber
     */
    public void getWarehouseList(BaseSubscriber<WarehouseInfo> subscriber){
        mService.getWarehouseList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 成品采购入库——待办
     * @param
     */
    public void doGoodsPurchaseInStorage(BaseSubscriber<ResponseInfo> subscriber, RequestBody body){
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
