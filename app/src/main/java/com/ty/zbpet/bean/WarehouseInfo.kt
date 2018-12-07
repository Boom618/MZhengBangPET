//package com.ty.zbpet.bean
//
///**
// * 仓库信息
// * @author TY
// */
//class WarehouseInfo {
//
//    /**
//     * tag : success
//     * status : 100
//     * message :
//     * count : 20
//     * list : [{"id":3,"warehouseNo":"CK201808000003","warehouseName":"仓库002","type":2,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"aqweq","userId":null,"state":"0","manager":"23","createdAt":"2018-08-24T03:07:36.000Z","updatedAt":"2018-09-14T03:03:15.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":2,"commonvalue.commonValueName":"成品仓","user.id":23,"user.name":"test"},{"id":10,"warehouseNo":"CK201808000008","warehouseName":"仓库001","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"aqweq","userId":23,"state":"1","manager":"14","createdAt":"2018-08-24T03:07:36.000Z","updatedAt":"2018-08-24T03:07:36.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":14,"user.name":"刘华金"},{"id":11,"warehouseNo":"CK201808000009","warehouseName":"测试002","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"是A","userId":null,"state":"1","manager":"14","createdAt":"2018-08-28T01:57:19.000Z","updatedAt":"2018-08-28T01:57:19.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":14,"user.name":"刘华金"},{"id":12,"warehouseNo":"CK201808000010","warehouseName":"测试002","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"萨达","userId":null,"state":"1","manager":"14","createdAt":"2018-08-28T02:02:33.000Z","updatedAt":"2018-08-28T02:02:33.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":14,"user.name":"刘华金"},{"id":13,"warehouseNo":"CK201808000011","warehouseName":"仓库003","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"啊啊啊","userId":null,"state":"1","manager":"14","createdAt":"2018-08-28T02:25:44.000Z","updatedAt":"2018-09-10T02:58:20.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":14,"user.name":"刘华金"},{"id":14,"warehouseNo":"CK201808000012","warehouseName":"测试009","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"11","userId":null,"state":"1","manager":"14","createdAt":"2018-08-28T02:32:30.000Z","updatedAt":"2018-09-10T02:58:25.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":14,"user.name":"刘华金"},{"id":15,"warehouseNo":"CK201808000013","warehouseName":"测试0010","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"111","userId":null,"state":"1","manager":"14","createdAt":"2018-08-28T02:36:45.000Z","updatedAt":"2018-09-10T02:58:30.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":14,"user.name":"刘华金"},{"id":16,"warehouseNo":"CK201808000014","warehouseName":"测试0011","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"11","userId":null,"state":"1","manager":"1","createdAt":"2018-08-28T02:37:33.000Z","updatedAt":"2018-08-28T02:37:33.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":1,"user.name":"admin"},{"id":17,"warehouseNo":"CK201808000015","warehouseName":"测试002","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"2222","userId":null,"state":"1","manager":"14","createdAt":"2018-08-28T02:41:54.000Z","updatedAt":"2018-08-28T02:41:54.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":14,"user.name":"刘华金"},{"id":18,"warehouseNo":"CK201808000016","warehouseName":"仓库001","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"111","userId":null,"state":"1","manager":"21","createdAt":"2018-08-28T02:50:26.000Z","updatedAt":"2018-08-28T02:50:26.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":21,"user.name":"袁绪军"},{"id":19,"warehouseNo":"CK201808000017","warehouseName":"测试001","type":1,"companyNo":"C000014","province":"青海省","city":"果洛藏族自治州","county":"班玛县","address":"q","userId":null,"state":"1","manager":"16","createdAt":"2018-08-28T02:51:44.000Z","updatedAt":"2018-08-28T02:51:44.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":16,"user.name":"黄巧似"},{"id":20,"warehouseNo":"CK201808000018","warehouseName":"仓库001","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"阿达ccc","userId":null,"state":"1","manager":"16","createdAt":"2018-08-28T03:00:28.000Z","updatedAt":"2018-09-03T06:17:52.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":16,"user.name":"黄巧似"},{"id":21,"warehouseNo":"CK201808000019","warehouseName":"a","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"aaa","userId":null,"state":"1","manager":"16","createdAt":"2018-08-30T07:36:38.000Z","updatedAt":"2018-08-30T11:39:46.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":16,"user.name":"黄巧似"},{"id":22,"warehouseNo":"CK201808000020","warehouseName":"aa","type":2,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"aaa","userId":null,"state":"1","manager":"13","createdAt":"2018-08-30T07:38:18.000Z","updatedAt":"2018-09-03T06:48:25.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":2,"commonvalue.commonValueName":"成品仓","user.id":13,"user.name":"刘甜"},{"id":23,"warehouseNo":"CK201809000001","warehouseName":"仓库100","type":2,"companyNo":"C000014","province":"安徽省","city":"芜湖市","county":"弋江区","address":"仓库100","userId":null,"state":"1","manager":"15","createdAt":"2018-09-05T06:36:45.000Z","updatedAt":"2018-09-05T06:39:47.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":2,"commonvalue.commonValueName":"成品仓","user.id":15,"user.name":"李文荣"},{"id":24,"warehouseNo":"CK201809000002","warehouseName":"仓库101","type":2,"companyNo":"C000014","province":"山西省","city":"阳泉市","county":"郊  区","address":"仓库101","userId":null,"state":"1","manager":"15","createdAt":"2018-09-05T06:40:33.000Z","updatedAt":"2018-09-05T06:40:33.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":2,"commonvalue.commonValueName":"成品仓","user.id":15,"user.name":"李文荣"},{"id":25,"warehouseNo":"CK201809000003","warehouseName":"仓库102","type":1,"companyNo":"C000014","province":"山西省","city":"阳泉市","county":"郊  区","address":"仓库102仓库102仓库102仓库仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102仓库102","userId":null,"state":"1","manager":"17","createdAt":"2018-09-05T06:40:44.000Z","updatedAt":"2018-09-11T03:06:15.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":17,"user.name":"邹园"},{"id":26,"warehouseNo":"CK201809000004","warehouseName":"阿达萨达撒大所多撒多撒大所多撒大大所大所","type":1,"companyNo":"C000014","province":"北京市","city":"北京市","county":"东城区","address":"啊啊啊啊","userId":null,"state":"1","manager":"14","createdAt":"2018-09-05T07:13:27.000Z","updatedAt":"2018-09-25T03:09:08.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":14,"user.name":"刘华金"},{"id":27,"warehouseNo":"CK201809000005","warehouseName":"请输入仓库名称请输入仓库名称库名称","type":1,"companyNo":"C000014","province":"天津市","city":"天津市","county":"河西区","address":"入仓库名称","userId":null,"state":"1","manager":"21","createdAt":"2018-09-11T03:08:48.000Z","updatedAt":"2018-09-13T02:48:15.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":1,"commonvalue.commonValueName":"原料仓","user.id":21,"user.name":"袁绪军"},{"id":28,"warehouseNo":"CK201809000006","warehouseName":"商品仓","type":3,"companyNo":"C000014","province":"上海市","city":"上海市","county":"黄浦区","address":"商品仓","userId":null,"state":"1","manager":"15","createdAt":"2018-09-11T06:31:18.000Z","updatedAt":"2018-09-11T06:32:02.000Z","commonvalue.commonNo":"C01","commonvalue.commonValueNo":3,"commonvalue.commonValueName":"商品仓","user.id":15,"user.name":"李文荣"}]
//     * subUrl : ["startAndStopWareHouse","updateWareHouse","addWarehouse"]
//     */
//
//    var tag: String? = null
//    var status: Int = 0
//    var message: String? = null
//    var count: Int = 0
//    var list: List<ListBean>? = null
//
//    class ListBean {
//        /**
//         * id : 3
//         * warehouseNo : CK201808000003
//         * warehouseName : 仓库002
//         * type : 2
//         * companyNo : C000014
//         * province : 北京市
//         * city : 北京市
//         * county : 东城区
//         * address : aqweq
//         * userId : null
//         * state : 0
//         * manager : 23
//         * createdAt : 2018-08-24T03:07:36.000Z
//         * updatedAt : 2018-09-14T03:03:15.000Z
//         * commonvalue.commonNo : C01
//         * commonvalue.commonValueNo : 2
//         * commonvalue.commonValueName : 成品仓
//         * user.id : 23
//         * user.name : test
//         */
//
//        var id: Int = 0
//        var warehouseNo: String? = null
//        var warehouseName: String? = null
//        var type: Int = 0
//        var companyNo: String? = null
//
//    }
//}
