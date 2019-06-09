package ru.otus.javadeveloper.hw06.atm.core;

import lombok.NonNull;
import ru.otus.javadeveloper.hw06.atm.BankNote;
import ru.otus.javadeveloper.hw06.exceptions.AtmHasNotEnoughBanknotesException;

import java.util.*;

class BankNoteContainer implements AtmContainer {
    private Map<BankNote, Cell> cellMap = new TreeMap<>();

    public BankNoteContainer(List<BankNote> bankNotes) {
        createEmptyCells();
        addAll(bankNotes);
    }

    public BankNoteContainer() {
        createEmptyCells();
    }

    private void createEmptyCells() {
        for (BankNote bankNote : BankNote.values()) {
            Cell cell = new Cell(bankNote);
            cellMap.put(bankNote, cell);
        }
    }

    @Override
    public List<BankNote> peekSum(int sum) {
        List<BankNote> resultBankNotesList = new ArrayList<>();
        int current = 0;
        for (Map.Entry<BankNote, Cell> cellEntry : cellMap.entrySet()) {
            while (cellEntry.getValue().getCount() > 0 && cellEntry.getKey().getNominal() <= sum - current) {
                resultBankNotesList.add(cellEntry.getKey());
                cellEntry.getValue().decrementCount();
                current += cellEntry.getKey().getNominal();
            }
        }
        if (current != sum) {
            addAll(resultBankNotesList);
            throw new AtmHasNotEnoughBanknotesException("Atm has not enough banknotes");
        }
        return resultBankNotesList;
    }

    @Override
    public void addAll(@NonNull List<BankNote> bankNotes) {
        for (BankNote bankNote : bankNotes) {
            cellMap.get(bankNote).incrementCount();
        }
    }

    @Override
    public Map<BankNote, Long> displayInnerBankNotesCount() {
        Map<BankNote, Long> innerBankNotesCountMap = new HashMap<>();
        for (Cell cell : cellMap.values()) {
            innerBankNotesCountMap.put(cell.getBankNote(), cell.getCount());
        }
        return innerBankNotesCountMap;
    }
}
