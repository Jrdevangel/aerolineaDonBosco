package com.flightDB.DBApp.repository;


import com.flightDB.DBApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}

