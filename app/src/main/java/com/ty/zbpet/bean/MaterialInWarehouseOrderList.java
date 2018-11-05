//package com.ty.zbpet.bean;
//
//import java.util.List;
//
///**
// * 原辅料采购已办列表
// * @author TY
// */
//public class MaterialInWarehouseOrderList {
//
//    /**
//     * tag : success
//     * message :
//     * data : {"list":[{"sapOrderNo":"SAP00002","state":"已入库","type":"采购入库","supplierName":"正邦上海宝山分部供应商","supplierId":"12","orderTime":"2018/09/19","orderId":"55"},{"sapOrderNo":"SAP00003","state":"已入库","type":"采购入库","supplierName":"正邦上海宝山分部供应商","supplierId":"12","orderTime":"2018/09/20","orderId":"56"},{"sapOrderNo":"SAP00004","state":"已入库","type":"采购入库","supplierName":"正邦上海宝山分部供应商","supplierId":"12","orderTime":"2018/09/21","orderId":"57"}]}
//     */
//
//    private String tag;
//    private String message;
//    private String error;
//    private DataBean data;
//
//    public String getError() {
//        return error;
//    }
//
//    public void setError(String error) {
//        this.error = error;
//    }
//
//    public String getTag() {
//        return tag;
//    }
//
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        private List<ListBean> list;
//
//        public List<ListBean> getList() {
//            return list;
//        }
//
//        public void setList(List<ListBean> list) {
//            this.list = list;
//        }
//
//        public static class ListBean {
//            /**
//             * sapOrderNo : SAP00002
//             * state : 已入库
//             * type : 采购入库
//             * supplierName : 正邦上海宝山分部供应商
//             * supplierId : 12
//             * orderTime : 2018/09/19
//             * orderId : 55
//             */
//
//            private String sapOrderNo;
//            private String state;
//            private String type;
//            private String supplierName;
//            private String supplierId;
//            private String orderTime;
//            private String orderId;
//
//            public String getSapOrderNo() {
//                return sapOrderNo;
//            }
//
//            public void setSapOrderNo(String sapOrderNo) {
//                this.sapOrderNo = sapOrderNo;
//            }
//
//            public String getState() {
//                return state;
//            }
//
//            public void setState(String state) {
//                this.state = state;
//            }
//
//            public String getType() {
//                return type;
//            }
//
//            public void setType(String type) {
//                this.type = type;
//            }
//
//            public String getSupplierName() {
//                return supplierName;
//            }
//
//            public void setSupplierName(String supplierName) {
//                this.supplierName = supplierName;
//            }
//
//            public String getSupplierId() {
//                return supplierId;
//            }
//
//            public void setSupplierId(String supplierId) {
//                this.supplierId = supplierId;
//            }
//
//            public String getOrderTime() {
//                return orderTime;
//            }
//
//            public void setOrderTime(String orderTime) {
//                this.orderTime = orderTime;
//            }
//
//            public String getOrderId() {
//                return orderId;
//            }
//
//            public void setOrderId(String orderId) {
//                this.orderId = orderId;
//            }
//        }
//    }
//}
