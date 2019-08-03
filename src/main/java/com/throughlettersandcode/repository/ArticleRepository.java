package com.throughlettersandcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.throughlettersandcode.model.Article;
import com.throughlettersandcode.repository.article.ArticleRepositoryQuery;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryQuery {

}
