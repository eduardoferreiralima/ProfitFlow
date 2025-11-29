package br.ifrn.edu.ProfitFlow.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<PhoneIsValid, String> {

    private Pattern pattern;

    @Override
    public void initialize(PhoneIsValid annotation) {
        this.pattern = Pattern.compile(annotation.regex());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return false;
        }

        return pattern.matcher(value).matches();
    }
}
