package ru.otus.javadeveloper.hw12.dao.model;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = {"user", "id"})
@ToString(exclude = "user")
public class PhoneDataSet {
    @Id
    @GeneratedValue
    @Expose
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;
    @Expose
    private String number;
}
