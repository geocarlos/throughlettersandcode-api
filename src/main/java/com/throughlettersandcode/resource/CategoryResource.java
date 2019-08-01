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
import com.throughlettersandcode.model.Category;
import com.throughlettersandcode.repository.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_READ_CATEGORY') and #oauth2.hasScope('read')")
	public List<Category> listAll(){
		return categoryRepository.findAll();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CREATE_CATEGORY') and #oauth2.hasScope('write')")
	public ResponseEntity<Category> create(@RequestBody Category category, HttpServletResponse response){
		Category savedCategory = categoryRepository.save(category);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, Long.valueOf(savedCategory.getId())));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	}
}
