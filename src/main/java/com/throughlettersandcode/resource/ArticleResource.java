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
import com.throughlettersandcode.model.Article;
import com.throughlettersandcode.repository.ArticleRepository;
import com.throughlettersandcode.repository.filter.ArticleFilter;
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
	// @PreAuthorize("hasAuthority('ROLE_READ_ARTICLE') and #oauth2.hasScope('read')")
	public Page<Article> getArticles(ArticleFilter articleFilter, Pageable pageable){
		return articleRepository.filterArticles(articleFilter, pageable);
	}

	@GetMapping("/categories/{id}")
	// @PreAuthorize("hasAuthority('ROLE_READ_ARTICLE') and #oauth2.hasScope('read')")
	public Page<Article> getArticlesByCategory(@PathVariable Integer id, ArticleFilter articleFilter, Pageable pageable){
		return articleRepository.findArticlesByCategoryId(id, articleFilter, pageable);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ROLE_READ_ARTICLE') and #oauth2.hasScope('read')")
	public ResponseEntity<Article> getById(@PathVariable Long id){
		Optional<Article> article = articleRepository.findById(id);
		return article.isPresent() ? ResponseEntity.ok(article.get()) : ResponseEntity.notFound().build();
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

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_DELETE_ARTICLE')")
	public void deleteArticle(@PathVariable Long id){
		articleRepository.deleteById(id);
	}
}
