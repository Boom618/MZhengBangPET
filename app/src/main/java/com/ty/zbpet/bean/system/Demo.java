package com.ty.zbpet.bean.system;

import java.util.List;

/**
 * @author TY on 2019/3/31.
 */
public class Demo {

    /**
     * positionNo : Y00001
     * warehouseNo : 0001
     * details : [{"stockId":416,"sapOrderNo":"000090017455","materialName":"原药_阿维菌素","materialNo":"10000066","supplierNo":"121321","sapBatchNo":"2345","supplierName":"测试","concentration":0.2,"number":478,"checkNumber":600,"unit":"KG"}]
     */

    private String positionNo;
    private String warehouseNo;
    private List<DetailsBean> details;

    public String getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(String positionNo) {
        this.positionNo = positionNo;
    }

    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * stockId : 416
         * sapOrderNo : 000090017455
         * materialName : 原药_阿维菌素
         * materialNo : 10000066
         * supplierNo : 121321
         * sapBatchNo : 2345
         * supplierName : 测试
         * concentration : 0.2
         * number : 478
         * checkNumber : 600
         * unit : KG
         */

        private int stockId;
        private String sapOrderNo;
        private String materialName;
        private String materialNo;
        private String supplierNo;
        private String sapBatchNo;
        private String supplierName;
        private double concentration;
        private int number;
        private int checkNumber;
        private String unit;

        public int getStockId() {
            return stockId;
        }

        public void setStockId(int stockId) {
            this.stockId = stockId;
        }

        public String getSapOrderNo() {
            return sapOrderNo;
        }

        public void setSapOrderNo(String sapOrderNo) {
            this.sapOrderNo = sapOrderNo;
        }

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public String getMaterialNo() {
            return materialNo;
        }

        public void setMaterialNo(String materialNo) {
            this.materialNo = materialNo;
        }

        public String getSupplierNo() {
            return supplierNo;
        }

        public void setSupplierNo(String supplierNo) {
            this.supplierNo = supplierNo;
        }

        public String getSapBatchNo() {
            return sapBatchNo;
        }

        public void setSapBatchNo(String sapBatchNo) {
            this.sapBatchNo = sapBatchNo;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public double getConcentration() {
            return concentration;
        }

        public void setConcentration(double concentration) {
            this.concentration = concentration;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getCheckNumber() {
            return checkNumber;
        }

        public void setCheckNumber(int checkNumber) {
            this.checkNumber = checkNumber;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
