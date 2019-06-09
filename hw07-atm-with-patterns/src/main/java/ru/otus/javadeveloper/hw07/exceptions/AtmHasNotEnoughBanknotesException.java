package ru.otus.javadeveloper.hw07.exceptions;

public class AtmHasNotEnoughBanknotesException extends RuntimeException {
    public AtmHasNotEnoughBanknotesException(String message) {
        super(message);
    }
}
