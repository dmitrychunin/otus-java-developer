package ru.otus.javadeveloper.hw07.atm;

import lombok.NonNull;

import java.util.List;
import java.util.Map;

interface AtmContainer {
    List<BankNote> peekSum(int sum);

    void addAll(@NonNull List<BankNote> bankNotes);

    Map<BankNote, Long> displayInnerBankNotesCount();
}
