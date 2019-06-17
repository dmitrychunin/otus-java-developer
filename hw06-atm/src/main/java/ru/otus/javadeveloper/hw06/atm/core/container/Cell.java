package ru.otus.javadeveloper.hw06.atm.core.container;

import lombok.Data;
import ru.otus.javadeveloper.hw06.atm.BankNote;

@Data
public class Cell {
    private final BankNote bankNote;
    private long count;

    public Cell(BankNote bankNote) {
        this.bankNote = bankNote;
        count = 0L;
    }

    public Cell(BankNote bankNote, long count) {
        this.bankNote = bankNote;
        this.count = count;
    }

    public void incrementCount() {
        count++;
    }

    public void decrementCount() {
        count--;
    }
}