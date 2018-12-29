> 微信公众号：BoomDev
如有问题或建议请留言
最近更新：`2018-12-26`

<p style="text-align:center;color:#1e819e;font-size:1.3em;font-weight: bold;">
Kotlin 使用填坑
</p>

###内容目录

[TOC]

###object 和 companion Object

- object 可以定义在全局也可以在类的内部使用
- object 就是单例模式的化身
- object 可以实现 Java 中的匿名类
- companion object 就是 Java 中的 static 变量
- companion object 只能定义在对应的类中

这些只是我们表面看到的，它们的共性和区别还有：

- object 可以作为变量的定义也可以是表达式
- object 匿名类可以继承并超越 Java 中匿名类而实现多个接口
- object 表达式当场实例化，但定义的 object 变量是延迟实例化的
- object 和 companion object 都可以为其取名也可以隐姓埋名
- object 匿名内部类甚至可以引用并更改局部变量
- companion object 甚至还可以被扩展
- Java 中需要结合 @JvmStatic 和 @JvmField 使用

### Bundle 可空
```
Caused by: java.lang.IllegalArgumentException: Parameter specified as non-null is null: parameter savedInstanceState
```
解决方法：在 `onCreate` 方法中使 `Bundle?` 原因是：Android 源码中没有指定 Bundle 为可空,使用 Kotlin 编译检查的时候就会报 `IllegalArgumentException`.
```
savedInstanceState: Bundle?
```

### {this} 和 （this）

- {this} 代表方法实现
- （this） 表示当前类实现的接口

比如：
```
// 方式一：不需要类去实现 OnClickListener 接口
findViewById<Button>(R.id.btn_login).setOnClickListener {
    gotoLogin()
}
// 方式二：需要实现 OnClickListener 接口
findViewById<Button>(R.id.btn_login).setOnClickListener(this)
```
### 修饰符可见性
在 Kotlin 中有这四个可见性修饰符：`private`、`protected`、 `internal` 和 `public`, 如果没有显式指定修饰符的话，默认可见性是 `public`.其他三种和 Java 中的可见性类似,`internal`表示的是在模块内可见
### butterknife
在 `Java`中使用
```
implementation 'com.jakewharton:butterknife:9.0.0-rc3'
annotationProcessor 'com.jakewharton:butterknife-compiler:9.0.0-rc3'
```
在 `Kotlin`中使用
```
implementation 'com.jakewharton:butterknife:9.0.0-rc3'
kapt 'com.jakewharton:butterknife-compiler:9.0.0-rc3'
```
添加依赖的方式从 Java 的`annotationProcessor`变成 Kotlin 的 `kapt`。之前在 Java 语言中一直不愿意使用它,主要原因是 `butterknife`解决的只是控件的绑定,减少`findViewById`的编写,内部利用反射去查找控件 ID,对于代码本身而言并没有太多的优势;这次项目开始,改用 Kotlin 编写后,他的优势就上来了：

它不需要 `@BindView`,不需要控件的初始化定义,极大的减少了代码的工作量:就像这样
```
import kotlinx.android.synthetic.main.activity_main.*

override fun onCreate(savedInstanceState: Bundle?) {
     bt_login.text = "登录"
}
```
看到没有,只需要导入
`import kotlinx.android.synthetic.main.activity_main.*`自己的 `XML`就可以了
### 数据类 data


扩展分析

- 要点1
- 要点2





> 我是一名有备而来的 Android 工程师
微信公众号：BoomDev
欢迎关注我、一起学习、一起进步!
![](http://pbl7l4exy.bkt.clouddn.com/%E5%85%AC%E4%BC%97%E5%8F%B78.jpg)


