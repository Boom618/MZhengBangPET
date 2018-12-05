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
            }else {
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

    private static SparseArray<SparseArray<Integer>> houseId = new SparseArray(5);
    private static SparseArray<Integer> sparseArray = new SparseArray<>(20);

    /**
     * SparseArray<SparseArray<Integer>> 采用这种结构是 key 必须唯一
     * <p>
     * SparseArray<Integer> 中
     * key 代表 item 中的 position 位置
     * which 代表 仓库选中的位置
     *
     * @param position
     * @param which
     */
    public static void setHouseId(int position, int which) {

        sparseArray.put(position, which);
        houseId.put(CodeConstant.SELECT_HOUSE_SA, sparseArray);
    }

    public static SparseArray<Integer> getHouseId() {

        SparseArray<Integer> houseIdSArray = houseId.get(CodeConstant.SELECT_HOUSE_SA);

        // 取值:选择的是哪个仓库
        //int which = houseIdSArray.get(position);

        return houseIdSArray;
    }

    public static void clearHouseId(){
        houseId.put(CodeConstant.SELECT_HOUSE_SA,null);
    }

}
