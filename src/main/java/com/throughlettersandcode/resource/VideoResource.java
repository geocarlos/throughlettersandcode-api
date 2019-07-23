package com.throughlettersandcode.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.throughlettersandcode.event.ResourceCreatedEvent;
import com.throughlettersandcode.model.Video;
import com.throughlettersandcode.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
public class VideoResource {
	@Autowired
	private VideoRepository videoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Video> listAll(){
		return videoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Video> create(@RequestBody Video video, HttpServletResponse response){
		Video savedVideo = videoRepository.save(video);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedVideo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedVideo);
	}
}
