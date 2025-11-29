package br.ifrn.edu.ProfitFlow.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneIsValid {
    String message() default "Telefone inválido!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String regex() default "^\\(?\\d{2}\\)? ?9?\\d{4}-?\\d{4}$";
}