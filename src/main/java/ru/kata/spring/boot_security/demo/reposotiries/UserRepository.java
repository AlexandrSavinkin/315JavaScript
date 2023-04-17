package ru.kata.spring.boot_security.demo.reposotiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    @Query("select u FROM User u  join fetch u.roles where u.username =:email")
    User findByUsername(String email);


}
