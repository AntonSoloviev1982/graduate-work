package ru.skypro.homework.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidationException extends RuntimeException{
    private final String message;
}
