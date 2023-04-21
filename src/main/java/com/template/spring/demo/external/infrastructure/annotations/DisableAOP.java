package com.template.spring.demo.external.infrastructure.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark Class or Method for AOP Pointcuts.
 * Used to easily discover the classes to be disabled.
 * e.g.:
 * @Pointcut("""
 *              !@annotation(com.template.spring.demo.infrastructure.annotations.DisableAOP) &&
 *              !@target(com.template.spring.demo.infrastructure.annotations.DisableAOP)
 * """)
 * public void enabledAOP() {}
 *
 * @Pointcut("somePointcut() && enabledAOP()")
 * public void finalPointcut() {}
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableAOP {
}
