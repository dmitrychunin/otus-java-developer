package ru.otus.javadeveloper.hw06.atm.core.state;

import lombok.RequiredArgsConstructor;
import ru.otus.javadeveloper.hw06.atm.BankNote;
import ru.otus.javadeveloper.hw06.atm.core.container.BankNoteContainer;
import ru.otus.javadeveloper.hw06.atm.core.container.BankNoteContainerResponse;
import ru.otus.javadeveloper.hw06.atm.core.memento.Caretaker;
import ru.otus.javadeveloper.hw06.atm.core.memento.Memento;
import ru.otus.javadeveloper.hw06.exceptions.AtmHasNotEnoughBanknotesException;

import java.util.List;

@RequiredArgsConstructor
public class WriteOffForFullAtmState implements State {
    private final BankNoteContainer bankNoteContainer;
    private final Caretaker caretaker;

    @Override
    public List<BankNote> peekSum(int sum) {

        BankNoteContainerResponse containerResponse = bankNoteContainer.writeOff(sum);
        if (containerResponse.isWriteOffSuccessfull()) {
            return containerResponse.getBankNoteList();
        }
        caretaker.setMemento(new Memento(containerResponse.getBankNoteList()));
        throw new AtmHasNotEnoughBanknotesException("BaseAtm has not enough banknotes");
    }
}
