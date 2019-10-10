package com.throughlettersandcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.throughlettersandcode.model.Category;
import com.throughlettersandcode.repository.impl.CategoryRepositoryQuery;

public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryRepositoryQuery {

}
