package ru.otus.javadeveloper.hw07.atm;

import lombok.Getter;

@Getter
public enum BankNote {
    FIFTY(50),
    ONE_HUNDRED(100),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    FIVE_THOUSAND(5000);

    private final int nominal;

    BankNote(int nominal) {
        this.nominal = nominal;
    }
}