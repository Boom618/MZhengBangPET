> 微信公众号：BoomDev
如有问题或建议请留言
最近更新：`2018-12-26`

<p style="text-align:center;color:#1e819e;font-size:1.3em;font-weight: bold;">
Kotlin 使用填坑
</p>

### 内容目录

[TOC]

### object 和 companion Object

- object 可以定义在全局也可以在类的内部使用
- object 就是单例模式的化身
- object 可以实现 Java 中的匿名类
- object 修饰类相当于 `public class`,类中的方法相当于`static`
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
### apply plugin: 'kotlin-android-extensions' kotlin 扩展库
butterknife 在 `Java`中使用
```
implementation 'com.jakewharton:butterknife:9.0.0-rc3'
annotationProcessor 'com.jakewharton:butterknife-compiler:9.0.0-rc3'
```
butterknife 在 `Kotlin`中使用
```
implementation 'com.jakewharton:butterknife:9.0.0-rc3'
kapt 'com.jakewharton:butterknife-compiler:9.0.0-rc3'
```
添加依赖的方式从 Java 的`annotationProcessor`变成 Kotlin 的 `kapt`。之前在 Java 语言中一直不愿意使用它,主要原因是 `butterknife`解决的只是控件的绑定,减少`findViewById`的编写,内部利用反射去查找控件 ID,对于代码本身而言并没有太多的优势;这次项目开始,改用 Kotlin 编写后,kotlin 扩展库的优势就上来了：

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

### @JvmOverloads 和 @JvmStatic
`@JvmOverloads` 注解表示告诉 JVM 该方法是重载方法
```
    @JvmOverloads
    fun gotoActivity(clz: Class<*>, isCloseCurrentActivity: Boolean = false, ex: Bundle? = null) {
        val intent = Intent(this, clz)
        if (ex != null) {
            intent.putExtras(ex)
        }
        startActivity(intent)
        if (isCloseCurrentActivity) {
            finish()
        }
    }
```
如果在 Java 中我们编写`gotoActivity`时,需要多次（可能大于三次）编写有参数的方法,如果我们在 Kotlin 中使用的话,只需要在方法上使用`@JvmOverloads`注解,有没有感觉就应该是这样的。

`@JvmStatic`注解表示该方法是静态的。Kotlin 没有静态变量与静态方法，采用 @JvmStatic 修饰的方法主要是兼容 Java 代码的调用方式和 Kotlin 一致

### lateinit 替换变量 null 的赋值
在使用 Kotlin 的时候,对变量的赋值操作经常是：
```
private var name: String? = null
private val age: Int = 10
```
对于 `var`修饰的可空变量赋值操作一直觉得不是很友好,在项目中可以采用`lateinit`来修饰变量,代表该变量初始化时不赋值,但是在我使用的时候保证该变量不为空.
```
lateinit var name: String
```
### Kotlin 内联函数 let

**在 lambda 表达式，只支持单抽象方法模型，也就是说设计的接口里面只有一个抽象的方法，才符合 lambda表达式的规则，多个回调方法不支持**

- 内联函数 let 

```
let 扩展函数的实际上是一个作用域函数，当你需要去定义一个变量在一个特定的作用域范围内，let函数的是一个不错的选择；let函数另一个作用就是可以避免写一些判断null的操作。
```

一般结构

```
object.let{
   it.todo()//在函数体内使用it替代object对象去访问其公有的属性和方法
   ...
}

// 另一种用途 判断object为null的操作
object?.let{//表示object不为null的条件下，才会去执行let函数体
   it.todo()
}
```

let 函数使用场景

1. 最常用的场景就是使用 let 函数处理需要针对一个可 null 的对象统一判空处理
2. 需要去明确一个变量所处特定的作用域范围内可以使用

- 内联函数 with

一般结构

```
with(object){
	// todo
}
```





扩展分析

- 要点1
- 要点2





> 我是一名有备而来的 Android 工程师
微信公众号：BoomDev
欢迎关注我、一起学习、一起进步!
![](http://pbl7l4exy.bkt.clouddn.com/%E5%85%AC%E4%BC%97%E5%8F%B78.jpg)


