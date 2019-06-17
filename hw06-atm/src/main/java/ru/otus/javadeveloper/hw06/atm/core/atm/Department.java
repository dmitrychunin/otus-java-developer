package ru.otus.javadeveloper.hw06.atm.core.atm;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Department implements CheckableAndRecoverable {
    private List<CheckableAndRecoverable> atmList = new ArrayList<>();

    public void removeObserver(CheckableAndRecoverable atm) {
        atmList.remove(atm);
    }

    public void addObserver(CheckableAndRecoverable atm) {
        atmList.add(atm);
    }

    @Override
    public long getBalance() {
        return atmList.stream().map(CheckableAndRecoverable::getBalance).reduce(0L, Long::sum);
    }

    @Override
    public void recover() {
        atmList.forEach(CheckableAndRecoverable::recover);
    }
}
