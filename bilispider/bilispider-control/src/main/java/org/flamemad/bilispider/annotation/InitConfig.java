package org.flamemad.bilispider.annotation;

import org.flamemad.bilispider.net.Api;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.TYPE,ElementType.METHOD})
@Repeatable(InitConfigs.class)
public @interface InitConfig {
    String threadName() default "BiliSpiderThread";
    String collectionName();
    String dbName();
    int thread() default 1;
    Api api();
    ModeConfig modeConfig();
}
