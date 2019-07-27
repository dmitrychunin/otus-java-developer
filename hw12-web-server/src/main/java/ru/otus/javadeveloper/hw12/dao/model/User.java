package ru.otus.javadeveloper.hw12.dao.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private int age;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID")
    @Expose
    private AddressDataSet addressDataSet;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @Expose
    private Set<PhoneDataSet> phoneDataSets;
}
