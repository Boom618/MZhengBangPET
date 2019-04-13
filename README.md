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
- Fragment 懒加载
- rc 输入框取值:RecycleView.getChildAt(position) 得到 View
- Rc button 冲突（利用 EventBus 解决）

**技术点**

- Rc 动态更新  RecycleView DiffUtil
  - notifyItemInserted(int position)
  - notifyItemRemoved(int position)
  - notifyItemRangeChanged(int positionStart, int itemCount)
  - notifyItemChanged(int position)
  - notifyDataSetChanged()
  - AsyncListDiffer
  - AsyncDifferConfig
  ```
  在使用 DiffUtil 中遇到一个困扰我很久的问题,在项目中真正要做到更新 item 中某一个属性
  （根据扫码得到的库位码,服务器校验成功后再更新到控件上）输入框,第一：这里需要判断扫码成功与否,
  第二：成功后的库位码去校验。最后我的解决方案是重写 `getChangePayload`方法在 adapter 中属性列表
  ```
- Rc 多个输入框 和 Button 事件冲突
- Rc 嵌套 Rc(质检列表中的 3 张图片)

**困扰**


## 待完成 

- 分页
- RecycleView 细节处理
- Adapter 绑定事件处理
- 列表分页加载
- 协程库使用
- Session 失效跳转登录
- P 层改写 Kotlin


## 困扰

- RecyclerView DiffUtil：oldList 每次都是 new List , newList 是原来的 List ,列表局部更新的时候
  添加的数据都是在添加在`尾部`。
- RecyclerView notifyItemChanged(position)  第一列输入框失去焦点,其他列表输入框有焦点


## 库

```
// 65536 问题
implementation 'com.android.support:multidex:1.0.3'
// glide 图片库
implementation "com.github.bumptech.glide:glide:${ZBConfig.glideVersion}"
// event bus 事件分发库
implementation "org.greenrobot:eventbus:${ZBConfig.eventbusVersion}"
//网络请求库
implementation "com.squareup.okhttp3:okhttp:${ZBConfig.okhttp3Version}"
implementation "com.squareup.retrofit2:retrofit:${ZBConfig.retrofit2Version}"
implementation "com.squareup.retrofit2:adapter-rxjava2:${ZBConfig.retrofit2Version}"
implementation "com.squareup.retrofit2:converter-gson:${ZBConfig.retrofit2Version}"
implementation "com.squareup.okhttp3:logging-interceptor:${ZBConfig.okhttp3Version}"
//rxjava相关依赖
implementation "io.reactivex.rxjava2:rxjava:${ZBConfig.rx2Version}"
implementation "io.reactivex.rxjava2:rxandroid:${ZBConfig.rx2AndroidVersion}"
// gson 解析
implementation "com.google.code.gson:gson:${ZBConfig.googleGsonVersion}"
implementation "com.alibaba:fastjson:1.1.70.android"
// Rx permissions 权限库
implementation "com.github.tbruyelle:rxpermissions:${ZBConfig.rxpermissions_version}"
// 时间选择框
implementation "com.contrarywind:Android-PickerView:${ZBConfig.PickerViewVersion}"
// Toasty
implementation 'com.github.GrenderG:Toasty:1.4.1'
// light key value 存值
implementation 'com.horizon.lightkv:lightkv:1.0.7'
// adapter
implementation "com.zhy:base-rvadapter:${ZBConfig.rvadapterVersion}"
// kotlin 标准库
implementation "org.jetbrains.kotlin:kotlin-stdlib:${kotlin_version}"
implementation "org.jetbrains.kotlin:kotlin-reflect:${kotlin_version}"
// 相册选择
implementation 'com.github.LuckSiege.PictureSelector:picture_library:v2.2.3'
// TabLayout 库
implementation "com.flyco.tablayout:FlycoTabLayout_Lib:${ZBConfig.tablayout_version}"
// bugly 管理
implementation "com.tencent.bugly:crashreport:${ZBConfig.bugly_version}"
// 沉浸状态栏
implementation 'com.jaeger.statusbarutil:library:1.5.1'
```


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
- RecyclerView item 的 XML `android:layout_height="wrap_content"` 自己习惯写 `android:layout_height="match_parent"`