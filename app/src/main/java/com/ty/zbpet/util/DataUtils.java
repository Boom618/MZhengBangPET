package com.ty.zbpet.util;

import android.util.SparseArray;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ty.zbpet.bean.UserInfo;
import com.ty.zbpet.constant.CodeConstant;

import java.util.ArrayList;

/**
 * @author TY
 */
public class DataUtils {
    /**
     * 将实体类转换成json字符串对象
     *
     * @param obj 对象
     * @return map
     */
    public static String toJson(Object obj, int method) {
        // TODO Auto-generated method stub
        if (method == 1) {
            //字段是首字母小写，其余单词首字母大写
            Gson gson = new Gson();
            String obj2 = gson.toJson(obj);
            return obj2;
        } else if (method == 2) {
            // FieldNamingPolicy.LOWER_CASE_WITH_DASHES:全部转换为小写，并用空格或者下划线分隔
            //FieldNamingPolicy.UPPER_CAMEL_CASE    所以单词首字母大写
            Gson gson2 = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            String obj2 = gson2.toJson(obj);
            return obj2;
        }
        return "";
    }

    /**
     * 000123 >  123
     * KW004 > KW004
     *
     * @param s
     * @return
     */
    public static String string2Int(String s) {

        String temp = s;
        try {
            temp = String.valueOf(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return temp;
    }


    /**
     * 假数据 处理
     *
     * @return
     */
    public static UserInfo getUserInfo() {

        UserInfo userInfo = new UserInfo();

        ArrayList<UserInfo.WarehouseListBean> userList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                UserInfo.WarehouseListBean bean = new UserInfo.WarehouseListBean();
                bean.setWarehouseId("3");
                bean.setWarehouseName("仓库1001");
                bean.setWarehouseNo("1001");
                userList.add(bean);
            } else {
                UserInfo.WarehouseListBean bean = new UserInfo.WarehouseListBean();
                bean.setWarehouseId("10");
                bean.setWarehouseName("仓库0002");
                bean.setWarehouseNo("0002");
                userList.add(bean);
            }
        }

        userInfo.setUserName("正邦用户");
        userInfo.setWarehouseList(userList);

        return userInfo;
    }

    /**
     * 成品 仓库和商品
     */
    private static SparseArray<SparseArray<Integer>> positionAndWhich = new SparseArray(5);
    private static SparseArray<Integer> sparseArray = new SparseArray<>(20);

    /**
     * 质检图片
     */
    private static ArrayList<String> imageList = new ArrayList<>(5);


    /**
     * 发货出库 中的商品存值算法
     *
     * @param position
     * @param which
     */
    public static void setGoodsId(int position, int which) {
        sparseArray.put(position, which);
        positionAndWhich.put(CodeConstant.INSTANCE.getSELECT_GOODS(), sparseArray);
    }

    public static SparseArray<Integer> getGoodsId() {
        SparseArray<Integer> goodsArray = positionAndWhich.get(CodeConstant.INSTANCE.getSELECT_GOODS());
        if (goodsArray == null) {
            goodsArray = new SparseArray<>();
            goodsArray.put(0, 0);
        }
        return goodsArray;
    }



    /**
     * ----- 保存质检图片 --------
     */
    public static void saveImage(String fileName) {
        imageList.add(fileName);
    }

    public static ArrayList<String> getImagePathList() {
        return imageList;
    }

    public static void clearImagePath() {
        if (imageList != null) {
            imageList.clear();
        }
    }


    /**
     * 清除 sparseArray 数据
     */
    public static void clearId() {
        positionAndWhich.put(CodeConstant.SELECT_HOUSE_BUY_IN, null);
        positionAndWhich.put(CodeConstant.INSTANCE.getSELECT_GOODS(), null);
    }
}
