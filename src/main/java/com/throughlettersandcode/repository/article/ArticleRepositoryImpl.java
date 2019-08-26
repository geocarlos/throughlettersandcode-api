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

import com.throughlettersandcode.model.Article;
import com.throughlettersandcode.repository.filter.ArticleFilter;
import com.throughlettersandcode.repository.projection.ArticleSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ArticleRepositoryImpl implements ArticleRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Article> filterArticles(ArticleFilter articleFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Article> criteria = builder.createQuery(Article.class);
        Root<Article> root = criteria.from(Article.class);

        Predicate[] predicates = createRestrictions(articleFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Article> query = manager.createQuery(criteria);
        addPaginationRestrictions(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(articleFilter));
    }

    @Override
    public Page<Article> findArticlesByCategoryId(Integer id, ArticleFilter articleFilter, Pageable pageable) {
        TypedQuery<Article> articles = manager
                .createQuery("SELECT a FROM Article a JOIN a.category c WHERE c.id = :id", Article.class)
                .setParameter("id", id);
        addPaginationRestrictions(articles, pageable);
        return new PageImpl<>(articles.getResultList(), pageable, total(articleFilter));
    }

    @Override
    public Page<ArticleSummary> summarize(ArticleFilter articleFilter, Pageable pageable) {
        return null;
    }

    private Long total(ArticleFilter articleFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Article> root = criteria.from(Article.class);

        Predicate[] predicates = createRestrictions(articleFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private void addPaginationRestrictions(TypedQuery<?> query, Pageable pageable) {
        int currentPage = pageable.getPageNumber();
        int totalRecordsPerPage = pageable.getPageSize();
        int firstRecordOnPage = currentPage * totalRecordsPerPage;

        query.setFirstResult(firstRecordOnPage);
        query.setMaxResults(totalRecordsPerPage);
    }

    private Predicate[] createRestrictions(ArticleFilter articleFilter, CriteriaBuilder builder, Root<Article> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (articleFilter.getTitle() != null) {
            predicates.add(
                    builder.like(builder.lower(root.get("title")), "%" + articleFilter.getTitle().toLowerCase() + "%"));
        }

        if (articleFilter.getContent() != null) {
            predicates.add(builder.like(builder.lower(root.get("content")),
                    "%" + articleFilter.getContent().toLowerCase() + "%"));
        }

        if (articleFilter.getCreatedDate() != null) {
            predicates.add(builder.equal(root.get("createdDate"), articleFilter.getCreatedDate()));
        }

        if (articleFilter.getLanguage() != null) {
            predicates.add(builder.equal(root.get("language"), articleFilter.getLanguage()));
        }

        if (articleFilter.getCategory() != null) {
            predicates.add(builder.equal(root.get("category").get("name"), articleFilter.getCategory()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}