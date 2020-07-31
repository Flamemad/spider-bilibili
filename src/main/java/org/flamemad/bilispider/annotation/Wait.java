package org.flamemad.bilispider.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface Wait {
    long connectionWait() default 1;

    long refuseWait() default 300;

    long serviceWaitTime() default 600;
}
