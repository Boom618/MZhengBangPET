package com.ty.zbpet.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ty.zbpet.base.BaseResponse;
import com.ty.zbpet.bean.CarPositionNoData;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.UserInfo;
import com.ty.zbpet.bean.eventbus.system.CheckDoneDetailEvent;
import com.ty.zbpet.bean.eventbus.system.PersonCenterEvent;
import com.ty.zbpet.bean.material.MaterialDetails;
import com.ty.zbpet.bean.material.MaterialList;
import com.ty.zbpet.bean.product.ProductDetails;
import com.ty.zbpet.bean.product.ProductList;
import com.ty.zbpet.bean.system.BoxCodeUrl;
import com.ty.zbpet.bean.system.ImageData;
import com.ty.zbpet.bean.system.PositionCode;
import com.ty.zbpet.bean.system.ProMoveList;
import com.ty.zbpet.bean.system.ProductInventorList;
import com.ty.zbpet.bean.system.ProductMove;
import com.ty.zbpet.bean.system.ProductQuery;
import com.ty.zbpet.bean.system.ReceiptList;
import com.ty.zbpet.constant.ApiNameConstant;
import com.ty.zbpet.constant.CodeConstant;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * description:
 * date: 2016/11/22.
 *
 * @author TY
 */
public class HttpMethods {

    /**
     * 默认超时时间
     */
    private static final int DEFAULT_TIMEOUT = 60;
    private ApiService mService;
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
        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(Level.BODY);
        //创建OKHttpClient
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new SessionInterceptor())
                // 日志拦截器: new LogInterceptor()
                .addInterceptor(log);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit mRetrofit = new Retrofit.Builder()
                .client(client.build())
