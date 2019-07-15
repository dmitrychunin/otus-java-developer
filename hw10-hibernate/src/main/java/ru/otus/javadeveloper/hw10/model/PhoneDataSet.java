package ru.otus.javadeveloper.hw10.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = {"user", "id"})
@ToString(exclude = "user")
public class PhoneDataSet {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="USER_ID")
    private User user;
    private String number;
}
