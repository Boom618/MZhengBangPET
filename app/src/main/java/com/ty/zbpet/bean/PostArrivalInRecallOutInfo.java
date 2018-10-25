package com.ty.zbpet.bean;

/**
 * 采购入库冲销出库--上传的参数
 */
public class PostArrivalInRecallOutInfo {
    private String warehouseId;//仓库Id
    private String sapMaterialBatchNo;//物料批次号
    private String outStoreDate;//出库时间
    private String inOutOrderId;//单据号
    private String sapProcOrder;//Sap单据号
    private String remark;//备注

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getSapMaterialBatchNo() {
        return sapMaterialBatchNo;
    }

    public void setSapMaterialBatchNo(String sapMaterialBatchNo) {
        this.sapMaterialBatchNo = sapMaterialBatchNo;
    }

    public String getOutStoreDate() {
        return outStoreDate;
    }

    public void setOutStoreDate(String outStoreDate) {
        this.outStoreDate = outStoreDate;
    }

    public String getInOutOrderId() {
        return inOutOrderId;
    }

    public void setInOutOrderId(String inOutOrderId) {
        this.inOutOrderId = inOutOrderId;
    }

    public String getSapProcOrder() {
        return sapProcOrder;
    }

    public void setSapProcOrder(String sapProcOrder) {
        this.sapProcOrder = sapProcOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
