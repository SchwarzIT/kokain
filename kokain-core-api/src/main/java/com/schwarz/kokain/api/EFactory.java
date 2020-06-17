package com.schwarz.kokain.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kotlin.reflect.KClass;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EFactory {


    Class<?>[] additionalFactories() default Void.class;

}
