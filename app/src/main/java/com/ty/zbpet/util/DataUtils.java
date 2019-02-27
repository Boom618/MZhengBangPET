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
     * 文本数据
     */
    private static SparseArray<SparseArray<String>> positionAndContent = new SparseArray(5);
    private static SparseArray<String> contentArray = new SparseArray<>(20);

    /**
     * zkg 数据
     */
    private static SparseArray<SparseArray<String>> positionAndZkg = new SparseArray(5);
    private static SparseArray<String> zkgArray = new SparseArray<>(20);

    /**
     * 库位码 数据
     */
    private static SparseArray<SparseArray<String>> positionAndCode = new SparseArray(5);
    private static SparseArray<String> codeArray = new SparseArray<>(20);

    /**
     * 开始码 数据
     */
    private static SparseArray<SparseArray<String>> positionAndStartCode = new SparseArray(5);
    private static SparseArray<String> startCodeArray = new SparseArray<>(20);

    /**
     * 结束码 数据
     */
    private static SparseArray<SparseArray<String>> positionAndEndCode = new SparseArray(5);
    private static SparseArray<String> endCodeArray = new SparseArray<>(20);

    /**
     * SAP 数据
     */
    private static SparseArray<SparseArray<String>> positionAndSap = new SparseArray(5);
    private static SparseArray<String> sapArray = new SparseArray<>(20);

    /**
     * 库位码 焦点 数据
     */
    private static SparseArray<String> codeFocusArray = new SparseArray<>(5);

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
        positionAndWhich.put(CodeConstant.INSTANCE.getSELECT_HOUSE_BUY_IN(), sparseArray);
    }

    public static SparseArray<Integer> getHouseId() {

        SparseArray<Integer> houseIdSArray = positionAndWhich.get(CodeConstant.INSTANCE.getSELECT_HOUSE_BUY_IN());
        if (houseIdSArray == null) {
            houseIdSArray = new SparseArray<>();
            houseIdSArray.put(0, 0);
        }

        // 取值:选择的是哪个仓库
        //int which = houseIdSArray.get(position);

        return houseIdSArray;
    }


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
     * 输入框 中文本数据
     *
     * @param position
     * @param content
     */
    public static void setContent(int position, String content) {
        contentArray.put(position, content);
        positionAndContent.put(CodeConstant.INSTANCE.getET_CONTENT_INT(), contentArray);
    }

    public static SparseArray<String> getContent() {
        SparseArray<String> contentArray = positionAndContent.get(CodeConstant.INSTANCE.getET_CONTENT_INT());
        if (contentArray == null) {
            contentArray = new SparseArray<>();
            contentArray.put(0, "");
        }
        return contentArray;
    }

    /**
     * 输入框 zkg 数据
     *
     * @param position
     * @param zkg
     */
    public static void setZkg(int position, String zkg) {
        zkgArray.put(position, zkg);
        positionAndZkg.put(CodeConstant.INSTANCE.getET_ZKG_INT(), zkgArray);
    }

    public static SparseArray<String> getZkg() {
        SparseArray<String> zkgArray = positionAndZkg.get(CodeConstant.INSTANCE.getET_ZKG_INT());
        if (zkgArray == null) {
            zkgArray = new SparseArray<>();
            zkgArray.put(0, "");
        }
        return zkgArray;
    }

    /**
     * 输入框 库位码 数据
     *
     * @param position
     * @param coed
     */
    public static void setCode(int position, String coed) {
        codeArray.put(position, coed);
        positionAndCode.put(CodeConstant.INSTANCE.getET_CODE_INT(), codeArray);
    }

    public static SparseArray<String> getCode() {
        SparseArray<String> codeArray = positionAndCode.get(CodeConstant.INSTANCE.getET_CODE_INT());
        if (codeArray == null) {
            codeArray = new SparseArray<>();
            codeArray.put(0, "");
        }
        return codeArray;
    }

    /**
     * 输入框 开始码 数据
     *
     * @param position
     * @param coed
     */
    public static void setStartCode(int position, String coed) {
        startCodeArray.put(position, coed);
        positionAndStartCode.put(CodeConstant.INSTANCE.getET_START_CODE_INT(), startCodeArray);
    }

    public static SparseArray<String> getStartCode() {
        SparseArray<String> startCodeArray = positionAndStartCode.get(CodeConstant.INSTANCE.getET_START_CODE_INT());
        if (startCodeArray == null) {
            startCodeArray = new SparseArray<>();
            startCodeArray.put(0, "");
        }
        return startCodeArray;
    }

    /**
     * 输入框 结束码 数据
     *
     * @param position
     * @param coed
     */
    public static void setEndCode(int position, String coed) {
        endCodeArray.put(position, coed);
        positionAndEndCode.put(CodeConstant.INSTANCE.getET_END_CODE_INT(), endCodeArray);
    }

    public static SparseArray<String> getEndCode() {
        SparseArray<String> endCodeArray = positionAndEndCode.get(CodeConstant.INSTANCE.getET_END_CODE_INT());
        if (endCodeArray == null) {
            endCodeArray = new SparseArray<>();
            endCodeArray.put(0, "");
        }
        return endCodeArray;
    }

    /**
     * 输入框 SAP 数据
     *
     * @param position
     * @param sap
     */
    public static void setSap(int position, String sap) {
        sapArray.put(position, sap);
        positionAndSap.put(CodeConstant.INSTANCE.getET_SAP_INT(), sapArray);
    }

    public static SparseArray<String> getSap() {
        SparseArray<String> sapArray = positionAndSap.get(CodeConstant.INSTANCE.getET_SAP_INT());
        if (sapArray == null) {
            sapArray = new SparseArray<>();
            sapArray.put(0, "");
        }
        return sapArray;
    }

    /**
     * 库位码是否有焦点 数据
     *
     * @param position
     * @param focus
     */
    public static void setCodeFocus(int position, boolean focus) {
        codeFocusArray.put(CodeConstant.INSTANCE.getET_CODE_FOCUS_INT(), position + "@" + focus);
    }

    public static String getCodeFocus() {

        return codeFocusArray.get(CodeConstant.INSTANCE.getET_CODE_FOCUS_INT());
    }

    /**
     * 用户选择图片存储
     *
     * @param position item 位置
     * @param fileName 文件名
     */
    public static void setImageId(int position, String fileName) {
        imageArray.put(position, fileName);
        positionAndFile.put(CodeConstant.INSTANCE.getSELECT_IMAGE(), imageArray);
    }

    public static SparseArray<String> getImageFileName() {

        SparseArray<String> imageFile = positionAndFile.get(CodeConstant.INSTANCE.getSELECT_IMAGE());
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
        positionAndNumber.put(CodeConstant.INSTANCE.getET_NUMBER_INT(), numberArray);
    }

    public static SparseArray<String> getNumber() {
        SparseArray<String> number = positionAndNumber.get(CodeConstant.INSTANCE.getET_NUMBER_INT());
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
        positionAndPercent.put(CodeConstant.INSTANCE.getET_PERCENT_INT(), percentArray);
    }

    public static SparseArray<String> getPercent() {
        SparseArray<String> percent = positionAndPercent.get(CodeConstant.INSTANCE.getET_PERCENT_INT());
        if (percent == null) {
            percent = new SparseArray<>();
            percent.put(0, "");
        }
        return percent;
    }


    /**
     * 清除 sparseArray 数据
     */
    public static void clearId() {
        positionAndWhich.put(CodeConstant.INSTANCE.getSELECT_HOUSE_BUY_IN(), null);
        positionAndWhich.put(CodeConstant.INSTANCE.getSELECT_GOODS(), null);
        positionAndFile.put(CodeConstant.INSTANCE.getSELECT_IMAGE(), null);
        positionAndNumber.put(CodeConstant.INSTANCE.getET_NUMBER_INT(), null);
        positionAndPercent.put(CodeConstant.INSTANCE.getET_PERCENT_INT(), null);
        positionAndContent.put(CodeConstant.INSTANCE.getET_CONTENT_INT(), null);
        positionAndZkg.put(CodeConstant.INSTANCE.getET_ZKG_INT(), null);
        positionAndCode.put(CodeConstant.INSTANCE.getET_CODE_INT(), null);
        positionAndStartCode.put(CodeConstant.INSTANCE.getET_START_CODE_INT(), null);
        positionAndEndCode.put(CodeConstant.INSTANCE.getET_END_CODE_INT(), null);
        positionAndSap.put(CodeConstant.INSTANCE.getET_SAP_INT(), null);
        codeFocusArray.put(CodeConstant.INSTANCE.getET_CODE_FOCUS_INT(), null);
    }
}
