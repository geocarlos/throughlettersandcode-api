package com.throughlettersandcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.throughlettersandcode.model.Video;
import com.throughlettersandcode.repository.article.VideoRepositoryQuery;

public interface VideoRepository extends JpaRepository<Video, Long>, VideoRepositoryQuery{

}
