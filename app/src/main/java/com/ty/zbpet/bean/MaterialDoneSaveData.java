package com.ty.zbpet.bean;

/**
 * @author TY on 2018/11/15.
 *
 * 已办 保存
 */
public class MaterialDoneSaveData {


    /**
     * warehouseId : 3
     * inOutOrderId : 26        // mInWarehouseOrderId
     * sapProcOrder : SAP00010  // sapOrderNo
     * positionId : 123
     * remark : 冲销
     */

    private String warehouseId;
    private String inOutOrderId;
    private String sapProcOrder;
    private String positionId;
    private String remark;

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
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

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
