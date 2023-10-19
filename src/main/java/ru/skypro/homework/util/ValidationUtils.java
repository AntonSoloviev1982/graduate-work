package ru.skypro.homework.util;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationUtils {

    private final Validator validator;

    public <T> void isValid(T t) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (!constraintViolations.isEmpty()) {
            String resultValidations = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce((s1, s2) -> s1 + ". " + s2).get();
            throw new ValidationException(resultValidations);
        }
    }



}
