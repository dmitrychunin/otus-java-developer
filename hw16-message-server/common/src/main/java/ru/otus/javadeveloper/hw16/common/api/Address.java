package ru.otus.javadeveloper.hw16.common.api;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tully
 */
@Data
@NoArgsConstructor
public final class Address {
    private String id;

    public Address(String id) {
        this.id = id;
    }
}
