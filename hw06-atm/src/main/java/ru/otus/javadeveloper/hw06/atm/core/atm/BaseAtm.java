package ru.otus.javadeveloper.hw06.atm.core.atm;

import ru.otus.javadeveloper.hw06.atm.BankNote;

import java.util.List;

public interface BaseAtm {
    void enroll(List<BankNote> bankNotes);

    List<BankNote> writeOff(int sum);
}
