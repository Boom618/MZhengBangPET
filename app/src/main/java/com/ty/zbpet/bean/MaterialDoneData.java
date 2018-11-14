package com.ty.zbpet.bean;

import java.util.List;

/**
 * @author TY on 2018/11/13.
 *
 * 原材料 已办列表
 */
public class MaterialDoneData {


    /**
     * count : 10
     * list : [{"mInWarehouseOrderNo":null,"companyNo":"C000014","supplierId":null,"warehouseId":10,"supplierNo":null,"arrivalOrderNo":null,"amount":null,"state":1,"type":1,"inTime":"2018-09-12T00:00:00.000Z","userId":23,"creator":"test","recallOrderId":null,"sapOrderNo":"SAP00010","createdAt":"2018-09-17T03:45:47.000Z","updatedAt":"2018-09-17T03:45:47.000Z","mInWarehouseOrderId":68},{"mInWarehouseOrderNo":null,"companyNo":"C000014","supplierId":null,"warehouseId":10,"supplierNo":null,"arrivalOrderNo":null,"amount":null,"state":100,"type":1,"inTime":"2018-09-17T00:00:00.000Z","userId":23,"creator":"test","recallOrderId":null,"sapOrderNo":"SAP00010","createdAt":"2018-09-17T06:10:04.000Z","updatedAt":"2018-09-17T06:52:17.000Z","mInWarehouseOrderId":69},{"mInWarehouseOrderNo":null,"companyNo":"C000014","supplierId":null,"warehouseId":10,"supplierNo":null,"arrivalOrderNo":null,"amount":null,"state":1,"type":1,"inTime":"2018-09-18T00:00:00.000Z","userId":23,"creator":"test","recallOrderId":null,"sapOrderNo":"SAP00011","createdAt":"2018-09-17T06:26:29.000Z","updatedAt":"2018-09-17T06:26:29.000Z","mInWarehouseOrderId":70},{"mInWarehouseOrderNo":null,"companyNo":"C000014","supplierId":null,"warehouseId":10,"supplierNo":null,"arrivalOrderNo":null,"amount":null,"state":100,"type":1,"inTime":"2018-09-18T00:00:00.000Z","userId":23,"creator":"test","recallOrderId":null,"sapOrderNo":"SAP00011","createdAt":"2018-09-17T06:57:48.000Z","updatedAt":"2018-09-17T07:01:06.000Z","mInWarehouseOrderId":71},{"mInWarehouseOrderNo":null,"companyNo":"C000014","supplierId":null,"warehouseId":10,"supplierNo":null,"arrivalOrderNo":null,"amount":null,"state":100,"type":1,"inTime":"2018-09-18T00:00:00.000Z","userId":23,"creator":"test","recallOrderId":null,"sapOrderNo":"SAP00011","createdAt":"2018-09-17T07:05:27.000Z","updatedAt":"2018-09-17T07:06:53.000Z","mInWarehouseOrderId":72},{"mInWarehouseOrderNo":null,"companyNo":"C000014","supplierId":null,"warehouseId":10,"supplierNo":null,"arrivalOrderNo":null,"amount":null,"state":1,"type":1,"inTime":"2018-09-18T00:00:00.000Z","userId":23,"creator":"test","recallOrderId":null,"sapOrderNo":"SAP00011","createdAt":"2018-09-17T07:12:04.000Z","updatedAt":"2018-09-17T07:12:04.000Z","mInWarehouseOrderId":73},{"mInWarehouseOrderNo":null,"companyNo":"C000014","supplierId":null,"warehouseId":10,"supplierNo":null,"arrivalOrderNo":null,"amount":null,"state":1,"type":1,"inTime":"2018-09-18T00:00:00.000Z","userId":23,"creator":"test","recallOrderId":null,"sapOrderNo":"SAP00011","createdAt":"2018-09-17T08:52:53.000Z","updatedAt":"2018-09-17T08:52:53.000Z","mInWarehouseOrderId":75},{"mInWarehouseOrderNo":null,"companyNo":"C000014","supplierId":null,"warehouseId":10,"supplierNo":null,"arrivalOrderNo":null,"amount":null,"state":1,"type":1,"inTime":"2018-09-18T00:00:00.000Z","userId":23,"creator":"test","recallOrderId":null,"sapOrderNo":"SAP00011","createdAt":"2018-09-18T02:49:09.000Z","updatedAt":"2018-09-18T02:49:09.000Z","mInWarehouseOrderId":77},{"mInWarehouseOrderNo":null,"companyNo":"C000014","supplierId":null,"warehouseId":10,"supplierNo":null,"arrivalOrderNo":null,"amount":null,"state":1,"type":1,"inTime":"2018-09-18T00:00:00.000Z","userId":23,"creator":"test","recallOrderId":null,"sapOrderNo":"SAP00011","createdAt":"2018-09-18T03:11:34.000Z","updatedAt":"2018-09-18T03:11:34.000Z","mInWarehouseOrderId":78},{"mInWarehouseOrderNo":null,"companyNo":"C000014","supplierId":null,"warehouseId":10,"supplierNo":null,"arrivalOrderNo":null,"amount":null,"state":1,"type":1,"inTime":"2018-09-18T00:00:00.000Z","userId":23,"creator":"test","recallOrderId":null,"sapOrderNo":"SAP00011","createdAt":"2018-09-18T05:48:46.000Z","updatedAt":"2018-09-18T05:48:46.000Z","mInWarehouseOrderId":81}]
     */

