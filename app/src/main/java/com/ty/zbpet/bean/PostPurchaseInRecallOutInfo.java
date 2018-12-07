//package com.ty.zbpet.bean;
//
//import java.util.List;
//
///**
// * 成品采购入库待办--上传的参数
// */
//public class PostPurchaseInRecallOutInfo {
//    private String warehouseId;
//    private String inStoreDate;
//    private String sapPlantNo;
//    private String sapMaterialBatchNo;
//    private String productionBatchNo;
//    private String sapOrderNo;
//    private String remark;
//    private List<BoxCodeInfo> details;
//
//    public String getSapOrderNo() {
//        return sapOrderNo;
//    }
//
//    public void setSapOrderNo(String sapOrderNo) {
//        this.sapOrderNo = sapOrderNo;
//    }
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
//    public String getSapPlantNo() {
//        return sapPlantNo;
//    }
//
//    public void setSapPlantNo(String sapPlantNo) {
//        this.sapPlantNo = sapPlantNo;
//    }
//
//    public String getSapMaterialBatchNo() {
//        return sapMaterialBatchNo;
//    }
//
//    public void setSapMaterialBatchNo(String sapMaterialBatchNo) {
//        this.sapMaterialBatchNo = sapMaterialBatchNo;
//    }
//
//    public String getProductionBatchNo() {
//        return productionBatchNo;
//    }
//
//    public void setProductionBatchNo(String productionBatchNo) {
//        this.productionBatchNo = productionBatchNo;
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
//    public List<BoxCodeInfo> getDetails() {
//        return details;
//    }
//
//    public void setDetails(List<BoxCodeInfo> details) {
//        this.details = details;
//    }
//
//    public static class BoxCodeInfo{
//        private String positionId;
//        private String number;
//        private String goodsId;
//        private String goodsQrCodeStart;
//        private String goodsQrCodeEnd;
//        private List<String> boxQrCode;
//
//        public String getPositionId() {
//            return positionId;
//        }
//
//        public void setPositionId(String positionId) {
//            this.positionId = positionId;
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
//        public String getGoodsId() {
//            return goodsId;
//        }
//
//        public void setGoodsId(String goodsId) {
//            this.goodsId = goodsId;
//        }
//
//        public String getGoodsQrCodeStart() {
//            return goodsQrCodeStart;
//        }
//
//        public void setGoodsQrCodeStart(String goodsQrCodeStart) {
//            this.goodsQrCodeStart = goodsQrCodeStart;
//        }
//
//        public String getGoodsQrCodeEnd() {
//            return goodsQrCodeEnd;
//        }
//
//        public void setGoodsQrCodeEnd(String goodsQrCodeEnd) {
//            this.goodsQrCodeEnd = goodsQrCodeEnd;
//        }
//
//        public List<String> getBoxQrCode() {
//            return boxQrCode;
//        }
//
//        public void setBoxQrCode(List<String> boxQrCode) {
//            this.boxQrCode = boxQrCode;
//        }
//    }
//
//}
