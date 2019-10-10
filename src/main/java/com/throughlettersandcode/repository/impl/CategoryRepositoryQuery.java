package com.throughlettersandcode.repository.impl;

import java.util.List;

import com.throughlettersandcode.model.Category;
import com.throughlettersandcode.repository.filter.CategoryFilter;

public interface CategoryRepositoryQuery {
    public List<Category> filterCategories(CategoryFilter categoryFilter);
}