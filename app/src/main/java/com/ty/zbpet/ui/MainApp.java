package com.ty.zbpet.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.billy.android.loading.Gloading;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.ty.zbpet.BuildConfig;
import com.ty.zbpet.config.AppInit;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.ACache;
import com.xiasuhuei321.loadingdialog.manager.StyleManager;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

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

        if (BuildConfig.DEBUG){
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);

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
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true)
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(@NonNull Exception e) {
                        // 处理捕获异常
                    }
                })
                .install();

    }

    public static Context getContext() {
        return context;
    }

}
