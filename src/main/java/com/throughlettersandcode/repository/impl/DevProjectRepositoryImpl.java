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

import com.throughlettersandcode.model.DevProject;
import com.throughlettersandcode.repository.filter.DevProjectFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class DevProjectRepositoryImpl implements DevProjectRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<DevProject> filterProjects(DevProjectFilter projectFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<DevProject> criteria = builder.createQuery(DevProject.class);
        Root<DevProject> root = criteria.from(DevProject.class);

        Predicate[] predicates = createRestrictions(projectFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<DevProject> query = manager.createQuery(criteria);
        addPaginationRestrictions(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(projectFilter));
    }

    private Long total(DevProjectFilter projectFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<DevProject> root = criteria.from(DevProject.class);
		
		Predicate[] predicates = createRestrictions(projectFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
    }

    private void addPaginationRestrictions(TypedQuery<?> query, Pageable pageable){
        int currentPage = pageable.getPageNumber();
        int totalRecordsPerPage = pageable.getPageSize();
        int firstRecordOnPage = currentPage * totalRecordsPerPage;

        query.setFirstResult(firstRecordOnPage);
        query.setMaxResults(totalRecordsPerPage);
    }
    
    private Predicate[] createRestrictions(DevProjectFilter projectFilter, CriteriaBuilder builder, Root<DevProject> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(projectFilter.getTitle() != null){
            predicates.add(builder.like(builder.lower(root.get("title")), "%" + 
			    projectFilter.getTitle().toLowerCase() + "%"));
        }
        
        if(projectFilter.getDescription() != null){
            predicates.add(builder.like(builder.lower(root.get("description")), "%" + 
            projectFilter.getDescription().toLowerCase() + "%"));
        }

        if(projectFilter.getLanguage() != null){
            predicates.add(builder.equal(root.get("language"), projectFilter.getLanguage()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
    
}