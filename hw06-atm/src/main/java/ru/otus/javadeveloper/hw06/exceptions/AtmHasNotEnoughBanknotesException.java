package ru.otus.javadeveloper.hw06.exceptions;

public class AtmHasNotEnoughBanknotesException extends RuntimeException {
    public AtmHasNotEnoughBanknotesException(String message) {
        super(message);
    }
}
