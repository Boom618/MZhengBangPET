package com.ty.zbpet.bean;

import java.util.List;

/**
 * @author TY on 2018/11/14.
 * 已办 详情
 *
 */
public class MaterialDoneDetailsData {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 90
         * mInWarehouseOrderId : 68
         * mInWarehouseOrderNo : null
         * state : 1
         * positionId : 5
         * positionNo : KWM201809000001
         * sapStorageNo : null
         * materialNo : GYS2018000019
         * materialName : 绿豆——品种
         * materialId : 14
         * concentration : null
         * number : 200
         * purchaseNumber : null
         * supplierId : 12
         * expirationDate : null
         * productionDate : null
         * createdAt : 2018-09-17T03:45:47.000Z
         * updatedAt : 2018-09-17T03:45:47.000Z
         */

        private String id;
        private String mInWarehouseOrderId;
        private String mInWarehouseOrderNo;
        private String state;
        private String positionId;
        private String positionNo;
        private String sapStorageNo;
        private String materialNo;
        private String materialName;
        private String materialId;
        private String concentration;
        private String number;
        private String purchaseNumber;
        private String supplierId;
        private String expirationDate;
        private String productionDate;
        private String createdAt;
        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getmInWarehouseOrderId() {
            return mInWarehouseOrderId;
        }

        public void setmInWarehouseOrderId(String mInWarehouseOrderId) {
            this.mInWarehouseOrderId = mInWarehouseOrderId;
        }

        public String getmInWarehouseOrderNo() {
            return mInWarehouseOrderNo;
        }

        public void setmInWarehouseOrderNo(String mInWarehouseOrderNo) {
            this.mInWarehouseOrderNo = mInWarehouseOrderNo;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPositionId() {
            return positionId;
        }

        public void setPositionId(String positionId) {
            this.positionId = positionId;
        }

        public String getPositionNo() {
            return positionNo;
        }

        public void setPositionNo(String positionNo) {
            this.positionNo = positionNo;
        }

        public String getSapStorageNo() {
            return sapStorageNo;
        }

        public void setSapStorageNo(String sapStorageNo) {
            this.sapStorageNo = sapStorageNo;
        }

        public String getMaterialNo() {
            return materialNo;
        }

        public void setMaterialNo(String materialNo) {
            this.materialNo = materialNo;
        }

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public String getMaterialId() {
            return materialId;
        }

        public void setMaterialId(String materialId) {
            this.materialId = materialId;
        }

        public String getConcentration() {
            return concentration;
        }

        public void setConcentration(String concentration) {
            this.concentration = concentration;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPurchaseNumber() {
            return purchaseNumber;
        }

        public void setPurchaseNumber(String purchaseNumber) {
            this.purchaseNumber = purchaseNumber;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(String productionDate) {
            this.productionDate = productionDate;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
