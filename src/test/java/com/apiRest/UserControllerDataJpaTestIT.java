package com.apiRest;

import com.apiRest.model.User;
import com.apiRest.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static com.apiRest.mock.SimulationData.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * * Author: Francisco Hernandez
 **/
@DataJpaTest
public class UserControllerDataJpaTestIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenCreateUserFindByNameThenReturnUser() {
        User user= createUser();
        User userSave = userRepository.save(user);

        // when
        List<User> userFindByNombre = userRepository.findByNameContainingIgnoreCase("Francisco Hernandez");
        // then
        assertThat(userFindByNombre.get(0).getName())
                .isEqualTo(userSave.getName());
    }

    @Test
    public void whenFindByIdThenReturnUser() {
        User user= createUser();
        User userSave = userRepository.save(user);
        // when
        Optional<User> userData = userRepository.findById(userSave.getId());
        // then
        assertThat(userData.get().getId())
                .isEqualTo(userSave.getId());
    }

    @Test
    public void whenCreateUserFindByEmailThenReturnUser() {
        User user= createUser();
        User userSave = userRepository.save(user);

        // when
        List<User> userFindByEmail = userRepository.findByEmailContainingIgnoreCase("fhernandez204@gmail.com");
        // then
        assertThat(userFindByEmail.get(0).getEmail())
                .isEqualTo(userSave.getEmail());
    }
}
