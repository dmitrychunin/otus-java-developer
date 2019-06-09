package ru.otus.javadeveloper.hw07.atm;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@NoArgsConstructor
public class Atm {
    private AtmContainer bankNoteContainer = new BankNoteContainer();

    public Atm(@NonNull List<BankNote> bankNotes) {
        enroll(bankNotes);
    }

    public void enroll(@NonNull List<BankNote> bankNotes) {
        bankNoteContainer.addAll(bankNotes);
    }

    public List<BankNote> writeOff(int sum) {
        return bankNoteContainer.peekSum(sum);
    }

    public long getBalance() {
        return bankNoteContainer.displayInnerBankNotesCount().entrySet().stream().map(entry -> entry.getKey().getNominal() * entry.getValue()).reduce(0L, Long::sum);
    }
}
