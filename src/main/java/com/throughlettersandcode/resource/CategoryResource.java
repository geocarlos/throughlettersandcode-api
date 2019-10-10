package com.throughlettersandcode.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.throughlettersandcode.event.ResourceCreatedEvent;
import com.throughlettersandcode.model.Category;
import com.throughlettersandcode.repository.CategoryRepository;
import com.throughlettersandcode.repository.filter.CategoryFilter;

@RestController
@CachePut(value = "categories")
@RequestMapping("/categories")
public class CategoryResource {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	// @PreAuthorize("hasAuthority('ROLE_READ_CATEGORY') and #oauth2.hasScope('read')")
	public List<Category> listCategories(CategoryFilter categoryFilter){
		return categoryRepository.filterCategories(categoryFilter);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CREATE_CATEGORY') and #oauth2.hasScope('write')")
	public ResponseEntity<Category> create(@RequestBody Category category, HttpServletResponse response){
		Category savedCategory = categoryRepository.save(category);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, Long.valueOf(savedCategory.getId())));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_DELETE_CATEGORY')")
	public void deleteCategory(@PathVariable Integer id){
		categoryRepository.deleteById(id);
	}
}
