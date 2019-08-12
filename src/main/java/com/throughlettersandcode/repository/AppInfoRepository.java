package com.throughlettersandcode.repository;

import com.throughlettersandcode.model.AppInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppInfoRepository extends JpaRepository<AppInfo, Integer>{
    
}