    private int count;
    private List<ListBean> list;

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
         * mInWarehouseOrderNo : null
         * companyNo : C000014
         * supplierId : null
         * warehouseId : 10
         * supplierNo : null
         * arrivalOrderNo : null
         * amount : null
         * state : 1
         * type : 1
         * inTime : 2018-09-12T00:00:00.000Z
         * userId : 23
         * creator : test
         * recallOrderId : null
         * sapOrderNo : SAP00010
         * createdAt : 2018-09-17T03:45:47.000Z
         * updatedAt : 2018-09-17T03:45:47.000Z
         * mInWarehouseOrderId : 68
         */

        private String mInWarehouseOrderNo;
        private String companyNo;
        private String supplierId;
        private String warehouseId;
        private String supplierNo;
        private String arrivalOrderNo;
        private String amount;
        private String state;
        private String type;
        private String inTime;
        private String userId;
        private String creator;
        private String recallOrderId;
        private String sapOrderNo;
        private String createdAt;
        private String updatedAt;
        private String mInWarehouseOrderId;

        public String getmInWarehouseOrderNo() {
            return mInWarehouseOrderNo;
        }

        public void setmInWarehouseOrderNo(String mInWarehouseOrderNo) {
            this.mInWarehouseOrderNo = mInWarehouseOrderNo;
        }

        public String getCompanyNo() {
            return companyNo;
        }

        public void setCompanyNo(String companyNo) {
            this.companyNo = companyNo;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(String warehouseId) {
            this.warehouseId = warehouseId;
        }

        public String getSupplierNo() {
            return supplierNo;
        }

        public void setSupplierNo(String supplierNo) {
            this.supplierNo = supplierNo;
        }

        public String getArrivalOrderNo() {
            return arrivalOrderNo;
        }

        public void setArrivalOrderNo(String arrivalOrderNo) {
            this.arrivalOrderNo = arrivalOrderNo;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
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

        public String getInTime() {
            return inTime;
        }

        public void setInTime(String inTime) {
            this.inTime = inTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getRecallOrderId() {
            return recallOrderId;
        }

        public void setRecallOrderId(String recallOrderId) {
            this.recallOrderId = recallOrderId;
        }

        public String getSapOrderNo() {
            return sapOrderNo;
        }

        public void setSapOrderNo(String sapOrderNo) {
            this.sapOrderNo = sapOrderNo;
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

        public String getmInWarehouseOrderId() {
            return mInWarehouseOrderId;
        }

        public void setmInWarehouseOrderId(String mInWarehouseOrderId) {
            this.mInWarehouseOrderId = mInWarehouseOrderId;
        }
    }
}
