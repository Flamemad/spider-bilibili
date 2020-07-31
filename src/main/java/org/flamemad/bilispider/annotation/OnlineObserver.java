package org.flamemad.bilispider.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface OnlineObserver {
    String threadName() default "BiliSpiderThread";

    String collectionName();
}
