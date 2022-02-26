package com.kwrobot.config;

import com.kwrobot.OSSService;
import org.springframework.context.annotation.Bean;

/**
 * @Description : 定义一个configuration ,注意这里并没有使用@Configuration注解,spring扫描的时候并不会装载该类
 * @Author : tangzy
 * @Since : 2022/2/25 13:47
 */
public class OSSServiceConfiguration {

    @Bean
    public OSSService ossService(){
        return new OSSService();
    }
}
