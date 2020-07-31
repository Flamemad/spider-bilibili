



# Spider-BiliBili-Java

## 一个基于Java的B站基本信息爬虫

### 版本更新日志

#### 1.1 

##### 优化

1.基于ProxyPool项目的网页api，对代理的实现进行了简化

2.原Init注解中的ModeConfig参数整合至各功能注解中，参数的设定更加简化

3.代码更加简化，减少了不必要的内存使用

4.现在secondZoneRoll方法可以自己追踪页面进度了

5.现在不需要手动指定数据表了

##### BUG修复

6.解决了在使用代理时，有可能因为意外的**illegalAccessException**异常和**NullPointerException**异常

而导致程序异常退出的问题

7.解决了使用代理时，因代理服务器状态异常，而使得线程进入长时间等待的状态的问题

#### 1.0

完成了基本功能，目前只对二级分区视频数据功能进行了一定的验证。

### 如何使用？

1.需要一个Mongodb数据库，url大概是类似于

> mongodb://localhost:27017/

2.需要按照这样，建立一个类

```java
@Wait
@Database(url = "localhost",port = 27017,username = "root",
        password = "12345",
        dbName = "video_data")
@VideoOrUploaderMessageSpider(
        api = Api.SecondZoneListAPI,mode = Mode.secondZoneRoll,
        thread = 4,startPage = 0,endPage = 10000000,startAid = 0,endAid = 0,
        zone = Zone.animals)
@VideoOrUploaderMessageSpider(
        api = Api.SecondZoneListAPI,zone = Zone.animals)
public class BiliSpiderApplication{
    public static void main(String[] args) {
        SpiderApplication.run(BiliSpiderApplication.class);
    }
}
```

当然你可能觉得这有点复杂，如果你的数据库在本地，没有密码，想要自动寻找数据边界，不需要进度跟踪程序，不需要在IP被Ban的情况减少请求次数的情况下，你的代码很简单

```java
@Database(dbName = "video_data")
@VideoOrUploaderMessageSpider(
        api = Api.SecondZoneListAPI,mode = Mode.secondZoneRoll,
        zone = Zone.mad_amv)
public class BiliSpiderApplication{
    public static void main(String[] args) {
        SpiderApplication.run(BiliSpiderApplication.class);
    }
}
```

后面会持续更新，未来可能就用python了呢