//                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addConverterFactory(GsonConverterFactory.create(gson))
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


    /*
     * --------------------------------- 系统登录 ----------------------------------------
     */

    /**
     * 用户登录
     *
     * @param observer observer
     * @param userName userName
     * @param password password
     */
    public void userLogin(SingleObserver<BaseResponse<UserInfo>> observer, String userName, String password) {
        mService.userLogin(userName, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 用户登出
     *
     * @param observer observer
     */
    public void userLogOut(SingleObserver<ResponseInfo> observer) {
        mService.userLogout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 修改密码
     *
     * @param observer         observer
     * @param oldPassword      原密码
     * @param newPassword      新密码
     * @param newPasswordAgain 新密码确认
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
     * @param observer observer
     */
    public void userCenter(SingleObserver<BaseResponse<PersonCenterEvent>> observer) {
        mService.userCenter()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /*--------------------------------- 到货入库 ----------------------------------------*/

    /**
     * 获取原辅料采购待办列表
     *
     * @param observer observer
     */
    public void getMaterialTodoList(SingleObserver<BaseResponse<MaterialList>> observer,
                                    String sapOrderNo, String startDate, String endDate) {
        mService.getMaterialTodoList(sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取原辅料采购 待办 详情
     *
     * @param observer observer
     */
    public void getMaterialTodoListDetail(SingleObserver<BaseResponse<MaterialDetails>> observer, String sapFirmNo, String sapOrderNo, String supplierNo) {
        mService.getMaterialTodoListDetail(sapFirmNo, sapOrderNo, supplierNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /**
     * 待办 保存
     *
     * @param observer observer
     * @param body     body
     */
    public void materialTodoInSave(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.materialPurchaseInSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    /**
     * 库位码校验
     */
    public void checkCarCode(SingleObserver<CarPositionNoData> subscriber, String positionNo, String warehouseNo) {
        mService.checkCarCode(positionNo, warehouseNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * url 解析
     */
    public void urlAnalyze(SingleObserver<BaseResponse<BoxCodeUrl>> subscriber, String url, String goodsNo) {
        mService.urlAnalyze(url, goodsNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 已办列表
     *
     * @param observer observer
     */
    public void getMaterialDoneList(SingleObserver<BaseResponse<MaterialList>> observer, String type,
                                    String sapOrderNo, String startDate, String endDate) {
        mService.getMaterialDoneList(type, sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 已办详情
     *
     * @param observer observer
     * @param orderId  订单ID
     */
    public void getMaterialDoneListDetail(SingleObserver<BaseResponse<MaterialDetails>> observer, String orderId) {
        mService.getMaterialDoneListDetail(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 原辅料到货冲销入库 已办 保存
     *
     * @param observer observer
     * @param body     body
     */
    public void materialDoneInSave(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.purchaseInRecallOut(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /*--------------------------------- 领料出库 ----------------------------------------*/

    /**
     * 领料出库 待办列表
     *
     * @param observer observer
     */
    public void pickOutTodoList(SingleObserver<BaseResponse<MaterialList>> observer,
                                String sign, String sapOrderNo, String startDate, String endDate) {
        mService.pickOutTodoList(sign, sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    /**
     * 领料出库 待办详情
     *
     * @param observer   observer
     * @param sapOrderNo SAP no
     */
    public void pickOutTodoListDetails(SingleObserver<BaseResponse<MaterialDetails>> observer,
                                       String sign, String sapOrderNo, String sapFirmNo, String orderTime) {
        mService.pickOutTodoListDetail(sign, sapOrderNo, sapFirmNo, orderTime)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 领料出库 待办详情 保存
     *
     * @param observer observer
     */
    public void pickOutTodoSave(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.pickOutTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 领料出库 已办列表
     *
     * @param observer observer
     */
    public void pickOutDoneList(SingleObserver<BaseResponse<MaterialList>> observer, String type,
                                String sapOrderNo, String startDate, String endDate) {
        mService.pickOutDoneList(type, sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    /**
     * 领料出库 已办详情
     *
     * @param observer   observer
     * @param sapOrderNo sapOrderNo
     */
    public void pickOutDoneListDetails(SingleObserver<BaseResponse<MaterialDetails>> observer, String sapOrderNo) {
        mService.pickOutDoneListDetail(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 领料出库 已办详情 保存
     *
     * @param observer observer
     */
    public void pickOutDoneSave(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.pickOutDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /*--------------------------------- 采购退货 ----------------------------------------*/


    /**
     * 采购退货 待办列表
     *
     * @param subscriber subscriber
     */
    public void getBackTodoList(SingleObserver<BaseResponse<MaterialList>> subscriber,
                                String sapOrderNo, String startDate, String endDate) {
        mService.getBackTodoList(sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 采购退货 待办列表 详情
     *
     * @param subscriber subscriber
     */
    public void getBackTodoListInfo(SingleObserver<BaseResponse<MaterialDetails>> subscriber, String sapOrderNo, String sapFirmNo, String supplierNo) {
        mService.getBackTodoListInfo(sapOrderNo, sapFirmNo, supplierNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 待办保存
     *
     * @param subscriber subscriber
     */
    public void getBackTodoSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getBackTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 已办列表
     *
     * @param subscriber subscriber
     */
    public void getBackDoneList(SingleObserver<BaseResponse<MaterialList>> subscriber, String type,
                                String sapOrderNo, String startDate, String endDate) {
        mService.getBackDoneList(type, sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 采购退货 已办列表 详情
     *
     * @param subscriber subscriber
     */
    public void getBackDoneListInfo(SingleObserver<BaseResponse<MaterialDetails>> subscriber, String orderId) {
        mService.getBackDoneListInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 采购退货 已办保存
     *
     * @param subscriber subscriber
     */
    public void getBackDoneSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getBackDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /*----------------------------------------------------------------------------------*/
    /*--------------------------------- 成品库存 ----------------------------------------*/
    /*----------------------------------------------------------------------------------*/

    /*--------------------------------- 外采入库 ----------------------------------------*/

    /**
     * 外采入库 待办列表
     *
     * @param subscriber subscriber
     */
    public void getBuyInOrderList(SingleObserver<BaseResponse<ProductList>> subscriber,
                                  String sapOrderNo, String startDate, String endDate) {
        mService.getBuyInOrderList(sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 待办详情
     *
     * @param subscriber subscriber
     */
    public void getBuyInOrderInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String sapOrderNo, String sapFirmNo, String supplierNo) {
        mService.getBuyInOrderInfo(sapOrderNo, sapFirmNo, supplierNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 待办保存
     *
     * @param subscriber subscriber
     */
    public void getBuyInTodoSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getBuyInTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 已办列表
     *
     * @param subscriber subscriber
     */
    public void getBuyInDoneList(SingleObserver<BaseResponse<ProductList>> subscriber, String type,
                                 String sapOrderNo, String startDate, String endDate) {
        mService.getBuyInDoneList(type, sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 外采入库 已办详情
     *
     * @param subscriber subscriber
     */
    public void getBuyInDoneListInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String orderId) {
        mService.getBuyInDoneListInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 外采入库 已办保存
     *
     * @param subscriber subscriber
     */
    public void getBuyInDoneSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getBuyInDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*--------------------------------- 生产入库 ----------------------------------------*/

    /**
     * 生产入库 待办列表
     *
     * @param subscriber subscriber
     */
    public void getProductTodoList(SingleObserver<BaseResponse<ProductList>> subscriber,
                                   String sapOrderNo, String startDate, String endDate) {
        mService.getProduceOrderList(sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 待办详情
     *
     * @param subscriber subscriber
     */
    public void getProduceOrderInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String sign, String sapOrderNo) {
        mService.getProduceOrderInfo(sign, sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 待办保存
     *
     * @param subscriber subscriber
     */
    public void getProduceTodoSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getProduceTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 已办列表
     *
     * @param subscriber Observer
     */
    public void getProduceDoneList(SingleObserver<BaseResponse<ProductList>> subscriber, String type,
                                   String sapOrderNo, String startDate, String endDate) {
        mService.getProduceDoneList(type, sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 生产入库 已办详情
     *
     * @param subscriber subscriber
     */
    public void getProduceDoneInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String orderId) {
        mService.getProduceDoneInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生产入库 已办保存
     *
     * @param subscriber subscriber
     */
    public void getProduceDoneSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getProduceDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /*--------------------------------- 发货出库 ----------------------------------------*/

    /**
     * 发货出库 已办列表
     *
     * @param subscriber subscriber
     */
    public void getShipOrderList(SingleObserver<BaseResponse<ProductList>> subscriber,
                                 String sapOrderNo, String startDate, String endDate) {
        mService.getShipOrderList(sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发货出库 待办详情
     *
     * @param subscriber subscriber
     */
    public void getShipOrderInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String sapOrderNo) {
        mService.getShipOrderInfo(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发货出库 待办保存
     *
     * @param subscriber subscriber
     */
    public void getShipTodoSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getShipTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发货出库 已办列表
     *
     * @param subscriber subscriber
     */
    public void getShipDoneList(SingleObserver<BaseResponse<ProductList>> subscriber, String type,
                                String sapOrderNo, String startDate, String endDate) {
        mService.getShipDoneList(type, sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 发货出库 已办详情
     *
     * @param subscriber subscriber
     */
    public void getShipDoneListInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String orderId) {
        mService.getShipDoneListInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发货出库 已办保存
     *
     * @param subscriber subscriber
     */
    public void getShipDoneSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getShipDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /*--------------------------------- 退货入库 ----------------------------------------*/

    /**
     * 退货入库 待办列表
     *
     * @param subscriber Observer
     */
    public void getReturnOrderList(SingleObserver<BaseResponse<ProductList>> subscriber,
                                   String sapOrderNo, String startDate, String endDate) {
        mService.getReturnOrderList(sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 待办详情
     *
     * @param subscriber subscriber
     */
    public void getReturnOrderInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String sapOrderNo) {
        mService.getReturnOrderInfo(sapOrderNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 待办保存
     *
     * @param subscriber subscriber
     */
    public void getReturnTodoSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getReturnTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 已办列表
     *
     * @param subscriber Observer
     */
    public void getReturnDoneList(SingleObserver<BaseResponse<ProductList>> subscriber, String type,
                                  String sapOrderNo, String startDate, String endDate) {
        mService.getReturnDoneList(type, sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 退货入库 已办详情
     *
     * @param subscriber subscriber
     */
    public void getReturnDoneListInfo(SingleObserver<BaseResponse<ProductDetails>> subscriber, String orderId) {
        mService.getReturnDoneListInfo(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退货入库 已办保存
     *
     * @param subscriber subscriber
     */
    public void getReturnDoneSave(SingleObserver<ResponseInfo> subscriber, RequestBody body) {
        mService.getReturnDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /*--------------------------------- End ----------------------------------------*/

    /*----------------------------------------------------------------------------------*/
    /*--------------------------------- 系统 --------------------------------------------*/
    /*----------------------------------------------------------------------------------*/

    /*
     * --------------------------------- 溯源 --------------------------------------------
     */

    /**
     * 成品查询
     *
     * @param observer observer
     * @param url      URL
     */
    public void getProductQuery(SingleObserver<BaseResponse<ProductQuery>> observer, String url) {
        mService.getProductQuery(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 库位码查询
     *
     * @param observer   observer
     * @param positionNo 库位码
     */
    public void getPositionQuery(SingleObserver<BaseResponse<PositionCode>> observer, String positionNo) {
        mService.getPositionQuery(positionNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /*
     * --------------------------------- 质检 --------------------------------------------
     */

    /**
     * 质检 待办保存
     *
     * @param observer observer
     */
    public void getQualityCheckTodoSave(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.getCheckTodoSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 质检 已办列表
     *
     * @param observer observer
     */
    public void getQualityCheckDoneList(SingleObserver<BaseResponse<MaterialList>> observer,
                                        String sapOrderNo, String startDate, String endDate) {
        mService.getCheckDoneList(CodeConstant.CHECK_STATE_DONE, sapOrderNo, startDate, endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 质检 已办详情
     *
     * @param observer observer
     */
    public void getQualityCheckDoneInfo(SingleObserver<BaseResponse<CheckDoneDetailEvent>> observer, String id) {
        mService.getCheckDoneInfo(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 质检 已办保存
     *
     * @param observer observer
     */
    public void getQualityCheckDoneSave(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.getCheckDoneSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /*--------------------------------- 质检 End ----------------------------------------*/
    /*--------------------------------- 盘点 start ----------------------------------------*/

    /**
     * 原辅料盘点
     */
    public void positionStock(SingleObserver<BaseResponse<PositionCode>> observer, String positionNo) {
        mService.positionStock(positionNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    /**
     * 原辅料盘点提交
     *
     * @param observer observer
     * @param body     body
     */
    public void positionStockSave(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.positionStockSave(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 成品盘点列表
     */
    public void getGoodsList(SingleObserver<BaseResponse<ProductInventorList>> observer, int pageSize, String goodsNo) {
        mService.getGoodsList(pageSize, goodsNo, 0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 成品盘点 提交
     */
    public void goodsInventory(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.goodsInventory(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * @param type 1产品盘点单 2原辅料盘点单
     *             盘点单据列表
     */
    public void getCheckList(SingleObserver<BaseResponse<ReceiptList>> observer, String type) {
        mService.getCheckList(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    /**
     * 删除盘点记录
     */
    public void deleteCheck(SingleObserver<ResponseInfo> observer, String id, String sapCheckNo) {
        mService.deleteCheck(id, sapCheckNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    /*--------------------------------- 移库 --------------------------------------------*/

    // 源库位 移出
    public void materialMoveOrder(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.materialMoveOrder(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 移库列表
    public void moveList(SingleObserver<BaseResponse<PositionCode>> observer) {
        mService.moveList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 目标库位
    public void moveMaterial(SingleObserver<ResponseInfo> observer, String id, String positionNo, String warehouseNo, String time) {
        mService.moveMaterial(id, positionNo, warehouseNo, time)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 原辅料移库待冲销列表
    public void reversalList(SingleObserver<BaseResponse<PositionCode>> observer) {
        mService.reversalList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 原辅料移库冲销
    public void reversalMove(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.reversalMove(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 获取产品仓库库存
    public void goodsStock(SingleObserver<BaseResponse<ProductInventorList>> observer, String goodsNo, String warehouseNo) {
        mService.goodsStock(goodsNo, warehouseNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 成品移出原仓库
    public void goodsMoveOrder(SingleObserver<ResponseInfo> observer, RequestBody body) {
        mService.goodsMoveOrder(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 获取成品移库单
    public void moveProductList(SingleObserver<BaseResponse<ProMoveList>> observer) {
        mService.moveProductList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 成品移入目标仓库
    public void goodsMoveToTarget(SingleObserver<ResponseInfo> observer, String id, String goodsNo, String goodsName) {
        mService.goodsMoveToTarget(id, goodsNo, goodsName, "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 成品移出源库位冲销(314冲销)
    public void goodsSourceRecall(SingleObserver<ResponseInfo> observer, String id){
        mService.goodsSourceRecall(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    // 成品待冲销列表
    public void goodsRecallList(SingleObserver<BaseResponse<ProMoveList>> observer) {
        mService.goodsRecallList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 成品冲销（316冲销/目标仓库冲销）
    public void goodsMoveRecall(SingleObserver<ResponseInfo> observer, String id){
        mService.goodsMoveRecall(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /*
     * --------------------------------- 图片上传 ----------------------------------------
     */

    /**
     * 上传图片
     *
     * @param observer observer
     * @param part     body
     */
    public void updateCheckImage(SingleObserver<ImageData> observer, MultipartBody.Part part) {
        mService.updateCheckImage(part)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
