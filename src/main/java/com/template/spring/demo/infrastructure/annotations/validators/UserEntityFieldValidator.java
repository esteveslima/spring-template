package com.template.spring.demo.infrastructure.annotations.validators;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import com.template.spring.demo.domain.entities.UserEntity;
import com.template.spring.demo.domain.exceptions.user.UserFieldFailedValidationException;

import java.lang.annotation.*;


@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserEntityFieldValidator.CustomValidator.class)
public @interface UserEntityFieldValidator {
    String message() default "Validation failure";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String fieldName() default "";

    class CustomValidator implements ConstraintValidator<UserEntityFieldValidator, String> {

        private String fieldName;

        @Override
        public void initialize(UserEntityFieldValidator annotation) {
            this.fieldName = annotation.fieldName();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            boolean hasDefinedFieldName = this.fieldName != "";
            if(!hasDefinedFieldName){
                String errorMessage = String.format("Validation annotation fieldName is required for '%s'", UserEntity.class);
                context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
                return false;
            }

            try{
                switch (this.fieldName){
                    case "username" -> UserEntity.validateUsername(value);
                    case "email" -> UserEntity.validateEmail(value);
                    case "password" -> UserEntity.validatePassword(value);
                }
                return true;
            } catch (UserFieldFailedValidationException exception) {
                String errorMessage = exception.getMessage();   // add domain entity validation error message to the response
                context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
                return false;
            }
        }

    }
}
