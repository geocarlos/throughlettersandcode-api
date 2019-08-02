package com.throughlettersandcode.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.throughlettersandcode.event.ResourceCreatedEvent;
import com.throughlettersandcode.model.Article;
import com.throughlettersandcode.repository.ArticleRepository;
import com.throughlettersandcode.service.PublicationService;

@RestController
@RequestMapping("/articles")
public class ArticleResource {
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PublicationService publicationService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_READ_ARTICLE') and #oauth2.hasScope('read')")
	public List<Article> listAll(){
		return articleRepository.findAll();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CREATE_ARTICLE') and #oauth2.hasScope('write')")
	public ResponseEntity<Article> create(@RequestBody Article article, HttpServletResponse response) {
		Article savedArticle = articleRepository.save(article);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedArticle.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CREATE_ARTICLE')")
	public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article article) {
		try {
			Article savedArticle = publicationService.updateArticle(id, article);
			return ResponseEntity.ok(savedArticle);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	} 
}
