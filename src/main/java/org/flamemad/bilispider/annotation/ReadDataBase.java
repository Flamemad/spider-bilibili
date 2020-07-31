package org.flamemad.bilispider.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface ReadDataBase {
    String url() default "localhost";

    int port();

    String username() default "";

    String password() default "";

    String dbname();

    String collectionName();
}
