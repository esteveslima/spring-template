package com.template.spring.demo.external.infrastructure.annotations.validators;

import com.template.spring.demo.core.domain.entities.StoreEntity;
import com.template.spring.demo.core.domain.entities.UserEntity;
import com.template.spring.demo.core.domain.exceptions.store.StoreFieldFailedValidationException;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StoreEntityFieldValidator.CustomValidator.class)
public @interface StoreEntityFieldValidator {
    String message() default "Validation failure";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String fieldName() default "";

    class CustomValidator implements ConstraintValidator<StoreEntityFieldValidator, String> {

        private String fieldName;

        @Override
        public void initialize(StoreEntityFieldValidator annotation) {
            this.fieldName = annotation.fieldName();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            boolean hasDefinedFieldName = this.fieldName != "";
            if(!hasDefinedFieldName){
                String errorMessage = String.format("Validation annotation fieldName is required for '%s'", StoreEntity.class.getName());
                throw new RuntimeException(errorMessage);
            }

            try{
                switch (this.fieldName){
                    case "name" -> StoreEntity.validateName(value);
                    default -> {
                        String errorMessage = String.format("Invalid fieldName '%s' for '%s' validation annotation", this.fieldName, StoreEntity.class.getName());
                        throw new RuntimeException(errorMessage);
                    }
                }
                return true;
            } catch (StoreFieldFailedValidationException exception) {
                String errorMessage = exception.getMessage();   // add domain entity validation error message to the response
                context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
                return false;
            }
        }

    }
}
