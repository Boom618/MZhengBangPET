package com.ty.zbpet.bean;

import java.util.List;

public class GoodsPurchaseOrderInfo {

    /**
     * data : {"list":[{"goodsId":"15","goodsName":"原辅料名称","goodsNo":"原辅料编号","orderNumber":"100","unit":"瓶"},{"goodsId":"14","goodsName":"原辅料名称","goodsNo":"原辅料编号","orderNumber":"100","unit":"箱"},{"goodsId":"13","goodsName":"原辅料名称","goodsNo":"原辅料编号","orderNumber":"100","unit":"桶"},{"goodsId":"12","goodsName":"原辅料名称","goodsNo":"原辅料编号","orderNumber":"100","unit":"盒"}],"sapOrderNo":"SAP0001"}
     * error :
     * message :
     * tag : success
     */

    private DataBean data;
    private String error;
    private String message;
    private String tag;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static class DataBean {
        /**
         * list : [{"goodsId":"15","goodsName":"原辅料名称","goodsNo":"原辅料编号","orderNumber":"100","unit":"瓶"},{"goodsId":"14","goodsName":"原辅料名称","goodsNo":"原辅料编号","orderNumber":"100","unit":"箱"},{"goodsId":"13","goodsName":"原辅料名称","goodsNo":"原辅料编号","orderNumber":"100","unit":"桶"},{"goodsId":"12","goodsName":"原辅料名称","goodsNo":"原辅料编号","orderNumber":"100","unit":"盒"}]
         * sapOrderNo : SAP0001
         */

        private String sapOrderNo;
        private List<ListBean> list;

        public String getSapOrderNo() {
            return sapOrderNo;
        }

        public void setSapOrderNo(String sapOrderNo) {
            this.sapOrderNo = sapOrderNo;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * goodsId : 15
             * goodsName : 原辅料名称
             * goodsNo : 原辅料编号
             * orderNumber : 100
             * unit : 瓶
             */

            private String goodsId;
            private String goodsName;
            private String goodsNo;
            private String orderNumber;
            private String unit;
            private String boxNum;//箱码数
            private List<String> boxCodeList;//箱码

            public List<String> getBoxCodeList() {
                return boxCodeList;
            }

            public void setBoxCodeList(List<String> boxCodeList) {
                this.boxCodeList = boxCodeList;
            }

            public String getBoxNum() {
                return boxNum;
            }

            public void setBoxNum(String boxNum) {
                this.boxNum = boxNum;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsNo() {
                return goodsNo;
            }

            public void setGoodsNo(String goodsNo) {
                this.goodsNo = goodsNo;
            }

            public String getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(String orderNumber) {
                this.orderNumber = orderNumber;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }
        }
    }
}
