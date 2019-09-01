package ru.otus.javadeveloper.hw16.ms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.javadeveloper.hw16.common.api.Address;

/**
 * Created by tully.
 */
@Component
@RequiredArgsConstructor
public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address frontAddress;
    private Address dbAddress;

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }
}
