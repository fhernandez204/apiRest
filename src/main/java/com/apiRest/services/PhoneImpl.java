package com.apiRest.services;

import com.apiRest.model.Phone;
import com.apiRest.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * * Author: Francisco Hernandez
 **/
public class PhoneImpl implements IPhone {
    public List<Phone> getPhones(List<Phone> phones, User userIn){
        List<Phone> phonesList = new ArrayList<>();
        for (Phone phoneIn : phones) {
            phoneIn.setUser(userIn);
            phonesList.add(phoneIn);
        }
        return phonesList;
    }
}
