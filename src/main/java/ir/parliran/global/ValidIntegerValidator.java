package ir.parliran.global;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidIntegerValidator implements ConstraintValidator<ValidInteger, Object> {
    @Override
    public void initialize(ValidInteger constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Integer.parseInt((String) value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
