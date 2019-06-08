package ru.otus.javadeveloper.hw06.atm.core;

import lombok.NonNull;
import ru.otus.javadeveloper.hw06.atm.BankNote;
import ru.otus.javadeveloper.hw06.exceptions.AtmHasNotEnoughBanknotesException;

import java.util.*;

class BankNoteContainer implements AtmContainer {
    private Map<Integer, Cell> cellMap = new TreeMap<>();

    public BankNoteContainer(List<BankNote> bankNotes) {
        createEmptyCells();
        addAll(bankNotes);
    }

    public BankNoteContainer() {
        createEmptyCells();
    }

    private void createEmptyCells() {
        for (Integer possibleNominal : BankNote.getPossibleNominals()) {
            Cell cell = new Cell(new BankNote(possibleNominal));
            cellMap.put(possibleNominal, cell);
        }
    }

    @Override
    public List<BankNote> peekSum(@NonNull Integer sum) {
        List<BankNote> resultBankNotesList = new ArrayList<>();
        Integer current = 0;
        for (Map.Entry<Integer, Cell> cellEntry : cellMap.entrySet()) {
            while (cellEntry.getValue().getCount() > 0 && cellEntry.getKey() <= sum - current) {
                BankNote bankNote = new BankNote(cellEntry.getKey());
                resultBankNotesList.add(bankNote);
                cellEntry.getValue().decrementCount();
                current += cellEntry.getKey();
            }
        }
        if (!current.equals(sum)) {
            addAll(resultBankNotesList);
            throw new AtmHasNotEnoughBanknotesException("Atm has not enough banknotes");
        }
        return resultBankNotesList;
    }

    @Override
    public void addAll(@NonNull List<BankNote> bankNotes) {
        for (BankNote bankNote : bankNotes) {
            cellMap.get(bankNote.getNominal()).incrementCount();
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
