//package com.ty.zbpet.bean;
//
//import java.util.List;
//
///**
// * @author TY on 2018/11/7.
// *
// * 待办 保存
// *
// */
//public class MaterialPurchaseInData {
//
//    /**
//     * warehouseId : 3
//     * inStoreDate : 2018-09-06
//     * sapProcOrder : SAP00009
//     * remark : 1
//     * details : [{"positionId":"1","supplierId":"12","number":"200","materialId":"1","concentration":"80","remark":"1"},{"positionId":"4","supplierId":"12","number":"300","materialId":"12","concentration":"90","remark":"1"}]
//     */
//
//    private String warehouseId;
//    private String inStoreDate;
//    private String sapProcOrder;
//    private String remark;
//    private List<DetailsBean> details;
//
//    public String getWarehouseId() {
//        return warehouseId;
//    }
//
//    public void setWarehouseId(String warehouseId) {
//        this.warehouseId = warehouseId;
//    }
//
//    public String getInStoreDate() {
//        return inStoreDate;
//    }
//
//    public void setInStoreDate(String inStoreDate) {
//        this.inStoreDate = inStoreDate;
//    }
//
//    public String getSapProcOrder() {
//        return sapProcOrder;
//    }
//
//    public void setSapProcOrder(String sapProcOrder) {
//        this.sapProcOrder = sapProcOrder;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    public List<DetailsBean> getDetails() {
//        return details;
//    }
//
//    public void setDetails(List<DetailsBean> details) {
//        this.details = details;
//    }
//
//    public static class DetailsBean {
//        /**
//         * positionId : 1
//         * supplierId : 12
//         * number : 200
//         * sapMaterialBatchNo : sap物料批次号
//         * materialId : 1
//         * concentration : 80
//         * remark : 1
//         */
//
//        private String positionId;
//        private String supplierId;
//        private String number;
//        private String sapMaterialBatchNo;
//        private String materialId;
//        private String concentration;
//        private String remark;
//
//        public String getPositionId() {
//            return positionId;
//        }
//
//        public void setPositionId(String positionId) {
//            this.positionId = positionId;
//        }
//
//        public String getSupplierId() {
//            return supplierId;
//        }
//
//        public void setSupplierId(String supplierId) {
//            this.supplierId = supplierId;
//        }
//
//        public String getNumber() {
//            return number;
//        }
//
//        public void setNumber(String number) {
//            this.number = number;
//        }
//
//        public String getSapMaterialBatchNo() {
//            return sapMaterialBatchNo;
//        }
//
//        public void setSapMaterialBatchNo(String sapMaterialBatchNo) {
//            this.sapMaterialBatchNo = sapMaterialBatchNo;
//        }
//
//        public String getMaterialId() {
//            return materialId;
//        }
//
//        public void setMaterialId(String materialId) {
//            this.materialId = materialId;
//        }
//
//        public String getConcentration() {
//            return concentration;
//        }
//
//        public void setConcentration(String concentration) {
//            this.concentration = concentration;
//        }
//
//        public String getRemark() {
//            return remark;
//        }
//
//        public void setRemark(String remark) {
//            this.remark = remark;
//        }
//    }
//}
