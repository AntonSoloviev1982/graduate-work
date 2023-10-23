package ru.skypro.homework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handlerValidationError(ConstraintViolationException e) {
        String resultValidations = e.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                                .reduce((s1, s2) -> s1 + ". " + s2).orElse("");
        logger.error("Переданный в запросе json не валиден, ошибки валидации: {}", resultValidations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
