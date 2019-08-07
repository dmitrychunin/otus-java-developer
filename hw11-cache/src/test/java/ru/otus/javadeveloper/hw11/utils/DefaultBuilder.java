package ru.otus.javadeveloper.hw11.utils;

import ru.otus.javadeveloper.hw11.model.AddressDataSet;
import ru.otus.javadeveloper.hw11.model.PhoneDataSet;
import ru.otus.javadeveloper.hw11.model.User;

import java.util.Set;

public class DefaultBuilder {
    public static User createDefaultTestUser() {
        var defaultUser = new User();
        defaultUser.setName("John");
        defaultUser.setAge(20);
        var addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("Boulevard of broken dreams");
        defaultUser.setAddressDataSet(addressDataSet);
        var phoneDataSet1 = new PhoneDataSet();
        phoneDataSet1.setUser(defaultUser);
        phoneDataSet1.setNumber("88005553535");
        var phoneDataSet2 = new PhoneDataSet();
        phoneDataSet2.setNumber("89457777777");
        phoneDataSet2.setUser(defaultUser);
        defaultUser.setPhoneDataSets(Set.of(phoneDataSet1, phoneDataSet2));
        return defaultUser;
    }
}
