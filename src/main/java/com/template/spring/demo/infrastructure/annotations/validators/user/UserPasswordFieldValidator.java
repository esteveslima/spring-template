package com.template.spring.demo.infrastructure.annotations.validators.user;

import com.template.spring.demo.domain.entities.UserEntity;
import com.template.spring.demo.domain.exceptions.user.validation.UserFailedValidationPasswordFieldException;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserPasswordFieldValidator.CustomValidator.class)
public @interface UserPasswordFieldValidator {
    String message() default "Validation failure";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    static class CustomValidator implements ConstraintValidator<UserPasswordFieldValidator, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            try{
                UserEntity.validatePassword(value);
                return true;
            } catch (UserFailedValidationPasswordFieldException exception) {
                String errorMessage = exception.getMessage();
                context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
                return false;
            }
        }
    }
}
