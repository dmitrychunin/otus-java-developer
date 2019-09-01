package ru.otus.javadeveloper.hw16.common.api.event.db;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.api.event.Event;

@Data
@NoArgsConstructor
public class GetUsersEvent extends Event {
    public GetUsersEvent(Address from, Address to) {
        super(from, to, "get", null);
    }
}
