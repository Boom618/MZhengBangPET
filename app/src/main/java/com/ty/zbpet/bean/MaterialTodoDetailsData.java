//package com.ty.zbpet.bean;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * @author TY on 2018/11/5.
// * 到货入库 待办 list 详情
// *
// */
//public class MaterialTodoDetailsData implements Serializable {
//
//
//    /**
//     * warehouseId : 3
//     * inStoreDate : 2018-09-06
//     * sapProcOrder : SAP00009  // sapProcOrder 全部换成 sapOrderNo
//     * details : [{"positionId":"1","supplierId":"12","number":"200","materialId":"1","concentration":"80","remark":"1"},{"positionId":"4","supplierId":"12","number":"300","materialId":"12","concentration":"90","remark":"1"}]
//     */
//
//    private String warehouseId;
//    private String inStoreDate;
//    private String sapOrderNo;
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
//    public String getSapOrderNo() {
//        return sapOrderNo;
//    }
//
//    public void setSapOrderNo(String sapOrderNo) {
//        this.sapOrderNo = sapOrderNo;
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
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    public static class DetailsBean {
//        /**
//         * positionId : 1
//         * supplierId : 12
//         * supplierNo ： 供应商 信息
//         * number : 200
//         * sapMaterialBatchNo : sap物料批次号
//         * materialName: "原辅料名称"
//         * unit: "箱"
//         * materialId : 1
//         * orderNumber   订单数量
//         * concentration : 80 浓度
//         * remark : 1
//         */
//
//        private String positionId;
//        private String supplierId;
//        private String supplierNo;
//        private String number;
//        private String unitS;
//        private String materialId;
//        private String orderNumber;
//        private String concentration;
//        private String materialName;
//        private String sapMaterialBatchNo;
//
//        public String getPositionId() {
//            return positionId;
//        }
//
//        public void setPositionId(String positionId) {
//            this.positionId = positionId;
//        }
//
//        public String getSupplierNo() {
//            return supplierNo;
//        }
//
//        public void setSupplierNo(String supplierNo) {
//            this.supplierNo = supplierNo;
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
//        public String getUnitS() {
//            return unitS;
//        }
//
//        public void setUnitS(String unit) {
//            this.unitS = unit;
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
//        public String getMaterialName() {
//            return materialName;
//        }
//
//        public void setMaterialName(String materialName) {
//            this.materialName = materialName;
//        }
//
//        public String getOrderNumber() {
//            return orderNumber;
//        }
//
//        public void setOrderNumber(String orderNumber) {
//            this.orderNumber = orderNumber;
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
//        public String getSapMaterialBatchNo() {
//            return sapMaterialBatchNo;
//        }
//
//        public void setSapMaterialBatchNo(String sapMaterialBatchNo) {
//            this.sapMaterialBatchNo = sapMaterialBatchNo;
//        }
//    }
//}
