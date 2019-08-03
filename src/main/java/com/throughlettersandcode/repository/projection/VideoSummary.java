package com.throughlettersandcode.repository.projection;

import java.time.LocalDate;

public class VideoSummary {

    private Long id;
    private String description;
    private String youtubeId;
    private LocalDate createdDate;
    private String category;
    private String author;

    public VideoSummary(Long id, String description, String youtubeId, LocalDate createdDate, String category,
            String author) {
        this.id = id;
        this.description = description;
        this.youtubeId = youtubeId;
        this.createdDate = createdDate;
        this.category = category;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
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