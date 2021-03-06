package com.ty.zbpet.ui;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ty.zbpet.net.gson.DoubleDefault0Adapter;
import com.ty.zbpet.net.gson.IntegerDefault0Adapter;
import com.ty.zbpet.net.gson.LongDefault0Adapter;
import com.ty.zbpet.net.gson.StringDefault0Adapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.cache.model.CacheMode;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainApp extends Application {

    public static Context context;
    private static Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        initHttp();
    }

    private void initHttp() {
        EasyHttp.init(this);
        EasyHttp.getInstance()
                .setBaseUrl("http://static.owspace.com/")
                .debug("RxEasyTAG", true)// 打开该调试开关并设置TAG
                //如果使用默认的60秒,以下三行也不需要设置
                .setReadTimeOut(60 * 1000)
                .setWriteTimeOut(60 * 100)
                .setConnectTimeout(60 * 100)
                .setRetryCount(3)//网络不好自动重试3次
                //可以全局统一设置超时重试间隔时间,默认为500ms,不需要可以设置为0
                .setRetryDelay(500)//每次延时500ms重试
                //可以全局统一设置超时重试间隔叠加时间,默认为0ms不叠加
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体请看CacheMode
                .setCacheMode(CacheMode.NO_CACHE)
                //可以全局统一设置缓存时间,默认永不过期
                .setCacheTime(-1)//-1表示永久缓存,单位:秒 ，Okhttp和自定义RxCache缓存都起作用
                //全局设置自定义缓存保存转换器，主要针对自定义RxCache缓存
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                //全局设置自定义缓存大小，默认50M
                .setCacheMaxSize(100 * 1024 * 1024)//设置缓存大小为100M
                //设置缓存版本，如果缓存有变化，修改版本后，缓存就不会被加载。特别是用于版本重大升级时缓存不能使用的情况
                .setCacheVersion(1)//缓存版本为1
                //.setHttpCache(new Cache())//设置Okhttp缓存，在缓存模式为DEFAULT才起作用
                //可以设置https的证书,以下几种方案根据需要自己设置
                .setCertificates()                                  //方法一：信任所有证书,不安全有风险
                //.setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
                //配置https的域名匹配规则，不需要就不要加入，使用不当会导致https握手失败
                //.setHostnameVerifier(new SafeHostnameVerifier())
                //.addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
                // .addCommonHeaders(headers)//设置全局公共头
                //  .addCommonParams(params)//设置全局公共参数
                //.addNetworkInterceptor(new NoCacheInterceptor())//设置网络拦截器
                //.setCallFactory()//局设置Retrofit对象Factory
                //.setCookieStore()//设置cookie
                //.setOkproxy()//设置全局代理
                //.setOkconnectionPool()//设置请求连接池
                //.setCallbackExecutor()//全局设置Retrofit callbackExecutor
                //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
                //.addInterceptor(new GzipRequestInterceptor())//开启post数据进行gzip后发送给服务器
                // .addInterceptor(new CustomSignInterceptor())//添加参数签名拦截器
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static Context getContext() {
        return context;
    }

    public static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setLenient()
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
