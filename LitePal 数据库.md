> 微信公众号：BoomDev
如有问题或建议请留言
最近更新：`2018-12-26`

<p style="text-align:center;color:#1e819e;font-size:1.3em;font-weight: bold;">
Kotlin 使用填坑
</p>
###内容目录

[TOC]

### 添加依赖

```
dependencies {
    compile 'org.litepal.android:java:3.0.0'
}
```



### 配置文件

**在assets目录下创建litepal.xml配置文件**

```
<?xml version="0.1" encoding="utf-8"?>
<litepal>
   <dbname value="litepaldemo" />            // 数据库名称为litepaldemo，默认以 .db 结尾,如未以此结尾,则自动添加。 
   <version value="1" />                     // 数据库版本为1,每次数据库发生变动版本号必须+1
   <list>                                    // 有几张表就在list之间写几个mapping
      // list标签中的实体类都应该继承LitePalSupport这个类,这个千万别忘记
       <mapping class="com.gpf.com.User"></mapping>
       <mapping class="com.gpf.com.Reader"></mapping>  
       <mapping class="com.gpf.com.Movie"></mapping>
   </list>                           
   <storage value="gpf/database" />  //storage 定义数据库文件存储的地方,可选 internal(内部) 和 external（外部）, 默认为 internal 
</litepal>
```



###  在 Application 中初始化

```
LitePal.initialize(this); 
```

### 创建实体类

继承 `LitePalSupport` 该类就拥有 `save()` 方法了

- 删除记录

```
// 删除单个记录，id=1
LitePal.delete(Movie.class,1);

//删除数据库中 movie 表的所有记录 
LitePal.deleteAll(Movie.class); 

//删除数据库 movie 表中 duration 大于 3500 的记录 
LitePal.deleteAll(Movie.class, "duration > ?" , "3500"); 

```

- 修改记录

```
// 方式一：
//第一步，查找id为1的记录 
Movie movie = LitePal.find(Movie.class, 1); 
//第二步，改变某个字段的值 
movie.setPrice(4020f); 
//第三步，保存数据 
movie.save();

// 方式二：
Movie movie=new Movie(); 
movie.setName("2Diots"); 
movie.setDirector("某人");
 //直接更新id为1的记录 
 movie.update(1);
```

- 查询记录

```
// 查找 movie 表的所有记录，返回值是一个泛型为 Movie 的 List 集合
List<Movie> allMovies = LitePal.findAll(Movie.class); 

//查找movie表id为1的记录 
Movie movie = LitePal.find(Movie.class,1); 

// 比如获取news表中的第一条数据
News firstNews = LitePal.findFirst(News.class);

// 获取News表中的最后一条数据
News lastNews = LitePal.findLast(News.class);

// 想把news表中id为1、3、5、7的数据都查出来
List<News> newsList = LitePal.findAll(News.class, 1, 3, 5, 7);
// 或者
long[] ids = new long[] { 1, 3, 5, 7 };
List<News> newsList = LitePal.findAll(News.class, ids);

//查找name为2Diots的记录,并且以时长作排序，where()方法接收任意个字符串参数，其中第一个参数用于进行条件约束，
//从第二个参数开始，都是用于替换第一个参数中的占位符的。那这个where()方法就对应了一条SQL语句中的where部分。 
List<Movie> movies = LitePal.where("name = ?", "2Diots").order("duration").find(Movie.class); 
// 但是这样会将news表中所有的列都查询出来，也许你并不需要那么多的数据，而是只要title和content这两列数据。那么也很简单，我们只要再增加一个连缀就行了，如下所示：
List<News> newsList = LitePal.select("title", "content")
		.where("commentcount > ?", "0").find(News.class);
//将查询出的新闻按照发布的时间倒序排列，即最新发布的新闻放在最前面，那就可以这样写：
List<News> newsList = LitePal.select("title", "content")
		.where("commentcount > ?", "0")
		.order("publishdate desc").find(News.class);
//order()方法中接收一个字符串参数，用于指定查询出的结果按照哪一列进行排序，asc表示正序排序，desc表示倒序排序，因此order()方法对应了一条SQL语句中的order by部分。
//也许你并不希望将所有条件匹配的结果一次性全部查询出来，因为这样数据量可能会有点太大了，而是希望只查询出前10条数据，那么使用连缀同样可以轻松解决这个问题，代码如下所示：
List<News> newsList = LitePal.select("title", "content")
		.where("commentcount > ?", "0")
		.order("publishdate desc").limit(10).find(News.class);
//limit()方法接收一个整型参数，用于指定查询前几条数据，这里指定成10，意思就是查询所有匹配结果中的前10条数据。
//刚才我们查询到的是所有匹配条件的前10条新闻，那么现在我想对新闻进行分页展示，翻到第二页时，展示第11到第20条新闻,只需要再连缀一个偏移量就可以了，如下所示：
List<News> newsList = LitePal.select("title", "content")
		.where("commentcount > ?", "0")
		.order("publishdate desc").limit(10).offset(10)
		.find(News.class);
//这里指定成10，就表示偏移十个位置，那么原来是查询前10条新闻的，偏移了十个位置之后，就变成了查询第11到第20条新闻了，如果偏移量是20，那就表示查询第21到第30条新闻

//查找所有年龄小于25岁的人
List<Person> person = LitePal.where("age < ?", 25).find(Person.class); 

```







> 我是一名有备而来的 Android 工程师
微信公众号：BoomDev
欢迎关注我、一起学习、一起进步!
![](http://pbl7l4exy.bkt.clouddn.com/%E5%85%AC%E4%BC%97%E5%8F%B78.jpg)


