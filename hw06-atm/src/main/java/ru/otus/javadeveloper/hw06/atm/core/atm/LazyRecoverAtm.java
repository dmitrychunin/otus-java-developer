package ru.otus.javadeveloper.hw06.atm.core.atm;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.otus.javadeveloper.hw06.atm.BankNote;
import ru.otus.javadeveloper.hw06.atm.core.container.BankNoteContainer;
import ru.otus.javadeveloper.hw06.atm.core.factoryMethod.AtmStateFactoryMethod;
import ru.otus.javadeveloper.hw06.atm.core.memento.Caretaker;
import ru.otus.javadeveloper.hw06.atm.core.state.State;

import java.util.List;

@NoArgsConstructor
public class LazyRecoverAtm implements FullFunctionalAtm {
    private final BankNoteContainer bankNoteContainer = new BankNoteContainer();
    private final Caretaker caretaker = new Caretaker();
    private final AtmStateFactoryMethod atmStateFactoryMethod = new AtmStateFactoryMethod(bankNoteContainer, caretaker);

    public LazyRecoverAtm(List<BankNote> bankNotes) {
        enroll(bankNotes);
    }

    @Override
    public void enroll(@NonNull List<BankNote> bankNotes) {
        bankNoteContainer.addAll(bankNotes);
    }

    @Override
    public List<BankNote> writeOff(int sum) {
        State state = atmStateFactoryMethod.getState(sum);
        return state.peekSum(sum);
    }

    @Override
    public long getBalance() {
        return bankNoteContainer.getBalance();
    }

    @Override
    public void recover() {
        while(caretaker.hasMemento()) {
            final List<BankNote> bankNotes = caretaker.getMemento().getBankNotes();
            bankNoteContainer.addAll(bankNotes);
        }
    }
}
