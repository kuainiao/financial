package cn.stylefeng.guns.core.common.annotion;

import java.lang.annotation.*;
/**
 *
 * 多数据源标识
 *
 * @author chenwenjie
 * @date 2018年12月5日
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DataSource {

    String name() default "";
}

