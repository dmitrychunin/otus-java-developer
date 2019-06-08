package ru.otus.javadeveloper.hw06.exceptions;

public class UnknownCurrencyException extends RuntimeException {
    public UnknownCurrencyException(String message) {
        super(message);
    }
}
