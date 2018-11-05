package com.ty.zbpet.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author TY on 2018/11/5.
 * 到货入库 待办 list 详情
 *
 */
public class MaterialDetailsData implements Serializable {


    /**
     * sapOrderNo : SAP0001
     * list : [{"materialName":"原辅料名称","materialId":"15","materialNo":"原辅料编号","concentration":"80"},{"materialName":"原辅料名称","materialId":"14","materialNo":"原辅料编号","concentration":"75"},{"materialName":"原辅料名称","materialId":"13","materialNo":"原辅料编号","concentration":"90"},{"materialName":"原辅料名称","materialId":"12","materialNo":"原辅料编号","concentration":"50"}]
     */

    private String sapOrderNo;

    private String warehouseId;

    private List<ListBean> list;

    public String getSapOrderNo() {
        return sapOrderNo;
    }

    public void setSapOrderNo(String sapOrderNo) {
        this.sapOrderNo = sapOrderNo;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * materialName : 原辅料名称
         * materialId : 15
         * materialNo : 原辅料编号
         * concentration : 80
         */

        private String materialName;
        private String materialId;
        private String materialNo;
        private String concentration;

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

        public String getMaterialNo() {
            return materialNo;
        }

        public void setMaterialNo(String materialNo) {
            this.materialNo = materialNo;
        }

        public String getConcentration() {
            return concentration;
        }

        public void setConcentration(String concentration) {
            this.concentration = concentration;
        }
    }
}
