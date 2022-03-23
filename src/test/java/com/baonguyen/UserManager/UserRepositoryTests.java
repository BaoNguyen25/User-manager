package com.baonguyen.UserManager;

import com.baonguyen.UserManager.user.User;
import com.baonguyen.UserManager.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest// Unit tests for Data Access Layer
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // run test against real db
@Rollback(false) // keep data committed to the db
public class UserRepositoryTests {
    @Autowired
    private UserRepository repository;

    @Test
    public void testAddNew(){
        User user = new User(2, "Vin", "Nguyen", "vinnguyen69@gmail.com");
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

}
