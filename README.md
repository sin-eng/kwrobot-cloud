## kwrobot-cloud

项目模板 for kwrobot-cloud

命名规范：
项目名以 kwrobot开头，如kwrobot-auth kwrobot-gateway

## OSS

阿里云存储服务（OpenStorageService，简称OSS），是阿里云对外提供的海量，安全，低成本，高可靠的云存储服务。用户可以通过简单的REST接口，在任何时间、任何地点、任何互联网设备上进行上传和下载数据，也可以使用WEB页面对数据进行管理。同时，OSS提供Java、 Python、 PHP、C#语言的SDK，简化用户的编程。基于OSS，用户可以搭建出各种多媒体分享网站、网盘、个人和企业数据备份等基于大规模数据的服务。

### 指南

#### 引入模块

```java
    <!--oss模块-->
        <dependency>
            <groupId>cloud.kwrobot</groupId>
            <artifactId>kwrobot-cloud-oss</artifactId>
            <version>2022.2.0</version>
   </dependency>
```
#### 快速入门
```java
import com.kwrobot.annotations.EnableOSSService;
import com.kwrobot.OSSService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

// 开启oss
@EnableOSSService
public class OSSServiceBootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(OSSServiceBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);

        // Bean 是否存在
        OSSService ossService =
                context.getBean("ossService", OSSService.class);

        System.out.println("ossService Bean : " + ossService);
        // 关闭上下文
        context.close();
    }
}

```
