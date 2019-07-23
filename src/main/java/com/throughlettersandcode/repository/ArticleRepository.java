package com.throughlettersandcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.throughlettersandcode.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
