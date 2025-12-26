package com.jishan.eventprocessing.repository;

import com.jishan.eventprocessing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Used for authentication
    Optional<User> findByUsername(String username);
}

