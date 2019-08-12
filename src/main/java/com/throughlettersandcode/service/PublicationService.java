package com.throughlettersandcode.service;

import java.util.Optional;

import com.throughlettersandcode.model.AppInfo;
import com.throughlettersandcode.model.Article;
import com.throughlettersandcode.model.UserEntity;
import com.throughlettersandcode.model.Video;
import com.throughlettersandcode.repository.AppInfoRepository;
import com.throughlettersandcode.repository.ArticleRepository;
import com.throughlettersandcode.repository.UserRepository;
import com.throughlettersandcode.repository.VideoRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicationService{
    @Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	AppInfoRepository appInfoRepository;

	public Article save(Article article) {
		validateUser(article);

		return articleRepository.save(article);
	}

	public Video save(Video video) {
		validateUser(video);

		return videoRepository.save(video);
	}

	public Article updateArticle(Long id, Article article) {
		Article savedArticle = searchExistingArticle(id);
		if (!article.getAuthor().equals(savedArticle.getAuthor())) {
			validateUser(article);
		}

		BeanUtils.copyProperties(article, savedArticle, "id");

		return articleRepository.save(savedArticle);
	}

	public Video updateVideo(Long id, Video video) {
		Video savedVideo = searchExistingVideo(id);
		if (!video.getAuthor().equals(savedVideo.getAuthor())) {
			validateUser(video);
		}

		BeanUtils.copyProperties(video, savedVideo, "id");

		return videoRepository.save(savedVideo);
	}

	public AppInfo updateApp(Integer id, AppInfo app) {
		AppInfo savedApp = searchExistingApp(id);
		if (!app.getAuthor().equals(savedApp.getAuthor())) {
			validateUser(app);
		}

		BeanUtils.copyProperties(app, savedApp, "id");

		return appInfoRepository.save(savedApp);
	}

	
	private void validateUser(Object object) {
		Optional<UserEntity> author = null;
		
		if(object instanceof Article){
			Article article = (Article) object;
			if (article.getAuthor().getId() != null) {
				author = userRepository.findById(article.getAuthor().getId());
			}
		}
		
		if(object instanceof Video){
			Video video = (Video) object;
			if (video.getAuthor().getId() != null) {
				author = userRepository.findById(video.getAuthor().getId());
			}
		}
		
		if (author == null || !author.isPresent()) {
			throw new RuntimeException();
		}
	}
	
	private Article searchExistingArticle(Long id) {
		Optional<Article> savedArticle = articleRepository.findById(id);
		if (!savedArticle.isPresent()) {
			throw new IllegalArgumentException();
		}
		return savedArticle.get();
	}
	
	private Video searchExistingVideo(Long id) {
		Optional<Video> savedVideo = videoRepository.findById(id);
		if (!savedVideo.isPresent()) {
			throw new IllegalArgumentException();
		}
		return savedVideo.get();
	}

	private AppInfo searchExistingApp(Integer id) {
		Optional<AppInfo> savedApp = appInfoRepository.findById(id);
		if(!savedApp.isPresent()) {
			throw new IllegalArgumentException();
		}
		return savedApp.get();
	}
}