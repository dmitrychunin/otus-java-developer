package ru.otus.javadeveloper.hw06.atm.core;

import lombok.Data;
import ru.otus.javadeveloper.hw06.atm.BankNote;

@Data
class Cell {
    private final BankNote bankNote;
    private Long count;

    Cell(BankNote bankNote) {
        this.bankNote = bankNote;
        count = 0L;
    }

    Cell(BankNote bankNote, Long count) {
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