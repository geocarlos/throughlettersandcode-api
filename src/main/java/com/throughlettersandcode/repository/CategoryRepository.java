package com.throughlettersandcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.throughlettersandcode.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
