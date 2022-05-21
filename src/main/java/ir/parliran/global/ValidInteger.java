package ir.parliran.global;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidIntegerValidator.class)
public @interface ValidInteger {
    String message() default "not a valid integer!";
    int min() default 1;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

