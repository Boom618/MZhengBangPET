package com.ty.zbpet.util;

import android.util.SparseArray;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ty.zbpet.bean.UserInfo;

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
                bean.setWarehouseName("仓库002");
                bean.setWarehouseNo("CK201808000003");
                userList.add(bean);
            } else {
                UserInfo.WarehouseListBean bean = new UserInfo.WarehouseListBean();
                bean.setWarehouseId("10");
                bean.setWarehouseName("仓库001");
                bean.setWarehouseNo("CK201808000008");
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
    private static SparseArray<SparseArray<String>> positionAndFile = new SparseArray<>(5);
    private static SparseArray<String> imageArray = new SparseArray<>(20);

    /**
     * 质检数量
     */
    private static SparseArray<SparseArray<String>> positionAndNumber = new SparseArray<>(5);
    private static SparseArray<String> numberArray = new SparseArray<>(20);

    /**
     * 质检含量
     */
    private static SparseArray<SparseArray<String>> positionAndPercent = new SparseArray<>(5);
    private static SparseArray<String> percentArray = new SparseArray<>(20);

    /**
     * SparseArray<SparseArray<Integer>> 采用这种结构是 key 必须唯一
     * <p>
     * SparseArray<Integer> 中
     * 外采入库 中仓库存值算法
     *
     * @param position 代表 item 中的 position 位置
     * @param which    代表仓库选中的位置
     */
    public static void setHouseId(int position, int which) {

        sparseArray.put(position, which);
        positionAndWhich.put(CodeConstant.SELECT_HOUSE_BUY_IN, sparseArray);
    }

    public static SparseArray<Integer> getHouseId() {

        SparseArray<Integer> houseIdSArray = positionAndWhich.get(CodeConstant.SELECT_HOUSE_BUY_IN);
        if (houseIdSArray == null) {
            houseIdSArray = new SparseArray<>();
            houseIdSArray.put(0, 0);
        }

        // 取值:选择的是哪个仓库
        //int which = houseIdSArray.get(position);

        return houseIdSArray;
    }

    public static void clearId() {
        positionAndWhich.put(CodeConstant.SELECT_HOUSE_BUY_IN, null);
        positionAndWhich.put(CodeConstant.SELECT_GOODS, null);
    }


    /**
     * 发货出库 中的商品存值算法
     *
     * @param position
     * @param which
     */
    public static void setGoodsId(int position, int which) {
        sparseArray.put(position, which);
        positionAndWhich.put(CodeConstant.SELECT_GOODS, sparseArray);
    }

    public static SparseArray<Integer> getGoodsId() {
        SparseArray<Integer> goodsArray = positionAndWhich.get(CodeConstant.SELECT_GOODS);
        if (goodsArray == null) {
            goodsArray = new SparseArray<>();
            goodsArray.put(0, 0);
        }
        return goodsArray;
    }

    /**
     * 用户选择图片存储
     *
     * @param position item 位置
     * @param fileName 文件名
     */
    public static void setImageId(int position, String fileName) {
        imageArray.put(position, fileName);
        positionAndFile.put(CodeConstant.SELECT_IMAGE, imageArray);
    }

    public static SparseArray<String> getImageFileName() {

        SparseArray<String> imageFile = positionAndFile.get(CodeConstant.SELECT_IMAGE);
        if (imageFile == null) {
            imageFile = new SparseArray<>();
            imageFile.put(0, "");
        }
        return imageFile;
    }

    /**
     * 质检 用户输入数量
     *
     * @param position item 位置
     * @param percent  数量
     */
    public static void setNumber(int position, String percent) {
        numberArray.put(position, percent);
        positionAndNumber.put(CodeConstant.ET_NUMBER_INT, numberArray);
    }

    public static SparseArray<String> getNumber() {
        SparseArray<String> number = positionAndNumber.get(CodeConstant.ET_NUMBER_INT);
        if (number == null) {
            number = new SparseArray<>();
            number.put(0, "");
        }
        return number;
    }

    /**
     * 质检 用户输入含量
     *
     * @param position item 位置
     * @param percent  含量
     */
    public static void setPercent(int position, String percent) {
        percentArray.put(position, percent);
        positionAndPercent.put(CodeConstant.ET_PERCENT_INT, percentArray);
    }

    public static SparseArray<String> getPercent() {
        SparseArray<String> percent = positionAndPercent.get(CodeConstant.ET_PERCENT_INT);
        if (percent == null) {
            percent = new SparseArray<>();
            percent.put(0, "");
        }
        return percent;
    }
}
