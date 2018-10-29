package com.ty.zbpet.bean;


import java.io.Serializable;
import java.util.List;

/**
 * @author TY on 2018/10/26.
 */
public class MaterialData implements Serializable {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * sapOrderNo : SAP00002
         * state : 已入库
         * type : 采购入库
         * supplierName : 正邦上海宝山分部供应商
         * supplierId : 12
         * orderTime : 2018/09/19
         * orderId : 55
         */

        private String sapOrderNo;
        private String state;
        private String type;
        private String supplierName;
        private String supplierId;
        private String orderTime;
        private String orderId;

        public String getSapOrderNo() {
            return sapOrderNo;
        }

        public void setSapOrderNo(String sapOrderNo) {
            this.sapOrderNo = sapOrderNo;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}
