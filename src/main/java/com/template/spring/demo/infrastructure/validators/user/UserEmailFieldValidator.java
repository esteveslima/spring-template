package com.template.spring.demo.infrastructure.validators.user;

import com.template.spring.demo.domain.entities.UserEntity;
import com.template.spring.demo.domain.exceptions.user.validation.UserFailedValidationEmailFieldException;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserEmailFieldValidator.CustomValidator.class)
public @interface UserEmailFieldValidator {
    String message() default "Validation failure";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    static class CustomValidator implements ConstraintValidator<UserEmailFieldValidator, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            try{
                UserEntity.validateEmail(value);
                return true;
            } catch (UserFailedValidationEmailFieldException exception) {
                String errorMessage = exception.getMessage();
                context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
                return false;
            }
        }
    }
}
