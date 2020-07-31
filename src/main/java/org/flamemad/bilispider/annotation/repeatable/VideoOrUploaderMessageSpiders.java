package org.flamemad.bilispider.annotation.repeatable;

import org.flamemad.bilispider.annotation.VideoOrUploaderMessageSpider;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface VideoOrUploaderMessageSpiders {
    VideoOrUploaderMessageSpider[] value();
}
