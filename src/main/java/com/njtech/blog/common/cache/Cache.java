package com.njtech.blog.common.cache;

import java.lang.annotation.*;

/**
 * @author chenxin
 * @date 2021/8/30 16:45
 */
// TYPE代表可以放在类上，METHOD放在方法上
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1*60*1000;

    String name() default "";
}
