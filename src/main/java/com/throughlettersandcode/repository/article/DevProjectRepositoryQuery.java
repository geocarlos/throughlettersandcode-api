package com.throughlettersandcode.repository.article;

import com.throughlettersandcode.model.DevProject;
import com.throughlettersandcode.repository.filter.DevProjectFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DevProjectRepositoryQuery {
    public Page<DevProject> filterProjects(DevProjectFilter projectFilter, Pageable pageable);
}