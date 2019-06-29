package ru.otus.javadeveloper.hw06.atm.core.atm;

import lombok.NonNull;
import ru.otus.javadeveloper.hw06.atm.BankNote;
import ru.otus.javadeveloper.hw06.atm.core.container.BankNoteContainer;
import ru.otus.javadeveloper.hw06.atm.core.container.BankNoteContainerResponse;
import ru.otus.javadeveloper.hw06.atm.core.memento.Caretaker;
import ru.otus.javadeveloper.hw06.atm.core.memento.Memento;
import ru.otus.javadeveloper.hw06.exceptions.AtmHasNotEnoughBanknotesException;

import java.util.List;


public class NotRecoverAtm implements FullFunctionalAtm {
    private final BankNoteContainer bankNoteContainer = new BankNoteContainer();
    private final Caretaker caretaker = new Caretaker();

    public NotRecoverAtm(List<BankNote> bankNotes) {
        enroll(bankNotes);
    }

    @Override
    public void enroll(@NonNull List<BankNote> bankNotes) {
        bankNoteContainer.addAll(bankNotes);
    }

    @Override
    public List<BankNote> writeOff(int sum) {

        BankNoteContainerResponse containerResponse = bankNoteContainer.writeOff(sum);
        if (containerResponse.isWriteOffSuccessfull()) {
            return containerResponse.getBankNoteList();
        }
        caretaker.setMemento(new Memento(containerResponse.getBankNoteList()));
        throw new AtmHasNotEnoughBanknotesException("BaseAtm has not enough banknotes");
    }

    @Override
    public long getBalance() {
        return bankNoteContainer.getBalance();
    }

    @Override
    public void recover() {
        while (caretaker.hasMemento()) {
            final List<BankNote> bankNotes = caretaker.getMemento().getBankNotes();
            bankNoteContainer.addAll(bankNotes);
        }
    }
}
