package ru.otus.javadeveloper.hw06.atm.core.factoryMethod;

import lombok.RequiredArgsConstructor;
import ru.otus.javadeveloper.hw06.atm.core.container.BankNoteContainer;
import ru.otus.javadeveloper.hw06.atm.core.memento.Caretaker;
import ru.otus.javadeveloper.hw06.atm.core.state.WriteOffForEmptyAtmState;
import ru.otus.javadeveloper.hw06.atm.core.state.WriteOffForFullAtmState;
import ru.otus.javadeveloper.hw06.atm.core.state.State;

@RequiredArgsConstructor
public class AtmStateFactoryMethod {
    private final BankNoteContainer bankNoteContainer;
    private final Caretaker caretaker;

    public State getState(int sum) {
        if (bankNoteContainer.getBalance() >= sum) {
            return new WriteOffForFullAtmState(bankNoteContainer, caretaker);
        } else {
            return new WriteOffForEmptyAtmState(bankNoteContainer, caretaker);
        }
    }
}
