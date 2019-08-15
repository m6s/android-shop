package info.mschmitt.shop.core.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Matthias Schmitt
 */
@Repeatable(value = HttpExceptionMapping.List.class)
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface HttpExceptionMapping {
    int errorCode();

    Class<? extends Exception> exceptionClass();

    @Documented
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface List {
        HttpExceptionMapping[] value();
    }
}
