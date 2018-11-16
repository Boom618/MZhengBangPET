package com.ty.zbpet.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
     * @param status
     * @return
     */
//    public static ArrayList<MaterialInWarehouseOrderList.DataBean.ListBean> getData(String status) {
//        ArrayList<MaterialInWarehouseOrderList.DataBean.ListBean> list = new ArrayList<>();
//
//        int i = 0;
//        for (; i < 10; i++) {
//            MaterialInWarehouseOrderList.DataBean.ListBean bean = new MaterialInWarehouseOrderList.DataBean.ListBean();
//            bean.setOrderId("1000" + i);
//            bean.setOrderTime("2018-10-25");
//            bean.setSapOrderNo("SAP00000" + i);
//            bean.setState(status + i);
//            bean.setSupplierId(String.valueOf(i));
//            bean.setSupplierName("供应商 " + i);
//            bean.setType(status);
//
//            list.add(bean);
//        }
//        return list;
//    }

}
