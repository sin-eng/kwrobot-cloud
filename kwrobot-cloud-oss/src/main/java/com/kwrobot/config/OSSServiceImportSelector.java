package com.kwrobot.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Description : {@link ImportSelector} 实现
 * @Author : tangzy
 * @Since : 2022/2/25 13:49
 */
public class OSSServiceImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{OSSServiceConfiguration.class.getName()};
    }
}
