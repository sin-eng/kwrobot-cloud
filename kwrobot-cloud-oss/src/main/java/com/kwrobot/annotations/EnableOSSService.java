package com.kwrobot.annotations;

import com.kwrobot.config.OSSServiceImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description : 定义一个注解通过@Import引入ImportSelector实现类OSSServiceImportSelector
 * @Author : tangzy
 * @Since : 2022/2/25 13:57
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(OSSServiceImportSelector.class)
public @interface EnableOSSService {
}
