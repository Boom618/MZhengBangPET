package com.ty.zbpet.util;

import com.ty.zbpet.bean.UserInfo;

/**
 * @author PVer on 2018/12/15.
 * <p>
 * 对 ACache 缓存 简单处理
 */
public class SimpleCache {

    private static ACache aCache = ACache.get(ResourceUtil.getContext());

    public SimpleCache() {
        aCache = ACache.get(ResourceUtil.getContext());
    }

    /**
     * 盘点 存 position 对应的 数量
     *
     * @param position 位置
     * @param number   数量
     */
    public static void putNumber(String position, String number) {
        aCache.put(position, number);
    }

    public static String getNumber(String position) {
        return aCache.getAsString(position);
    }

    /*----------------- 盘点 存 position 对应的 数量 end --------*/

    public static void putString(String key, String value) {
        aCache.put(key, value);
    }

    public static void putObject(String key, UserInfo object) {
        aCache.put(key, object);
    }


    public static String getString(String key) {
        return aCache.getAsString(key);
    }

    public static UserInfo getUserInfo(String key) {
        UserInfo userInfo = (UserInfo) aCache.getAsObject(key);
        return userInfo;
    }


    /**
     * 移除某个key
     *
     * @param key
     * @return 是否移除成功
     */
    public static boolean clearKey(String key) {
        return aCache.remove(key);
    }

    /**
     * 清除所有数据
     */
    public static void clearAll() {
        aCache.clear();
    }
}
