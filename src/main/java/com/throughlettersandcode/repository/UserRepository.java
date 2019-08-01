package com.throughlettersandcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.throughlettersandcode.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    public Optional<UserEntity> findByEmail(String email);
}
