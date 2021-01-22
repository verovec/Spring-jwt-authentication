package com.quest.etna.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.quest.etna.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByUsername(String username);
	
    @Query("SELECT u FROM User u WHERE u.username = ?1 AND u.password = ?2")
	User findByUsernameAndPassword(String username, String password);
}
