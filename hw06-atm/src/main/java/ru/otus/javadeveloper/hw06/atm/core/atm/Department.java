package ru.otus.javadeveloper.hw06.atm.core.atm;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Department implements CheckableBalance, ManuallyRecoverable {
    private List<Object> atmList = new ArrayList<>();

    public <T extends CheckableBalance & ManuallyRecoverable> void removeObserver(T atm) {
        atmList.remove(atm);
    }

    public <T extends CheckableBalance & ManuallyRecoverable> void addObserver(T atm) {
        atmList.add(atm);
    }

    @Override
    public long getBalance() {
        return atmList.stream().map(atm -> ((CheckableBalance) atm).getBalance()).reduce(0L, Long::sum);
    }

    @Override
    public void recover() {
        atmList.forEach(atm -> ((ManuallyRecoverable) atm).recover());
    }
}
