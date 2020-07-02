package org.flamemad.bilispider.annotation;

import org.flamemad.bilispider.DataBaseType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE,ElementType.METHOD})
public @interface DataBaseConfig {
    boolean useDataBase() default true;
    String url() default "localhost";
    String username() default "";
    String password() default "";
    DataBaseType databaseType();
}
