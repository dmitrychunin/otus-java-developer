package ru.otus.javadeveloper.hw06.atm.core.command;

import lombok.RequiredArgsConstructor;
import ru.otus.javadeveloper.hw06.atm.BankNote;
import ru.otus.javadeveloper.hw06.atm.core.container.BankNoteContainer;
import ru.otus.javadeveloper.hw06.atm.core.container.BankNoteContainerResponse;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class WriteOffWithAutoRecover implements Command {
    private final BankNoteContainer bankNoteContainer;
    private List<BankNote> lastPeekedSum = new ArrayList<>();

    @Override
    public BankNoteContainerResponse writeOff(int sum) {
        BankNoteContainerResponse containerResponse = bankNoteContainer.writeOff(sum);
        lastPeekedSum = containerResponse.getBankNoteList();
        return containerResponse;
    }

    @Override
    public void undo() {
        bankNoteContainer.addAll(lastPeekedSum);
    }
}
