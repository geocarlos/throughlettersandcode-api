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
import com.throughlettersandcode.model.User;
import com.throughlettersandcode.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserResource {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<User> listAll(){
		return userRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user, HttpServletResponse response){
		User savedUser = userRepository.save(user);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, Long.valueOf(savedUser.getId())));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}
}
