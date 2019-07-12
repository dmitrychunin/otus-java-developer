package ru.otus.javadeveloper.hw10.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = "id")
public class AddressDataSet {
    @Id
    @GeneratedValue
    @Column(name = "ADDRESS_ID")
    private Long id;
    private String street;
}
