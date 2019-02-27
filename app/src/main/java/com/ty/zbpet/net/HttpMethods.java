package com.ty.zbpet.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.UserInfo;
import com.ty.zbpet.bean.material.MaterialDetails;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.bean.product.ProductDetails;
import com.ty.zbpet.bean.product.ProductList;
import com.ty.zbpet.bean.product.ProductDetails;
import com.ty.zbpet.bean.system.ImageData;
import com.ty.zbpet.bean.system.QualityCheckTodoDetails;
import com.ty.zbpet.bean.system.QualityCheckTodoList;
import com.ty.zbpet.constant.ApiNameConstant;
import com.ty.zbpet.net.gson.DoubleDefault0Adapter;
import com.ty.zbpet.net.gson.IntegerDefault0Adapter;
import com.ty.zbpet.net.gson.LongDefault0Adapter;
import com.ty.zbpet.net.gson.StringDefault0Adapter;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.constant.CodeConstant;

import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
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
                .addInterceptor(new SessionInterceptor())
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


    /**
     * --------------------------------- 系统登录 ----------------------------------------
     */

    /**
     * 用户登录
     *
     * @param observer
     * @param userName
     * @param password
     */
    public void userLogin(SingleObserver<BaseResponse<UserInfo>> observer, String userName, String password) {
        mService.userLogin(userName, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 用户登出
     *
     * @param observer
     */
    public void userLogOut(SingleObserver<ResponseInfo> observer) {
        mService.userLogout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 修改密码
     *
     * @param observer
     * @param oldPassword
     * @param newPassword
     * @param newPasswordAgain
     */
    public void userUpdatePass(SingleObserver<ResponseInfo> observer, String oldPassword
            , String newPassword, String newPasswordAgain) {
        mService.userUpdatePass(oldPassword, newPassword, newPasswordAgain)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 个人中心
     *
     * @param observer
     */
    public void userCenter(SingleObserver<ResponseInfo> observer) {
        mService.userCenter()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /**--------------------------------- 到货入库 ----------------------------------------*/

    /**
     * 获取原辅料采购待办列表
     *
     * @param subscriber
     */
    public void getMaterialTodoList(SingleObserver<BaseResponse<MaterialList>> subscriber) {
        mService.getMaterialTodoList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取原辅料采购 待办 详情
     *
     * @param subscriber
     */
    public void getMaterialTodoListDetail(SingleObserver<BaseResponse<MaterialDetails>> subscriber, String sapFirmNo,String sapOrderNo,String supplierNo) {
        mService.getMaterialTodoListDetail(sapFirmNo,sapOrderNo,supplierNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 待办 保存
     *
     * @param subscriber
     * @param body
     */
    public void materialTodoInSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
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
    public void getMaterialDoneList(SingleObserver<BaseResponse<MaterialList>> subscriber, String type) {
        mService.getMaterialDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 已办详情
     *
     * @param subscriber
     * @param orderId
     */
    public void getMaterialDoneListDetail(SingleObserver<BaseResponse<MaterialDetails>> subscriber, String orderId) {
        mService.getMaterialDoneListDetail(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 原辅料到货冲销入库 已办 保存
     *
     * @param subscriber
     * @param body
     */
    public void materialDoneInSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
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
    public void pickOutTodoList(SingleObserver<BaseResponse<MaterialList>> subscriber) {
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
    public void pickOutTodoListDetails(SingleObserver<BaseResponse<MaterialDetails>> subscriber, String sapOrderNo,String sapFirmNo) {
        mService.pickOutTodoListDetail(sapOrderNo,sapFirmNo)
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
    public void pickOutDoneList(SingleObserver<BaseResponse<MaterialList>> subscriber, String type) {
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
    public void pickOutDoneListDetails(SingleObserver<BaseResponse<MaterialDetails>> subscriber, String sapOrderNo) {
        mService.pickOutDoneListDetail(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 领料出库 已办详情 保存
     *
     * @param subscriber
     */
    public void pickOutDoneSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
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
    public void getBackTodoList(SingleObserver<BaseResponse<MaterialList>> subscriber) {
        mService.getBackTodoList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 采购退货 待办列表 详情
     *
     * @param subscriber
     */
    public void getBackTodoListInfo(SingleObserver<BaseResponse<MaterialDetails>> subscriber, String sapOrderNo,String sapFirmNo,String supplierNo) {
        mService.getBackTodoListInfo(sapOrderNo,sapFirmNo,supplierNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 待办保存
     *
     * @param subscriber
     */
    public void getBackTodoSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getBackTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 已办列表
     *
     * @param subscriber
     */
    public void getBackDoneList(SingleObserver<BaseResponse<MaterialList>> subscriber, String type) {
        mService.getBackDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 采购退货 已办列表 详情
     *
     * @param subscriber
     */
    public void getBackDoneListInfo(SingleObserver<BaseResponse<MaterialDetails>> subscriber, String orderId) {
        mService.getBackDoneListInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 已办保存
     *
     * @param subscriber
     */
    public void getBackDoneSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
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
    public void getBuyInOrderList(SingleObserver<BaseResponse<ProductList>> subscriber) {
        mService.getBuyInOrderList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 待办详情
     *
     * @param subscriber
     */
    public void getBuyInOrderInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String sapOrderNo,String sapFirmNo,String supplierNo) {
        mService.getBuyInOrderInfo(sapOrderNo,sapFirmNo,supplierNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 待办保存
     *
     * @param subscriber
     */
    public void getBuyInTodoSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getBuyInTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 已办列表
     *
     * @param subscriber
     */
    public void getBuyInDoneList(SingleObserver<BaseResponse<ProductList>> subscriber, String type) {
        mService.getBuyInDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 外采入库 已办详情
     *
     * @param subscriber
     */
    public void getBuyInDoneListInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String orderId) {
        mService.getBuyInDoneListInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 已办保存
     *
     * @param subscriber
     */
    public void getBuyInDoneSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
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
    public void getProductTodoList(SingleObserver<BaseResponse<ProductList>> subscriber) {
        mService.getProduceOrderList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 待办详情
     *
     * @param subscriber
     */
    public void getProduceOrderInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String sapOrderNo) {
        mService.getProduceOrderInfo(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 待办保存
     *
     * @param subscriber
     */
    public void getProduceTodoSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getProduceTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 已办列表
     *
     * @param subscriber
     */
    public void getProduceDoneList(SingleObserver<BaseResponse<ProductList>> subscriber, String type) {
        mService.getProduceDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 生产入库 已办详情
     *
     * @param subscriber
     */
    public void getProduceDoneInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String orderId) {
        mService.getProduceDoneInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 已办保存
     *
     * @param subscriber
     */
    public void getProduceDoneSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
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
    public void getShipOrderList(SingleObserver<BaseResponse<ProductList>> subscriber) {
        mService.getShipOrderList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发货出库 待办详情
     *
     * @param subscriber
     */
    public void getShipOrderInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String sapOrderNo) {
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
    public void getShipDoneList(SingleObserver<BaseResponse<ProductList>> subscriber, String type) {
        mService.getShipDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 发货出库 已办详情
     *
     * @param subscriber
     */
    public void getShipDoneListInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String orderId) {
        mService.getShipDoneListInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发货出库 已办保存
     *
     * @param subscriber
     */
    public void getShipDoneSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
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
    public void getReturnOrderList(SingleObserver<BaseResponse<ProductList>> subscriber) {
        mService.getReturnOrderList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 待办详情
     *
     * @param subscriber
     */
    public void getReturnOrderInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String sapOrderNo) {
        mService.getReturnOrderInfo(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 待办保存
     *
     * @param subscriber
     */
    public void getReturnTodoSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getReturnTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 已办列表
     *
     * @param subscriber
     */
    public void getReturnDoneList(SingleObserver<BaseResponse<ProductList>> subscriber, String type) {
        mService.getReturnDoneList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 退货入库 已办详情
     *
     * @param subscriber
     */
    public void getReturnDoneListInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String orderId) {
        mService.getReturnDoneListInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 已办保存
     *
     * @param subscriber
     */
    public void getReturnDoneSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getReturnDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**--------------------------------- End ----------------------------------------*/

    /**----------------------------------------------------------------------------------*/
    /**--------------------------------- 系统 --------------------------------------------*/
    /**----------------------------------------------------------------------------------*/

    /**
     * --------------------------------- 质检 --------------------------------------------
     */

    /**
     * 质检 待办列表
     *
     * @param observer
     */
    public void getQualityCheckTodoList(SingleObserver<BaseResponse<QualityCheckTodoList>> observer) {
        mService.getCheckTodoList(CodeConstant.INSTANCE.getCHECK_STATE_TODO())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 质检 待办详情
     *
     * @param observer
     */
    public void getQualityCheckTodoInfo(SingleObserver<BaseResponse<QualityCheckTodoDetails>> observer, String arrivalOrderNo) {
        mService.getCheckTodoInfo(arrivalOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 质检 待办保存
     *
     * @param observer
     */
    public void getQualityCheckTodoSave(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.getCheckTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 质检 已办列表
     *
     * @param observer
     */
    public void getQualityCheckDoneList(SingleObserver<BaseResponse<QualityCheckTodoList>> observer) {
        mService.getCheckDoneList(CodeConstant.INSTANCE.getCHECK_STATE_DONE())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 质检 已办详情
     *
     * @param observer
     */
    public void getQualityCheckDoneInfo(SingleObserver<BaseResponse<QualityCheckTodoList>> observer, String arrivalOrderNo) {
        mService.getCheckDoneInfo(arrivalOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 质检 已办保存
     *
     * @param observer
     */
    public void getQualityCheckDoneSave(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.getCheckDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**--------------------------------- 质检 End ----------------------------------------*/


    /**
     * --------------------------------- 图片上传 ----------------------------------------
     */

    /**
     * 上传图片
     *
     * @param observer
     * @param part
     */
    public void updateCheckImage(SingleObserver<ImageData> observer, MultipartBody.Part part) {
        mService.updateCheckImage(part)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /**
     * --------------------------------- Gson  ----------------------------------------
     */

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
