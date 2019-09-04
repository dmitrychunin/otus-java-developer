package ru.otus.javadeveloper.hw16.common.api.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.javadeveloper.hw16.common.api.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event<T> {
    private Address from;
    private Address to;
    private String eventId;
    private T payload;
}
