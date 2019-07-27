package ru.otus.javadeveloper.hw12.dao.model;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(exclude = "id")
public class AddressDataSet {
    @Id
    @GeneratedValue
    @Column(name = "ADDRESS_ID")
    @Expose
    private Long id;
    @Expose
    private String street;
}
