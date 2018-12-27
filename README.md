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
- 网络日志拦截器和头部拦截器
- 详情页面利用 RecycleView  getItemViewType 替换 NestedScrollView
- 数据存储采用 DataUtils 中 SparseArray 处理保存
- 清空任务栈（建国提供 Helper）
- 权限

**技术点**

- Rc 动态更新  RecycleView DiffUtil
- Rc 多个输入框 和 Button 事件冲突
- Rc 嵌套 Rc(质检列表中的 3 张图片)

**困扰**



## 待完成 

- Fragment 懒加载
- 分页
- RecycleView 细节处理
- Adapter 绑定事件处理
- 列表分页加载
- RxEasyHttp
- rc 输入框取值
- Rc button 冲突
- Kotlin 改版



## 困扰

- RecyclerView DiffUtil：oldList 每次都是 new List , newList 是原来的 List ,列表局部更新的时候
  添加的数据都是在添加在`尾部`。
- RecyclerView notifyItemChanged(position)  第一列输入框失去焦点,其他列表输入框有焦点


## 疑问

- 用户登录约定：sessionId  、角色切换、 md5 加密

## 犯错

- 需要缓存到文件的 Object ,里面的类也需要序列化: UserInfo
- RecyclerView 不设置 `setLayoutManager` 不显示
- adapter 执行：
  1.构造方法
  2.getItemCount
  3.getItemViewType
  4.onCreateViewHolder
  5.ViewHolder
  6.onBindViewHolder
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