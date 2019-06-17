package ru.otus.javadeveloper.hw06.atm.core.memento;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private List<Memento> mementos = new ArrayList<>();

    public boolean hasMemento() {
        return !mementos.isEmpty();
    }

    public Memento getMemento() {
        return mementos.remove(mementos.size() - 1);
    }

    public void setMemento(Memento memento) {
        mementos.add(memento);
    }
}
