package org.flamemad.bilispider.annotation;

import org.flamemad.bilispider.Mode;
import org.flamemad.bilispider.Zone;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.TYPE,ElementType.METHOD})
public @interface ModeConfig {
    Mode mode();
    Zone zone() default Zone.All;
    long startAid() default 0;
    long endAid() default 0;
    long startPage() default 0;
    long endPage() default 0;
    long maxContain() default 20;
    long waitTime() default 60000;
}
