package ru.otus.javadeveloper.hw06.atm.core.atm;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ru.otus.javadeveloper.hw06.atm.BankNote;
import ru.otus.javadeveloper.hw06.atm.core.command.Command;
import ru.otus.javadeveloper.hw06.atm.core.command.WriteOffWithAutoRecover;
import ru.otus.javadeveloper.hw06.atm.core.container.BankNoteContainer;
import ru.otus.javadeveloper.hw06.atm.core.container.BankNoteContainerResponse;
import ru.otus.javadeveloper.hw06.exceptions.AtmHasNotEnoughBanknotesException;

import java.util.List;

@Slf4j
@NoArgsConstructor
public class ActiveRecoverAtm implements FullFunctionalAtm {
    private BankNoteContainer bankNoteContainer = new BankNoteContainer();

    public ActiveRecoverAtm(@NonNull List<BankNote> bankNotes) {
        enroll(bankNotes);
    }

    @Override
    public void enroll(@NonNull List<BankNote> bankNotes) {
        bankNoteContainer.addAll(bankNotes);
    }

    @Override
    public List<BankNote> writeOff(int sum) {
        Command peekSum = new WriteOffWithAutoRecover(bankNoteContainer);
        BankNoteContainerResponse containerResponse = peekSum.writeOff(sum);

        if (containerResponse.isWriteOffSuccessfull()) {
            return containerResponse.getBankNoteList();
        }
        peekSum.undo();
        throw new AtmHasNotEnoughBanknotesException("BaseAtm has not enough banknotes");    }

    @Override
    public long getBalance() {
        return bankNoteContainer.getBalance();
    }

    @Override
    public void recover() {
//        todo how to avoid this compromise?
    }
}
