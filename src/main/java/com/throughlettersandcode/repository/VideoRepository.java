package com.throughlettersandcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.throughlettersandcode.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{

}
