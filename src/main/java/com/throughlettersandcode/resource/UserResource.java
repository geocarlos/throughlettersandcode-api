package com.throughlettersandcode.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.throughlettersandcode.event.ResourceCreatedEvent;
import com.throughlettersandcode.model.UserEntity;
import com.throughlettersandcode.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserResource {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_READ_USER') and #oauth2.hasScope('read')")
	public List<UserEntity> listAll(){
		return userRepository.findAll();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CREATE_USER') and #oauth2.hasScope('write')")
	public ResponseEntity<UserEntity> create(@RequestBody UserEntity user, HttpServletResponse response){
		UserEntity savedUser = userRepository.save(user);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, Long.valueOf(savedUser.getId())));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}
}
