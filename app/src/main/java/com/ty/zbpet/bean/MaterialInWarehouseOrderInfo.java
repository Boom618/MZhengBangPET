//package com.ty.zbpet.bean;
//
//import java.util.List;
//
///**
// * 原辅料采购已办详情
// */
//public class MaterialInWarehouseOrderInfo {
//
//    /**
//     * tag : success
//     * message :
//     * data : {"sapOrderNo":"SAP0001","list":[{"materialName":"原辅料名称","materialId":"15","materialNo":"原辅料编号","concentration":"80"},{"materialName":"原辅料名称","materialId":"14","materialNo":"原辅料编号","concentration":"75"},{"materialName":"原辅料名称","materialId":"13","materialNo":"原辅料编号","concentration":"90"},{"materialName":"原辅料名称","materialId":"12","materialNo":"原辅料编号","concentration":"50"}]}
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
//        /**
//         * sapOrderNo : SAP0001
//         * list : [{"materialName":"原辅料名称","materialId":"15","materialNo":"原辅料编号","concentration":"80"},{"materialName":"原辅料名称","materialId":"14","materialNo":"原辅料编号","concentration":"75"},{"materialName":"原辅料名称","materialId":"13","materialNo":"原辅料编号","concentration":"90"},{"materialName":"原辅料名称","materialId":"12","materialNo":"原辅料编号","concentration":"50"}]
//         */
//
//        private String sapOrderNo;
//        private List<ListBean> list;
//        private String warehouseId;
//
//        public String getWarehouseId() {
//            return warehouseId;
//        }
//
//        public void setWarehouseId(String warehouseId) {
//            this.warehouseId = warehouseId;
//        }
//
//        public String getSapOrderNo() {
//            return sapOrderNo;
//        }
//
//        public void setSapOrderNo(String sapOrderNo) {
//            this.sapOrderNo = sapOrderNo;
//        }
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
//             * materialName : 原辅料名称
//             * materialId : 15
//             * materialNo : 原辅料编号
//             * concentration : 80
//             */
//
//            private String materialName;
//            private String materialId;
//            private String materialNo;
//            private String concentration;
//            private String unit;
//            private String  orderNumber;
//            private String positionId;//库位Id
//
//            public String getPositionId() {
//                return positionId;
//            }
//
//            public void setPositionId(String positionId) {
//                this.positionId = positionId;
//            }
//
//            public String getUnit() {
//                return unit;
//            }
//
//            public void setUnit(String unit) {
//                this.unit = unit;
//            }
//
//            public String getOrderNumber() {
//                return orderNumber;
//            }
//
//            public void setOrderNumber(String orderNumber) {
//                this.orderNumber = orderNumber;
//            }
//
//            public String getMaterialName() {
//                return materialName;
//            }
//
//            public void setMaterialName(String materialName) {
//                this.materialName = materialName;
//            }
//
//            public String getMaterialId() {
//                return materialId;
//            }
//
//            public void setMaterialId(String materialId) {
//                this.materialId = materialId;
//            }
//
//            public String getMaterialNo() {
//                return materialNo;
//            }
//
//            public void setMaterialNo(String materialNo) {
//                this.materialNo = materialNo;
//            }
//
//            public String getConcentration() {
//                return concentration;
//            }
//
//            public void setConcentration(String concentration) {
//                this.concentration = concentration;
//            }
//        }
//    }
//}
