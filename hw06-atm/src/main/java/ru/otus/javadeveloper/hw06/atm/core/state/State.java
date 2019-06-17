package ru.otus.javadeveloper.hw06.atm.core.state;

import ru.otus.javadeveloper.hw06.atm.BankNote;

import java.util.List;

public interface State {
    List<BankNote> peekSum(int sum);
}
