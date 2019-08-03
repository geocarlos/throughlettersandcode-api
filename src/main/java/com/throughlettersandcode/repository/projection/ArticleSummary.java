package com.throughlettersandcode.repository.projection;

import java.time.LocalDate;

public class ArticleSummary {

    public ArticleSummary(Long id, String content, LocalDate createdDate, String category, String author) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.category = category;
        this.author = author;
    }

    private Long id;
    private String content;
    private LocalDate createdDate;
    private String category;
    private String author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
}