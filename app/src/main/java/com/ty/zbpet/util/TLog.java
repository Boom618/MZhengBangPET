package com.ty.zbpet.util;

import com.orhanobut.logger.Logger;
import com.ty.zbpet.BuildConfig;

/**
 * @author TY on 2018/11/6.
 *
 * Ty Logger
 */
public class TLog {


    private static final String TAG = "TLog";

    /**
     * 是否开启 Logger
     */
    private static final Boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String msg){
        if (DEBUG) {
            Logger.d(TAG,msg);
        }
    }

    public static void d(Object obj){
        if (DEBUG) {
            Logger.d(TAG,obj);
        }
    }

    public static void d(String tag,String msg){
        if (DEBUG) {
            Logger.d(tag,msg);
        }
    }

    public static void d(String tag,Object obj){
        if (DEBUG) {
            Logger.d(tag,obj);
        }
    }


    public static void e(String msg){
        if (DEBUG) {
            Logger.e(TAG,msg);
        }
    }

    public static void e(Object obj){
        if (DEBUG) {
            Logger.e(TAG,obj);
        }
    }

    public static void e(String tag,String msg){
        if (DEBUG) {
            Logger.e(tag,msg);
        }
    }

    public static void e(String tag,Object obj){
        if (DEBUG) {
            Logger.e(tag,obj);
        }
    }

}
