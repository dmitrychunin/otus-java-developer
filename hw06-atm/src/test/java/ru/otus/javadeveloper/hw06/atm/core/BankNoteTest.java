package ru.otus.javadeveloper.hw06.atm.core;

import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw06.atm.BankNote;
import ru.otus.javadeveloper.hw06.exceptions.NoSuchNominalException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankNoteTest {
    @Test
    public void shouldThrowExceptionIfInvalidCurrencyNominal() {
        assertThrows(NoSuchNominalException.class, () -> new BankNote(37));
    }
}
