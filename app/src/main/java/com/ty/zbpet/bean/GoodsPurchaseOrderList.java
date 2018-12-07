//package com.ty.zbpet.bean;
//
//import java.util.List;
//
///**
// * 成品——外采入库待办列表
// */
//public class GoodsPurchaseOrderList {
//
//    /**
//     * tag : success
//     * error :
//     * message :
//     * data : {"list":[{"sapOrderNo":"201808071024","state":"状态","type":"采购类型","supplierName":"正邦上海宝山淞南分部供应商","supplierId":"12","supplierNo":"20180910","orderTime":"2018/09/10"},{"sapOrderNo":"201808071024","state":"状态","type":"采购类型","supplierName":"正邦上海宝山淞南分部供应商","supplierId":"12","supplierNo":"20180910","orderTime":"2018/09/10"},{"sapOrderNo":"201808071024","state":"状态","type":"采购类型","supplierName":"正邦上海宝山淞南分部供应商","supplierId":"12","supplierNo":"20180910","orderTime":"2018/09/10"}]}
//     */
//
//    private String tag;
//    private String error;
//    private String message;
//    private DataBean data;
//
//    public String getTag() {
//        return tag;
//    }
//
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
//
//    public String getError() {
//        return error;
//    }
//
//    public void setError(String error) {
//        this.error = error;
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
//             * sapOrderNo : 201808071024
//             * state : 状态
//             * type : 采购类型
//             * supplierName : 正邦上海宝山淞南分部供应商
//             * supplierId : 12
//             * supplierNo : 20180910
//             * orderTime : 2018/09/10
//             */
//
//            private String sapOrderNo;
//            private String state;
//            private String type;
//            private String supplierName;
//            private String supplierId;
//            private String supplierNo;
//            private String orderTime;
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
//            public String getSupplierNo() {
//                return supplierNo;
//            }
//
//            public void setSupplierNo(String supplierNo) {
//                this.supplierNo = supplierNo;
//            }
//
//            public String getOrderTime() {
//                return orderTime;
//            }
//
//            public void setOrderTime(String orderTime) {
//                this.orderTime = orderTime;
//            }
//        }
//    }
//}
