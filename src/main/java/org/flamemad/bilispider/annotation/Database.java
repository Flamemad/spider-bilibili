package org.flamemad.bilispider.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface Database {
    String url() default "localhost";

    int port() default 27017;

    String username() default "";

    String password() default "";

    String dbName();
}
