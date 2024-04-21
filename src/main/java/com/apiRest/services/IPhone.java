package com.apiRest.services;

import com.apiRest.model.Phone;
import com.apiRest.model.User;

import java.util.List;

/**
 * * Author: Francisco Hernandez
 **/
public interface IPhone {
    List<Phone> getPhones(List<Phone> phones, User userIn);
}
