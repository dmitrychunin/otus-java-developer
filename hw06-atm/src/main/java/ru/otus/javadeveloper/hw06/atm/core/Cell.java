package ru.otus.javadeveloper.hw06.atm.core;

import lombok.Data;
import ru.otus.javadeveloper.hw06.atm.BankNote;

@Data
class Cell {
    private final BankNote bankNote;
    private long count;

    Cell(BankNote bankNote) {
        this.bankNote = bankNote;
        count = 0L;
    }

    Cell(BankNote bankNote, long count) {
        this.bankNote = bankNote;
        this.count = count;
    }

    void incrementCount() {
        count++;
    }

    void decrementCount() {
        count--;
    }
}