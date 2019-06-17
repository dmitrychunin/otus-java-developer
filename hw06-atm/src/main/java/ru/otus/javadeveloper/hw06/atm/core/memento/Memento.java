package ru.otus.javadeveloper.hw06.atm.core.memento;

import lombok.Value;
import ru.otus.javadeveloper.hw06.atm.BankNote;

import java.util.List;

@Value
public class Memento {
    private final List<BankNote> bankNotes;
}
