package com.throughlettersandcode.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.throughlettersandcode.model.Category;
import com.throughlettersandcode.repository.filter.CategoryFilter;

public class CategoryRepositoryImpl implements CategoryRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;
    
    @Override
    public List<Category> filterCategories(CategoryFilter categoryFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        Root<Category> root = criteria.from(Category.class);

        Predicate[] predicates = createRestrictions(categoryFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Category> query = manager.createQuery(criteria);

        return query.getResultList();
    }

    private Predicate[] createRestrictions(CategoryFilter categoryFilter, CriteriaBuilder builder, Root<Category> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (categoryFilter.getName() != null) {
            predicates.add(
                    builder.like(builder.lower(root.get("name")), "%" + categoryFilter.getName().toLowerCase() + "%"));
        }

        if (categoryFilter.getLanguage() != null) {
            predicates.add(builder.equal(root.get("language"), categoryFilter.getLanguage()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
    
}