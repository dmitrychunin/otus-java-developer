package ru.otus.javadeveloper.hw06.atm.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw06.atm.BankNote;
import ru.otus.javadeveloper.hw06.exceptions.AtmHasNotEnoughBanknotesException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.otus.javadeveloper.hw06.atm.BankNote.*;

public class BankNoteContainerTest {
    private AtmContainer bankNoteContainer;

    @BeforeEach
    public void setUp() {
        bankNoteContainer = new BankNoteContainer(Arrays.asList(ONE_HUNDRED, ONE_HUNDRED));
    }

    @Test
    public void shouldInAtmCells() {
        List<BankNote> list = Arrays.asList(ONE_HUNDRED, FIFTY);
        bankNoteContainer.addAll(list);

        Map<BankNote, Long> bankNoteList = bankNoteContainer.displayInnerBankNotesCount();
        assertEquals(1L, bankNoteList.get(FIFTY));
        assertEquals(3L, bankNoteList.get(ONE_HUNDRED));
        assertEquals(0L, bankNoteList.get(FIVE_HUNDRED));
        assertEquals(0L, bankNoteList.get(ONE_THOUSAND));
        assertEquals(0L, bankNoteList.get(FIVE_THOUSAND));
    }

    @Test
    public void shouldOutAtmCells() {
        List<BankNote> rur = bankNoteContainer.peekSum(100);
        assertEquals(Collections.singletonList(ONE_HUNDRED), rur);

        Map<BankNote, Long> bankNoteList = bankNoteContainer.displayInnerBankNotesCount();
        assertEquals(0L, bankNoteList.get(FIFTY));
        assertEquals(1L, bankNoteList.get(ONE_HUNDRED));
        assertEquals(0L, bankNoteList.get(FIVE_HUNDRED));
        assertEquals(0L, bankNoteList.get(ONE_THOUSAND));
        assertEquals(0L, bankNoteList.get(FIVE_THOUSAND));
    }

    @Test
    public void shouldThrowExceptionOnOutIfAtmHasNotEnoughMoney() {
        assertThrows(AtmHasNotEnoughBanknotesException.class, () -> bankNoteContainer.peekSum(50));

        Map<BankNote, Long> bankNoteList = bankNoteContainer.displayInnerBankNotesCount();
        assertEquals(0L, bankNoteList.get(FIFTY));
        assertEquals(2L, bankNoteList.get(ONE_HUNDRED));
        assertEquals(0L, bankNoteList.get(FIVE_HUNDRED));
        assertEquals(0L, bankNoteList.get(ONE_THOUSAND));
        assertEquals(0L, bankNoteList.get(FIVE_THOUSAND));
    }
}
