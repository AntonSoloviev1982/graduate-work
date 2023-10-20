package ru.skypro.homework.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.skypro.homework.util.Violation;

import java.util.List;

@RequiredArgsConstructor
public class ValidationException extends RuntimeException {
    @Getter
    private final List<Violation> violations;

}
