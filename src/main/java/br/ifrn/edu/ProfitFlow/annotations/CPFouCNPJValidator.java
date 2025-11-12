package br.ifrn.edu.ProfitFlow.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

public class CPFouCNPJValidator implements ConstraintValidator<CPFouCNPJ, String> {

    private final CPFValidator cpfValidator = new CPFValidator();
    private final CNPJValidator cnpjValidator = new CNPJValidator();

    @Override
    public void initialize(CPFouCNPJ constraintAnnotation) {
        // Inicializa os validadores (obrigatório)
        cpfValidator.initialize(null);
        cnpjValidator.initialize(null);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return cpfValidator.isValid(value, context) || cnpjValidator.isValid(value, context);
    }
}
