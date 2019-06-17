package ru.otus.javadeveloper.hw06.atm.core.container;

import lombok.NonNull;
import ru.otus.javadeveloper.hw06.atm.BankNote;

import java.util.*;

public class BankNoteContainer {
    private Map<BankNote, Cell> cellMap = new TreeMap<>(Collections.reverseOrder());

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

    public void addAll(@NonNull List<BankNote> bankNotes) {
        for (BankNote bankNote : bankNotes) {
            cellMap.get(bankNote).incrementCount();
        }
    }

    public BankNoteContainerResponse writeOff(int sum) {
        List<BankNote> lastPeekedSum = new ArrayList<>();
        int current = 0;
        for (Map.Entry<BankNote, Cell> cellEntry : cellMap.entrySet()) {
            while (cellEntry.getValue().getCount() > 0 && cellEntry.getKey().getNominal() <= sum - current) {
                lastPeekedSum.add(cellEntry.getKey());
                cellEntry.getValue().decrementCount();
                current += cellEntry.getKey().getNominal();
            }
        }
        if (current != sum) {
            return new BankNoteContainerResponse(lastPeekedSum, false);
        }
        return new BankNoteContainerResponse(lastPeekedSum, true);
    }

    public long getBalance() {
        return cellMap.entrySet().stream()
                .map(entry -> entry.getKey().getNominal() * entry.getValue().getCount())
                .reduce(0L, Long::sum);
    }
}
