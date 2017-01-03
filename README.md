
# personal-blog-webapp
该项目是原作者左潇龙大神个人博客源码，欢迎大家使用，但请仔细阅读Apache License 2.0。
本人Fork了这个项目并提交了一些修改。

# 示例网站地址
www.zuoxiaolong.com

# 运行步骤（持续更新中）
首先你可以注意到这是个maven项目，用eclipse打开后会有最外层的personal-blog-webapp，它是空的，只包含两个子项目：
 - **native-blog-webapp** 真正的Web项目
 - **refactor-blog-webapp** 基于Spring Boot框架的项目，用于启动上面的Web项目。

> 关于什么是 Spring Boot，[这里](https://spring.io/guides/gs/spring-boot/)有一个入门介绍。概括起来，它能够方便地启动你真正想运行的程序——用Tomcat把web应用跑起来。但是现在我们只是想来学习Web项目，所以暂时就不管Spring Boot，而直接运行 **native-blog-webapp** 就可以了。（真实情况是对Spring Boot我还没怎么看懂，《**大写的尴尬**》 ，后面会把这部分内容加进来）。

### 直接运行native-blog-webapp项目
是的，用eclipse打开外层项目，然后这个项目就会出现在你的项目列表里。在你右键-> Run as -> Run on server之前，请确保：

 - Eclipse已配置好了maven。一般情况下你下载下来的Eclipse for Java EE Developers已经自带maven，即使不自己安装maven也能用。
 - 安装好了Tomcat，已经在Eclipse中配好了Tomcat server。

接下来，还有准备工作要做：
 - 安装好mysql
 - 建立Blog数据库，把**native-blog-webapp**根路径下面的sql文件都执行一遍，初始schema和数据就都设置好了。请确保它们都执行成功了，如有失败是因为互相有些依赖关系，重复执行即可。
 - 在*resource*目录下找到jdbc.properties，把你的密码配好。

现在可以右键运行项目了。

**注意：** Tomcat会默认把web应用运行在 http://localhost:8080/zuoxiaolong/ 这个URL下面。这里有个问题，程序在计算某些变量的时候依赖的是已经配置在setting.properties中的context.path变量。它表示程序的根目录，现在的值是http://localhost:8080 。因此，会导致程序中某些链接不对，或者部分功能不工作。有两种解决方案：

1. 修改*context.path*变量，改成http://localhost:8080/zuoxiaolong
2. 修改Server设置，让Web应用运行在根目录。方法是找到Server工程，打开server.xml，找到`Context`那行，修改`path`为空。

![具体步骤](https://github.com/xinlmain/personal-blog-webapp/blob/master/tomcat.PNG?raw=true)

我暂时倾向于2，因为1可能还需要你修改程序中的某些代码。

### 访问Web应用
可以注意到web.xml中配置的欢迎页面是一个静态的html文件，放在html/目录下，可是这个目录并不存在。其实，这些静态文件是应用在启动后自动生成的，代码在*com.zuoxiaolong.generator*包里。由于当前的配置并不是product环境，所以默认并不生成。后面会讲解如何生成这些静态页面。

> 好在我们可以直接访问动态页面。http://localhost:8080/blog/index.ftl
就是首页，上面所有的链接都能够访问。想写博客的话，访问http://localhost:8080/admin 。系统会提示你登陆，初始密码是123456（hash在setting.properties中配置）。

### 生成静态页面
将setting.properties文件中的`environment.product`改为`true`，可以在Console中看到starting fetch and generate thread...

### 继续学习项目
请移步我的博客：https://segmentfault.com/a/1190000007912251 。我在写一个系列来系统学习这个项目, 内容持续更新中。

欢迎拍砖及反馈！
