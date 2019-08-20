package ru.otus.javadeveloper.hw12.dao.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = "id")
@ToString
public class PhoneDataSet {
    @Id
    @GeneratedValue
    private Long id;
    private String number;
}
