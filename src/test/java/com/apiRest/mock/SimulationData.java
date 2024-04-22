package com.apiRest.mock;

import com.apiRest.model.Phone;
import com.apiRest.model.User;
import com.apiRest.services.CreateJWTImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.apiRest.security.Constans.JW_TIME_TO_LIVE;

public class SimulationData {

    public static long ID_USER = 1552;
    public static String VOID = "";
    public static Integer OK = 200;
    public static String statusCard = "NORM";

    public static User createUser() {
            User user = new User();
                    user.setName("Francisco Hernandez");
                    user.setEmail("fhernandez204@gmail.com");
                    user.setDateCreate((String.valueOf(new Date())));
                    user.setDateModified(String.valueOf(new Date()));
                    user.setDateLastLogin((String.valueOf(new Date())));
                    user.setActive(true);
                    user.setPhones(createPhones());
                    user.getPhones().get(0).setUser(user);
        String token=createJWT(user);
        user.setToken(token);
        return user;
    }

    public static List<Phone> createPhones() {
        List<Phone> listPhones=new ArrayList<Phone>();
        Phone phone=new Phone();
        phone.setNumber("11111");
        phone.setContrycode("04444");
        phone.setCitycode("5555555");
        listPhones.add(phone);
        return listPhones;
    }

    public static String createJWT(User user){
        return new CreateJWTImpl().createJWT(
                user.getPassword(), // claim = jti
                user.getEmail(),    // claim = iss
                user.getName(),     // claim = sub
                JW_TIME_TO_LIVE
        );
    }

}
