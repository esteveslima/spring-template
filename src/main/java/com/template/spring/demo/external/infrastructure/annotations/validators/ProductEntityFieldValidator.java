package com.template.spring.demo.external.infrastructure.annotations.validators;

import com.template.spring.demo.core.domain.entities.ProductEntity;
import com.template.spring.demo.core.domain.exceptions.product.ProductFieldFailedValidationException;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.util.Objects;


@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProductEntityFieldValidator.CustomValidator.class)
public @interface ProductEntityFieldValidator {
    String message() default "Validation failure";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String fieldName() default "";

    class CustomValidator implements ConstraintValidator<ProductEntityFieldValidator, Object> {

        private String fieldName;

        @Override
        public void initialize(ProductEntityFieldValidator annotation) {
            this.fieldName = annotation.fieldName();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            boolean hasDefinedFieldName = this.fieldName != "";
            if(!hasDefinedFieldName){
                String errorMessage = String.format("Validation annotation fieldName is required for '%s'", ProductEntity.class.getName());
                throw new RuntimeException(errorMessage);
            }

            try{
                switch (this.fieldName){
                    case "name" -> ProductEntity.validateName(Objects.toString(value, null));
                    case "description" -> ProductEntity.validateDescription(Objects.toString(value, null));
                    case "price" -> ProductEntity.validatePrice((double)value);
                    case "currency" -> ProductEntity.validateCurrency(Objects.toString(value, null));
                    default -> {
                        String errorMessage = String.format("Invalid fieldName '%s' for '%s' validation annotation", this.fieldName, ProductEntity.class.getName());
                        throw new RuntimeException(errorMessage);
                    }
                }
                return true;
            } catch (ProductFieldFailedValidationException exception) {
                String errorMessage = exception.getMessage();   // add domain entity validation error message to the response
                context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
                return false;
            }
        }

    }
}
