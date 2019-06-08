package ru.otus.javadeveloper.hw06.atm;

import lombok.EqualsAndHashCode;
import ru.otus.javadeveloper.hw06.exceptions.NoSuchNominalException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
public class BankNote {
    protected static List<Integer> possibleNominals = Arrays.asList(50, 100, 500, 1000, 5000);
    private final Integer nominal;

    public BankNote(Integer nominal) {
        if (!possibleNominals.contains(nominal)) {
            throw new NoSuchNominalException("bank note has not such nominal");
        }
        this.nominal = nominal;
    }

    public static List<Integer> getPossibleNominals() {
        return new ArrayList<>(possibleNominals);
    }

    public Integer getNominal() {
        return nominal;
    }
}
