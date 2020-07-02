package org.flamemad.bilispider.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.TYPE,ElementType.METHOD})
public @interface InitConfigs {
    InitConfig[] value();
}
