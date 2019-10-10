package com.throughlettersandcode.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.throughlettersandcode.event.ResourceCreatedEvent;
import com.throughlettersandcode.model.DevProject;
import com.throughlettersandcode.repository.DevProjectRepository;
import com.throughlettersandcode.repository.filter.DevProjectFilter;
import com.throughlettersandcode.service.PublicationService;

@RestController
@RequestMapping("/devprojects")
public class DevProjectResource {
	@Autowired
	private DevProjectRepository devProjectRepository;

	@Autowired
	private PublicationService publicationService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@CachePut(value = "devProjects")
	// @PreAuthorize("hasAuthority('ROLE_READ_VIDEO') and #oauth2.hasScope('read')")
	public Page<DevProject> getProjects(DevProjectFilter projectFilter, Pageable pageable){
		return devProjectRepository.filterProjects(projectFilter, pageable);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ROLE_READ_VIDEO') and #oauth2.hasScope('read')")
	public ResponseEntity<DevProject> getById(@PathVariable Integer id){
		Optional<DevProject> project = devProjectRepository.findById(id);
		return project.isPresent() ? ResponseEntity.ok(project.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CREATE_VIDEO') and #oauth2.hasScope('write')")
	public ResponseEntity<DevProject> create(@RequestBody DevProject project, HttpServletResponse response){
		DevProject savedProject = devProjectRepository.save(project);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedProject.getId().longValue()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CREATE_VIDEO')")
	public ResponseEntity<DevProject> updateArticle(@PathVariable Integer id, @RequestBody DevProject project) {
		try {
			DevProject savedProject = publicationService.updateProject(id, project);
			return ResponseEntity.ok(savedProject);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_DELETE_VIDEO')")
	public void deleteProject(@PathVariable Integer id){
		devProjectRepository.deleteById(id);
	}
}
