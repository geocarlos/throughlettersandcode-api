package com.throughlettersandcode.repository.article;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.throughlettersandcode.model.Video;
import com.throughlettersandcode.repository.filter.VideoFilter;
import com.throughlettersandcode.repository.projection.VideoSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class VideoRepositoryImpl implements VideoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Video> filterVideos(VideoFilter videoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Video> criteria = builder.createQuery(Video.class);
        Root<Video> root = criteria.from(Video.class);

        Predicate[] predicates = createRestrictions(videoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Video> query = manager.createQuery(criteria);
        addPaginationRestrictions(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(videoFilter));
    }

    @Override
    public Page<VideoSummary> summarize(VideoFilter videoFilter, Pageable pageable) {
        return null;
    }

    private Long total(VideoFilter videoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Video> root = criteria.from(Video.class);
		
		Predicate[] predicates = createRestrictions(videoFilter, builder, root);
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
    
    private Predicate[] createRestrictions(VideoFilter videoFilter, CriteriaBuilder builder, Root<Video> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(videoFilter.getTitle() != null){
            predicates.add(builder.like(builder.lower(root.get("title")), "%" + 
			    videoFilter.getTitle().toLowerCase() + "%"));
        }
        
        if(videoFilter.getDescription() != null){
            predicates.add(builder.like(builder.lower(root.get("content")), "%" + 
			    videoFilter.getDescription().toLowerCase() + "%"));
        }

        if(videoFilter.getCreatedDate() != null){
            predicates.add(builder.equal(root.get("createdDate"), videoFilter.getCreatedDate()));
        }

        if(videoFilter.getLanguage() != null){
            predicates.add(builder.equal(root.get("language"), videoFilter.getLanguage()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
    
}