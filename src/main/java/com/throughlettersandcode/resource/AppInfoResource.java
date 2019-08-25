package com.throughlettersandcode.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import com.throughlettersandcode.model.AppInfo;
import com.throughlettersandcode.repository.AppInfoRepository;
import com.throughlettersandcode.service.PublicationService;

@RestController
@RequestMapping("/apps")
public class AppInfoResource {
	@Autowired
	private AppInfoRepository appInfoRepository;

	@Autowired
	private PublicationService publicationService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	// @PreAuthorize("hasAuthority('ROLE_READ_VIDEO') and #oauth2.hasScope('read')")
	public List<AppInfo> getApps(){
		return appInfoRepository.findAll();
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ROLE_READ_VIDEO') and #oauth2.hasScope('read')")
	public ResponseEntity<AppInfo> getById(@PathVariable Integer id){
		Optional<AppInfo> app = appInfoRepository.findById(id);
		return app.isPresent() ? ResponseEntity.ok(app.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CREATE_VIDEO') and #oauth2.hasScope('write')")
	public ResponseEntity<AppInfo> create(@RequestBody AppInfo app, HttpServletResponse response){
		AppInfo savedApp = appInfoRepository.save(app);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedApp.getId().longValue()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedApp);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CREATE_VIDEO')")
	public ResponseEntity<AppInfo> updateArticle(@PathVariable Integer id, @RequestBody AppInfo app) {
		try {
			AppInfo savedApp = publicationService.updateApp(id, app);
			return ResponseEntity.ok(savedApp);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_DELETE_VIDEO')")
	public void deleteVideo(@PathVariable Integer id){
		appInfoRepository.deleteById(id);
	}
}
