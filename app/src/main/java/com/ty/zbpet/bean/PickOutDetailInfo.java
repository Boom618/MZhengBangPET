package com.ty.zbpet.bean;

import java.util.List;

/**
 * @author TY on 2018/10/29.
 * 领料出库- 列表
 */
public class PickOutDetailInfo {

    /**
     * warehouseId : 3
     * outStoreDate : 2018-09-06
     * sapMaterialBatchNo : sap物料批次号
     * sapPlantNo : SAP00009
     * remark : 1
     * details : [{"positionId":"1","supplierId":"12","number":"200","materialId":"1","concentration":"80","remark":"1"},{"positionId":"4","supplierId":"12","number":"300","materialId":"12","concentration":"90","remark":"1"}]
     */

    private String warehouseId;
    private String outStoreDate;
    private String sapMaterialBatchNo;
    private String sapPlantNo;
    private String remark;
    private List<DetailsBean> details;

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getOutStoreDate() {
        return outStoreDate;
    }

    public void setOutStoreDate(String outStoreDate) {
        this.outStoreDate = outStoreDate;
    }

    public String getSapMaterialBatchNo() {
        return sapMaterialBatchNo;
    }

    public void setSapMaterialBatchNo(String sapMaterialBatchNo) {
        this.sapMaterialBatchNo = sapMaterialBatchNo;
    }

    public String getSapPlantNo() {
        return sapPlantNo;
    }

    public void setSapPlantNo(String sapPlantNo) {
        this.sapPlantNo = sapPlantNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * positionId : 1
         * supplierId : 12
         * number : 200
         * materialId : 1
         * concentration : 80
         * remark : 1
         */

        private String positionId;
        private String supplierId;
        private String number;
        private String materialId;
        private String concentration;
        private String remark;

        public String getPositionId() {
            return positionId;
        }

        public void setPositionId(String positionId) {
            this.positionId = positionId;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
