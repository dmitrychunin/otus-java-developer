package ru.otus.javadeveloper.hw09.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.otus.javadeveloper.hw09.executor.Id;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "no")
public class Account {
    @Id
    private long no;

    public Account(String type, BigDecimal rest) {
        this.type = type;
        this.rest = rest;
    }

    private String type;
    private BigDecimal rest;
}
