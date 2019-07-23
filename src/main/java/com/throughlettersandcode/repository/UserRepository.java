package com.throughlettersandcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.throughlettersandcode.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
