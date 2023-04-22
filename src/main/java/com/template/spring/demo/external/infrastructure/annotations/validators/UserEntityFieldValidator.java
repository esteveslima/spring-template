package com.template.spring.demo.external.infrastructure.annotations.validators;

import com.template.spring.demo.core.domain.entities.StoreEntity;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import com.template.spring.demo.core.domain.entities.UserEntity;
import com.template.spring.demo.core.domain.exceptions.user.UserFieldFailedValidationException;

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
                String errorMessage = String.format("Validation annotation fieldName is required for '%s'", UserEntity.class.getName());
                throw new RuntimeException(errorMessage);
            }

            try{
                switch (this.fieldName){
                    case "username" -> UserEntity.validateUsername(value);
                    case "email" -> UserEntity.validateEmail(value);
                    case "password" -> UserEntity.validatePlainPassword(value);
                    default -> {
                        String errorMessage = String.format("Invalid fieldName '%s' for '%s' validation annotation", this.fieldName, UserEntity.class.getName());
                        throw new RuntimeException(errorMessage);
                    }
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
