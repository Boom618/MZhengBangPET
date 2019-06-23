package com.ty.zbpet.mode;

import org.litepal.crud.LitePalSupport;

/**
 * @author TY
 * @Date: 2019/6/21 16:35
 * @Description:
 */
public class Material extends LitePalSupport {

    /**
     * 位置、单位、入库数量、选择冲销、冲销数量
     * 行备注、库位码、含量、SAP 批次号
     */
    private int index;
    private String unit;
    private String number;
    private String isReversal;
    private String reversalNumber;
    private String remarkSub;
    private String positionNo;
    private String concentration;
    private String sapMaterialBatchNo;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIsReversal() {
        return isReversal;
    }

    public void setIsReversal(String isReversal) {
        this.isReversal = isReversal;
    }

    public String getReversalNumber() {
        return reversalNumber;
    }

    public void setReversalNumber(String reversalNumber) {
        this.reversalNumber = reversalNumber;
    }

    public String getRemarkSub() {
        return remarkSub;
    }

    public void setRemarkSub(String remarkSub) {
        this.remarkSub = remarkSub;
    }

    public String getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(String positionNo) {
        this.positionNo = positionNo;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getSapMaterialBatchNo() {
        return sapMaterialBatchNo;
    }

    public void setSapMaterialBatchNo(String sapMaterialBatchNo) {
        this.sapMaterialBatchNo = sapMaterialBatchNo;
    }
}
