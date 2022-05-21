package ir.parliran.global;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.StringJoiner;

@Service
public class AjaxBindingErrorChecker {
    public void checkBindingError(BindingResult bindingResult) {
        if (!bindingResult.hasErrors())
            return;

        StringJoiner err = new StringJoiner("\n").setEmptyValue("");
        for (ObjectError error : bindingResult.getAllErrors()) {
            err.add(error.getDefaultMessage());
        }
        throw new RuntimeException(err.toString());

    }
}
