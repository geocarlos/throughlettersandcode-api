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
import com.throughlettersandcode.model.Article;
import com.throughlettersandcode.repository.ArticleRepository;

@RestController
@RequestMapping("/articles")
public class ArticleResource {
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_READ_ARTICLE') and #oauth2.hasScope('read')")
	public List<Article> listAll(){
		return articleRepository.findAll();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CREATE_ARTICLE') and #oauth2.hasScope('write')")
	public ResponseEntity<Article> create(@RequestBody Article article, HttpServletResponse response){
		Article savedArticle = articleRepository.save(article);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedArticle.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
	}

}
