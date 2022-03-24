package com.baonguyen.UserManager;

import com.baonguyen.UserManager.user.User;
import com.baonguyen.UserManager.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest// Unit tests for Data Access Layer
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // run test against real db
@Rollback(false) // keep data committed to the db
public class UserRepositoryTests {
    @Autowired
    private UserRepository repository;

    @Test
    public void testAddNew(){
        User user = new User("Bao", "Nguyen", "nmgb2501@gmail.com");
        User savedUser = repository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<User> users = repository.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);
        for(User usr : users){
            System.out.println(usr.toString());
        }
    }

    @Test
    public void testDelete(){
        repository.deleteById(3);
        Optional<User> optionalUser = repository.findById(3);
        Assertions.assertThat(optionalUser).isNotPresent();
    }

    @Test
    public void testUpdate(){
        Optional<User> optionalUser = repository.findById(4);
        User user = optionalUser.get();
        user.setEmail("nguyenminhgiabao2501@gmail.com");
        repository.save(user);

        User updatedUser = repository.findById(4).get();
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo("nguyenminhgiabao2501@gmail.com");
    }

    @Test
    public void testGet(){
        Optional<User> optionalUser = repository.findById(1);
        User user = optionalUser.get();
        System.out.println(user);

        Assertions.assertThat(user).isNotNull();
    }

}
