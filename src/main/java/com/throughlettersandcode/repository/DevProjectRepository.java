package com.throughlettersandcode.repository;

import com.throughlettersandcode.model.DevProject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DevProjectRepository extends JpaRepository<DevProject, Integer>{
    
}