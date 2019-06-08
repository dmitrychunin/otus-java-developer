package ru.otus.javadeveloper.hw06.atm.core;

import lombok.NonNull;
import ru.otus.javadeveloper.hw06.atm.BankNote;

import java.util.List;
import java.util.Map;

interface AtmContainer {
    List<BankNote> peekSum(@NonNull Integer sum);

    void addAll(@NonNull List<BankNote> bankNotes);

    Map<BankNote, Long> displayInnerBankNotesCount();
}
