# MZhengBangPET
> 正邦项目

**原辅料**

- 扫码 RxJava 异步解析
- 异步校验（RxJava）
- 动态更新指定输入框，列表局部刷新
- gradle 配置
- main XML 修改为约束布局
- Kotlin 编写已办详情
- select dialog
- 编写 lifecycle、ViewModel、LiveData 测试页面
- 加班整理正邦业务
- 列表刷新
- 页面销毁关闭请求
- 网络配置（Single）
-

**技术点**

- Rc 动态更新  RecycleView DiffUtil
- Rc 多个输入框 和 Button 事件冲突
- Rc 嵌套 Rc(质检列表中的 3 张图片)

**困扰**

- Retrofit 上传图片：
```
// 构建请求
RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

`"file"`:是和服务器约定好的 key

    // 接口定义
    @Multipart
    @POST(ApiNameConstant.POST_USER_QUA_CHECK_IMAGE)
    Single<ImageData> updateCheckImage(@Part MultipartBody.Part part);


```


## 待完成 

- Fragment 懒加载
- 分页
- RecycleView 细节处理
- Adapter 绑定事件处理
- 列表分页加载
- RxEasyHttp
- rc 输入框取值



## 困扰

- RecyclerView DiffUtil：oldList 每次都是 new List , newList 是原来的 List ,列表局部更新的时候
添加的数据都是在添加在`尾部`。


## 疑问

## 1、外采入库 生产入库

2、外采入库的【仓库】是在列表【里面】选择、生产入库的【仓库】是在列表【外面】选

3、冲销列表只读，不能选仓库等。。

4、外采入库【没有成品起始值】，生产入库【有成品起始值】

5、外采入库已办冲销详情、箱码绑定是什么逻辑