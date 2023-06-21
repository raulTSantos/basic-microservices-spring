package com.userservice.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userservice.app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
