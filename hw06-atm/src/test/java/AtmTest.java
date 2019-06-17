import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw06.atm.BankNote;
import ru.otus.javadeveloper.hw06.atm.core.atm.*;
import ru.otus.javadeveloper.hw06.exceptions.AtmHasNotEnoughBanknotesException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtmTest {

    @Test
    public void activeAtmShouldAutoRecover() {
        FullFunctionalAtm activeAtm = new ActiveRecoverAtm(Arrays.asList(BankNote.ONE_HUNDRED, BankNote.FIFTY, BankNote.ONE_HUNDRED, BankNote.ONE_HUNDRED, BankNote.FIFTY));

        activeAtm.writeOff(300);
        assertEquals(100, activeAtm.getBalance());
        assertThrows(AtmHasNotEnoughBanknotesException.class, () -> activeAtm.writeOff(150));
        assertEquals(100, activeAtm.getBalance());
        activeAtm.writeOff(100);
        assertEquals(0, activeAtm.getBalance());
    }

    @Test
    public void lazyAtmShouldRecoverLazily() {
        FullFunctionalAtm lazyRecoverAtm = new LazyRecoverAtm(Arrays.asList(BankNote.ONE_HUNDRED, BankNote.FIFTY, BankNote.ONE_HUNDRED, BankNote.ONE_HUNDRED, BankNote.FIFTY));

        lazyRecoverAtm.writeOff(300);
        assertEquals(100, lazyRecoverAtm.getBalance());
        assertThrows(AtmHasNotEnoughBanknotesException.class, () -> lazyRecoverAtm.writeOff(150));
        assertEquals(0, lazyRecoverAtm.getBalance());
        lazyRecoverAtm.writeOff(50);
        assertEquals(50, lazyRecoverAtm.getBalance());
    }

    @Test
    public void departmentShouldGetAllAtmBalance() {
        Department department = new Department();
        department.addObserver(new ActiveRecoverAtm(Arrays.asList(BankNote.FIFTY, BankNote.ONE_HUNDRED)));
        department.addObserver(new LazyRecoverAtm(Arrays.asList(BankNote.ONE_HUNDRED)));
        department.addObserver(new NotRecoverAtm(Collections.singletonList(BankNote.ONE_THOUSAND)));
        assertEquals(1250, department.getBalance());
    }

    @Test
    public void departmentShouldRecoverAllAtmManually() {
        FullFunctionalAtm lazyRecoverAtm = new LazyRecoverAtm(Arrays.asList(BankNote.ONE_HUNDRED, BankNote.FIFTY, BankNote.ONE_HUNDRED));
        assertThrows(AtmHasNotEnoughBanknotesException.class, () -> lazyRecoverAtm.writeOff(300));
        assertEquals(0, lazyRecoverAtm.getBalance());

        FullFunctionalAtm notRecoverAtm = new NotRecoverAtm(Arrays.asList(BankNote.ONE_HUNDRED, BankNote.FIFTY, BankNote.ONE_HUNDRED));
        assertThrows(AtmHasNotEnoughBanknotesException.class, () -> notRecoverAtm.writeOff(300));
        assertEquals(0, notRecoverAtm.getBalance());

        Department department = new Department();
        department.addObserver(lazyRecoverAtm);
        department.addObserver(notRecoverAtm);
        department.recover();

        assertEquals(250, lazyRecoverAtm.getBalance());
        assertEquals(250, notRecoverAtm.getBalance());
    }
}
