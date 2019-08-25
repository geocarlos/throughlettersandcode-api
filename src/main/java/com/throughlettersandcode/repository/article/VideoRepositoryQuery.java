package com.throughlettersandcode.repository.article;

import com.throughlettersandcode.model.Video;
import com.throughlettersandcode.repository.filter.VideoFilter;
import com.throughlettersandcode.repository.projection.VideoSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoRepositoryQuery {
    public Page<Video> filterVideos(VideoFilter videoFilter, Pageable pageable);
    public Page<VideoSummary> summarize(VideoFilter videoFilter, Pageable pageable);
    public Page<Video> findVideosByCategoryId(Integer id, VideoFilter articleFilter, Pageable pageable);
}