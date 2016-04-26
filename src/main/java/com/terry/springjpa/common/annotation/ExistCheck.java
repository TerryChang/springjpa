package com.terry.springjpa.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.terry.springjpa.config.servlet.validate.ExistCheckValidator;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy={ExistCheckValidator.class})
public @interface ExistCheck {

	String message() default "{ExistCheck}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	String tableName() default "";
	String columnName() default "";
}
