package org.flamemad.bilispider.annotation;

import org.flamemad.bilispider.annotation.repeatable.VideoOrUploaderMessageSpiders;
import org.flamemad.bilispider.constant.Mode;
import org.flamemad.bilispider.constant.Zone;
import org.flamemad.bilispider.net.Api;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Repeatable(VideoOrUploaderMessageSpiders.class)
public @interface VideoOrUploaderMessageSpider {
    Api api();

    int thread() default 1;

    Mode mode() default Mode.service;

    Zone zone();

    long startAid() default 0;

    long endAid() default 0;

    long startPage() default 0;

    long endPage() default 0;

    long maxContain() default 50;
}
