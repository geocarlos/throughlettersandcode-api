package com.throughlettersandcode.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.throughlettersandcode.model.Video;
import com.throughlettersandcode.repository.VideoRepository;
import com.throughlettersandcode.repository.filter.VideoFilter;
import com.throughlettersandcode.service.PublicationService;

@RestController
@RequestMapping("/videos")
public class VideoResource {
	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	private PublicationService publicationService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	// @PreAuthorize("hasAuthority('ROLE_READ_VIDEO') and #oauth2.hasScope('read')")
	public Page<Video> getVideos(VideoFilter videoFilter, Pageable pageable){
		return videoRepository.filterVideos(videoFilter, pageable);
	}

	@GetMapping("/categories/{id}")
	// @PreAuthorize("hasAuthority('ROLE_READ_VIDEO') and #oauth2.hasScope('read')")
	public Page<Video> getArticlesByCategory(@PathVariable Integer id, VideoFilter videoFilter, Pageable pageable){
		return videoRepository.findVideosByCategoryId(id, videoFilter, pageable);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ROLE_READ_VIDEO') and #oauth2.hasScope('read')")
	public ResponseEntity<Video> getById(@PathVariable Long id){
		Optional<Video> video = videoRepository.findById(id);
		return video.isPresent() ? ResponseEntity.ok(video.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CREATE_VIDEO') and #oauth2.hasScope('write')")
	public ResponseEntity<Video> create(@RequestBody Video video, HttpServletResponse response){
		Video savedVideo = videoRepository.save(video);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedVideo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedVideo);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CREATE_VIDEO')")
	public ResponseEntity<Video> updateArticle(@PathVariable Long id, @RequestBody Video video) {
		try {
			Video savedVideo = publicationService.updateVideo(id, video);
			return ResponseEntity.ok(savedVideo);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_DELETE_VIDEO')")
	public void deleteVideo(@PathVariable Long id){
		videoRepository.deleteById(id);
	}
}
