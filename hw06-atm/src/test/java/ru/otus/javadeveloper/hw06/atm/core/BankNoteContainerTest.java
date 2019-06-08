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
import static ru.otus.javadeveloper.hw06.atm.core.BankNoteHelper.*;

public class BankNoteContainerTest {
    private AtmContainer bankNoteContainer;

    @BeforeEach
    public void setUp() {
        bankNoteContainer = new BankNoteContainer(Arrays.asList(ONE_HUNDRED_RUBLES, ONE_HUNDRED_RUBLES));
    }

    @Test
    public void shouldInAtmCells() {
        List<BankNote> list = Arrays.asList(ONE_HUNDRED_RUBLES, FIFTY_RUBLES);
        bankNoteContainer.addAll(list);

        Map<BankNote, Long> bankNoteList = bankNoteContainer.displayInnerBankNotesCount();
        assertEquals(1L , bankNoteList.get(FIFTY_RUBLES));
        assertEquals(3L, bankNoteList.get(ONE_HUNDRED_RUBLES));
        assertEquals(0L, bankNoteList.get(FIVE_HUNDRED_RUBLES));
        assertEquals(0L, bankNoteList.get(ONE_THOUSAND_RUBLES));
        assertEquals(0L, bankNoteList.get(FIVE_THOUSAND_RUBLES));
    }

    @Test
    public void shouldOutAtmCells() {
        List<BankNote> rur = bankNoteContainer.peekSum(100);
        assertEquals(Collections.singletonList(ONE_HUNDRED_RUBLES), rur);

        Map<BankNote, Long> bankNoteList = bankNoteContainer.displayInnerBankNotesCount();
        assertEquals(0L, bankNoteList.get(FIFTY_RUBLES));
        assertEquals(1L, bankNoteList.get(ONE_HUNDRED_RUBLES));
        assertEquals(0L, bankNoteList.get(FIVE_HUNDRED_RUBLES));
        assertEquals(0L, bankNoteList.get(ONE_THOUSAND_RUBLES));
        assertEquals(0L, bankNoteList.get(FIVE_THOUSAND_RUBLES));
    }

    @Test
    public void shouldThrowExceptionOnOutIfAtmHasNotEnoughMoney() {
        assertThrows(AtmHasNotEnoughBanknotesException.class, () -> bankNoteContainer.peekSum(50));

        Map<BankNote, Long> bankNoteList = bankNoteContainer.displayInnerBankNotesCount();
        assertEquals(0L, bankNoteList.get(FIFTY_RUBLES));
        assertEquals(2L, bankNoteList.get(ONE_HUNDRED_RUBLES));
        assertEquals(0L, bankNoteList.get(FIVE_HUNDRED_RUBLES));
        assertEquals(0L, bankNoteList.get(ONE_THOUSAND_RUBLES));
        assertEquals(0L, bankNoteList.get(FIVE_THOUSAND_RUBLES));
    }
}
