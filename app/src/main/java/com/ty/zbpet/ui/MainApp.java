package com.ty.zbpet.ui;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.ACache;

/**
 * @author TY
 */
//public class MainApp extends Application {
public class MainApp extends MultiDexApplication {

    public static Context context;
    public static ACache mCache;

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        mCache = ACache.get(context);
        // 日志 logger 库
        Logger.addLogAdapter(new AndroidLogAdapter());
        // bugly
        CrashReport.initCrashReport(getApplicationContext(), CodeConstant.BUG_LY_APP_ID, false);

        // 内存检测
        LeakCanary.install(this);

        // 管理任务栈
        ActivitiesHelper.init(this);

    }

    public static Context getContext() {
        return context;
    }

}
