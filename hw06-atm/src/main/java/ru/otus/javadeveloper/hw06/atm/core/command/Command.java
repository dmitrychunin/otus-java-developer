package ru.otus.javadeveloper.hw06.atm.core.command;

import ru.otus.javadeveloper.hw06.atm.core.container.BankNoteContainerResponse;

public interface Command {
    BankNoteContainerResponse writeOff(int sum);

    void undo();
}
