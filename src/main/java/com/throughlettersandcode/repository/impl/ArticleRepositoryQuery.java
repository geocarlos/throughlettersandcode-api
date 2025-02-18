package com.throughlettersandcode.repository.impl;

import com.throughlettersandcode.model.Article;
import com.throughlettersandcode.repository.filter.ArticleFilter;
import com.throughlettersandcode.repository.projection.ArticleSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryQuery {
    public Page<Article> filterArticles(ArticleFilter articleFilter, Pageable pageable);
    public Page<ArticleSummary> summarize(ArticleFilter articleFilter, Pageable pageable);
    public Page<Article> findArticlesByCategoryId(Integer id, ArticleFilter articleFilter, Pageable pageable);
}