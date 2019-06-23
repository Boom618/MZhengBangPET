package com.ty.zbpet.ui;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.ty.zbpet.config.AppInit;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.ACache;
import com.xiasuhuei321.loadingdialog.manager.StyleManager;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.litepal.LitePal;

/**
 * @author TY
 */
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
        AppInit.init(this);

        LitePal.initialize(this);
        mCache = ACache.get(context);
        // dialog manager
        StyleManager s = new StyleManager();
        //在这里调用方法设置s的属性
        s.Anim(false).repeatTime(0).contentSize(-1).intercept(true);
        LoadingDialog.initStyle(s);

        // 日志 logger 库
        Logger.addLogAdapter(new AndroidLogAdapter());
        // bugly
        CrashReport.initCrashReport(getApplicationContext(), CodeConstant.BUG_LY_APP_ID, false);

        // 内存检测
        LeakCanary.install(this);

        // 管理任务栈
        ActivitiesHelper.init(this);

        // yokeyword 大佬的 Fragment 库
//        Fragmentation.builder()
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(true)
//                .handleException(new ExceptionHandler() {
//                    @Override
//                    public void onException(@NonNull Exception e) {
//                        // 处理捕获异常
//                    }
//                })
//                .install();

    }

    public static Context getContext() {
        return context;
    }

}
