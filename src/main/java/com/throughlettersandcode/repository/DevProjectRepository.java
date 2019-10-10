package com.throughlettersandcode.repository;

import com.throughlettersandcode.model.DevProject;
import com.throughlettersandcode.repository.impl.DevProjectRepositoryQuery;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DevProjectRepository extends JpaRepository<DevProject, Integer>, DevProjectRepositoryQuery {
    
}