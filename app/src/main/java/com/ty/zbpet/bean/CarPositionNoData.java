package com.ty.zbpet.bean;

import java.util.List;

/**
 * @author TY on 2018/11/16.
 *
 * 库位码 校验
 */
public class CarPositionNoData {


    /**
     * tag : success
     * status : 100
     * message :
     * count : 25
     * list : [{"id":1,"positionNo":"P000001","warehouseId":"10","warehouseNo":"CK201808000008","state":1,"type":3,"companyNo":"C000014","createdAt":"2018-09-01T07:59:12.000Z","updatedAt":"2018-09-13T02:20:33.000Z","warehouse.id":10,"warehouse.warehouseName":"仓库001","commonvalue.commonNo":"C02","commonvalue.commonValueNo":3,"commonvalue.commonValueName":"卖场1-库位类型"}]
     */

    private String tag;
    private int status;
    private String message;
    private int count;
    private List<ListBean> list;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * positionNo : P000001
         * warehouseId : 10
         * warehouseNo : CK201808000008
         * state : 1
         * type : 3
         * companyNo : C000014
         * createdAt : 2018-09-01T07:59:12.000Z
         * updatedAt : 2018-09-13T02:20:33.000Z
         *
         * warehouse.id : 10
         * warehouse.warehouseName : 仓库001
         * commonvalue.commonNo : C02
         * commonvalue.commonValueNo : 3
         * commonvalue.commonValueName : 卖场1-库位类型
         */

        private String id;
        private String positionNo;
        private String warehouseId;
        private String warehouseNo;
        private String state;
        private String type;
        private String companyNo;
        private String createdAt;
        private String updatedAt;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPositionNo() {
            return positionNo;
        }

        public void setPositionNo(String positionNo) {
            this.positionNo = positionNo;
        }

        public String getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(String warehouseId) {
            this.warehouseId = warehouseId;
        }

        public String getWarehouseNo() {
            return warehouseNo;
        }

        public void setWarehouseNo(String warehouseNo) {
            this.warehouseNo = warehouseNo;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCompanyNo() {
            return companyNo;
        }

        public void setCompanyNo(String companyNo) {
            this.companyNo = companyNo;
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
