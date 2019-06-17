package ru.otus.javadeveloper.hw06.atm.core.container;

import lombok.Value;
import ru.otus.javadeveloper.hw06.atm.BankNote;

import java.util.List;

@Value
public class BankNoteContainerResponse {
    private final List<BankNote> bankNoteList;
    private final boolean isWriteOffSuccessfull;
}
