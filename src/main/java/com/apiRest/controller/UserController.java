package com.apiRest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.apiRest.model.Message;
import com.apiRest.model.Phone;
import com.apiRest.model.User;
import com.apiRest.model.UserReturn;
import com.apiRest.repository.UserRepository;
import com.apiRest.services.CreateJWTImpl;
import com.apiRest.security.ValidateEmail;
import com.apiRest.services.IManageJWT;
import com.apiRest.services.IPhone;
import com.apiRest.services.PhoneImpl;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.apiRest.security.Constans.*;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private static final Logger logger = LogManager.getLogger();

      @Autowired
      UserRepository userRepository;

     @GetMapping("/users")
      public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) {
        try {
          List<User> users = new ArrayList<User>();

          logger.info("name: "+name);

          if (name == null)
              userRepository.findAll().forEach(users::add);
          else
              userRepository.findByNameContainingIgnoreCase(name).forEach(users::add);
          logger.info("users: "+users);

          if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }

      @GetMapping("/users/{id}")
      public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> userData = userRepository.findById(id);

          return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
      }

      @PostMapping("/users")
      public String createUser(@RequestBody User user) {
          Message message;
          Gson gson = new Gson();
          IPhone iPhone = new PhoneImpl();
            try {
                String jwt = new CreateJWTImpl().createJWT(
                        user.getPassword(), // claim = jti
                        user.getEmail(),    // claim = iss
                        user.getName(),     // claim = sub
                        JW_TIME_TO_LIVE
                );

                User userIn = new User(user.getName(), user.getEmail(), user.getPassword(), jwt.toString(), true, user.getPhones());
                if(!ValidateEmail.isValidateEmail(userIn.getEmail())) {
                    message = new Message(EMAIL_WITH_FORMAT);
                    return gson.toJson(message);
                }
                List<User> listUserCorreExiste = findByEmail(userIn.getEmail());
                if (listUserCorreExiste.size()>0) {
                    message = new Message(USER_EXISTS_BD);
                    return gson.toJson(message);
                }

                // Lista de Telefonos
                List<Phone> phones = iPhone.getPhones(user.getPhones(), userIn);

                // add phone list to User
                userIn.setPhones(phones);

              User _user = userRepository.save(userIn);
              logger.info("_user: "+_user);

              ResponseEntity<User> responseUser = new ResponseEntity<>(_user, HttpStatus.CREATED);

                if(responseUser.getStatusCodeValue()==HttpStatus.CREATED.value()) {
                    UserReturn userReturn = new UserReturn(_user.getId(),_user.getDateCreate(),_user.getDateModified(),_user.getDateLastLogin(),jwt,_user.getActive());
                    return gson.toJson(userReturn);
                }
                else {
                    message = new Message(ERROR_INSERT_STATUS+responseUser.getStatusCode());
                    return gson.toJson(message);
                }
            } catch (Exception e) {
                message = new Message(ERROR_EXCEPTION_THROWN+" "+e.getMessage());
                return gson.toJson(message);
            }
      }

  @PutMapping("/users/{id}")
  public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
    Optional<User> userData = userRepository.findById(id);
    if (userData.isPresent()) {
        String jwt = new CreateJWTImpl().createJWT(user.getPassword(),user.getEmail(),  user.getName(),JW_TIME_TO_LIVE);
        User _user = userData.get();
        _user.setName(user.getName());
        _user.setEmail(user.getEmail());
        _user.setPassword(user.getPassword());
        user.setActive(user.getActive() == null || user.getActive());

        _user.setActive(user.getActive());
        _user.setDateModified(String.valueOf(new Date()));
        _user.setDateCreate(userData.get().getDateCreate());
        _user.setDateLastLogin(userData.get().getDateLastLogin());
        _user.setToken(jwt);
        return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
    try {
    	userRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/users/published")
  public ResponseEntity<List<User>> findByActive() {
    try {
      List<User> users = userRepository.findByActive(true);

      if (users.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public List<User> findByEmail(String email) {
	    try {
	      return userRepository.findByEmailContainingIgnoreCase(email);
	    } catch (Exception e) {
	      return null;
	    }
  }

}