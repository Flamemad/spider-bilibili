package org.flamemad.bilispider.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.TYPE,ElementType.METHOD})
public @interface OpenWaitTime {
    long connectionWait() default 1000;
    long refuseWait() default 1200000;
}
